/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.journal.model.JournalArticle;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemObjectVariationProvider.class)
public class JournalArticleInfoItemObjectVariationProvider
	implements InfoItemObjectVariationProvider<JournalArticle> {

	@Override
	public String getInfoItemFormVariationKey(JournalArticle journalArticle) {
		if (journalArticle == null) {
			return null;
		}

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return String.valueOf(ddmStructure.getStructureId());
	}

	@Override
	public String getInfoItemFormVariationLabel(
		JournalArticle journalArticle, Locale locale) {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return ddmStructure.getName(locale);
	}

}