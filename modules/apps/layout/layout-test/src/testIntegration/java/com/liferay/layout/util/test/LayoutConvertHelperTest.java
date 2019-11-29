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

package com.liferay.layout.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutConvertHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class LayoutConvertHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		_contentLayout = null;
		_corruptedLayout = null;
		_nonConvertibleLayout = null;
		_privateLayout = null;
		_publicLayout = null;
	}

	@Test
	public void testConvertPublicLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());

		_layoutConvertHelper.convertLayout(layout.getPlid());

		Layout convertedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, convertedLayout.getType());
	}

	private Layout _contentLayout;
	private Layout _corruptedLayout;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutConvertHelper _layoutConvertHelper;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Layout _nonConvertibleLayout;
	private Layout _privateLayout;
	private Layout _publicLayout;

}