/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
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
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Layout Page Template Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Layout Page Template Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);
	}

	@Test
	public void testAddLayoutPageTemplateCollection() throws PortalException {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			"Layout Page Template Collection",
			layoutPageTemplateCollection.getName());
	}

	@Test(expected = LayoutPageTemplateCollectionNameException.class)
	public void testAddLayoutPageTemplateCollectionWithEmptyName()
		throws Exception {

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			StringPool.BLANK, null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test(expected = LayoutPageTemplateCollectionNameException.class)
	public void testAddLayoutPageTemplateCollectionWithNullName()
		throws Exception {

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			null, null, LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testAddMultipleLayoutPageTemplateCollections()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Layout Page Template Collection 1", StringPool.BLANK,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Layout Page Template Collection 2", StringPool.BLANK,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		int actualLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

		Assert.assertEquals(
			originalLayoutPageTemplateCollectionsCount + 2,
			actualLayoutPageTemplateCollectionsCount);
	}

	@Test
	public void testDeleteLayoutPageTemplateCollection() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId(), TestPropsValues.getUserId()));

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
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 1", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection2 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 2", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		_layoutPageTemplateCollectionService.
			deleteLayoutPageTemplateCollections(
				new long[] {
					layoutPageTemplateCollection1.
						getLayoutPageTemplateCollectionId(),
					layoutPageTemplateCollection2.
						getLayoutPageTemplateCollectionId()
				});

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
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 1", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection2 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 2", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		List<LayoutPageTemplateCollection> actualLayoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

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

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"AA Page Template Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"AB Page Template Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"AC Page Template Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		OrderByComparator<LayoutPageTemplateCollection> orderByComparator =
			new LayoutPageTemplateCollectionNameComparator(true);

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		LayoutPageTemplateCollection firstLayoutPageTemplateCollection =
			layoutPageTemplateCollections.get(0);

		Assert.assertEquals(
			firstLayoutPageTemplateCollection, layoutPageTemplateCollection);

		orderByComparator = new LayoutPageTemplateCollectionNameComparator(
			false);

		layoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		LayoutPageTemplateCollection lastLayoutPageTemplateCollection =
			layoutPageTemplateCollections.get(
				layoutPageTemplateCollections.size() - 1);

		Assert.assertEquals(
			lastLayoutPageTemplateCollection, layoutPageTemplateCollection);
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
					_group.getGroupId(), "Theme",
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Fjord Theme collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"Theme Westeros collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		int actualLayoutPageTemplateCollectionsCount =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					_group.getGroupId(), "Theme",
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

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

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"AA Fjord Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"AB Theme Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		_layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(
			_group.getGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			"AC Theme Collection", null,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, serviceContext);

		OrderByComparator<LayoutPageTemplateCollection> orderByComparator =
			new LayoutPageTemplateCollectionNameComparator(true);

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(), "Theme",
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		LayoutPageTemplateCollection firstLayoutPageTemplateCollection =
			layoutPageTemplateCollections.get(0);

		Assert.assertEquals(
			firstLayoutPageTemplateCollection, layoutPageTemplateCollection);

		orderByComparator = new LayoutPageTemplateCollectionNameComparator(
			false);

		layoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(), "Theme",
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		LayoutPageTemplateCollection lastLayoutPageTemplateCollection =
			layoutPageTemplateCollections.get(
				layoutPageTemplateCollections.size() - 1);

		Assert.assertEquals(
			lastLayoutPageTemplateCollection, layoutPageTemplateCollection);
	}

	@Test
	public void testGetLayoutPageTemplateCollectionsByRange() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection1 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 1", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		LayoutPageTemplateCollection layoutPageTemplateCollection2 =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection 2", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		List<LayoutPageTemplateCollection> actualLayoutPageTemplateCollections =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollections(
					_group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 2, 0);

		Assert.assertTrue(
			actualLayoutPageTemplateCollections.contains(
				layoutPageTemplateCollection1));
		Assert.assertTrue(
			actualLayoutPageTemplateCollections.contains(
				layoutPageTemplateCollection2));
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
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			_group.getGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Layout Page Template Entry",
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
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

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					"Layout Page Template Collection", null,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId(), TestPropsValues.getUserId()));

		layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					"Layout Page Template Collection New", "Description New");

		Assert.assertEquals(
			"layout-page-template-collection-new",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionKey());
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