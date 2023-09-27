/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiPage;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tibor Lipusz
 */
@Component(
	property = "indexer.class.name=com.liferay.wiki.model.WikiPage",
	service = ModelDocumentContributor.class
)
public class WikiPageModelDocumentContributor
	implements ModelDocumentContributor<WikiPage> {

	@Override
	public void contribute(Document document, WikiPage wikiPage) {
		uidFactory.setUID(wikiPage, document);

		String content = null;

		try {
			content = _htmlParser.extractText(
				_wikiEngineRenderer.convert(wikiPage, null, null, null));

			document.addText(Field.CONTENT, content);
		}
		catch (PageContentException | WikiFormatException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get wiki engine for " + wikiPage.getFormat(),
					exception);
			}
		}

		document.addKeyword(Field.NODE_ID, wikiPage.getNodeId());

		String title = wikiPage.getTitle();

		if (wikiPage.isInTrash()) {
			title = _trashHelper.getOriginalTitle(title);
		}

		document.addText(Field.TITLE, title);

		for (Locale locale :
				_language.getAvailableLocales(wikiPage.getGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				_localization.getLocalizedName(Field.CONTENT, languageId),
				content);
			document.addText(
				_localization.getLocalizedName(Field.TITLE, languageId), title);
		}

		document.addNumber(
			"versionCount", GetterUtil.getDouble(wikiPage.getVersion()));
	}

	@Reference
	protected UIDFactory uidFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageModelDocumentContributor.class);

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private WikiEngineRenderer _wikiEngineRenderer;

}