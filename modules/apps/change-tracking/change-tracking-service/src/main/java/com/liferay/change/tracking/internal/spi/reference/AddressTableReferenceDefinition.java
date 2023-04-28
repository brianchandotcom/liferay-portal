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

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.model.UserTable;
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

		childTableReferenceInfoBuilder.singleColumnReference(
			AddressTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			AddressTable.INSTANCE.countryId, CountryTable.INSTANCE.countryId
		).singleColumnReference(
			AddressTable.INSTANCE.regionId, RegionTable.INSTANCE.regionId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AddressTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ContactTable.INSTANCE
			).innerJoinON(
				AddressTable.INSTANCE,
				AddressTable.INSTANCE.classPK.eq(
					ContactTable.INSTANCE.contactId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					AddressTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Contact.class.getName())
				)
			)
		).singleColumnReference(
			AddressTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
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