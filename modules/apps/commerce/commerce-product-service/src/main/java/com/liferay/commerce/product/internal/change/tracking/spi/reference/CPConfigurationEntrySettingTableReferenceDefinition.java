/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPConfigurationEntrySettingTable;
import com.liferay.commerce.product.model.CPConfigurationEntryTable;
import com.liferay.commerce.product.service.persistence.CPConfigurationEntrySettingPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = TableReferenceDefinition.class)
public class CPConfigurationEntrySettingTableReferenceDefinition
	implements TableReferenceDefinition<CPConfigurationEntrySettingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPConfigurationEntrySettingTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPConfigurationEntrySettingTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPConfigurationEntrySettingTable.INSTANCE.CPConfigurationEntryId,
			CPConfigurationEntryTable.INSTANCE.CPConfigurationEntryId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpConfigurationEntrySettingPersistence;
	}

	@Override
	public CPConfigurationEntrySettingTable getTable() {
		return CPConfigurationEntrySettingTable.INSTANCE;
	}

	@Reference
	private CPConfigurationEntrySettingPersistence
		_cpConfigurationEntrySettingPersistence;

}