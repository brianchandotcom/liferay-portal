/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = ModelDocumentContributor.class
)
public class DDLRecordSetModelDocumentContributor
	implements ModelDocumentContributor<DDLRecordSet> {

	@Override
	public void contribute(Document document, DDLRecordSet ddlRecordSet) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] descriptionLanguageIds = _getLanguageIds(
			defaultLanguageId, ddlRecordSet.getDescription());

		for (String descriptionLanguageId : descriptionLanguageIds) {
			document.addText(
				_localization.getLocalizedName(
					Field.DESCRIPTION, descriptionLanguageId),
				ddlRecordSet.getDescription(descriptionLanguageId));
		}

		String[] nameLanguageIds = _getLanguageIds(
			defaultLanguageId, ddlRecordSet.getName());

		for (String nameLanguageId : nameLanguageIds) {
			document.addText(
				_localization.getLocalizedName(Field.NAME, nameLanguageId),
				ddlRecordSet.getName(nameLanguageId));
		}

		document.addKeyword(Field.VERSION, ddlRecordSet.getVersion());
		document.addKeyword("DDMStructureId", ddlRecordSet.getDDMStructureId());
		document.addKeyword("scope", ddlRecordSet.getScope());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	private String[] _getLanguageIds(String defaultLanguageId, String content) {
		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	private Localization _localization;

}