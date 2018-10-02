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
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.test.randomizerbumpers.FriendlyURLRandomizerBumper;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutFriendlyURLUADTestUtil {

	public static LayoutFriendlyURL addLayoutFriendlyURL(
			LayoutFriendlyURLLocalService layoutFriendlyURLLocalService,
			LayoutLocalService layoutLocalService, long userId)
		throws Exception {

		String name = RandomTestUtil.randomString(
			FriendlyURLRandomizerBumper.INSTANCE,
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = layoutLocalService.addLayout(
			userId, TestPropsValues.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			ServiceContextTestUtil.getServiceContext());

		return layoutFriendlyURLLocalService.getLayoutFriendlyURL(
			layout.getPlid(), layout.getDefaultLanguageId());
	}

	public static void cleanUpDependencies(
			LayoutLocalService layoutLocalService,
			List<LayoutFriendlyURL> layoutFriendlyURLs)
		throws Exception {

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			layoutLocalService.deleteLayout(layoutFriendlyURL.getPlid());
		}
	}

}