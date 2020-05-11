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
import com.liferay.portal.kernel.model.AccountTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.ContactTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<ContactTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			ContactTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ContactTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			ContactTable.INSTANCE.userName, ContactTable.INSTANCE.createDate,
			ContactTable.INSTANCE.modifiedDate,
			ContactTable.INSTANCE.classNameId, ContactTable.INSTANCE.classPK
		).singleColumnReference(
			ContactTable.INSTANCE.accountId, AccountTable.INSTANCE.accountId
		).parentColumnReference(
			ContactTable.INSTANCE.contactId,
			ContactTable.INSTANCE.parentContactId
		).nonreferenceColumns(
			ContactTable.INSTANCE.emailAddress, ContactTable.INSTANCE.firstName,
			ContactTable.INSTANCE.middleName, ContactTable.INSTANCE.lastName,
			ContactTable.INSTANCE.prefixId, ContactTable.INSTANCE.suffixId,
			ContactTable.INSTANCE.male, ContactTable.INSTANCE.birthday,
			ContactTable.INSTANCE.smsSn, ContactTable.INSTANCE.facebookSn,
			ContactTable.INSTANCE.jabberSn, ContactTable.INSTANCE.skypeSn,
			ContactTable.INSTANCE.twitterSn,
			ContactTable.INSTANCE.employeeStatusId,
			ContactTable.INSTANCE.employeeNumber,
			ContactTable.INSTANCE.jobTitle, ContactTable.INSTANCE.jobClass,
			ContactTable.INSTANCE.hoursOfOperation
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