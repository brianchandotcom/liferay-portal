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

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyAddress {

	GroovyAddress(
		GroovyUser groovyUser, String street1_, String city_, String zip_,
		long countryId_, int typeId_) {

		this(
			groovyUser.user.getUserId(), Contact.class.getName(),
			groovyUser.user.contact.getContactId(), street1_, city_, zip_, 0,
			countryId_, typeId_, true, true);
	}

	GroovyAddress(
		long userId_, String className_, long classPK_, String street1_,
		String city_, String zip_, long regionId_, long countryId_, int typeId_,
		boolean mailing_, boolean primary_) {

		userId = userId_;
		className = className_;
		classPK = classPK_;
		street1 = street1_;
		city = city_;
		zip = zip_;
		regionId = regionId_;
		countryId = countryId_;
		typeId = typeId_;
		mailing = mailing_;
		primary = primary_;
	}

	void create(GroovyScriptingContext groovyScriptingContext) {
		address = AddressLocalServiceUtil.addAddress(
			userId, className, classPK, street1, "", "", city, zip, regionId,
			countryId, typeId, true, true,
			groovyScriptingContext.serviceContext);
	}

	Address address;
	String city;
	String className;
	long classPK;
	long countryId;
	boolean mailing;
	boolean primary;
	long regionId;
	String street1;
	int typeId;
	long userId;
	String zip;

}