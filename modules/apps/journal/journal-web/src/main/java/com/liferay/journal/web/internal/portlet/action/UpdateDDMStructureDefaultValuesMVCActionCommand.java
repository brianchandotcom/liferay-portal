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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.ArticleContentSizeException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/add_ddm_structure_default_values",
		"mvc.command.name=/journal/update_ddm_structure_default_values"
	},
	service = MVCActionCommand.class
)
public class UpdateDDMStructureDefaultValuesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadException uploadException =
			(UploadException)actionRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable cause = uploadException.getCause();

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(cause);
			}

			if (uploadException.isExceededFileSizeLimit() ||
				uploadException.isExceededUploadRequestSizeLimit()) {

				throw new ArticleContentSizeException(cause);
			}

			throw new PortalException(cause);
		}

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
		long classNameId = ParamUtil.getLong(
			uploadPortletRequest, "classNameId");
		long classPK = ParamUtil.getLong(uploadPortletRequest, "classPK");
		String articleId = ParamUtil.getString(
			uploadPortletRequest, "articleId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "titleMapAsXML");

		String ddmStructureKey = ParamUtil.getString(
			uploadPortletRequest, "ddmStructureKey");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_portal.getSiteGroupId(groupId),
			_portal.getClassNameId(JournalArticle.class), ddmStructureKey,
			true);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), uploadPortletRequest);

		Fields fields = DDMUtil.getFields(
			ddmStructure.getStructureId(), serviceContext);

		String content = _journalConverter.getContent(ddmStructure, fields);

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLanguageId(content));

		if ((classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) &&
			!_hasDefaultLocale(titleMap, articleDefaultLocale)) {

			titleMap.put(
				articleDefaultLocale,
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest), "untitled-x",
					HtmlUtil.escape(
						ddmStructure.getName(themeDisplay.getLocale()))));
		}

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");

		String ddmTemplateKey = ParamUtil.getString(
			uploadPortletRequest, "ddmTemplateKey");
		int displayPageType = ParamUtil.getInteger(
			uploadPortletRequest, "displayPageType");

		String layoutUuid = ParamUtil.getString(
			uploadPortletRequest, "layoutUuid");

		if ((displayPageType == AssetDisplayPageConstants.TYPE_DEFAULT) ||
			(displayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC)) {

			Layout targetLayout = _journalHelper.getArticleLayout(
				layoutUuid, groupId);

			JournalArticle latestArticle = _journalArticleService.fetchArticle(
				groupId, articleId);

			if ((displayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC) &&
				(targetLayout == null) && (latestArticle != null) &&
				Validator.isNotNull(latestArticle.getLayoutUuid())) {

				Layout latestTargetLayout = _journalHelper.getArticleLayout(
					latestArticle.getLayoutUuid(), groupId);

				if (latestTargetLayout == null) {
					layoutUuid = latestArticle.getLayoutUuid();
				}
			}
			else if (targetLayout == null) {
				layoutUuid = null;
			}
		}
		else {
			layoutUuid = null;
		}

		boolean indexable = ParamUtil.getBoolean(
			uploadPortletRequest, "indexable");

		String smallImageSource = ParamUtil.getString(
			uploadPortletRequest, "smallImageSource", "none");

		boolean smallImage = !Objects.equals(smallImageSource, "none");

		String smallImageURL = StringPool.BLANK;
		File smallFile = null;

		if (Objects.equals(smallImageSource, "url")) {
			smallImageURL = ParamUtil.getString(
				uploadPortletRequest, "smallImageURL");
		}
		else if (Objects.equals(smallImageSource, "file")) {
			smallFile = uploadPortletRequest.getFile("smallFile");
		}

		JournalArticle article = null;

		if (actionName.equals("/journal/add_ddm_structure_default_values")) {

			// Add article

			article = _journalArticleService.addArticleDefaultValues(
				groupId, classNameId, classPK, titleMap, descriptionMap,
				content, ddmStructureKey, ddmTemplateKey, layoutUuid, indexable,
				smallImage, smallImageURL, smallFile, serviceContext);
		}
		else if (actionName.equals(
					"/journal/update_ddm_structure_default_values")) {

			// Update article

			article = _journalArticleService.updateArticleDefaultValues(
				groupId, articleId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, indexable,
				smallImage, smallImageURL, smallFile, serviceContext);
		}

		// Asset display page

		_assetDisplayPageEntryFormProcessor.process(
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			actionRequest);
	}

	private boolean _hasDefaultLocale(Map<Locale, String> map, Locale locale) {
		if (MapUtil.isEmpty(map)) {
			return false;
		}

		if (Validator.isNull(map.get(locale))) {
			return false;
		}

		return true;
	}

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private Portal _portal;

}