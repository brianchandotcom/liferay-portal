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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.VirtualHostTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.VirtualHostPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class VirtualHostTableReferenceDefinition
	implements TableReferenceDefinition<VirtualHostTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<VirtualHostTable>
			tableTableReferenceInfoBuilder) {

		tableTableReferenceInfoBuilder.singleColumnReference(
			VirtualHostTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			VirtualHostTable.INSTANCE.layoutSetId,
			LayoutSetTable.INSTANCE.layoutSetId
		).nonreferenceColumns(
			VirtualHostTable.INSTANCE.hostname,
			VirtualHostTable.INSTANCE.defaultVirtualHost,
			VirtualHostTable.INSTANCE.languageId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _virtualHostPersistence;
	}

	@Override
	public VirtualHostTable getTable() {
		return VirtualHostTable.INSTANCE;
	}

	@Reference
	private VirtualHostPersistence _virtualHostPersistence;

}