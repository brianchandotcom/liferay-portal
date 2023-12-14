/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.search;

import com.liferay.list.type.internal.search.spi.model.index.contributor.ListTypeDefinitionModelIndexerWriterContributor;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelSearchConfigurator.class)
public class ListTypeDefinitionModelSearchConfigurator
	implements ModelSearchConfigurator<ListTypeDefinition> {

	@Override
	public String getClassName() {
		return ListTypeDefinition.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<ListTypeDefinition>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new ListTypeDefinitionModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_listTypeDefinitionLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ModelIndexerWriterContributor<ListTypeDefinition>
		_modelIndexWriterContributor;

}