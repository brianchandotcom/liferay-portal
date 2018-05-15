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

package com.liferay.portal.uad.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = SystemEventUADTestHelper.class)
public class SystemEventUADTestHelper {

	/**
	 * Implement addSystemEvent() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid SystemEvents with a specified user ID in order to execute correctly. Implement addSystemEvent() such that it creates a valid SystemEvent with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public SystemEvent addSystemEvent(long userId) throws Exception {
		return _systemEventLocalService.addSystemEvent(
			userId, TestPropsValues.getGroupId(), Group.class.getName(),
			RandomTestUtil.nextLong(), PortalUUIDUtil.generate(),
			StringPool.BLANK, SystemEventConstants.TYPE_DELETE,
			StringPool.BLANK);
	}

	/**
	 * Implement cleanUpDependencies(List<SystemEvent> systemEvents) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid SystemEvents with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<SystemEvent> systemEvents) such that any additional objects created during the construction of systemEvents are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<SystemEvent> systemEvents)
		throws Exception {
	}

	@Reference
	private SystemEventLocalService _systemEventLocalService;

}