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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldValueException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_structured_content"
	},
	service = MVCActionCommand.class
)
public class AddStructuredContentMVCActionCommand extends BaseMVCActionCommand {

	protected JSONObject addJournalArticle(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			JournalArticle journalArticle = doAddJournalArticle(actionRequest);

			jsonObject.put(
				"classNameId",
				_portal.getClassNameId(JournalArticle.class.getName())
			).put(
				"classPK", journalArticle.getResourcePrimKey()
			).put(
				"title", journalArticle.getTitle()
			);
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(t, t);
			}

			_handleException(themeDisplay, t, jsonObject);
		}

		return jsonObject;
	}

	@Transactional(rollbackFor = Exception.class)
	protected JournalArticle doAddJournalArticle(ActionRequest actionRequest)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Creating article " +
					MapUtil.toString(actionRequest.getParameterMap()));
		}

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String serializedDDMFormValues = GetterUtil.getString(
			serviceContext.getAttribute("ddmFormValues"));

		JSONObject serializedDDMFormValuesJSONObject =
			JSONFactoryUtil.createJSONObject(serializedDDMFormValues);

		JSONArray fieldValuesJSONArray =
			serializedDDMFormValuesJSONObject.getJSONArray("fieldValues");

		JSONArray updatedFieldValuesJSONArray =
			JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = ParamUtil.getString(actionRequest, "title");

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			String fieldName = fieldValueJSONObject.getString("name");

			if (fieldName.equals("-")) {
				continue;
			}

			String fieldValue = fieldValueJSONObject.getString("value");

			if (ddmStructure.getFieldRequired(fieldName) &&
				Validator.isBlank(fieldValue)) {

				throw new StorageFieldValueException.RequiredValue(fieldName);
			}

			String fieldType = ddmStructure.getFieldType(fieldName);

			if (fieldType.equals("ddm-image")) {
				String imageName = title + " - " + fieldName;

				String uniqueImageName = _uniqueFileNameProvider.provide(
					imageName,
					curImageName -> _exists(themeDisplay, curImageName));

				FileEntry fileEntry = _addImage(
					fieldName, uniqueImageName, fieldValue, serviceContext,
					themeDisplay);

				JSONObject imageFieldValueJSONObject = JSONUtil.put(
					"alt", StringPool.BLANK
				).put(
					"groupId", fileEntry.getGroupId()
				).put(
					"title", uniqueImageName
				).put(
					"uuid", fileEntry.getUuid()
				);

				JSONObject imageFieldJSONObject = JSONUtil.put(
					"name", fieldName
				).put(
					"value", imageFieldValueJSONObject.toString()
				);

				fieldValueJSONObject = imageFieldJSONObject;
			}

			updatedFieldValuesJSONArray.put(fieldValueJSONObject);
		}

		serializedDDMFormValuesJSONObject.put(
			"fieldValues", updatedFieldValuesJSONArray);

		serviceContext.setAttribute(
			"ddmFormValues", serializedDDMFormValuesJSONObject.toJSONString());

		Fields fields = _ddm.getFields(ddmStructureId, serviceContext);

		String content = _journalConverter.getContent(ddmStructure, fields);

		Map<Locale, String> titleMap = new HashMap<Locale, String>() {
			{
				put(
					LocaleUtil.fromLanguageId(
						LocalizationUtil.getDefaultLanguageId(content)),
					title);
			}
		};

		return _journalArticleService.addArticle(
			themeDisplay.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap, null,
			content, ddmStructure.getStructureKey(), null, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = addJournalArticle(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private FileEntry _addImage(
			String fieldName, String imageName, String url,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws IOException, PortalException {

		byte[] bytes = {};

		try {
			if (url.startsWith("data:image/")) {
				String[] urlParts = url.split(";base64,");

				bytes = Base64.decode(urlParts[1]);
			}
			else if (Validator.isUrl(url, true)) {
				if (StringUtil.startsWith(url, StringPool.SLASH)) {
					url = _portal.getPortalURL(themeDisplay) + url;
				}

				URL imageURL = new URL(url);

				bytes = FileUtil.getBytes(imageURL.openStream());
			}

			ImageTool imageTool = ImageToolImpl.getInstance();

			Image image = imageTool.getImage(bytes);

			return _dlAppLocalService.addFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, imageName,
				image.getType(), bytes, serviceContext);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			throw new StorageFieldValueException(
				LanguageUtil.format(
					themeDisplay.getRequest(),
					"image-content-is-invalid-for-field-x", fieldName));
		}
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				themeDisplay.getScopeGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, curFileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	private void _handleException(
		ThemeDisplay themeDisplay, Throwable throwable, JSONObject jsonObject) {

		if (throwable instanceof StorageFieldRequiredException ||
			throwable instanceof StorageFieldValueException) {

			jsonObject.put("errorMessage", throwable.getLocalizedMessage());
		}
		else {
			jsonObject.put(
				"errorMessage",
				LanguageUtil.get(
					themeDisplay.getRequest(),
					"the-web-content-article-could-not-be-created"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddStructuredContentMVCActionCommand.class);

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private Portal _portal;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}