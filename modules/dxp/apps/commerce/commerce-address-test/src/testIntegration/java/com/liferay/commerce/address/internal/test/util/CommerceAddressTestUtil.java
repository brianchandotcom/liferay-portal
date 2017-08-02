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

package com.liferay.commerce.address.internal.test.util;

import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.service.CommerceCountryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceAddressTestUtil {

	public static CommerceCountry addCommerceCountry(long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceCountryLocalServiceUtil.addCommerceCountry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(2), RandomTestUtil.randomString(3),
			RandomTestUtil.randomInt(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			serviceContext);
	}

}