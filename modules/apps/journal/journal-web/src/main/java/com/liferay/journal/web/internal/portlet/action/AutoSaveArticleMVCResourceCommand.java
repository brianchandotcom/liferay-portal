/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.web.internal.util.JournalArticleUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/auto_save_article"
	},
	service = MVCResourceCommand.class
)
public class AutoSaveArticleMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject;

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(resourceRequest);

			String articleId = ParamUtil.getString(
				uploadPortletRequest, "articleId");

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchArticle(
					ParamUtil.getLong(uploadPortletRequest, "groupId"),
					articleId);

			String actionName = "/journal/add_article";

			if (journalArticle != null) {
				actionName = "/journal/update_article";
			}

			JournalArticle article = JournalArticleUtil.addOrUpdateArticle(
				actionName, _assetDisplayPageEntryFormProcessor,
				_ddmFormValuesFactory, _ddmFormValuesToFieldsConverter,
				_ddmStructureLocalService, _journalArticleService,
				_journalConverter, _journalHelper, _localization, _portal,
				resourceRequest);

			jsonObject = JSONUtil.put(
				"articleId", article.getArticleId()
			).put(
				"friendlyURL",
				() -> {
					if (Validator.isNotNull(articleId)) {
						return null;
					}

					Map<Locale, String> friendlyURLMap =
						article.getFriendlyURLMap();

					return friendlyURLMap.get(
						LocaleUtil.fromLanguageId(
							article.getDefaultLanguageId()));
				}
			).put(
				"success", true
			).put(
				"version", String.valueOf(article.getVersion())
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.error("Unable to perform action", exception);
			}

			jsonObject = JSONUtil.put("success", false);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AutoSaveArticleMVCResourceCommand.class);

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
	private JournalArticleLocalService _journalArticleLocalService;

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