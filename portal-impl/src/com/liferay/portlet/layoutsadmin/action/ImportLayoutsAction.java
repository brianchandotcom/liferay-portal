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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARFileSizeException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.MissingReferenceException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.ExportImportHelper;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ImportLayoutsAction extends EditFileEntryAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest, actionResponse);

				validateImportLayoutsFile(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.IMPORT)) {
				importLayouts(actionRequest, actionResponse);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				handleUploadException(
					portletConfig, actionRequest, actionResponse, cmd, e);
			}
			else {
				if ((e instanceof LARFileException) ||
					(e instanceof LARFileSizeException) ||
					(e instanceof LARTypeException)) {

					SessionErrors.add(actionRequest, e.getClass());
				}
				else if ((e instanceof LayoutPrototypeException) ||
						 (e instanceof LocaleException)) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					_log.error(e, e);

					SessionErrors.add(
						actionRequest, LayoutImportException.class.getName());
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.import_layouts"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/layouts_admin/import_layouts_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void addTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		checkExceededSizeLimit(uploadPortletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		deleteTempFileEntry(themeDisplay.getScopeGroupId());

		InputStream inputStream = null;

		try {
			String sourceFileName = uploadPortletRequest.getFileName("file");

			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			LayoutServiceUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), sourceFileName,
				ExportImportHelper.TEMP_FOLDER_NAME, inputStream, contentType);
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) &&
				(uploadException.getCause()
					instanceof FileUploadBase.IOFileUploadException)) {

				// Cancelled a temporary upload

			}
			else if ((uploadException != null) &&
					 uploadException.isExceededSizeLimit()) {

				throw new FileSizeException(uploadException.getCause());
			}
			else {
				throw e;
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void checkExceededSizeLimit(HttpServletRequest request)
		throws PortalException {

		UploadException uploadException = (UploadException)request.getAttribute(
			WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededSizeLimit()) {
				throw new LARFileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}
	}

	@Override
	protected void deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			String fileName = ParamUtil.getString(actionRequest, "fileName");

			LayoutServiceUtil.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), fileName,
				ExportImportHelper.TEMP_FOLDER_NAME);

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

	protected void deleteTempFileEntry(long groupId)
		throws PortalException, SystemException {

		String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(
			groupId, ExportImportHelper.TEMP_FOLDER_NAME);

		for (String tempFileEntryName : tempFileEntryNames) {
			LayoutServiceUtil.deleteTempFileEntry(
				groupId, tempFileEntryName,
				ExportImportHelper.TEMP_FOLDER_NAME);
		}
	}

	protected JSONArray getMissingReferencesJSONArray(
		ThemeDisplay themeDisplay,
		Map<String, MissingReference> missingReferences) {

		JSONArray errorMessageJSONArray = JSONFactoryUtil.createJSONArray();

		for (String missingReferenceDisplayName : missingReferences.keySet()) {
			MissingReference missingReference = missingReferences.get(
				missingReferenceDisplayName);

			JSONObject errorMessageJSONObject =
				JSONFactoryUtil.createJSONObject();

			Map<String, String> referrers = missingReference.getReferrers();

			if (referrers.size() == 1) {
				Set<Map.Entry<String, String>> referrerDisplayNames =
					referrers.entrySet();

				Iterator<Map.Entry<String, String>> iterator =
					referrerDisplayNames.iterator();

				Map.Entry<String, String> entry = iterator.next();

				String referrerDisplayName = entry.getKey();
				String referrerClasName = entry.getValue();

				errorMessageJSONObject.put(
					"info",
					themeDisplay.translate(
						"referenced-by-a-x-x",
						new String[] {
							ResourceActionsUtil.getModelResource(
								themeDisplay.getLocale(),
								referrerClasName), referrerDisplayName
						}
					));
			}
			else {
				errorMessageJSONObject.put(
					"info",
					themeDisplay.translate(
						"referenced-by-x-elements", referrers.size()));
			}

			errorMessageJSONObject.put("name", missingReferenceDisplayName);
			errorMessageJSONObject.put(
				"type",
				ResourceActionsUtil.getModelResource(
					themeDisplay.getLocale(), missingReference.getClassName()));

			errorMessageJSONArray.put(errorMessageJSONObject);
		}

		return errorMessageJSONArray;
	}

	protected JSONArray getWarningMessagesJSONArray(
		ThemeDisplay themeDisplay,
		Map<String, MissingReference> missingReferences) {

		JSONArray errorMessageJSONArray = JSONFactoryUtil.createJSONArray();

		for (String missingReferenceReferrerClassName :
				missingReferences.keySet()) {

			MissingReference missingReference = missingReferences.get(
				missingReferenceReferrerClassName);

			Map<String, String> referrers = missingReference.getReferrers();

			String errorMessage = null;

			if (referrers.size() == 1) {
				errorMessage = themeDisplay.translate(
					"1-entry-of-type-x-would-not-be-imported-because-the-" +
						"original-x-does-not-exist-in-the-current-environment",
					new String[] {
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							missingReferenceReferrerClassName),
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							missingReference.getClassName())
					}
				);
			}
			else {
				errorMessage = themeDisplay.translate(
					"x-entries-of-type-x-would-not-be-imported-because-the-" +
						"original-x-does-not-exist-in-the-current-environment",
					new String[] {
						String.valueOf(referrers.size()),
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							missingReferenceReferrerClassName),
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							missingReference.getClassName())

					}
				);
			}

			errorMessageJSONArray.put(errorMessage);
		}

		return errorMessageJSONArray;
	}

	@Override
	protected void handleUploadException(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd, Exception e)
		throws Exception {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.TEXT_HTML);
		response.setStatus(HttpServletResponse.SC_OK);

		String errorMessage = StringPool.BLANK;
		JSONArray errorMessageJSONArray = null;
		JSONArray warningMessageJSONArray = null;
		int errorType = 0;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (e instanceof DuplicateFileException ||
			e instanceof FileExtensionException ||
			e instanceof FileNameException ||
			e instanceof FileSizeException ||
			e instanceof LARFileException ||
			e instanceof LARFileSizeException ||
			e instanceof LARTypeException ||
			e instanceof LayoutPrototypeException ||
			e instanceof LocaleException ||
			e instanceof MissingReferenceException) {

			if (e instanceof DuplicateFileException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-unique-document-name");
				errorType =
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
			}
			else if (e instanceof FileExtensionException) {
				errorMessage = themeDisplay.translate(
					"document-names-must-end-with-one-of-the-following-" +
						"extensions",
					StringUtil.merge(
						getAllowedFileExtensions(
							portletConfig, actionRequest, actionResponse)));
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (e instanceof FileNameException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-name");
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}
			else if (e instanceof FileSizeException ||
					 e instanceof LARFileSizeException) {

				long fileMaxSize = PrefsPropsUtil.getLong(
					PropsKeys.DL_FILE_MAX_SIZE);

				if (fileMaxSize == 0) {
					fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
				}

				fileMaxSize /= 1024;

				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-size-no-larger-" +
						"than-x",
					fileMaxSize);
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (e instanceof LARTypeException) {
				LARTypeException lte = (LARTypeException)e;

				errorMessage = themeDisplay.translate(
					"please-import-a-lar-file-of-the-correct-type-x-is-not-" +
						"valid",
					lte.getMessage());
				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			}
			else if (e instanceof LARFileException) {
				errorMessage = themeDisplay.translate(
					"please-specify-a-lar-file-to-import");
				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			}
			else if (e instanceof LayoutPrototypeException) {
				LayoutPrototypeException lpe = (LayoutPrototypeException)e;

				StringBundler sb = new StringBundler(4);

				sb.append("the-lar-file-could-not-be-imported-because-it-");
				sb.append("requires-page-templates-or-site-templates-that-");
				sb.append("could-not-be-found.-please-import-the-following-");
				sb.append("templates-manually");

				errorMessage = themeDisplay.translate(sb.toString());

				errorMessageJSONArray = JSONFactoryUtil.createJSONArray();

				List<Tuple> missingLayoutPrototypes =
					lpe.getMissingLayoutPrototypes();

				for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
					JSONObject errorMessageJSONObject =
						JSONFactoryUtil.createJSONObject();

					String layoutPrototypeUuid =
						(String)missingLayoutPrototype.getObject(1);

					errorMessageJSONObject.put("info", layoutPrototypeUuid);

					String layoutPrototypeName =
						(String)missingLayoutPrototype.getObject(2);

					errorMessageJSONObject.put("name", layoutPrototypeName);

					String layoutPrototypeClassName =
						(String)missingLayoutPrototype.getObject(0);

					errorMessageJSONObject.put(
						"type",
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							layoutPrototypeClassName));

					errorMessageJSONArray.put(errorMessageJSONObject);
				}

				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			}
			else if (e instanceof LocaleException) {
				LocaleException le = (LocaleException)e;

				errorMessage = themeDisplay.translate(
					"the-available-languages-in-the-lar-file-x-do-not-match-" +
						"the-portal's-available-languages-x",
					new String[] {
						StringUtil.merge(
							le.getSourceAvailableLocales(),
							StringPool.COMMA_AND_SPACE),
						StringUtil.merge(
							le.getTargetAvailableLocales(),
							StringPool.COMMA_AND_SPACE)
					});
				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			}
			else if (e instanceof MissingReferenceException) {
				MissingReferenceException mre = (MissingReferenceException)e;

				errorMessage = themeDisplay.translate(
					"there-are-missing-references-that-could-not-be-found-" +
						"in-the-current-site.-please-import-another-lar-file-" +
							"containing-the-following-elements");

				MissingReferences missingReferences =
					mre.getMissingReferences();

				errorMessageJSONArray = getMissingReferencesJSONArray(
					themeDisplay,
					missingReferences.getDependencyMissingReferences());
				warningMessageJSONArray = getWarningMessagesJSONArray(
					themeDisplay, missingReferences.getWeakMissingReferences());
				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			}
		}
		else {
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}

		deleteTempFileEntry(themeDisplay.getScopeGroupId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", errorMessage);

		if ((errorMessageJSONArray != null) &&
			(errorMessageJSONArray.length() > 0)) {

			jsonObject.put("messageListItems", errorMessageJSONArray);
		}

		if ((warningMessageJSONArray != null) &&
			(warningMessageJSONArray.length() > 0)) {

			jsonObject.put("warningMessages", warningMessageJSONArray);
		}

		jsonObject.put("status", errorType);

		writeJSON(actionRequest, actionResponse, jsonObject);

		ServletResponseUtil.write(response, String.valueOf(errorType));
	}

	protected void importLayouts(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId());

		File file = DLFileEntryLocalServiceUtil.getFile(
			themeDisplay.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getVersion(), false);

		if ((file == null) || !file.exists()) {
			throw new LARFileException("Import file does not exist");
		}

		boolean successfulRename = false;

		File newFile = null;

		try {
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");

			String newFileName = StringUtil.replace(
				file.getPath(), file.getName(), fileEntry.getTitle());

			newFile = new File(newFileName);

			successfulRename = file.renameTo(newFile);

			if (!successfulRename) {
				newFile = FileUtil.createTempFile(fileEntry.getExtension());

				FileUtil.copyFile(file, newFile);
			}

			LayoutServiceUtil.importLayouts(
				groupId, privateLayout, actionRequest.getParameterMap(),
				newFile);

			deleteTempFileEntry(groupId);

			addSuccessMessage(actionRequest, actionResponse);
		}
		finally {
			if (successfulRename) {
				successfulRename = newFile.renameTo(file);

				if (!successfulRename) {
					FileUtil.copyFile(newFile, file);

					FileUtil.delete(newFile);
				}
			}
			else {
				FileUtil.delete(newFile);
			}
		}
	}

	protected void validateImportLayoutsFile(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId());

		File file = DLFileEntryLocalServiceUtil.getFile(
			themeDisplay.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getVersion(), false);

		if ((file == null) || !file.exists()) {
			throw new LARFileException("Import file does not exist");
		}

		boolean successfulRename = false;

		File newFile = null;

		try {
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");

			String newFileName = StringUtil.replace(
				file.getPath(), file.getName(), fileEntry.getTitle());

			newFile = new File(newFileName);

			successfulRename = file.renameTo(newFile);

			if (!successfulRename) {
				newFile = FileUtil.createTempFile(fileEntry.getExtension());

				FileUtil.copyFile(file, newFile);
			}

			MissingReferences missingReferences =
				LayoutServiceUtil.validateImportLayoutsFile(
				groupId, privateLayout, actionRequest.getParameterMap(),
				newFile);

			Map<String, MissingReference> weakMissingReferences =
				missingReferences.getWeakMissingReferences();

			if (weakMissingReferences.isEmpty()) {
				return;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if ((weakMissingReferences != null) &&
				(weakMissingReferences.size() > 0)) {

				jsonObject.put(
					"warningMessages",
					getWarningMessagesJSONArray(
						themeDisplay, weakMissingReferences));
			}

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		finally {
			if (successfulRename) {
				successfulRename = newFile.renameTo(file);

				if (!successfulRename) {
					FileUtil.copyFile(newFile, file);

					FileUtil.delete(newFile);
				}
			}
			else {
				FileUtil.delete(newFile);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImportLayoutsAction.class);

}