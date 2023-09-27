/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleImportDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	public JournalArticleImportDDMFormFieldValueTransformer(
		JournalArticleLocalService journalArticleLocalService,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		_journalArticleLocalService = journalArticleLocalService;
		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
	}

	@Override
	public String getFieldType() {
		return JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE;
	}

	@Override
	public void transform(DDMFormFieldValue ddmFormFieldValue)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		for (Locale locale : value.getAvailableLocales()) {
			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(
					value.getString(locale));
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsonException);
				}

				continue;
			}

			JournalArticle journalArticle = null;

			long articlePrimaryKey = GetterUtil.getLong(
				_portletDataContext.getNewPrimaryKey(
					JournalArticle.class + ".primaryKey",
					jsonObject.getLong("articlePrimaryKey")));

			if (articlePrimaryKey != 0) {
				journalArticle =
					_journalArticleLocalService.fetchJournalArticle(
						articlePrimaryKey);
			}

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get journal article with primary key " +
							articlePrimaryKey);
				}

				_portletDataContext.removePrimaryKey(
					ExportImportPathUtil.getModelPath(_stagedModel));

				continue;
			}

			value.addString(
				locale,
				JSONUtil.put(
					"className", JournalArticle.class.getName()
				).put(
					"classPK", journalArticle.getResourcePrimKey()
				).put(
					"title",
					journalArticle.getTitle(
						journalArticle.getDefaultLanguageId())
				).put(
					"titleMap", journalArticle.getTitleMap()
				).toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleImportDDMFormFieldValueTransformer.class);

	private final JournalArticleLocalService _journalArticleLocalService;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}