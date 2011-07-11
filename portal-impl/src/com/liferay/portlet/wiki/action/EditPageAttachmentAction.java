/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class EditPageAttachmentAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleAttachments(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempAttachment(actionRequest, actionResponse);
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
			if (e instanceof DuplicateFileException ||
				e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_page_attachment"));
	}

	protected void addAttachment(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		int numOfFiles = ParamUtil.getInteger(actionRequest, "numOfFiles");

		List<ObjectValuePair<String, byte[]>> files =
			new ArrayList<ObjectValuePair<String, byte[]>>();

		if (numOfFiles == 0) {
			File file = uploadRequest.getFile("file");
			String fileName = uploadRequest.getFileName("file");

			if (file != null) {
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					ObjectValuePair<String, byte[]> ovp =
						new ObjectValuePair<String, byte[]>(fileName, bytes);

					files.add(ovp);
				}
			}
		}
		else {
			for (int i = 1; i <= numOfFiles; i++) {
				File file = uploadRequest.getFile("file" + i);

				String fileName = uploadRequest.getFileName("file" + i);

				if (file != null) {
					byte[] bytes = FileUtil.getBytes(file);

					if ((bytes != null) && (bytes.length > 0)) {
						ObjectValuePair<String, byte[]> ovp =
							new ObjectValuePair<String, byte[]>(
								fileName, bytes);

						files.add(ovp);
					}
				}
			}
		}

		WikiPageServiceUtil.addPageAttachments(nodeId, title, files);
	}

	protected void addMultipleAttachments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName");

		List<String> validUpdateFileEntries = new ArrayList<String>();
		List<KeyValuePair> invalidUpdateFileEntries =
			new ArrayList<KeyValuePair>();

		for (String selectedFileName : selectedFileNames) {
			File file = TempFileUtil.getTempFile(
				themeDisplay.getUserId(), selectedFileName, _TEMP_PATHNAME);

			if (file != null) {
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					try {
						WikiPageServiceUtil.addPageAttachment(
							nodeId, title, selectedFileName, bytes);

						validUpdateFileEntries.add(selectedFileName);
					}
					catch (Exception e) {
						String errorMessage = "an-unexpected-error-occurred-" +
							"while-uploading-your-file";

					if (e instanceof DuplicateFileException) {
						errorMessage = LanguageUtil.get(
							themeDisplay.getLocale(),
							"the-folder-you-selected-already-has-an-entry-" +
								"with-this-name.-please-select-a-different-" +
								"folder");
					}
					else if (e instanceof FileExtensionException) {
						errorMessage = LanguageUtil.format(
							themeDisplay.getLocale(),
							"please-enter-a-file-with-a-valid-extension-x",
							StringUtil.merge(
								PrefsPropsUtil.getStringArray(
									PropsKeys.DL_FILE_EXTENSIONS,
									StringPool.COMMA)));
					}
					else if (e instanceof FileNameException) {
						errorMessage = LanguageUtil.get(
							themeDisplay.getLocale(),
							"please-enter-a-file-with-a-valid-file-name");
					}
					else if (e instanceof FileSizeException) {
						long maxSizeMB = PrefsPropsUtil.getLong(
							PropsKeys.DL_FILE_MAX_SIZE)	/ 1024 / 1024;

						errorMessage = LanguageUtil.format(
							themeDisplay.getLocale(),
							"file-size-is-larger-than-x-megabytes",	maxSizeMB);
					}

					invalidUpdateFileEntries.add(new KeyValuePair(
						selectedFileName, errorMessage));
					}
					finally {
						FileUtil.delete(file);
					}
				}
			}
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String validUpdateFileEntry : validUpdateFileEntries) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("fileName", validUpdateFileEntry);
			jsonObject.put("added", Boolean.TRUE);

			jsonArray.put(jsonObject);
		}

		for (KeyValuePair invalidUpdateFileEntry : invalidUpdateFileEntries) {
			String fileName = invalidUpdateFileEntry.getKey();
			String errorMessage = invalidUpdateFileEntry.getValue();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("fileName", fileName);
			jsonObject.put("added", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);

			jsonArray.put(jsonObject);
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response =
			PortalUtil.getHttpServletResponse(actionResponse);
		InputStream is = new UnsyncByteArrayInputStream(
			jsonArray.toString().getBytes());
		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		ServletResponseUtil.sendFile(
			request, response, null, is, contentType);
	}

	protected void addTempAttachment(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long nodeId = ParamUtil.getLong(uploadRequest, "nodeId");

		File file = uploadRequest.getFile("file");
		String sourceFileName = uploadRequest.getFileName("file");

		WikiPageServiceUtil.addTempPageAttachment(
			nodeId, sourceFileName, _TEMP_PATHNAME, file);
	}

	protected void deleteAttachment(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String attachment = ParamUtil.getString(actionRequest, "fileName");

		WikiPageServiceUtil.deletePageAttachment(nodeId, title, attachment);
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
				nodeId, fileName, _TEMP_PATHNAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response =
			PortalUtil.getHttpServletResponse(actionResponse);
		InputStream is = new UnsyncByteArrayInputStream(
			jsonObject.toString().getBytes());
		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		ServletResponseUtil.sendFile(
			request, response, null, is, contentType);
	}

	private static final String _TEMP_PATHNAME = "document_temp_upload";

}