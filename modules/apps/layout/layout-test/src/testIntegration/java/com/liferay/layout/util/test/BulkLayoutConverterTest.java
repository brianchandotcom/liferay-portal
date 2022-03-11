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
import com.liferay.layout.exception.LayoutConvertException;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.BulkLayoutConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.HashMap;

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
public class BulkLayoutConverterTest {

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
		_privateLayout = null;
		_publicLayout = null;
	}

	@Test(expected = LayoutConvertException.class)
	public void testConvertCorruptedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			StringPool.BLANK);

		_bulkLayoutConverter.convertLayout(layout.getPlid());
	}

	@Test
	public void testConvertLayouts() throws Exception {
		_addLayouts();

		_assertPlids(
			_getConvertibleLayoutPlids(),
			_bulkLayoutConverter.convertLayouts(_getLayoutPlids()));

		_assertLayouts();
	}

	@Test
	public void testConvertLayoutsInGroup() throws Exception {
		_addLayouts();

		_assertPlids(
			_getConvertibleLayoutPlids(),
			_bulkLayoutConverter.convertLayouts(_group.getGroupId()));

		_assertLayouts();
	}

	@Test
	public void testConvertLinkedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(),
			UnicodePropertiesBuilder.put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).buildString());

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			StringUtil.randomString());

		layout.setLayoutPrototypeUuid(layoutPrototype.getUuid());

		layout.setLayoutPrototypeLinkEnabled(true);

		layout = _layoutLocalService.updateLayout(layout);

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());
		Assert.assertTrue(layout.isLayoutPrototypeLinkEnabled());
		Assert.assertNotNull(layout.getLayoutPrototypeUuid());

		_bulkLayoutConverter.convertLayout(layout.getPlid());

		Layout convertedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, convertedLayout.getType());

		Assert.assertFalse(convertedLayout.isLayoutPrototypeLinkEnabled());
		Assert.assertEquals(
			convertedLayout.getLayoutPrototypeUuid(), StringPool.BLANK);
	}

	@Test
	public void testConvertPrivateLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), true, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			UnicodePropertiesBuilder.put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).buildString(),
			new HashMap<>(), false);

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());

		_bulkLayoutConverter.convertLayout(layout.getPlid());

		Layout convertedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, convertedLayout.getType());
	}

	@Test
	public void testConvertPublicLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(),
			UnicodePropertiesBuilder.put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).buildString());

		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());

		_bulkLayoutConverter.convertLayout(layout.getPlid());

		Layout convertedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, convertedLayout.getType());
	}

	@Test
	public void testGetConvertibleLayoutPlids() throws Exception {
		_addLayouts();

		_assertPlids(
			_getConvertibleLayoutPlids(),
			_bulkLayoutConverter.getConvertibleLayoutPlids(
				_group.getGroupId()));
	}

	@Test
	public void testGetConvertibleLayoutPlidsAfterConvertLayoutsInGroup()
		throws Exception {

		_addLayouts();

		_bulkLayoutConverter.convertLayouts(_group.getGroupId());

		_assertPlids(
			new long[0],
			_bulkLayoutConverter.getConvertibleLayoutPlids(
				_group.getGroupId()));
	}

	private void _addLayouts() throws Exception {
		_contentLayout = LayoutTestUtil.addTypeContentLayout(_group);

		_corruptedLayout = LayoutTestUtil.addTypePortletLayout(_group);

		_layoutLocalService.updateLayout(
			_corruptedLayout.getGroupId(), _corruptedLayout.isPrivateLayout(),
			_corruptedLayout.getLayoutId(), StringPool.BLANK);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).build();

		_privateLayout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), true, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			typeSettingsUnicodeProperties.toString(), new HashMap<>(), false);

		_publicLayout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), typeSettingsUnicodeProperties.toString());

		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _contentLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _corruptedLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _privateLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_PORTLET, _publicLayout.getType());
	}

	private void _assertLayouts() throws Exception {
		_contentLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_contentLayout.getUuid(), _contentLayout.getGroupId(),
			_contentLayout.isPrivateLayout());
		_corruptedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_corruptedLayout.getUuid(), _corruptedLayout.getGroupId(),
			_corruptedLayout.isPrivateLayout());
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
			LayoutConstants.TYPE_CONTENT, _privateLayout.getType());
		Assert.assertEquals(
			LayoutConstants.TYPE_CONTENT, _publicLayout.getType());
	}

	private void _assertPlids(long[] expectedPlids, long[] actualPlids) {
		for (long plid : expectedPlids) {
			Assert.assertTrue(ArrayUtil.contains(actualPlids, plid));
		}

		Assert.assertEquals(
			Arrays.toString(actualPlids), expectedPlids.length,
			actualPlids.length);
	}

	private long[] _getConvertibleLayoutPlids() {
		return new long[] {_privateLayout.getPlid(), _publicLayout.getPlid()};
	}

	private long[] _getLayoutPlids() {
		return new long[] {
			_contentLayout.getPlid(), _corruptedLayout.getPlid(),
			_privateLayout.getPlid(), _publicLayout.getPlid()
		};
	}

	@Inject
	private BulkLayoutConverter _bulkLayoutConverter;

	private Layout _contentLayout;
	private Layout _corruptedLayout;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Layout _privateLayout;
	private Layout _publicLayout;

}