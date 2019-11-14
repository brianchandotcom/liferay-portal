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

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorTrackerUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class GroupSelectorDisplayContextTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			GroupItemSelectorTrackerUtil.class, "_serviceTrackerMap",
			new MockServiceTrackerMap());
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			GroupItemSelectorTrackerUtil.class, "_serviceTrackerMap", null);
	}

	@Test
	public void testGetGroupItemSelectorIcon() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(
				new MockLiferayResourceRequest());

		Assert.assertEquals(
			"icon",
			groupSelectorDisplayContext.getGroupItemSelectorIcon("test"));
	}

	@Test
	public void testGetGroupItemSelectorLabel() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(
				new MockLiferayResourceRequest());

		Assert.assertEquals(
			"label",
			groupSelectorDisplayContext.getGroupItemSelectorLabel("test"));
	}

	@Test
	public void testGetGroupTypes() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(
				new MockLiferayResourceRequest());

		Assert.assertEquals(
			Collections.singleton("test"),
			groupSelectorDisplayContext.getGroupTypes());
	}

	private static class MockGroupItemSelectorProvider
		implements GroupItemSelectorProvider {

		@Override
		public int getGroupCount(
			long companyId, long groupId, String keywords) {

			return 3;
		}

		@Override
		public List<Group> getGroups(
			long companyId, long groupId, String keywords, int start, int end) {

			return Collections.singletonList(Mockito.mock(Group.class));
		}

		@Override
		public String getIcon() {
			return "icon";
		}

		@Override
		public String getLabel(Locale locale) {
			return "label";
		}

	}

	private static class MockServiceTrackerMap
		implements ServiceTrackerMap<String, GroupItemSelectorProvider> {

		public MockServiceTrackerMap() {
			_groupItemSelectorProviderMap = Collections.singletonMap(
				"test", new MockGroupItemSelectorProvider());
		}

		@Override
		public void close() {
		}

		@Override
		public boolean containsKey(String key) {
			return _groupItemSelectorProviderMap.containsKey(key);
		}

		@Override
		public GroupItemSelectorProvider getService(String key) {
			return _groupItemSelectorProviderMap.get(key);
		}

		@Override
		public Set<String> keySet() {
			return _groupItemSelectorProviderMap.keySet();
		}

		@Override
		public Collection<GroupItemSelectorProvider> values() {
			return _groupItemSelectorProviderMap.values();
		}

		private final Map<String, GroupItemSelectorProvider>
			_groupItemSelectorProviderMap;

	}

}