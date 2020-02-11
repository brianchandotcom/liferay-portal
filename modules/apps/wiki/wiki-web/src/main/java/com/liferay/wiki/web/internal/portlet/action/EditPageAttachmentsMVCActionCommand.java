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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.dynamic.data.mapping.kernel.StorageFieldRequiredException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.web.internal.WikiAttachmentsHelper;
import com.liferay.wiki.web.internal.upload.TempAttachmentWikiUploadFileEntryHandler;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/edit_page_attachment"
	},
	service = MVCActionCommand.class
)
public class EditPageAttachmentsMVCActionCommand extends BaseMVCActionCommand {

	@Reference(unbind = "-")
	public void setWikiAttachmentsHelper(
		WikiAttachmentsHelper wikiAttachmentsHelper) {

		_wikiAttachmentsHelper = wikiAttachmentsHelper;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	protected void addTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_tempAttachmentWikiUploadFileEntryHandler, _uploadResponseHandler,
			actionRequest, actionResponse);
	}

	protected void deleteAttachment(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		TrashedModel trashedModel = _wikiAttachmentsHelper.deleteAttachment(
			actionRequest, moveToTrash);

		if (moveToTrash && (trashedModel != null)) {
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				Constants.CMD, Constants.REMOVE
			).put(
				"trashedModels", ListUtil.fromArray(trashedModel)
			).build();

			addDeleteSuccessData(actionRequest, data);
		}
	}

	protected void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(uploadPortletRequest, "nodeId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_wikiPageService.deleteTempFileEntry(
				nodeId, WikiConstants.TEMP_FOLDER_NAME, fileName);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception exception) {
			jsonObject.put("deleted", Boolean.FALSE);

			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("errorMessage", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		PortletConfig portletConfig = getPortletConfig(actionRequest);

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(actionRequest, false);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				_wikiAttachmentsHelper.emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteAttachment(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				_wikiAttachmentsHelper.restoreEntries(actionRequest);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (NoSuchNodeException | NoSuchPageException | PrincipalException
					exception) {

			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/wiki/error.jsp");
		}
		catch (Exception exception) {
			handleUploadException(
				portletConfig, actionRequest, actionResponse, cmd, exception);
		}
	}

	/**
	 * TODO: Remove. This should extend from EditFileEntryAction once it is
	 * modularized.
	 */
	protected String[] getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		return _dlConfiguration.fileExtensions();
	}

	/**
	 * TODO: Remove. This should extend from EditFileEntryAction once it is
	 * modularized.
	 */
	protected void handleUploadException(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd, Exception exception)
		throws Exception {

		if (exception instanceof AssetCategoryException ||
			exception instanceof AssetTagException) {

			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}
		else if (exception instanceof AntivirusScannerException ||
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

			if (!cmd.equals(Constants.ADD_DYNAMIC) &&
				!cmd.equals(Constants.ADD_MULTIPLE) &&
				!cmd.equals(Constants.ADD_TEMP)) {

				if (exception instanceof AntivirusScannerException) {
					SessionErrors.add(
						actionRequest, exception.getClass(), exception);
				}
				else {
					SessionErrors.add(actionRequest, exception.getClass());
				}

				return;
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				hideDefaultErrorMessage(actionRequest);
			}

			if (exception instanceof AntivirusScannerException ||
				exception instanceof DuplicateFileEntryException ||
				exception instanceof FileExtensionException ||
				exception instanceof FileNameException ||
				exception instanceof FileSizeException ||
				exception instanceof UploadRequestSizeException) {

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(actionResponse);

				httpServletResponse.setContentType(ContentTypes.TEXT_HTML);
				httpServletResponse.setStatus(HttpServletResponse.SC_OK);

				String errorMessage = StringPool.BLANK;
				int errorType = 0;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (exception instanceof AntivirusScannerException) {
					AntivirusScannerException antivirusScannerException =
						(AntivirusScannerException)exception;

					errorMessage = themeDisplay.translate(
						antivirusScannerException.getMessageKey());

					errorType =
						ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				}

				if (exception instanceof DuplicateFileEntryException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-unique-document-name");
					errorType =
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
				}
				else if (exception instanceof FileExtensionException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-extension-x",
						StringUtil.merge(
							getAllowedFileExtensions(
								portletConfig, actionRequest, actionResponse)));
					errorType =
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
				}
				else if (exception instanceof FileNameException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-name");
					errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
				}
				else if (exception instanceof FileSizeException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-size-no-" +
							"larger-than-x",
						LanguageUtil.formatStorageSize(
							_dlValidator.getMaxAllowableSize(),
							themeDisplay.getLocale()));
					errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
				}

				JSONObject jsonObject = JSONUtil.put(
					"message", errorMessage
				).put(
					"status", errorType
				);

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
				"mvcPath", "/html/porltet/document_library/error.jsp");
		}
		else {
			Throwable cause = exception.getCause();

			if (cause instanceof DuplicateFileEntryException) {
				SessionErrors.add(
					actionRequest, DuplicateFileEntryException.class);
			}
			else {
				throw exception;
			}
		}
	}

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private Portal _portal;

	@Reference
	private TempAttachmentWikiUploadFileEntryHandler
		_tempAttachmentWikiUploadFileEntryHandler;

	@Reference
	private UploadHandler _uploadHandler;

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _uploadResponseHandler;

	private WikiAttachmentsHelper _wikiAttachmentsHelper;

	@Reference
	private WikiPageService _wikiPageService;

}