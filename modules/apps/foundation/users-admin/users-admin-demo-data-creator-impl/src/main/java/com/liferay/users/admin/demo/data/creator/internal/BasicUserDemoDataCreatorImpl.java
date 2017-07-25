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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(service = BasicUserDemoDataCreator.class)
public class BasicUserDemoDataCreatorImpl
	extends BaseUserDemoDataCreator implements BasicUserDemoDataCreator {

	@Override
	public User create(long companyId) throws PortalException {
		return create(companyId, null);
	}

	@Override
	public User create(long companyId, String emailAddress)
		throws PortalException {

		return createUser(companyId, emailAddress);
	}

	@Override
	public User create(
			long companyId, String screenName, String emailAddress,
			String firstName, String lastName)
		throws PortalException {

		return createUser(
			companyId, screenName, emailAddress, firstName, lastName);
	}

}