/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
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
				LanguageUtil.getCompanyAvailableLocales(
					sxpBlueprint.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpBlueprint.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(Field.TITLE, languageId)),
				sxpBlueprint.getTitle(locale), true);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				sxpBlueprint.getDescription(locale));
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				sxpBlueprint.getTitle(locale));
		}
	}

}