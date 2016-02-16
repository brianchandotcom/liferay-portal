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

package com.liferay.portal.scripting.executor.groovy;

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
			groovyUser.user.getContact().getContactId(),
			street1_, city_, zip_, 0, countryId_, typeId_,
			true, true);
	}

	GroovyAddress(
		long userId_, String className_, long classPk_,
		String street1_, String city_, String zip_,
		long regionId_, long countryId_, int typeId_, boolean mailing_,
		boolean primary_) {

		city = city_;
		className = className_;
		classPk = classPk_;
		countryId = countryId_;
		mailing = mailing_;
		primary = primary_;
		regionId = regionId_;
		street1 = street1_;
		typeId = typeId_;
		userId = userId_;
		zip = zip_;
	}

	void create(GroovyScriptingContext groovyScriptingContext) {

		address = AddressLocalServiceUtil.addAddress(
			userId, className, classPk, street1, "", "", city, zip, regionId,
			countryId, typeId, true, true,
			groovyScriptingContext.serviceContext);
	}

	String city;
	String className;
	long classPk;
	long countryId;
	boolean mailing;
	boolean primary;
	long regionId;
	String street1;
	int typeId;
	long userId;
	String zip;

	Address address;
}
