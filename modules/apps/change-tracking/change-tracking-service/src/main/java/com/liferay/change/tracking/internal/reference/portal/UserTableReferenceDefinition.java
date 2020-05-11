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
		TableReferenceInfoBuilder<UserTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumns(
			UserTable.INSTANCE.uuid, UserTable.INSTANCE.externalReferenceCode
		).referenceInnerJoin(
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
			)
		).singleColumnReference(
			UserTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			UserTable.INSTANCE.createDate, UserTable.INSTANCE.modifiedDate,
			UserTable.INSTANCE.defaultUser
		).singleColumnReference(
			UserTable.INSTANCE.contactId, ContactTable.INSTANCE.contactId
		).nonreferenceColumns(
			UserTable.INSTANCE.password, UserTable.INSTANCE.passwordEncrypted,
			UserTable.INSTANCE.passwordReset,
			UserTable.INSTANCE.passwordModifiedDate, UserTable.INSTANCE.digest,
			UserTable.INSTANCE.reminderQueryQuestion,
			UserTable.INSTANCE.reminderQueryAnswer,
			UserTable.INSTANCE.graceLoginCount, UserTable.INSTANCE.screenName,
			UserTable.INSTANCE.emailAddress, UserTable.INSTANCE.facebookId,
			UserTable.INSTANCE.googleUserId, UserTable.INSTANCE.ldapServerId,
			UserTable.INSTANCE.openId
		).singleColumnReference(
			UserTable.INSTANCE.portraitId, ImageTable.INSTANCE.imageId
		).nonreferenceColumns(
			UserTable.INSTANCE.languageId, UserTable.INSTANCE.timeZoneId,
			UserTable.INSTANCE.greeting, UserTable.INSTANCE.comments,
			UserTable.INSTANCE.firstName, UserTable.INSTANCE.middleName,
			UserTable.INSTANCE.lastName, UserTable.INSTANCE.jobTitle,
			UserTable.INSTANCE.loginDate, UserTable.INSTANCE.loginIP,
			UserTable.INSTANCE.lastLoginDate, UserTable.INSTANCE.lastLoginIP,
			UserTable.INSTANCE.lastFailedLoginDate,
			UserTable.INSTANCE.failedLoginAttempts, UserTable.INSTANCE.lockout,
			UserTable.INSTANCE.lockoutDate,
			UserTable.INSTANCE.agreedToTermsOfUse,
			UserTable.INSTANCE.emailAddressVerified, UserTable.INSTANCE.status
		);
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