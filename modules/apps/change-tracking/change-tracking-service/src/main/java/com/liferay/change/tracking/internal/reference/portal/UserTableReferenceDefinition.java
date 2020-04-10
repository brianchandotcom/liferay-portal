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
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class UserTableReferenceDefinition
	implements TableReferenceDefinition<UserTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<UserTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.externalReferenceCode);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ContactTable.INSTANCE
			).innerJoinON(
				UserTable.INSTANCE,
				UserTable.INSTANCE.userId.eq(ContactTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					Contact.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						ContactTable.INSTANCE.classNameId)
				)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				UserTable.INSTANCE,
				UserTable.INSTANCE.companyId.eq(CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.defaultUser);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ContactTable.INSTANCE
			).innerJoinON(
				UserTable.INSTANCE,
				UserTable.INSTANCE.contactId.eq(ContactTable.INSTANCE.contactId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.password);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.passwordEncrypted);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.passwordReset);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.passwordModifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.digest);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.reminderQueryQuestion);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.reminderQueryAnswer);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.graceLoginCount);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.screenName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.emailAddress);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.facebookId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.googleUserId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.ldapServerId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.openId);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				UserTable.INSTANCE,
				UserTable.INSTANCE.portraitId.eq(ImageTable.INSTANCE.imageId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.languageId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.timeZoneId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.greeting);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.comments);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.firstName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.middleName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lastName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.jobTitle);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.loginDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.loginIP);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lastLoginDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lastLoginIP);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lastFailedLoginDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.failedLoginAttempts);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lockout);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.lockoutDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.agreedToTermsOfUse);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.emailAddressVerified);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			UserTable.INSTANCE.status);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _userPersistence;
	}

	@Override
	public UserTable getTable() {
		return UserTable.INSTANCE;
	}

	@Reference
	private UserPersistence _userPersistence;

}