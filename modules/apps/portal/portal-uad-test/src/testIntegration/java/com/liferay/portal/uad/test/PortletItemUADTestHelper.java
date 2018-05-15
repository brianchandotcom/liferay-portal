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

import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = PortletItemUADTestHelper.class)
public class PortletItemUADTestHelper {

	/**
	 * Implement addPortletItem() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid PortletItems with a specified user ID in order to execute correctly. Implement addPortletItem() such that it creates a valid PortletItem with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public PortletItem addPortletItem(long userId) throws Exception {
		return _portletItemLocalService.addPortletItem(
			userId, TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	/**
	 * Implement cleanUpDependencies(List<PortletItem> portletItems) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid PortletItems with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<PortletItem> portletItems) such that any additional objects created during the construction of portletItems are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<PortletItem> portletItems)
		throws Exception {
	}

	@Reference
	private PortletItemLocalService _portletItemLocalService;

}