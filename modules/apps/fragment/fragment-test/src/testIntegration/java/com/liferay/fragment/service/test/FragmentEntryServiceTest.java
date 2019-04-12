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

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
public class FragmentEntryServiceTest {

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

	@Test
	public void testAddFragmentEntry() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		Assert.assertEquals("Fragment Entry", fragmentEntry.getName());
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testAddFragmentEntryWithEmptyHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, null, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test(expected = FragmentEntryNameException.class)
	public void testAddFragmentEntryWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			StringPool.BLANK, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testAddFragmentEntryWithInvalidHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, "<div id=\"divId></div>", null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithMixedHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null,
			"<div>Text Inside</div> Text Outside", null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test(expected = FragmentEntryNameException.class)
	public void testAddFragmentEntryWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			null, WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithoutHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, "Text only fragment", null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddMultipleFragmentEntries() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		List<FragmentEntry> originalFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testCopyFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", "div {\ncolor: red\n}", "<div>Test</div>",
			"alert(\"test\")", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry copyFragmentEntry =
			FragmentEntryServiceUtil.copyFragmentEntry(
				_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
				fragmentEntry.getFragmentCollectionId(), serviceContext);

		_assertCopiedFragment(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			fragmentEntry.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testCopyFragmentEntryToDifferentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentCollection targetFragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", "div {\ncolor: red\n}", "<div>Test</div>",
			"alert(\"test\")", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry copyFragmentEntry =
			FragmentEntryServiceUtil.copyFragmentEntry(
				_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
				targetFragmentCollection.getFragmentCollectionId(),
				serviceContext);

		_assertCopiedFragment(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			targetFragmentCollection.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testDeleteFragmentEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry1 =
			FragmentEntryServiceUtil.addFragmentEntry(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry fragmentEntry2 =
			FragmentEntryServiceUtil.addFragmentEntry(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		FragmentEntryServiceUtil.deleteFragmentEntries(fragmentEntryIds);

		Assert.assertNull(
			FragmentEntryServiceUtil.fetchFragmentEntry(
				fragmentEntry1.getFragmentEntryId()));

		Assert.assertNull(
			FragmentEntryServiceUtil.fetchFragmentEntry(
				fragmentEntry2.getFragmentEntryId()));
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			FragmentEntryServiceUtil.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testGetFragmentCollectionsCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalFragmentCollectionsCount =
			FragmentEntryServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId());

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry 1", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry 2", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		int actualFragmentCollectionsCount =
			FragmentEntryServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountByKeywords() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		String fragmentEntryName = RandomTestUtil.randomString();

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			fragmentEntryName, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			fragmentEntryName, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), fragmentEntryName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			actualFragmentEntries.toString(), 2, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentCollectionsCountByKeywordsAndStatus()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		String fragmentEntryName = RandomTestUtil.randomString();

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			fragmentEntryName, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			fragmentEntryName, WorkflowConstants.STATUS_DRAFT, serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), fragmentEntryName,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			actualFragmentEntries.toString(), 1, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentCollectionsCountByStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalApprovedFragmentEntryCount =
			FragmentEntryServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		int originalDraftFragmentEntryCount =
			FragmentEntryServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		List<FragmentEntry> approvedFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		List<FragmentEntry> draftFragmentEntries =
			FragmentEntryServiceUtil.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			approvedFragmentEntries.toString(),
			originalApprovedFragmentEntryCount + 2,
			approvedFragmentEntries.size());

		Assert.assertEquals(
			draftFragmentEntries.toString(),
			originalDraftFragmentEntryCount + 1, draftFragmentEntries.size());
	}

	@Test
	public void testUpdateFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Name Original", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		fragmentEntry = FragmentEntryServiceUtil.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Name Updated");

		Assert.assertEquals("Fragment Name Updated", fragmentEntry.getName());
	}

	@Test
	public void testUpdateFragmentEntryValues() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry Original", null,
			"<div>Original</div>", null, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		fragmentEntry = FragmentEntryServiceUtil.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Entry Updated",
			"div {\ncolor: red;\n}", "<div>Updated</div>", "alert(\"test\");",
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals("Fragment Entry Updated", fragmentEntry.getName());

		Assert.assertEquals("div {\ncolor: red;\n}", fragmentEntry.getCss());

		Assert.assertEquals("<div>Updated</div>", fragmentEntry.getHtml());

		Assert.assertEquals("alert(\"test\");", fragmentEntry.getJs());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fragmentEntry.getStatus());
	}

	private void _assertCopiedFragment(
		FragmentEntry fragmentEntry, FragmentEntry copyFragmentEntry) {

		Assert.assertEquals(
			fragmentEntry.getGroupId(), copyFragmentEntry.getGroupId());

		Assert.assertEquals(
			fragmentEntry.getName() + " (Copy)", copyFragmentEntry.getName());

		Assert.assertEquals(fragmentEntry.getCss(), copyFragmentEntry.getCss());

		Assert.assertEquals(
			fragmentEntry.getHtml(), copyFragmentEntry.getHtml());

		Assert.assertEquals(fragmentEntry.getJs(), copyFragmentEntry.getJs());

		Assert.assertEquals(
			fragmentEntry.getStatus(), copyFragmentEntry.getStatus());

		Assert.assertEquals(
			fragmentEntry.getType(), copyFragmentEntry.getType());
	}

	@DeleteAfterTestRun
	private Group _group;

}