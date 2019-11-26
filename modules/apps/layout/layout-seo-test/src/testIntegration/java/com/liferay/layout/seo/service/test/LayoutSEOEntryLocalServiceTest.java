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

package com.liferay.layout.seo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class LayoutSEOEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testAddLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNotNull(layoutSEOEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			StringPool.BLANK,
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			StringPool.BLANK, layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphTitleEnabled());
	}

	@Test
	public void testAddLayoutSEOEntryWithAllFields() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNotNull(layoutSEOEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			12345, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			"description",
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(
			"title", layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphTitleEnabled());
	}

	@Test
	public void testDeleteLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(
			_group.getGroupId(), false, _layout.getLayoutId());

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNull(layoutSEOEntry);
	}

	@Test
	public void testUpdateLayoutSEOEntry() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNotNull(layoutSEOEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			StringPool.BLANK,
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(0, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			StringPool.BLANK, layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertFalse(layoutSEOEntry.isOpenGraphTitleEnabled());
	}

	@Test
	public void testUpdateLayoutSEOEntryWithAllFields() throws PortalException {
		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "description"), 12345, true,
			Collections.singletonMap(LocaleUtil.US, "title"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_group.getGroupId(), false, _layout.getLayoutId());

		Assert.assertNotNull(layoutSEOEntry.getLayoutSEOEntryId());
		Assert.assertEquals(
			"http://example.com",
			layoutSEOEntry.getCanonicalURL(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isCanonicalURLEnabled());
		Assert.assertEquals(
			"description",
			layoutSEOEntry.getOpenGraphDescription(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(
			12345, layoutSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			"title", layoutSEOEntry.getOpenGraphTitle(LocaleUtil.US));
		Assert.assertTrue(layoutSEOEntry.isOpenGraphTitleEnabled());
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}