/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 */
public class GroupSelectorDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_serviceRegistration = bundleContext.registerService(
			GroupItemSelectorProvider.class,
			new MockGroupItemSelectorProvider(), null);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();

		_frameworkUtilMockedStatic.close();
	}

	@Test
	public void testGetGroupItemSelectorIcon() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(
				"test", new MockLiferayResourceRequest());

		Assert.assertEquals(
			"icon", groupSelectorDisplayContext.getGroupItemSelectorIcon());
	}

	@Test
	public void testGetGroupItemSelectorLabel() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(new MockLiferayResourceRequest());

		Assert.assertEquals(
			"label",
			groupSelectorDisplayContext.getGroupItemSelectorLabel("test"));
	}

	@Test
	public void testGetGroupTypes() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(new MockLiferayResourceRequest());

		Assert.assertEquals(
			Collections.singleton("test"),
			groupSelectorDisplayContext.getGroupTypes());
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static ServiceRegistration<GroupItemSelectorProvider>
		_serviceRegistration;

	private static class MockGroupItemSelectorProvider
		implements GroupItemSelectorProvider {

		@Override
		public String getEmptyResultsMessage() {
			return null;
		}

		@Override
		public List<Group> getGroups(
			long companyId, long groupId, String keywords, int start, int end) {

			return Collections.singletonList(Mockito.mock(Group.class));
		}

		@Override
		public int getGroupsCount(
			long companyId, long groupId, String keywords) {

			return 3;
		}

		@Override
		public String getGroupType() {
			return "test";
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

}