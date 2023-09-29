/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.RegionLocalizationTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RegionLocalizationPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class RegionLocalizationTableReferenceDefinition
	implements TableReferenceDefinition<RegionLocalizationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<RegionLocalizationTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<RegionLocalizationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			RegionLocalizationTable.INSTANCE.regionId,
			RegionTable.INSTANCE.regionId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _regionLocalizationPersistence;
	}

	@Override
	public RegionLocalizationTable getTable() {
		return RegionLocalizationTable.INSTANCE;
	}

	@Reference
	private RegionLocalizationPersistence _regionLocalizationPersistence;

}