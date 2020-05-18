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
import com.liferay.dynamic.data.mapping.model.DDMContentTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMContentPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMContentTableReferenceDefinintion
	implements TableReferenceDefinition<DDMContentTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMContentTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DDMContentTable.INSTANCE
		).nonreferenceColumns(
			DDMContentTable.INSTANCE.uuid, DDMContentTable.INSTANCE.name,
			DDMContentTable.INSTANCE.description, DDMContentTable.INSTANCE.data
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmContentPersistence;
	}

	@Override
	public DDMContentTable getTable() {
		return DDMContentTable.INSTANCE;
	}

	@Reference
	private DDMContentPersistence _ddmContentPersistence;

}