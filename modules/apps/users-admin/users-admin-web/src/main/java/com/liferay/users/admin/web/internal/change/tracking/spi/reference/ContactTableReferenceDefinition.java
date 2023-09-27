/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactTable;
import com.liferay.portal.kernel.model.EmailAddressTable;
import com.liferay.portal.kernel.model.PhoneTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ContactPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ContactTableReferenceDefinition
	implements TableReferenceDefinition<ContactTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ContactTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				AddressTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.contactId.eq(
					AddressTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					AddressTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Contact.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				EmailAddressTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.contactId.eq(
					EmailAddressTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					EmailAddressTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Contact.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				PhoneTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.contactId.eq(PhoneTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					PhoneTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Contact.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ContactTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ContactTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ContactTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).parentColumnReference(
			ContactTable.INSTANCE.contactId,
			ContactTable.INSTANCE.parentContactId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _contactPersistence;
	}

	@Override
	public ContactTable getTable() {
		return ContactTable.INSTANCE;
	}

	@Reference
	private ContactPersistence _contactPersistence;

}