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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
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
	public void testConvertPrivateLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group, true);

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());

		_layoutConvertHelper.convertLayout(layout.getPlid());

		Layout convertedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, convertedLayout.getType());
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

	private void _addLayouts() throws Exception {
		_contentLayout = LayoutTestUtil.addLayout(_group);

		_contentLayout.setType(LayoutConstants.TYPE_CONTENT);

		_contentLayout = _layoutLocalService.updateLayout(_contentLayout);

		_corruptedLayout = LayoutTestUtil.addLayout(_group);

		_layoutLocalService.updateLayout(
			_corruptedLayout.getGroupId(), _corruptedLayout.isPrivateLayout(),
			_corruptedLayout.getLayoutId(), StringPool.BLANK);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutConstants.CUSTOMIZABLE_LAYOUT, Boolean.TRUE.toString());

		_nonConvertibleLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		_privateLayout = LayoutTestUtil.addLayout(_group, true);
		_publicLayout = LayoutTestUtil.addLayout(_group, false);

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _contentLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _corruptedLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _nonConvertibleLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _privateLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _publicLayout.getType());
	}

	private void _assertLayouts() throws PortalException {
		_contentLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_contentLayout.getUuid(), _contentLayout.getGroupId(),
			_contentLayout.isPrivateLayout());
		_corruptedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_corruptedLayout.getUuid(), _corruptedLayout.getGroupId(),
			_corruptedLayout.isPrivateLayout());
		_nonConvertibleLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_nonConvertibleLayout.getUuid(), _nonConvertibleLayout.getGroupId(),
			_nonConvertibleLayout.isPrivateLayout());
		_privateLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_privateLayout.getUuid(), _privateLayout.getGroupId(),
			_privateLayout.isPrivateLayout());
		_publicLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_publicLayout.getUuid(), _publicLayout.getGroupId(),
			_publicLayout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _contentLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _corruptedLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _nonConvertibleLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _privateLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _publicLayout.getType());
	}

	private void _assertPlids(long[] expectedPlids, long[] actualPlids) {
		for (long plid : expectedPlids) {
			Assert.assertTrue(ArrayUtil.contains(actualPlids, plid));
		}

		Assert.assertEquals(expectedPlids.length, actualPlids.length);
	}

	private long[] _getConvertibleLayoutsPlids() {
		return new long[] {_privateLayout.getPlid(), _publicLayout.getPlid()};
	}

	private long[] _getLayoutsPlids() {
		return new long[] {
			_contentLayout.getPlid(), _corruptedLayout.getPlid(),
			_nonConvertibleLayout.getPlid(), _privateLayout.getPlid(),
			_publicLayout.getPlid()
		};
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