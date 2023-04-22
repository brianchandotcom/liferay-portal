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
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactTable;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.PhoneTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.PhonePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class PhoneTableReferenceDefinition
	implements TableReferenceDefinition<PhoneTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<PhoneTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			PhoneTable.INSTANCE.userId, UserTable.INSTANCE.userId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<PhoneTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				AddressTable.INSTANCE
			).innerJoinON(
				PhoneTable.INSTANCE,
				PhoneTable.INSTANCE.classPK.eq(AddressTable.INSTANCE.addressId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					PhoneTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Address.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ContactTable.INSTANCE
			).innerJoinON(
				PhoneTable.INSTANCE,
				PhoneTable.INSTANCE.classPK.eq(ContactTable.INSTANCE.contactId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					PhoneTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Contact.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				OrganizationTable.INSTANCE
			).innerJoinON(
				PhoneTable.INSTANCE,
				PhoneTable.INSTANCE.classPK.eq(
					OrganizationTable.INSTANCE.organizationId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					PhoneTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			)
		).singleColumnReference(
			PhoneTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _phonePersistence;
	}

	@Override
	public PhoneTable getTable() {
		return PhoneTable.INSTANCE;
	}

	@Reference
	private PhonePersistence _phonePersistence;

}