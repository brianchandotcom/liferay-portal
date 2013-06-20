/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiPageAttachmentsUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class EditPageAttachmentsAction extends EditFileEntryAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Validator.isNull(cmd)) {
				UploadException uploadException =
					(UploadException)actionRequest.getAttribute(
						WebKeys.UPLOAD_EXCEPTION);

				if (uploadException != null) {
					if (uploadException.isExceededSizeLimit()) {
						throw new FileSizeException(uploadException.getCause());
					}

					throw new PortalException(uploadException.getCause());
				}
			}
			else if (cmd.equals(Constants.ADD)) {
				addAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleFileEntries(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_FROM_TRASH)) {
				restoreAttachmentFromTrash(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteAttachment(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreAttachment(actionRequest);
			}

			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof FileNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				if (e instanceof DuplicateFileException) {
					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}
				else {
					response.setStatus(
						ServletResponseConstants.SC_FILE_NAME_EXCEPTION);
				}
			}
			else if (e instanceof FileExtensionException ||
					 e instanceof FileSizeException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_page_attachment"));
	}

	protected void addAttachment(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		int numOfFiles = ParamUtil.getInteger(actionRequest, "numOfFiles");

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>();

		try {
			if (numOfFiles == 0) {
				String fileName = uploadPortletRequest.getFileName("file");
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file");

				if (inputStream != null) {
					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<String, InputStream>(
							fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}
			else {
				for (int i = 1; i <= numOfFiles; i++) {
					String fileName = uploadPortletRequest.getFileName(
						"file" + i);
					InputStream inputStream =
						uploadPortletRequest.getFileAsStream("file" + i);

					if (inputStream == null) {
						continue;
					}

					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<String, InputStream>(
							fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}

			WikiPageServiceUtil.addPageAttachments(
				nodeId, title, inputStreamOVPs);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	@Override
	protected void addMultipleFileEntries(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String selectedFileName, List<String> validFileNames,
			List<KeyValuePair> invalidFileNameKVPs)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		FileEntry tempFileEntry = null;

		try {
			tempFileEntry = TempFileUtil.getTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				selectedFileName, _TEMP_FOLDER_NAME);

			InputStream inputStream = tempFileEntry.getContentStream();
			String mimeType = tempFileEntry.getMimeType();

			WikiPageServiceUtil.addPageAttachment(
				nodeId, title, selectedFileName, inputStream, mimeType);

			validFileNames.add(selectedFileName);
		}
		catch (Exception e) {
			String errorMessage = getAddMultipleFileEntriesErrorMessage(
				themeDisplay, e);

			KeyValuePair invalidFileNameKVP = new KeyValuePair(
				selectedFileName, errorMessage);

			invalidFileNameKVPs.add(invalidFileNameKVP);
		}
		finally {
			if (tempFileEntry != null) {
				TempFileUtil.deleteTempFile(tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void addTempAttachment(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String mimeType = uploadPortletRequest.getContentType("file");

			WikiPageServiceUtil.addTempPageAttachment(
				nodeId, sourceFileName, _TEMP_FOLDER_NAME, inputStream,
				mimeType);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void deleteAttachment(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String attachment = ParamUtil.getString(actionRequest, "fileName");

		long dlFileEntryId = 0;

		if (moveToTrash) {
			dlFileEntryId = WikiPageServiceUtil.movePageAttachmentToTrash(
				nodeId, title, attachment);
		}
		else {
			WikiPageServiceUtil.deletePageAttachment(nodeId, title, attachment);
		}

		if (moveToTrash && (dlFileEntryId > 0)) {
			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreEntryIds",
				new String[] {String.valueOf(dlFileEntryId)});

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	protected void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			WikiPageServiceUtil.deleteTempPageAttachment(
				nodeId, fileName, _TEMP_FOLDER_NAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void emptyTrash(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		WikiPageServiceUtil.deleteTrashPageAttachments(nodeId, title);
	}

	protected void restoreAttachment(ActionRequest actionRequest)
		throws Exception {

		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				restoreEntryId);

			WikiPage page = WikiPageAttachmentsUtil.getPage(
				fileEntry.getFileEntryId());

			WikiPageServiceUtil.restorePageAttachmentFromTrash(
				page.getNodeId(), page.getTitle(), fileEntry.getTitle());
		}
	}

	protected void restoreAttachmentFromTrash(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject =
			com.liferay.portlet.trash.action.ActionUtil.checkEntry(
				actionRequest);

		writeJSON(actionRequest, actionResponse, jsonObject);

		WikiPageServiceUtil.restorePageAttachmentFromTrash(
			nodeId, title, fileName);
	}

	private static final String _TEMP_FOLDER_NAME =
		EditPageAttachmentsAction.class.getName();

}