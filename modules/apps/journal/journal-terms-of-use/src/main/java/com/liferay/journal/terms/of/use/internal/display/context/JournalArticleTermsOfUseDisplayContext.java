/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.terms.of.use.internal.display.context;

import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class JournalArticleTermsOfUseDisplayContext {

	public JournalArticleTermsOfUseDisplayContext(
			JournalServiceConfiguration journalServiceConfiguration,
			ThemeDisplay themeDisplay)
		throws Exception {

		_journalServiceConfiguration = journalServiceConfiguration;

		_settings = FallbackKeysSettingsUtil.getSettings(
			new CompanyServiceSettingsLocator(
				themeDisplay.getCompanyId(),
				JournalServiceConfiguration.class.getName()));
	}

	public JournalArticle getJournalArticle() {
		long termsOfUseJournalArticleGroupId =
			getTermsOfUseJournalArticleGroupId();
		String termsOfUseJournalArticleId = getTermsOfUseJournalArticleId();

		if ((termsOfUseJournalArticleGroupId > 0) &&
			Validator.isNotNull(termsOfUseJournalArticleId)) {

			return JournalArticleLocalServiceUtil.fetchArticle(
				termsOfUseJournalArticleGroupId, termsOfUseJournalArticleId);
		}

		return null;
	}

	public long getTermsOfUseJournalArticleGroupId() {
		return GetterUtil.getLong(
			_settings.getValue(
				_TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID_CONFIGURATION_PROPERTY,
				String.valueOf(
					_journalServiceConfiguration.
						termsOfUseJournalArticleGroupId())));
	}

	public String getTermsOfUseJournalArticleGroupIdConfigurationProperty() {
		return _TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID_CONFIGURATION_PROPERTY;
	}

	public String getTermsOfUseJournalArticleId() {
		return _settings.getValue(
			_TERMS_OF_USE_JOURNAL_ARTICLE_ID_CONFIGURATION_PROPERTY,
			String.valueOf(
				_journalServiceConfiguration.termsOfUseJournalArticleId()));
	}

	public String getTermsOfUseJournalArticleIdConfigurationProperty() {
		return _TERMS_OF_USE_JOURNAL_ARTICLE_ID_CONFIGURATION_PROPERTY;
	}

	private static final String
		_TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID_CONFIGURATION_PROPERTY =
			"terms.of.use.journal.article.group.id";

	private static final String
		_TERMS_OF_USE_JOURNAL_ARTICLE_ID_CONFIGURATION_PROPERTY =
			"terms.of.use.journal.article.id";

	private final JournalServiceConfiguration _journalServiceConfiguration;
	private final Settings _settings;

}