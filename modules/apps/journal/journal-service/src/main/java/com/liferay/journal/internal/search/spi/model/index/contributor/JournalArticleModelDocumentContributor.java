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

package com.liferay.journal.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.internal.util.JournalUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = ModelDocumentContributor.class
)
public class JournalArticleModelDocumentContributor
	implements ModelDocumentContributor<JournalArticle> {

	@Override
	public void contribute(Document document, JournalArticle journalArticle) {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing article " + journalArticle);
		}

		_uidFactory.setUID(journalArticle, document);

		String articleId = journalArticle.getArticleId();

		if (journalArticle.isInTrash()) {
			articleId = _trashHelper.getOriginalTitle(articleId);
		}

		document.addKeywordSortable(Field.ARTICLE_ID, articleId);

		Localization localization = LocalizationUtil.getLocalization();

		String[] contentAvailableLanguageIds =
			localization.getAvailableLanguageIds(journalArticle.getDocument());

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_portal.getSiteGroupId(journalArticle.getGroupId()),
			_portal.getClassNameId(JournalArticle.class),
			journalArticle.getDDMStructureKey(), true);

		if (ddmStructure != null) {
			for (String contentAvailableLanguageId :
					contentAvailableLanguageIds) {

				String content = _extractDDMContent(
					journalArticle, ddmStructure, contentAvailableLanguageId);

				document.addText(
					localization.getLocalizedName(
						Field.CONTENT, contentAvailableLanguageId),
					content);
			}
		}

		String[] descriptionAvailableLanguageIds =
			localization.getAvailableLanguageIds(
				journalArticle.getDescriptionMapAsXML());

		for (String descriptionAvailableLanguageId :
				descriptionAvailableLanguageIds) {

			String description = _html.stripHtml(
				journalArticle.getDescription(descriptionAvailableLanguageId));

			document.addText(
				localization.getLocalizedName(
					Field.DESCRIPTION, descriptionAvailableLanguageId),
				description);
		}

		document.addDate(Field.DISPLAY_DATE, journalArticle.getDisplayDate());
		document.addDate(
			Field.EXPIRATION_DATE, journalArticle.getExpirationDate());
		document.addKeyword(Field.FOLDER_ID, journalArticle.getFolderId());
		document.addKeyword(Field.LAYOUT_UUID, journalArticle.getLayoutUuid());

		String[] titleAvailableLanguageIds =
			localization.getAvailableLanguageIds(
				journalArticle.getTitleMapAsXML());

		for (String titleAvailableLanguageId : titleAvailableLanguageIds) {
			String title = journalArticle.getTitle(titleAvailableLanguageId);

			document.addText(
				localization.getLocalizedName(
					Field.TITLE, titleAvailableLanguageId),
				title);
		}

		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(journalArticle.getTreePath(), CharPool.SLASH));
		document.addKeyword(Field.VERSION, journalArticle.getVersion());
		document.addKeyword(
			"ddmStructureKey", journalArticle.getDDMStructureKey());
		document.addKeyword(
			"ddmTemplateKey", journalArticle.getDDMTemplateKey());

		String defaultLanguageId = localization.getDefaultLanguageId(
			journalArticle.getDocument());

		document.addText("defaultLanguageId", defaultLanguageId);

		document.addDate("displayDate", journalArticle.getDisplayDate());
		document.addKeyword("head", JournalUtil.isHead(journalArticle));

		boolean headListable = JournalUtil.isHeadListable(journalArticle);

		document.addKeyword("headListable", headListable);

		boolean latestArticle = JournalUtil.isLatestArticle(journalArticle);

		document.addKeyword("latest", latestArticle);

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
					localization.getLocalizedName(
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

		if (ddmStructure != null) {
			_addDDMStructureAttributes(ddmStructure, document, journalArticle);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + journalArticle + " indexed successfully");
		}
	}

	private void _addDDMStructureAttributes(
		DDMStructure ddmStructure, Document document, JournalArticle article) {

		document.addKeyword(Field.CLASS_TYPE_ID, ddmStructure.getStructureId());

		DDMFormValues ddmFormValues = null;

		try {
			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, article.getDocument());

			ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
				ddmStructure, fields);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return;
		}

		if (ddmFormValues != null) {
			_ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);
		}
	}

	private String _extractDDMContent(
		JournalArticle article, DDMStructure ddmStructure, String languageId) {

		DDMFormValues ddmFormValues = null;

		try {
			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, article.getDocument());

			ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
				ddmStructure, fields);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		return _ddmIndexer.extractIndexableAttributes(
			ddmStructure, ddmFormValues, LocaleUtil.fromLanguageId(languageId));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleModelDocumentContributor.class);

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private Html _html;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private UIDFactory _uidFactory;

}