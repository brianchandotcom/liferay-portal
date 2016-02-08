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

package com.liferay.portal.deploy.hot.bundle.servicewrapperregistry;

import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 * @author Miguel Pastor
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class TestEmailLocalServiceWrapper
	extends EmailAddressLocalServiceWrapper {

	public TestEmailLocalServiceWrapper() {
		super(null);
	}

	public TestEmailLocalServiceWrapper(
		EmailAddressLocalService emailAddressService) {

		super(emailAddressService);
	}

	@Override
	public EmailAddress getEmailAddress(long emailAddressId) {
		EmailAddress emailAddress = createEmailAddress(1);

		emailAddress.setAddress("email@liferay.com");

		return emailAddress;
	}

}