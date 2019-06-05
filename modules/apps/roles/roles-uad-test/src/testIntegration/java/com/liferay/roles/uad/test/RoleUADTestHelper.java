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

package com.liferay.roles.uad.test;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = RoleUADTestHelper.class)
public class RoleUADTestHelper {

	public Role addRole(long userId) throws Exception {
		String name = RandomTestUtil.randomString(
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		return _roleLocalService.addRole(
			userId, null, 0, name, null, null, RoleConstants.TYPE_REGULAR, null,
			null);
	}

	public void cleanUpDependencies(List<Role> roles) throws Exception {
	}

	@Reference
	private RoleLocalService _roleLocalService;

}