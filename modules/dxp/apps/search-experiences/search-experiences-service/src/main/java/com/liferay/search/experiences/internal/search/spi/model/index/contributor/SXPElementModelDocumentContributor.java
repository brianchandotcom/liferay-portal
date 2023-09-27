/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.model.SXPElement;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "indexer.class.name=com.liferay.search.experiences.model.SXPElement",
	service = ModelDocumentContributor.class
)
public class SXPElementModelDocumentContributor
	implements ModelDocumentContributor<SXPElement> {

	@Override
	public void contribute(Document document, SXPElement sxpElement) {
		document.addDate(Field.MODIFIED_DATE, sxpElement.getModifiedDate());
		document.addKeyword(Field.HIDDEN, sxpElement.isHidden());
		document.addKeyword(Field.TYPE, sxpElement.getType());
		document.addKeyword("readOnly", sxpElement.isReadOnly());

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					sxpElement.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					_localization.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpElement.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					_localization.getLocalizedName(Field.TITLE, languageId)),
				sxpElement.getTitle(locale), true);
			document.addText(
				_localization.getLocalizedName(Field.DESCRIPTION, languageId),
				sxpElement.getDescription(locale));
			document.addText(
				_localization.getLocalizedName(Field.TITLE, languageId),
				sxpElement.getTitle(locale));
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}