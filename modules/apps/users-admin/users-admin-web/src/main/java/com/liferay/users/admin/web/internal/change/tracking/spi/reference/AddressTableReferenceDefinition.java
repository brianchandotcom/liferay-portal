/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.persistence.AddressPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = TableReferenceDefinition.class)
public class AddressTableReferenceDefinition
	implements TableReferenceDefinition<AddressTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AddressTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AddressTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AddressTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AddressTable.INSTANCE.countryId, CountryTable.INSTANCE.countryId
		).singleColumnReference(
			AddressTable.INSTANCE.regionId, RegionTable.INSTANCE.regionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _addressPersistence;
	}

	@Override
	public AddressTable getTable() {
		return AddressTable.INSTANCE;
	}

	@Reference
	private AddressPersistence _addressPersistence;

}