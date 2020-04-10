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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<ContactTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AccountTable.INSTANCE
			).innerJoinON(
				ContactTable.INSTANCE,
				ContactTable.INSTANCE.accountId.eq(
					AccountTable.INSTANCE.accountId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				ContactTable aliasContactTable = ContactTable.INSTANCE.as(
					"aliasContactTable");

				return fromStep.from(
					aliasContactTable
				).innerJoinON(
					ContactTable.INSTANCE,
					ContactTable.INSTANCE.parentContactId.eq(
						aliasContactTable.contactId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.emailAddress);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.firstName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.middleName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.lastName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.prefixId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.suffixId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.male);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.birthday);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.smsSn);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.facebookSn);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.jabberSn);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.skypeSn);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.twitterSn);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.employeeStatusId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.employeeNumber);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.jobTitle);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.jobClass);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ContactTable.INSTANCE.hoursOfOperation);
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