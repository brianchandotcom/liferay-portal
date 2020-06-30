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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ContactLocalService;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class ContactModelListener extends BaseEntityModelListener<Contact> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	protected Contact getModel(long id) throws Exception {
		return _contactLocalService.getContact(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "contactId";
	}

	@Override
	protected boolean isExcluded(Contact contact) {
		return isUserExcluded(userLocalService.fetchUser(contact.getClassPK()));
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"accountId", "birthday", "classNameId", "classPK", "companyId",
		"createDate", "emailAddress", "employeeNumber", "employeeStatusId",
		"facebookSn", "firstName", "hoursOfOperation", "jabberSn", "jobClass",
		"jobTitle", "lastName", "male", "middleName", "modifiedDate",
		"parentContactId", "prefixId", "skypeSn", "smsSn", "suffixId",
		"twitterSn", "userId", "userName");

	@Reference
	private ContactLocalService _contactLocalService;

}