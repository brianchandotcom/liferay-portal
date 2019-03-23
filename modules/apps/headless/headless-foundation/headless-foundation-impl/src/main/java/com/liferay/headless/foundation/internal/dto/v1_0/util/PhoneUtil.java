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

package com.liferay.headless.foundation.internal.dto.v1_0.util;

import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ListType;

/**
 * @author Javier Gamarra
 */
public class PhoneUtil {

	public static Phone toPhone(com.liferay.portal.kernel.model.Phone phone)
		throws PortalException {

		ListType listType = phone.getType();

		return new Phone() {
			{
				extension = phone.getExtension();
				id = phone.getPhoneId();
				phoneNumber = phone.getNumber();
				phoneType = listType.getName();
				primary = phone.isPrimary();
			}
		};
	}

}