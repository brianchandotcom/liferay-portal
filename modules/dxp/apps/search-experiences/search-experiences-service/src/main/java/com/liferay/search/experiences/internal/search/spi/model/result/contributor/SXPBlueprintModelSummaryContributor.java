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

package com.liferay.search.experiences.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
	property = "indexer.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = ModelSummaryContributor.class
)
public class SXPBlueprintModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		String languageId = LocaleUtil.toLanguageId(locale);

		return _createSummary(
			document,
			LocalizationUtil.getLocalizedName(Field.DESCRIPTION, languageId),
			LocalizationUtil.getLocalizedName(Field.TITLE, languageId));
	}

	private Summary _createSummary(
		Document document, String descriptionField, String titleField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		return new Summary(
			document.get(prefix + titleField, titleField),
			document.get(prefix + descriptionField, descriptionField));
	}

}