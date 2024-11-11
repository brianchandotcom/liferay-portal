/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListTable;
import com.liferay.commerce.product.service.persistence.CPConfigurationListPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = TableReferenceDefinition.class)
public class CPConfigurationListTableReferenceDefinition
	implements TableReferenceDefinition<CPConfigurationListTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPConfigurationListTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			CPConfigurationListTable.INSTANCE.CPConfigurationListId,
			CPConfigurationList.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPConfigurationListTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPConfigurationListTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpConfigurationListPersistence;
	}

	@Override
	public CPConfigurationListTable getTable() {
		return CPConfigurationListTable.INSTANCE;
	}

	@Reference
	private CPConfigurationListPersistence _cpConfigurationListPersistence;

}