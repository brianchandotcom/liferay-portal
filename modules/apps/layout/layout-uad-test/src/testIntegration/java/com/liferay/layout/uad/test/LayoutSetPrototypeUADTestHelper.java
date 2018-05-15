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

import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LayoutSetPrototypeUADTestHelper.class)
public class LayoutSetPrototypeUADTestHelper {

	public LayoutSetPrototype addLayoutSetPrototype(long userId)
		throws Exception {

		return _layoutSetPrototypeLocalService.addLayoutSetPrototype(
			userId, TestPropsValues.getCompanyId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true, true,
			ServiceContextTestUtil.getServiceContext());
	}

	public void cleanUpDependencies(
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {
	}

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}