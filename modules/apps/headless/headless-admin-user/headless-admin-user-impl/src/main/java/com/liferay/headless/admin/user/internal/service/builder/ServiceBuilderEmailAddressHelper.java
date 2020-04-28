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

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ServiceBuilderEmailAddressHelper.class)
public class ServiceBuilderEmailAddressHelper {

	public com.liferay.portal.kernel.model.EmailAddress
		toServiceBuilderEmailAddress(EmailAddress emailAddress, String type) {

		String address = emailAddress.getEmailAddress();

		if (Validator.isNull(address)) {
			return null;
		}

		com.liferay.portal.kernel.model.EmailAddress
			serviceBuilderEmailAddress =
				_emailAddressLocalService.createEmailAddress(
					GetterUtil.getLong(emailAddress.getId()));

		serviceBuilderEmailAddress.setAddress(address);
		serviceBuilderEmailAddress.setTypeId(
			_serviceBuilderListTypeHelper.toServiceBuilderListTypeId(
				"email-address", emailAddress.getType(), type));
		serviceBuilderEmailAddress.setPrimary(
			GetterUtil.getBoolean(emailAddress.getPrimary()));

		return serviceBuilderEmailAddress;
	}

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private ServiceBuilderListTypeHelper _serviceBuilderListTypeHelper;

}