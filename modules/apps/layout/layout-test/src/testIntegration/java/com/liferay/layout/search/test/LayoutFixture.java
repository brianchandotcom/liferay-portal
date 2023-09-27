/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.search.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

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
			LayoutConstants.TYPE_CONTENT,
			UnicodePropertiesBuilder.put(
				"published", "true"
			).buildString(),
			false, friendlyUrlMap.getValues(),
			ServiceContextTestUtil.getServiceContext());

		_layouts.add(layout);

		return layout;
	}

	public Layout createLayout(String name) throws PortalException {
		LocalizedValuesMap nameMap = new LocalizedValuesMap() {
			{
				put(LocaleUtil.getSiteDefault(), name);
			}
		};

		return createLayout(nameMap, new LocalizedValuesMap());
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