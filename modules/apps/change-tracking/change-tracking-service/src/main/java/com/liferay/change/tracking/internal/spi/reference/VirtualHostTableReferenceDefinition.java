/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<VirtualHostTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<VirtualHostTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			VirtualHostTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			VirtualHostTable.INSTANCE.layoutSetId,
			LayoutSetTable.INSTANCE.layoutSetId
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