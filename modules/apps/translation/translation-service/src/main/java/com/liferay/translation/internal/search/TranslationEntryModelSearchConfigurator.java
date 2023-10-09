/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.translation.model.TranslationEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelSearchConfigurator.class)
public class TranslationEntryModelSearchConfigurator
	implements ModelSearchConfigurator<TranslationEntry> {

	@Override
	public String getClassName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.CONTENT, Field.GROUP_ID, Field.MODIFIED_DATE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<TranslationEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.translation.model.TranslationEntry)"
	)
	private ModelIndexerWriterContributor<TranslationEntry>
		_modelIndexWriterContributor;

}