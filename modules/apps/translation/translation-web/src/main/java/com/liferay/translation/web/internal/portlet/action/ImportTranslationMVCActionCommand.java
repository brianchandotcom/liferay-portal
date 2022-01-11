/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.service.TranslationEntryService;
import com.liferay.translation.snapshot.TranslationSnapshot;
import com.liferay.translation.snapshot.TranslationSnapshotProvider;
import com.liferay.translation.url.provider.TranslationURLProvider;
import com.liferay.translation.web.internal.display.context.ImportTranslationResultsDisplayContext;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
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
					_infoItemServiceTracker, actionRequest);
			Map<String, String> failureMessages = new HashMap<>();
			List<String> successMessages = new ArrayList<>();
			String fileName = uploadPortletRequest.getFileName("file");

			_processUploadedFile(
				actionRequest, uploadPortletRequest,
				translationRequestHelper.getGroupId(),
				translationRequestHelper.getModelClassName(),
				translationRequestHelper.getModelClassPK(), fileName,
				successMessages, failureMessages, themeDisplay.getLocale());

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

	private static String _getMustHaveValidIdMessage(String className) {
		if (className.equals(Layout.class.getName())) {
			return "the-translation-file-does-not-correspond-to-this-page";
		}

		return "the-translation-file-does-not-correspond-to-this-web-content";
	}

	private void _checkContentType(String contentType)
		throws XLIFFFileException {

		if (!Objects.equals(ContentTypes.APPLICATION_ZIP, contentType) &&
			!Objects.equals("application/x-xliff+xml", contentType) &&
			!Objects.equals("application/xliff+xml", contentType)) {

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

	private InfoItemReference _getInfoItemReference(
		String className, long classPK) {

		if (classPK == 0) {
			return null;
		}

		return new InfoItemReference(className, classPK);
	}

	private void _importXLIFFFile(
			ActionRequest actionRequest, long groupId, String className,
			long classPK, InputStream inputStream)
		throws IOException, PortalException {

		TranslationSnapshot translationSnapshot =
			_translationSnapshotProvider.getTranslationSnapshot(
				groupId, _getInfoItemReference(className, classPK),
				inputStream);

		InfoItemFieldValues infoItemFieldValues =
			translationSnapshot.getInfoItemFieldValues();

		_translationEntryService.addOrUpdateTranslationEntry(
			groupId,
			_language.getLanguageId(translationSnapshot.getTargetLocale()),
			infoItemFieldValues.getInfoItemReference(), infoItemFieldValues,
			ServiceContextFactory.getInstance(actionRequest));
	}

	private void _processUploadedFile(
			ActionRequest actionRequest,
			UploadPortletRequest uploadPortletRequest, long groupId,
			String className, long classPK, String fileName,
			List<String> successMessages, Map<String, String> failureMessages,
			Locale locale)
		throws IOException, PortalException {

		if (Objects.equals(
				uploadPortletRequest.getContentType("file"),
				ContentTypes.APPLICATION_ZIP)) {

			try (InputStream inputStream1 =
					uploadPortletRequest.getFileAsStream("file")) {

				ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
					inputStream1);

				try {
					for (String entry : zipReader.getEntries()) {
						try (InputStream inputStream2 =
								zipReader.getEntryAsInputStream(entry)) {

							_processXLIFFFile(
								actionRequest, groupId, className, classPK,
								entry, successMessages, failureMessages,
								inputStream2, locale);
						}
					}
				}
				finally {
					zipReader.close();
				}
			}
		}
		else {
			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file")) {

				_processXLIFFFile(
					actionRequest, groupId, className, classPK, fileName,
					successMessages, failureMessages, inputStream, locale);
			}
		}
	}

	private void _processXLIFFFile(
			ActionRequest actionRequest, long groupId, String className,
			long classPK, String fileName, List<String> successMessages,
			Map<String, String> failureMessages, InputStream inputStream,
			Locale locale)
		throws IOException, PortalException {

		try {
			_importXLIFFFile(
				actionRequest, groupId, className, classPK, inputStream);

			successMessages.add(fileName);
		}
		catch (XLIFFFileException xliffFileException) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				locale, getClass());

			Function<String, String> exceptionMessageFunction =
				_exceptionMessageFunctions.get(xliffFileException.getClass());

			failureMessages.put(
				fileName,
				_language.get(
					resourceBundle, exceptionMessageFunction.apply(className)));
		}
	}

	private static final Map
		<Class<? extends Exception>, Function<String, String>>
			_exceptionMessageFunctions =
				HashMapBuilder.
					<Class<? extends Exception>, Function<String, String>>put(
						XLIFFFileException.MustBeSupportedLanguage.class,
						s ->
							"the-xliff-file-has-an-unavailable-language-" +
								"translation"
					).put(
						XLIFFFileException.MustBeValid.class,
						s -> "the-file-is-an-invalid-xliff-file"
					).put(
						XLIFFFileException.MustBeWellFormed.class,
						s -> "the-xliff-file-does-not-have-all-needed-fields"
					).put(
						XLIFFFileException.MustHaveCorrectEncoding.class,
						s ->
							"the-translation-file-has-an-incorrect-encoding." +
								"the-supported-encoding-format-is-utf-8"
					).put(
						XLIFFFileException.MustHaveValidId.class,
						ImportTranslationMVCActionCommand::
							_getMustHaveValidIdMessage
					).put(
						XLIFFFileException.MustHaveValidParameter.class,
						s -> "the-xliff-file-has-invalid-parameters"
					).put(
						XLIFFFileException.MustNotHaveMoreThanOne.class,
						s -> "the-xliff-file-is-invalid"
					).build();

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationEntryService _translationEntryService;

	@Reference
	private TranslationSnapshotProvider _translationSnapshotProvider;

	@Reference
	private TranslationURLProvider _translationURLProvider;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}