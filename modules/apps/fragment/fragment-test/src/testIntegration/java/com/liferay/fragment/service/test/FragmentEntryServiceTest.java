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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		Assert.assertEquals("Fragment Entry", fragmentEntry.getName());
	}

	@Test
	public void testAddFragmentEntryByFragmentEntryKeyAndStatus()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry",
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		Assert.assertEquals(
			"fragmententrykey", fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryByFragmentEntryKeyAndType()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry",
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		Assert.assertEquals(
			"fragmententrykey", fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, fragmentEntry.getType());
	}

	@Test
	public void testAddFragmentEntryByType() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, fragmentEntry.getType());
	}

	@Test
	public void testAddFragmentEntryByTypeAndValidHTML()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		String html = "<div>Valid HTML</div>";

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, html, null,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, fragmentEntry.getType());
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testAddFragmentEntryWithEmptyHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			null, WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithoutHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", "div {\ncolor: red\n}", "<div>Test</div>",
			"alert(\"test\")", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryService.copyFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentCollection targetFragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", "div {\ncolor: red\n}", "<div>Test</div>",
			"alert(\"test\")", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryService.copyFragmentEntry(
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
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry1 = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry fragmentEntry2 = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		_fragmentEntryService.deleteFragmentEntries(fragmentEntryIds);

		Assert.assertNull(
			_fragmentEntryService.fetchFragmentEntry(
				fragmentEntry1.getFragmentEntryId()));

		Assert.assertNull(
			_fragmentEntryService.fetchFragmentEntry(
				fragmentEntry2.getFragmentEntryId()));
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			_fragmentEntryService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testGetFragmentCollectionsCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry 1", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry 2", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountByKeyword() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Entry");

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry One", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry Two", WorkflowConstants.STATUS_DENIED,
			serviceContext);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Entry");

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountByKeywordAndStatus()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Entry",
				WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry One", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry Two", WorkflowConstants.STATUS_DENIED,
			serviceContext);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Entry",
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountByStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalApprovedFragmentEntryCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		int originalDraftFragmentEntryCount =
			_fragmentEntryService.getFragmentCollectionsCount(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		List<FragmentEntry> approvedFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		List<FragmentEntry> draftFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
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
	public void testGetFragmentCollectionsCountByType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCountByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYONE", "Fragment Entry One",
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYTWO", "Fragment Entry Two",
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentCollectionsCountByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT);

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentEntriesByKeywordAndStatusOrderByCreateDateComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AD Fragment",
			WorkflowConstants.STATUS_DRAFT, Timestamp.valueOf(localDateTime));

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			WorkflowConstants.STATUS_ANY, Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			WorkflowConstants.STATUS_ANY, Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			WorkflowConstants.STATUS_ANY, Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByKeywordAndStatusOrderByNameComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AC Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AD Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Entry",
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Entry", WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryNameComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByKeywordOrderByCreateDateComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByKeywordOrderByNameComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AC Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryNameComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryNameComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByStatusOrderByCreateDateComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByStatusOrderByNameComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AC Fragment Entry", WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryNameComparatorDesc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYONE", "Fragment Entry One",
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYTWO", "Fragment Entry Two",
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 1, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesByTypeOrderByCreateDateComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntryTestUtil.addFragmentEntryByType(
			fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			FragmentConstants.TYPE_SECTION);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByType(
				fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", FragmentConstants.TYPE_COMPONENT);

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntryByType(
			fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			FragmentConstants.TYPE_COMPONENT);

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByType(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_COMPONENT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByTypeOrderByNameComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntryTestUtil.addFragmentEntryByType(
			fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			FragmentConstants.TYPE_SECTION);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByType(
				fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", FragmentConstants.TYPE_COMPONENT);

		FragmentEntryTestUtil.addFragmentEntryByType(
			fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			FragmentConstants.TYPE_COMPONENT);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByType(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_COMPONENT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryNameComparatorDesc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesOrderByCreateDateComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		LocalDateTime localDateTime = LocalDateTime.now();

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AC Fragment Entry", WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesOrderByNameComparator() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"AA Fragment", WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryNameComparatorDesc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testMoveFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection1 =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection1.getFragmentCollectionId(),
			"Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentCollection fragmentCollection2 =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		_fragmentEntryService.moveFragmentEntry(
			fragmentEntry.getFragmentEntryId(),
			fragmentCollection2.getFragmentCollectionId());

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			fragmentCollection2.getFragmentCollectionId(),
			persistedFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testUpdateFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Name Original", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Name Updated");

		Assert.assertEquals("Fragment Name Updated", fragmentEntry.getName());
	}

	@Test
	public void testUpdateFragmentEntryPreviewFileEntryId() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry Original", null,
			"<div>Original</div>", null, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		Assert.assertEquals(0, fragmentEntry.getPreviewFileEntryId());

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Entry Updated",
			"div {\ncolor: red;\n}", "<div>Updated</div>", "alert(\"test\");",
			1, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals("Fragment Entry Updated", fragmentEntry.getName());

		Assert.assertEquals("div {\ncolor: red;\n}", fragmentEntry.getCss());

		Assert.assertEquals("<div>Updated</div>", fragmentEntry.getHtml());

		Assert.assertEquals("alert(\"test\");", fragmentEntry.getJs());

		Assert.assertEquals(1, fragmentEntry.getPreviewFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryValues() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry Original", null,
			"<div>Original</div>", null, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
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

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

}