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

package com.liferay.layout.page.template.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.LayoutPrototypeTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutPrototypePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutPrototypeTableReferenceDefinition
	implements TableReferenceDefinition<LayoutPrototypeTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutPrototypeTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumns(
			LayoutPrototypeTable.INSTANCE.active,
			LayoutPrototypeTable.INSTANCE.createDate,
			LayoutPrototypeTable.INSTANCE.description,
			LayoutPrototypeTable.INSTANCE.modifiedDate,
			LayoutPrototypeTable.INSTANCE.name,
			LayoutPrototypeTable.INSTANCE.settings,
			LayoutPrototypeTable.INSTANCE.userName,
			LayoutPrototypeTable.INSTANCE.uuid
		).singleColumnReference(
			LayoutPrototypeTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			LayoutPrototypeTable.INSTANCE.userId, UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPrototypePersistence;
	}

	@Override
	public LayoutPrototypeTable getTable() {
		return LayoutPrototypeTable.INSTANCE;
	}

	@Reference
	private LayoutPrototypePersistence _layoutPrototypePersistence;

}