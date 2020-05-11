/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstancePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMDataProviderInstanceTableReferenceDefinintion
	implements TableReferenceDefinition<DDMDataProviderInstanceTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMDataProviderInstanceTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DDMDataProviderInstanceTable.INSTANCE
		).nonreferenceColumns(
			DDMDataProviderInstanceTable.INSTANCE.uuid,
			DDMDataProviderInstanceTable.INSTANCE.name,
			DDMDataProviderInstanceTable.INSTANCE.description,
			DDMDataProviderInstanceTable.INSTANCE.definition,
			DDMDataProviderInstanceTable.INSTANCE.type,
			DDMDataProviderInstanceTable.INSTANCE.lastPublishDate
		).resourcePermissionReference(
			DDMDataProviderInstanceTable.INSTANCE.dataProviderInstanceId,
			DDMDataProviderInstance.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmDataProviderInstancePersistence;
	}

	@Override
	public DDMDataProviderInstanceTable getTable() {
		return DDMDataProviderInstanceTable.INSTANCE;
	}

	@Reference
	private DDMDataProviderInstancePersistence
		_ddmDataProviderInstancePersistence;

}