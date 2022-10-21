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
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.ArticleContentSizeException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.Calendar;
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
		"mvc.command.name=/journal/save_as_draft_article"
	},
	service = MVCActionCommand.class
)
public class SaveAsDraftArticleMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			JournalArticle article = _processAction(actionRequest);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"articleId", article.getArticleId()
				).put(
					"classPK", article.getResourcePrimKey()
				).put(
					"friendlyUrlMap", article.getFriendlyURLMap()
				).put(
					"version", article.getVersion()
				));
		}
		catch (Exception exception) {
			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("error", exception.getMessage()));
		}
	}

	private JournalArticle _processAction(ActionRequest actionRequest)
		throws Exception {

		UploadException uploadException =
			(UploadException)actionRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable throwable = uploadException.getCause();

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(throwable);
			}

			if (uploadException.isExceededFileSizeLimit() ||
				uploadException.isExceededUploadRequestSizeLimit()) {

				throw new ArticleContentSizeException(throwable);
			}

			throw new PortalException(throwable);
		}

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Updating article " +
					MapUtil.toString(uploadPortletRequest.getParameterMap()));
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String articleId = ParamUtil.getString(
			uploadPortletRequest, "articleId");

		Map<Locale, String> titleMap = _localization.getLocalizationMap(
			actionRequest, "titleMapAsXML");

		String ddmStructureKey = ParamUtil.getString(
			uploadPortletRequest, "ddmStructureKey");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_portal.getSiteGroupId(groupId),
			_portal.getClassNameId(JournalArticle.class), ddmStructureKey,
			true);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), uploadPortletRequest);

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			actionRequest, ddmStructure.getDDMForm());

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

		String content = _journalConverter.getContent(
			ddmStructure, fields, groupId);

		Map<Locale, String> descriptionMap = _localization.getLocalizationMap(
			actionRequest, "descriptionMapAsXML");
		Map<Locale, String> friendlyURLMap = _localization.getLocalizationMap(
			actionRequest, "friendlyURL");

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
			else if ((displayPageType ==
						AssetDisplayPageConstants.TYPE_DEFAULT) ||
					 (targetLayout == null)) {

				layoutUuid = null;
			}
		}
		else {
			layoutUuid = null;
		}

		int displayDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateAmPm");

		boolean neverExpire = ParamUtil.getBoolean(
			uploadPortletRequest, "neverExpire", displayDateYear == 0);

		if (!PropsValues.SCHEDULER_ENABLED) {
			neverExpire = true;
		}

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int reviewDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateMonth");
		int reviewDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateDay");
		int reviewDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateYear");
		int reviewDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateHour");
		int reviewDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateMinute");
		int reviewDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateAmPm");

		boolean neverReview = ParamUtil.getBoolean(
			uploadPortletRequest, "neverReview", displayDateYear == 0);

		if (!PropsValues.SCHEDULER_ENABLED) {
			neverReview = true;
		}

		if (reviewDateAmPm == Calendar.PM) {
			reviewDateHour += 12;
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

		String articleURL = ParamUtil.getString(
			uploadPortletRequest, "articleURL");

		serviceContext.setAttribute(
			"updateAutoTags",
			ParamUtil.getBoolean(actionRequest, "updateAutoTags"));

		JournalArticle article = null;

		if (Objects.equals(cmd, Constants.ADD)) {

			// Add article

			long classNameId = ParamUtil.getLong(
				uploadPortletRequest, "classNameId");
			long classPK = ParamUtil.getLong(uploadPortletRequest, "classPK");
			boolean autoArticleId = ParamUtil.getBoolean(
				uploadPortletRequest, "autoArticleId");

			article = _journalArticleService.addArticle(
				null, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, friendlyURLMap,
				content, ddmStructureKey, ddmTemplateKey, layoutUuid,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL, smallFile,
				null, articleURL, serviceContext);
		}
		else {

			// Update article

			double version = ParamUtil.getDouble(
				uploadPortletRequest, "version");

			article = _journalArticleService.getArticle(
				groupId, articleId, version);

			if (Objects.equals(cmd, Constants.UPDATE)) {
				article = _journalArticleService.updateArticle(
					groupId, folderId, articleId, version, titleMap,
					descriptionMap, friendlyURLMap, content, ddmStructureKey,
					ddmTemplateKey, layoutUuid, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, smallImage,
					smallImageURL, smallFile, null, articleURL, serviceContext);
			}
		}

		// Recent articles

		JournalUtil.addRecentArticle(actionRequest, article);

		// Asset display page

		_assetDisplayPageEntryFormProcessor.process(
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			actionRequest);

		return article;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SaveAsDraftArticleMVCActionCommand.class);

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}