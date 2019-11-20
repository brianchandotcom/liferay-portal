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
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

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
			PermissionCheckerMethodTestRule.INSTANCE);

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

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection", null,
			serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection", null,
			serviceContext);
	}

	@Test
	public void testAddLayoutPageTemplateCollection() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
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

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), StringPool.BLANK, null, serviceContext);
	}

	@Test(expected = LayoutPageTemplateCollectionNameException.class)
	public void testAddLayoutPageTemplateCollectionWithNullName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), null, null, serviceContext);
	}

	@Test
	public void testAddMultipleLayoutPageTemplateCollections()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(_group.getGroupId());

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection 1",
			StringPool.BLANK, serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Layout Page Template Collection 2",
			StringPool.BLANK, serviceContext);

		int actualLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(_group.getGroupId());

		Assert.assertEquals(
			originalLayoutPageTemplateCollectionsCount + 2,
			actualLayoutPageTemplateCollectionsCount);
	}

	@Test
	public void testDeleteLayoutPageTemplateCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()));
	}

	@Test
	public void testDeleteLayoutPageTemplateCollections() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection1 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection 1",
					null, serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection2 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection 2",
					null, serviceContext);

		long[] layoutPageTemplateCollections = {
			layoutPageTemplateCollection1.getLayoutPageTemplateCollectionId(),
			layoutPageTemplateCollection2.getLayoutPageTemplateCollectionId()
		};

		_layoutPageTemplateCollectionService.
			deleteLayoutPageTemplateCollections(layoutPageTemplateCollections);

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection1.
						getLayoutPageTemplateCollectionId()));

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection2.
						getLayoutPageTemplateCollectionId()));
	}

	@Test
	public void testGetLayoutPageTemplateCollections() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection1 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection 1",
					null, serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection2 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection 2",
					null, serviceContext);

		List<LayoutPageTemplateCollection> actualLayoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(_group.getGroupId());

		Assert.assertTrue(
			actualLayoutPageTemplateCollections.contains(
				layoutPageTemplateCollection1));
		Assert.assertTrue(
			actualLayoutPageTemplateCollections.contains(
				layoutPageTemplateCollection2));
	}

	@Test
	public void testGetLayoutPageTemplateCollectionsByComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Group group = GroupTestUtil.addGroup();

		try {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						group.getGroupId(), "AA Page Template Collection", null,
						serviceContext);

			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					group.getGroupId(), "AB Page Template Collection", null,
					serviceContext);

			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					group.getGroupId(), "AC Page Template Collection", null,
					serviceContext);

			OrderByComparator orderByComparator =
				new LayoutPageTemplateCollectionNameComparator(true);

			List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollections(
						group.getGroupId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, orderByComparator);

			LayoutPageTemplateCollection firstLayoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			Assert.assertEquals(
				firstLayoutPageTemplateCollection,
				layoutPageTemplateCollection);

			orderByComparator = new LayoutPageTemplateCollectionNameComparator(
				false);

			layoutPageTemplateCollections =
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollections(
						group.getGroupId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, orderByComparator);

			LayoutPageTemplateCollection lastLayoutPageTemplateCollection =
				layoutPageTemplateCollections.get(
					layoutPageTemplateCollections.size() - 1);

			Assert.assertEquals(
				lastLayoutPageTemplateCollection, layoutPageTemplateCollection);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetLayoutPageTemplateCollectionsByKeywords()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					_group.getGroupId(), "Theme");

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Fjord Theme collection", null,
			serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(), "Theme Westeros collection", null,
			serviceContext);

		int actualLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					_group.getGroupId(), "Theme");

		Assert.assertEquals(
			originalLayoutPageTemplateCollectionsCount + 2,
			actualLayoutPageTemplateCollectionsCount);
	}

	@Test
	public void testGetLayoutPageTemplateCollectionsByKeywordsAndComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Group group = GroupTestUtil.addGroup();

		try {
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					group.getGroupId(), "AA Fjord Collection", null,
					serviceContext);

			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						group.getGroupId(), "AB Theme Collection", null,
						serviceContext);

			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					group.getGroupId(), "AC Theme Collection", null,
					serviceContext);

			OrderByComparator orderByComparator =
				new LayoutPageTemplateCollectionNameComparator(true);

			List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollections(
						group.getGroupId(), "Theme", QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, orderByComparator);

			LayoutPageTemplateCollection firstLayoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			Assert.assertEquals(
				firstLayoutPageTemplateCollection,
				layoutPageTemplateCollection);

			orderByComparator = new LayoutPageTemplateCollectionNameComparator(
				false);

			layoutPageTemplateCollections =
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollections(
						group.getGroupId(), "Theme", QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, orderByComparator);

			LayoutPageTemplateCollection lastLayoutPageTemplateCollection =
				layoutPageTemplateCollections.get(
					layoutPageTemplateCollections.size() - 1);

			Assert.assertEquals(
				lastLayoutPageTemplateCollection, layoutPageTemplateCollection);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetLayoutPageTemplateCollectionsByRange() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Group group = GroupTestUtil.addGroup();

		try {
			LayoutPageTemplateCollection layoutPageTemplateCollection1 =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						group.getGroupId(), "Layout Page Template Collection 1",
						null, serviceContext);

			LayoutPageTemplateCollection layoutPageTemplateCollection2 =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						group.getGroupId(), "Layout Page Template Collection 2",
						null, serviceContext);

			List<LayoutPageTemplateCollection>
				actualLayoutPageTemplateCollections =
					_layoutPageTemplateCollectionService.
						getLayoutPageTemplateCollections(
							group.getGroupId(), 0, 2);

			Assert.assertTrue(
				actualLayoutPageTemplateCollections.contains(
					layoutPageTemplateCollection1));
			Assert.assertTrue(
				actualLayoutPageTemplateCollections.contains(
					layoutPageTemplateCollection2));
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
	}

	@Test
	public void testLayoutPageTemplateCollectionsWithPageTemplates()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			_group.getGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Layout Page Template Entry",
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()));
	}

	@Test
	public void testUpdateLayoutPageTemplateCollection()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), "Layout Page Template Collection",
					null, serviceContext);

		layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					"Layout Page Template Collection New", "Description New");

		Assert.assertEquals(
			"Layout Page Template Collection New",
			layoutPageTemplateCollection.getName());

		Assert.assertEquals(
			"Description New", layoutPageTemplateCollection.getDescription());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

}