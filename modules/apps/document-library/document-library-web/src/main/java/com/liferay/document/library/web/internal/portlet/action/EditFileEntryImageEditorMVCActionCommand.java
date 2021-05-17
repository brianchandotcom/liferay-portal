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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.exception.DLStorageQuotaExceededException;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileEntryLockException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry_image_editor"
	},
	service = MVCActionCommand.class
)
public class EditFileEntryImageEditorMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
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

			_updateFileEntry(
				actionRequest, actionResponse,
				_portal.getUploadPortletRequest(actionRequest));

			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			if (Validator.isNotNull(portletResource)) {
				hideDefaultSuccessMessage(actionRequest);

				MultiSessionMessages.add(
					actionRequest, portletResource + "requestProcessed");
			}
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		catch (Exception exception) {
			_handleUploadException(actionRequest, actionResponse, exception);
		}
	}

	private ServiceContext _createServiceContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (!cmd.equals(Constants.ADD_DYNAMIC)) {
			return ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), httpServletRequest);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		Group group = themeDisplay.getScopeGroup();

		if (layout.isPublicLayout() ||
			(layout.isTypeControlPanel() && !group.hasPrivateLayouts())) {

			return ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), httpServletRequest);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), httpServletRequest);

		ModelPermissions modelPermissions =
			serviceContext.getModelPermissions();

		serviceContext.setModelPermissions(
			ModelPermissionsFactory.create(
				modelPermissions.getActionIds(
					RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE),
				_RESTRICTED_GUEST_PERMISSIONS, DLFileEntry.class.getName()));

		return serviceContext;
	}

	private HttpServletRequest _getDDMStructureHttpServletRequest(
		HttpServletRequest httpServletRequest, long structureId) {

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			httpServletRequest, new HashMap<>());

		String namespace = structureId + StringPool.UNDERLINE;

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			if (StringUtil.startsWith(parameterName, namespace)) {
				dynamicServletRequest.setParameterValues(
					parameterName.substring(namespace.length()),
					entry.getValue());
			}
		}

		return dynamicServletRequest;
	}

	private void _handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Exception exception)
		throws Exception {

		if (exception instanceof AssetCategoryException ||
			exception instanceof AssetTagException) {

			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}
		else if (exception instanceof AntivirusScannerException ||
				 exception instanceof DLStorageQuotaExceededException ||
				 exception instanceof DuplicateFileEntryException ||
				 exception instanceof DuplicateFolderNameException ||
				 exception instanceof FileExtensionException ||
				 exception instanceof FileMimeTypeException ||
				 exception instanceof FileNameException ||
				 exception instanceof FileSizeException ||
				 exception instanceof LiferayFileItemException ||
				 exception instanceof NoSuchFolderException ||
				 exception instanceof SourceFileNameException ||
				 exception instanceof StorageFieldRequiredException ||
				 exception instanceof UploadRequestSizeException) {

			if (exception instanceof AntivirusScannerException ||
				exception instanceof DLStorageQuotaExceededException ||
				exception instanceof DuplicateFileEntryException ||
				exception instanceof FileExtensionException ||
				exception instanceof FileNameException ||
				exception instanceof FileSizeException ||
				exception instanceof UploadRequestSizeException) {

				JSONObject jsonObject =
					_multipleUploadResponseHandler.onFailure(
						actionRequest, (PortalException)exception);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}

			if (exception instanceof AntivirusScannerException) {
				SessionErrors.add(
					actionRequest, exception.getClass(), exception);
			}
			else {
				SessionErrors.add(actionRequest, exception.getClass());
			}
		}
		else if (exception instanceof DuplicateLockException ||
				 exception instanceof FileEntryLockException.MustOwnLock ||
				 exception instanceof InvalidFileVersionException ||
				 exception instanceof NoSuchFileEntryException ||
				 exception instanceof PrincipalException) {

			if (exception instanceof DuplicateLockException) {
				DuplicateLockException duplicateLockException =
					(DuplicateLockException)exception;

				SessionErrors.add(
					actionRequest, duplicateLockException.getClass(),
					duplicateLockException.getLock());
			}
			else {
				SessionErrors.add(actionRequest, exception.getClass());
			}

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		else {
			Throwable throwable = exception.getCause();

			if (throwable instanceof DuplicateFileEntryException) {
				SessionErrors.add(
					actionRequest, DuplicateFileEntryException.class);
			}
			else {
				throw exception;
			}
		}
	}

	private void _setUpDDMFormValues(ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1);

		if (fileEntryTypeId == -1) {
			return;
		}

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.getDLFileEntryType(fileEntryTypeId);

		for (DDMStructure ddmStructure : dlFileEntryType.getDDMStructures()) {
			String className =
				com.liferay.dynamic.data.mapping.kernel.DDMFormValues.class.
					getName();

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				_getDDMStructureHttpServletRequest(
					serviceContext.getRequest(), ddmStructure.getStructureId()),
				_ddmBeanTranslator.translate(ddmStructure.getDDMForm()));

			serviceContext.setAttribute(
				className + StringPool.POUND + ddmStructure.getStructureId(),
				_ddmBeanTranslator.translate(ddmFormValues));
		}
	}

	private FileEntry _updateFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse,
			UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		String changeLog = ParamUtil.getString(
			uploadPortletRequest, "changeLog");

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"imageBlob")) {

			long size = uploadPortletRequest.getSize("imageBlob");

			String contentType = ParamUtil.getString(
				uploadPortletRequest, "contentType");

			ServiceContext serviceContext = _createServiceContext(
				uploadPortletRequest);

			_setUpDDMFormValues(serviceContext);

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			fileEntry = _dlAppService.updateFileEntry(
				fileEntryId, fileEntry.getFileName(), contentType,
				fileEntry.getTitle(), fileEntry.getDescription(), changeLog,
				DLVersionNumberIncrease.AUTOMATIC, inputStream, size, null,
				null, serviceContext);

			_assetDisplayPageEntryFormProcessor.process(
				FileEntry.class.getName(), fileEntry.getFileEntryId(),
				actionRequest);

			JSONObject jsonObject = JSONUtil.put("success", Boolean.TRUE);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				themeDisplay.getLocale(),
				EditFileEntryImageEditorMVCActionCommand.class);

			SessionMessages.add(
				actionRequest, "requestProcessed",
				LanguageUtil.get(
					resourceBundle, "the-image-was-edited-successfully"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);

			return fileEntry;
		}
	}

	private static final String[] _RESTRICTED_GUEST_PERMISSIONS = {};

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _multipleUploadResponseHandler;

	@Reference
	private Portal _portal;

}