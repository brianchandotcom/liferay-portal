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
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "indexer.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = ModelDocumentContributor.class
)
public class SXPBlueprintModelDocumentContributor
	implements ModelDocumentContributor<SXPBlueprint> {

	@Override
	public void contribute(Document document, SXPBlueprint sxpBlueprint) {
		document.addDate(Field.MODIFIED_DATE, sxpBlueprint.getModifiedDate());
		document.addKeyword(Field.STATUS, sxpBlueprint.getStatus());

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					sxpBlueprint.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					_localization.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpBlueprint.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					_localization.getLocalizedName(Field.TITLE, languageId)),
				sxpBlueprint.getTitle(locale), true);
			document.addText(
				_localization.getLocalizedName(Field.DESCRIPTION, languageId),
				sxpBlueprint.getDescription(locale));
			document.addText(
				_localization.getLocalizedName(Field.TITLE, languageId),
				sxpBlueprint.getTitle(locale));
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}