/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.manager.Translation;
import com.liferay.translation.manager.TranslationManager;
import com.liferay.translation.url.provider.TranslationURLProvider;
import com.liferay.translation.web.internal.display.context.ImportTranslationResultsDisplayContext;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 * @author Roberto Díaz
 */
@Component(
	property = {
		"jakarta.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/import_translation"
	},
	service = MVCActionCommand.class
)
public class ImportTranslationMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			_checkExceededSizeLimit(uploadPortletRequest);

			_checkContentType(uploadPortletRequest.getContentType("file"));

			TranslationRequestHelper translationRequestHelper =
				new TranslationRequestHelper(
					_infoItemServiceRegistry, actionRequest,
					_segmentsExperienceLocalService);
			List<Map<String, String>> failureMessages = new LinkedList<>();
			List<String> successMessages = new ArrayList<>();
			String fileName = uploadPortletRequest.getFileName("file");

			_processUploadedFiles(
				actionRequest, uploadPortletRequest,
				translationRequestHelper.getGroupId(),
				translationRequestHelper.getModelClassName(),
				translationRequestHelper.getModelClassPK(), successMessages,
				failureMessages, themeDisplay.getLocale());

			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			if (Validator.isNotNull(portletResource)) {
				hideDefaultSuccessMessage(actionRequest);

				MultiSessionMessages.add(
					actionRequest, portletResource + "requestProcessed");
			}

			String title = ParamUtil.getString(actionRequest, "title");

			actionRequest.setAttribute(
				WebKeys.REDIRECT,
				PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(actionResponse)
				).setMVCRenderCommandName(
					"/translation/import_translation_results"
				).setRedirect(
					ParamUtil.getString(actionRequest, "redirect")
				).setParameter(
					"classNameId", translationRequestHelper.getClassNameId()
				).setParameter(
					"classPK", translationRequestHelper.getModelClassPK()
				).setParameter(
					"fileName", fileName
				).setParameter(
					"groupId", translationRequestHelper.getGroupId()
				).setParameter(
					"title", title
				).buildString());

			HttpServletRequest httpServletRequest =
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(actionRequest));

			HttpSession httpSession = httpServletRequest.getSession();

			int workflowAction = ParamUtil.getInteger(
				actionRequest, "workflowAction",
				WorkflowConstants.ACTION_PUBLISH);

			httpSession.setAttribute(
				ImportTranslationResultsDisplayContext.class.getName(),
				new ImportTranslationResultsDisplayContext(
					translationRequestHelper.getClassNameId(),
					translationRequestHelper.getModelClassPK(),
					themeDisplay.getCompanyId(),
					translationRequestHelper.getGroupId(), failureMessages,
					fileName, successMessages, title, workflowAction,
					_workflowDefinitionLinkLocalService));
		}
		catch (Exception exception) {
			try {
				SessionErrors.add(
					actionRequest, exception.getClass(), exception);

				actionResponse.sendRedirect(
					PortletURLBuilder.create(
						_translationURLProvider.getImportTranslationURL(
							ParamUtil.getLong(actionRequest, "groupId"),
							ParamUtil.getLong(actionRequest, "classNameId"),
							ParamUtil.getLong(actionRequest, "classPK"),
							RequestBackedPortletURLFactoryUtil.create(
								actionRequest))
					).setRedirect(
						ParamUtil.getString(actionRequest, "redirect")
					).buildString());

				if (exception instanceof XLIFFFileException) {
					hideDefaultErrorMessage(actionRequest);
				}
			}
			catch (PortalException portalException) {
				throw new IOException(portalException);
			}
		}
	}

	private void _checkContentType(String contentType)
		throws XLIFFFileException {

		if (!Objects.equals(ContentTypes.APPLICATION_ZIP, contentType) &&
			!Objects.equals(contentType, "application/x-xliff+xml") &&
			!Objects.equals(contentType, "application/xliff+xml")) {

			throw new XLIFFFileException.MustBeValid(
				"Unsupported content type: " + contentType);
		}
	}

	private void _checkExceededSizeLimit(HttpServletRequest httpServletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)httpServletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable throwable = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(throwable);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(throwable);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(throwable);
			}

			throw new PortalException(throwable);
		}
	}

	private void _processUploadedFiles(
			ActionRequest actionRequest,
			UploadPortletRequest uploadPortletRequest, long groupId,
			String className, long classPK, List<String> successMessages,
			List<Map<String, String>> failureMessages, Locale locale)
		throws IOException, PortalException {

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		for (Map.Entry<String, FileItem[]> entry :
				multipartParameterMap.entrySet()) {

			for (FileItem fileItem : entry.getValue()) {
				_translationManager.processXLIFFTranslation(
					groupId, className, classPK,
					new Translation(
						() -> MimeTypesUtil.getContentType(
							fileItem.getFileName()),
						fileItem.getFileName(), fileItem::getInputStream),
					successMessages, failureMessages, locale,
					ServiceContextFactory.getInstance(actionRequest));
			}
		}
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private TranslationManager _translationManager;

	@Reference
	private TranslationURLProvider _translationURLProvider;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}