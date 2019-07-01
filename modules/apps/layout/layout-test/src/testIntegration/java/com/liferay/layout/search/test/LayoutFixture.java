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

package com.liferay.layout.search.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Igor Fabiano Nazar
 * @author Vagner B.C
 */
public class LayoutFixture {

	public LayoutFixture(Group group) {
		_group = group;
	}

	public Layout createLayout() throws PortalException {
		return createLayout(RandomTestUtil.randomString());
	}

	public Layout createLayout(
			LocalizedValuesMap nameMap, LocalizedValuesMap titleMap)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LocalizedValuesMap friendlyUrlMap = new LocalizedValuesMap() {
			{
				String randomString = FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString());

				put(LocaleUtil.US, StringPool.SLASH + randomString);
			}
		};

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap.getValues(),
			titleMap.getValues(), null, null, null,
			LayoutConstants.TYPE_CONTENT, null, false,
			friendlyUrlMap.getValues(), serviceContext);

		_layouts.add(layout);

		return layout;
	}

	public Layout createLayout(String name) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		String randomString = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());

		String friendlyURL = StringPool.SLASH + randomString;

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null,
			RandomTestUtil.randomString(), LayoutConstants.TYPE_CONTENT, false,
			friendlyURL, serviceContext);

		_layouts.add(layout);

		return layout;
	}

	public List<Layout> getLayouts() {
		return _layouts;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	private final Group _group;
	private final List<Layout> _layouts = new ArrayList<>();

}