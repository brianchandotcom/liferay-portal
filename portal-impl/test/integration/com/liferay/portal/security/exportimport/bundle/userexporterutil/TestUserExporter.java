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

package com.liferay.portal.security.exportimport.bundle.userexporterutil;

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = {
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestUserExporter implements UserExporter {

	@Override
	public void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		_atomicReference.set("exportUser.contact.contactExpandoAttributes");
	}

	@Override
	public void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		_atomicReference.set("exportUser.userId.userGroupId.userOperation");
	}

	@Override
	public void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		_atomicReference.set("exportUser.user.userExpandoAttributes");
	}

	@Reference(target = "(test=AtomicState)")
	protected void getAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}