/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import com.liferay.object.internal.search.spi.model.index.contributor.ObjectRelationshipModelIndexerWriterContributor;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gleice Lisbino
 */
@Component(service = ModelSearchConfigurator.class)
public class ObjectRelationshipModelSearchConfigurator
	implements ModelSearchConfigurator<ObjectRelationship> {

	@Override
	public String getClassName() {
		return ObjectRelationship.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.NAME, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<ObjectRelationship>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new ObjectRelationshipModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_objectRelationshipLocalServiceLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<ObjectRelationship>
		_modelIndexWriterContributor;

	@Reference
	private ObjectRelationshipLocalService
		_objectRelationshipLocalServiceLocalService;

}