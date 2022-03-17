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
import com.liferay.search.experiences.model.SXPElement;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
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
				LanguageUtil.getCompanyAvailableLocales(
					sxpElement.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						Field.DESCRIPTION, languageId)),
				sxpElement.getDescription(locale), true);
			document.addKeyword(
				Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(Field.TITLE, languageId)),
				sxpElement.getTitle(locale), true);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				sxpElement.getDescription(locale));
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				sxpElement.getTitle(locale));
		}
	}

}