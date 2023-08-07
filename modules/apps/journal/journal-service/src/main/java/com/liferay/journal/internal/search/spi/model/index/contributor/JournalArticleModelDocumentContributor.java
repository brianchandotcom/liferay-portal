/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.internal.util.JournalUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = ModelDocumentContributor.class
)
public class JournalArticleModelDocumentContributor
	implements ModelDocumentContributor<JournalArticle> {

	@Override
	public void contribute(Document document, JournalArticle journalArticle) {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing journal article " + journalArticle);
		}

		_uidFactory.setUID(journalArticle, document);

		String articleId = journalArticle.getArticleId();

		if (journalArticle.isInTrash()) {
			articleId = _trashHelper.getOriginalTitle(articleId);
		}

		document.addKeywordSortable(Field.ARTICLE_ID, articleId);

		DDMFormValues ddmFormValues = null;

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			journalArticle.getDDMStructureId());

		if (ddmStructure != null) {
			document.addKeyword(
				Field.CLASS_TYPE_ID, ddmStructure.getStructureId());

			document.addKeyword(
				"ddmStructureKey", ddmStructure.getStructureKey());

			ddmFormValues = journalArticle.getDDMFormValues();

			if (ddmFormValues != null) {
				for (Locale contentAvailableLocale :
						ddmFormValues.getAvailableLocales()) {

					String content = _ddmIndexer.extractIndexableAttributes(
						ddmStructure, ddmFormValues, contentAvailableLocale);

					document.addText(
						_localization.getLocalizedName(
							Field.CONTENT,
							LocaleUtil.toLanguageId(contentAvailableLocale)),
						content);
				}

				_ddmIndexer.addAttributes(
					document, ddmStructure, ddmFormValues);
			}
		}

		String[] descriptionAvailableLanguageIds =
			_localization.getAvailableLanguageIds(
				journalArticle.getDescriptionMapAsXML());

		for (String descriptionAvailableLanguageId :
				descriptionAvailableLanguageIds) {

			String description = _html.stripHtml(
				journalArticle.getDescription(descriptionAvailableLanguageId));

			document.addText(
				_localization.getLocalizedName(
					Field.DESCRIPTION, descriptionAvailableLanguageId),
				description);
		}

		document.addDate(Field.DISPLAY_DATE, journalArticle.getDisplayDate());
		document.addDate(
			Field.EXPIRATION_DATE, journalArticle.getExpirationDate());
		document.addKeyword(Field.FOLDER_ID, journalArticle.getFolderId());
		document.addKeyword(Field.LAYOUT_UUID, journalArticle.getLayoutUuid());

		String[] titleAvailableLanguageIds =
			_localization.getAvailableLanguageIds(
				journalArticle.getTitleMapAsXML());

		for (String titleAvailableLanguageId : titleAvailableLanguageIds) {
			String title = journalArticle.getTitle(titleAvailableLanguageId);

			document.addText(
				_localization.getLocalizedName(
					Field.TITLE, titleAvailableLanguageId),
				title);
		}

		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(journalArticle.getTreePath(), CharPool.SLASH));
		document.addKeyword(Field.VERSION, journalArticle.getVersion());
		document.addKeyword(
			"ddmTemplateKey", journalArticle.getDDMTemplateKey());

		if (ddmFormValues != null) {
			document.addText(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()));
		}
		else {
			document.addText(
				"defaultLanguageId",
				_language.getLanguageId(LocaleUtil.getSiteDefault()));
		}

		document.addKeyword("head", JournalUtil.isHead(journalArticle));

		boolean headListable = JournalUtil.isHeadListable(journalArticle);

		document.addKeyword("headListable", headListable);

		document.addKeyword(
			"latest", JournalUtil.isLatestArticle(journalArticle));

		document.addDate("reviewDate", journalArticle.getReviewDate());

		// Scheduled listable articles should be visible in asset browser

		if (journalArticle.isScheduled() && headListable) {
			boolean visible = GetterUtil.getBoolean(document.get("visible"));

			if (!visible) {
				document.addKeyword("visible", true);
			}
		}

		for (String titleAvailableLanguageId : titleAvailableLanguageIds) {
			try {
				document.addKeywordSortable(
					_localization.getLocalizedName(
						"urlTitle", titleAvailableLanguageId),
					journalArticle.getUrlTitle(
						LocaleUtil.fromLanguageId(titleAvailableLanguageId)));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to get friendly URL for article ID ",
							journalArticle.getId(), " and language ID ",
							titleAvailableLanguageId),
						portalException);
				}
			}
		}

		document.addNumber(
			"versionCount", GetterUtil.getDouble(journalArticle.getVersion()));

		document.addKeyword(Field.UUID, journalArticle.getUuid());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Journal article " + journalArticle + " indexed successfully");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleModelDocumentContributor.class);

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private UIDFactory _uidFactory;

}