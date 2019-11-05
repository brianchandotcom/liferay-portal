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

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ContactLocalService;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactModelListener extends BaseEntityModelListener<Contact> {

	@Override
	public String getObjectType() {
		return "user";
	}

	@Override
	public void onAfterCreate(Contact contact) throws ModelListenerException {
		send("add", contact);
	}

	@Override
	public void onBeforeRemove(Contact contact) throws ModelListenerException {
		send("delete", contact);
	}

	@Override
	public void onBeforeUpdate(Contact newContact)
		throws ModelListenerException {

		try {
			Contact oldContact = _contactLocalService.getContact(
				newContact.getContactId());

			if (_equals(newContact, oldContact)) {
				return;
			}

			send("update", newContact);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _equals(Contact newContact, Contact oldContact) {
		Set<String> modifiedAttributes = getModifiedAttributes(
			new ArrayList<String>() {
				{
					add("birthday");
					add("employeeNumber");
					add("employeeStatusId");
					add("facebookSn");
					add("firstName");
					add("hoursOfOperation");
					add("jabberSn");
					add("jobClass");
					add("jobTitle");
					add("lastName");
					add("male");
					add("middleName");
					add("prefixId");
					add("skypeSn");
					add("smsSn");
					add("suffixId");
					add("twitterSn");
				}
			},
			newContact, oldContact);

		if (!modifiedAttributes.isEmpty()) {
			return false;
		}

		return true;
	}

	@Reference
	private ContactLocalService _contactLocalService;

}