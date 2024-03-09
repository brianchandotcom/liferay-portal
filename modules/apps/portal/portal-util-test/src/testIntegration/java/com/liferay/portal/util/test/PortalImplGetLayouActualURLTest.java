/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class PortalImplGetLayouActualURLTest extends BasePortalImplURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	@Test
	public void testGetLayoutActualURL() throws Exception {
		Layout layout = _addLayout(
			publicLayout.getLayoutId(), LayoutConstants.TYPE_PORTLET);

		_assertGetLayoutActualURL(layout, layout);
	}

	@Test
	public void testGetLayoutActualURLNoBrowsableLayout() throws Exception {
		_assertGetLayoutActualURL(
			publicLayout,
			_addLayout(publicLayout.getLayoutId(), LayoutConstants.TYPE_NODE));
	}

	private Layout _addLayout(long parentLayoutId, String type)
		throws Exception {

		return layoutLocalService.addLayout(
			_serviceContext.getUserId(), group.getGroupId(), false,
			parentLayoutId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), type,
			false, StringPool.BLANK, _serviceContext);
	}

	private void _assertGetLayoutActualURL(
		Layout expectedLayout, Layout layout) {

		String actualURL = portal.getLayoutActualURL(layout);

		Map<String, String[]> parameterMap = HttpComponentsUtil.getParameterMap(
			HttpComponentsUtil.getQueryString(actualURL));

		if (expectedLayout.getPlid() == layout.getPlid()) {
			Assert.assertEquals(
				MapUtil.toString(parameterMap), expectedLayout.getPlid(),
				MapUtil.getLong(parameterMap, "p_l_id"));

			return;
		}

		Assert.assertEquals(
			MapUtil.toString(parameterMap), expectedLayout.getGroupId(),
			MapUtil.getLong(parameterMap, "groupId"));
		Assert.assertEquals(
			MapUtil.toString(parameterMap), expectedLayout.isPrivateLayout(),
			MapUtil.getBoolean(parameterMap, "privateLayout"));
		Assert.assertEquals(
			MapUtil.toString(parameterMap), expectedLayout.getLayoutId(),
			MapUtil.getLong(parameterMap, "layoutId"));
	}

	private ServiceContext _serviceContext;

}