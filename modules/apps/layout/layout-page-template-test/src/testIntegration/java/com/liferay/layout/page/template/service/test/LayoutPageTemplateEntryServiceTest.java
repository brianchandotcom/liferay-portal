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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.layout.page.template.service.persistence.impl.constants.LayoutPersistenceConstants;
import com.liferay.layout.page.template.util.LayoutPageTemplateTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
public class LayoutPageTemplateEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				LayoutPersistenceConstants.BUNDLE_SYMBOLIC_NAME));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutPageTemplateCollection =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateCollection(
				_group.getGroupId());
	}

	@Test(
		expected = LayoutPageTemplateEntryNameException.MustNotBeDuplicate.class
	)
	public void testAddDuplicateLayoutPageTemplateEntries() throws Exception {
		String name = RandomTestUtil.randomString();

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			name);

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			name);
	}

	@Test(expected = LayoutPageTemplateEntryNameException.class)
	public void testAddLayoutPageEntryWithNullName() throws Exception {
		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			null);
	}

	@Test
	public void testAddLayoutPageTemplateEntry() throws PortalException {
		String name = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name, serviceContext);

		LayoutPageTemplateEntry persistedLayoutPageTemplateEntry =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(name, persistedLayoutPageTemplateEntry.getName());
	}

	@Test
	public void testAddLayoutPageTemplateEntryByTypeAndStatus()
		throws PortalException {

		String name = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name, LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		Assert.assertEquals(name, layoutPageTemplateEntry.getName());
		Assert.assertEquals(
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
			layoutPageTemplateEntry.getType());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			layoutPageTemplateEntry.getStatus());
	}

	@Test(expected = LayoutPageTemplateEntryNameException.class)
	public void testAddLayoutPageTemplateEntryWithEmptyName() throws Exception {
		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			StringPool.BLANK);
	}

	@Test(expected = LayoutPageTemplateEntryNameException.class)
	public void testAddLayoutPageTemplateEntryWithSymbolInName()
		throws Exception {

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Test %&# Name");
	}

	@Test
	public void testAddLayoutPageTemplateEntryWithUTF8CharsInName()
		throws Exception {

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"你好andこんにちは");
	}

	@Test
	public void testAddMultipleLayoutPageTemplateEntries()
		throws PortalException {

		List<LayoutPageTemplateEntry> originalLayoutPageTemplateEntries =
			_layoutPageTemplateEntryPersistence.findByG_L(
				_layoutPageTemplateCollection.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		List<LayoutPageTemplateEntry> actualLayoutPageTemplateEntries =
			_layoutPageTemplateEntryPersistence.findByG_L(
				_layoutPageTemplateCollection.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		Assert.assertEquals(
			actualLayoutPageTemplateEntries.toString(),
			originalLayoutPageTemplateEntries.size() + 2,
			actualLayoutPageTemplateEntries.size());
	}

	@Test
	public void testDeleteLayoutPageTemplateEntries() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		long[] layoutPageTemplateEntries = {
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			layoutPageTemplateEntry2.getLayoutPageTemplateEntryId()
		};

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntries(
			layoutPageTemplateEntries);

		Assert.assertNull(
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry1.getLayoutPageTemplateEntryId()));

		Assert.assertNull(
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry2.getLayoutPageTemplateEntryId()));
	}

	@Test
	public void testDeleteLayoutPageTemplateEntry() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertNull(
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryByRemovingFragmentEntryIds()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry1 =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "{fieldSets: []}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry fragmentEntry2 =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "{fieldSets: []}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), fragmentEntryIds, serviceContext);

		_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), null, serviceContext);

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				_group.getGroupId(),
				PortalUtil.getClassNameId(
					LayoutPageTemplateEntry.class.getName()),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 0, fragmentEntryLinks.size());
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryClassNameAndClassType()
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		long classNameId = PortalUtil.getClassNameId(Layout.class);
		long classTypeId = RandomTestUtil.randomLong();

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				classNameId, classTypeId);

		LayoutPageTemplateEntry persistedLayoutPageTemplateEntry =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			classNameId, persistedLayoutPageTemplateEntry.getClassNameId());

		Assert.assertEquals(
			classTypeId, persistedLayoutPageTemplateEntry.getClassTypeId());
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryDefaultTemplate()
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);

		LayoutPageTemplateEntry persistedLayoutPageTemplateEntry =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertTrue(persistedLayoutPageTemplateEntry.isDefaultTemplate());
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryFragmentEntryIds()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry1 =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "{fieldSets: []}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry fragmentEntry2 =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "{fieldSets: []}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			layoutPageTemplateEntry.getName(), fragmentEntryIds,
			serviceContext);

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				_group.getGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 2, fragmentEntryLinks.size());
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryName() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		String updatedName = RandomTestUtil.randomString();

		_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			updatedName);

		LayoutPageTemplateEntry persistedLayoutPageTemplateEntry =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			updatedName, persistedLayoutPageTemplateEntry.getName());
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryStatus()
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		layoutPageTemplateEntry = _layoutPageTemplateEntryService.updateStatus(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			WorkflowConstants.STATUS_PENDING);

		LayoutPageTemplateEntry persistedLayoutPageTemplateEntry =
			_layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING,
			persistedLayoutPageTemplateEntry.getStatus());
	}

	@DeleteAfterTestRun
	private Group _group;

	private LayoutPageTemplateCollection _layoutPageTemplateCollection;

	@Inject
	private LayoutPageTemplateEntryPersistence
		_layoutPageTemplateEntryPersistence;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

}