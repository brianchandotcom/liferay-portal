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

package com.liferay.layout.uad.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.test.randomizerbumpers.FriendlyURLRandomizerBumper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LayoutUADTestHelper.class)
public class LayoutUADTestHelper {

	/**
	 * Implement addLayout() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid Layouts with a specified user ID in order to execute correctly. Implement addLayout() such that it creates a valid Layout with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public Layout addLayout(long userId) throws Exception {
		String name = RandomTestUtil.randomString(
			FriendlyURLRandomizerBumper.INSTANCE,
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		return _layoutLocalService.addLayout(
			userId, TestPropsValues.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			ServiceContextTestUtil.getServiceContext());
	}

	/**
	 * Implement cleanUpDependencies(List<Layout> layouts) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid Layouts with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<Layout> layouts) such that any additional objects created during the construction of layouts are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<Layout> layouts) throws Exception {
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}