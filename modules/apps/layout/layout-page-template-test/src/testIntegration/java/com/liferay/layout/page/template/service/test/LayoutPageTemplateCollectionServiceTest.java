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

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateCollectionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = DuplicateLayoutPageTemplateCollectionException.class)
	public void testAddDuplicateLayoutPageTemplateCollections()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection", null,
			serviceContext);

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection", null,
			serviceContext);
	}

	@Test
	public void testAddLayoutPageTemplateCollection() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		Assert.assertEquals(
			"Layout Page Template Collection",
			layoutPageTemplateCollection.getName());
	}

	@Test(expected = LayoutPageTemplateCollectionNameException.class)
	public void testAddLayoutPageTemplateCollectionWithEmptyName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), StringPool.BLANK, null, serviceContext);
	}

	@Test(expected = LayoutPageTemplateCollectionNameException.class)
	public void testAddLayoutPageTemplateCollectionWithNullName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), null, null, serviceContext);
	}

	@Test
	public void testAddMultipleLayoutPageTemplateCollections()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalLayoutPageTemplateCollectionsCount =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollectionsCount(_group.getGroupId());

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection 1",
			StringPool.BLANK, serviceContext);

		LayoutPageTemplateCollectionServiceUtil.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection 2",
			StringPool.BLANK, serviceContext);

		int actualLayoutPageTemplateCollectionsCount =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollectionsCount(_group.getGroupId());

		Assert.assertEquals(
			originalLayoutPageTemplateCollectionsCount + 2,
			actualLayoutPageTemplateCollectionsCount);
	}

	@Test
	public void testDeleteLayoutPageTemplate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		LayoutPageTemplateCollectionServiceUtil.
			deleteLayoutPageTemplateCollection(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		Assert.assertNull(
			LayoutPageTemplateCollectionServiceUtil.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()));
	}

	@Test
	public void testLayoutPageTemplateCollectionsWithPageTemplates()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		LayoutPageTemplateEntryServiceUtil.addLayoutPageTemplateEntry(
			_group.getGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Layout Page Template Entry", null, serviceContext);

		LayoutPageTemplateCollectionServiceUtil.
			deleteLayoutPageTemplateCollection(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		Assert.assertNull(
			LayoutPageTemplateCollectionServiceUtil.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}