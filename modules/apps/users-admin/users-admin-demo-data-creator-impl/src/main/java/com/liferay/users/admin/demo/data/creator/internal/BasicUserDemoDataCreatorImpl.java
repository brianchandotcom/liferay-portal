/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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