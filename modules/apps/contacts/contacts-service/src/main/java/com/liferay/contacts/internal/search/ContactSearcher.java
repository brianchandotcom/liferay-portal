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

package com.liferay.contacts.internal.search;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.search.BaseSearcher;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lucas Marques de Paula
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Contact",
	service = BaseSearcher.class
)
public class ContactSearcher extends BaseSearcher {

	public static final String CLASS_NAME = Contact.class.getName();

	public ContactSearcher() {
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}