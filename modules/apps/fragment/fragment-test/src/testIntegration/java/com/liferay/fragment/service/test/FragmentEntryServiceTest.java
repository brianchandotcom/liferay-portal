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
import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());
	}

	@Test
	public void testAddFragmentEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testAddFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String name = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			"{fieldSets: []}", 0, FragmentConstants.TYPE_SECTION,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
	}

	@Test(expected = FragmentEntryNameException.class)
	public void testAddFragmentEntryUsingEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringPool.BLANK, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = FragmentEntryConfigurationException.class)
	public void testAddFragmentEntryUsingInvalidConfiguration()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			"<div></div>", null,
			_read("configuration-invalid-missing-field-sets.json"), 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test
	public void testAddFragmentEntryUsingMixedHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String html = "<div>Text Inside</div> Text Outside";

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, html, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testAddFragmentEntryUsingNullHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, null, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test(expected = FragmentEntryNameException.class)
	public void testAddFragmentEntryUsingNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			null, WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryUsingPlainTextHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, "Text only fragment", null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryUsingValidConfiguration() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String configuration = _read("configuration-valid-complete.json");

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			"<div></div>", null, configuration, 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			configuration, persistedFragmentEntry.getConfiguration());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKey() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"fragmententrykey", persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyAndType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"fragmententrykey", persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, persistedFragmentEntry.getType());
	}

	@Test
	public void testAddFragmentEntryWithHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String html = "<div>Valid HTML</div>";

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, html, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
	}

	@Test
	public void testAddFragmentEntryWithType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, persistedFragmentEntry.getType());
	}

	@Test
	public void testAddFragmentEntryWithTypeAndHTML() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String html = "<div>Valid HTML</div>";

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), null, html, null,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(
			FragmentConstants.TYPE_COMPONENT, persistedFragmentEntry.getType());
	}

	@Test
	public void testCopyFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String name = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			name, "div {\ncolor: red\n}", "<div>Test</div>", "alert(\"test\")",
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryService.copyFragmentEntry(
				_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
				fragmentEntry.getFragmentCollectionId(), serviceContext);

		_assertCopiedFragment(fragmentEntry, copyFragmentEntry);
	}

	@Test
	public void testCopyFragmentEntryToDifferentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			name, "div {\ncolor: red\n}", "<div>Test</div>", "alert(\"test\")",
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryService.copyFragmentEntry(
				_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
				targetFragmentCollection.getFragmentCollectionId(),
				serviceContext);

		_assertCopiedFragment(fragmentEntry, copyFragmentEntry);
	}

	@Test
	public void testDeleteFragmentEntries() throws Exception {
		FragmentEntry fragmentEntry1 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntry fragmentEntry2 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		_fragmentEntryService.deleteFragmentEntries(fragmentEntryIds);

		Assert.assertNull(
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry1.getFragmentEntryId()));

		Assert.assertNull(
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry2.getFragmentEntryId()));
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testFetchFragmentEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), name);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByKeywordAndStatusOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AD Fragment",
			WorkflowConstants.STATUS_DRAFT, Timestamp.valueOf(localDateTime));

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByStatus(
				_fragmentCollection.getFragmentCollectionId(),
				"AC Fragment Entry", WorkflowConstants.STATUS_APPROVED,
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			WorkflowConstants.STATUS_APPROVED,
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			WorkflowConstants.STATUS_APPROVED,
			Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByName(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByName(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByNameAndStatusOrderByNameComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), "AC Fragment Entry",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), "AA Fragment",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), "AB Fragment Entry",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), "AD Fragment Entry",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByNameAndStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByNameAndStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByNameOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByName(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByName(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByNameOrderByNameComparator()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry");

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByName(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryNameComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByName(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
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

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			WorkflowConstants.STATUS_DRAFT, Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByStatus(
				_fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED,
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			WorkflowConstants.STATUS_APPROVED,
			Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorAsc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		FragmentEntryCreateDateComparator
			fragmentEntryCreateDateComparatorDesc =
				new FragmentEntryCreateDateComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByStatus(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByStatusOrderByNameComparator()
		throws Exception {

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			WorkflowConstants.STATUS_DRAFT);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByStatus(
				_fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentEntryNameComparatorAsc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntriesByStatus(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
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

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYONE", "Fragment Entry One",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEYTWO", "Fragment Entry Two",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 1, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesByTypeOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			FragmentConstants.TYPE_SECTION, Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByType(
				_fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", FragmentConstants.TYPE_COMPONENT,
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			FragmentConstants.TYPE_COMPONENT, Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
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
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
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

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			FragmentConstants.TYPE_SECTION);

		FragmentEntry fragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByType(
				_fragmentCollection.getFragmentCollectionId(),
				"AB Fragment Entry", FragmentConstants.TYPE_COMPONENT);

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			FragmentConstants.TYPE_COMPONENT);

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntriesByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
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
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_COMPONENT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, fragmentEntryNameComparatorDesc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesCount() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCount(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCount(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentEntriesCountByName() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByName(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				"Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "Fragment Entry One",
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(), "Fragment Entry Two",
			WorkflowConstants.STATUS_DENIED);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByName(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				"Fragment Entry");

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentEntriesCountByNameAndStatus() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByNameAndStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Fragment Entry",
				WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "Fragment Entry One",
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(), "Fragment Entry Two",
			WorkflowConstants.STATUS_DENIED);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByNameAndStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Fragment Entry",
				WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentEntriesCountByStatus() throws Exception {
		int originalApprovedFragmentEntryCount =
			_fragmentEntryService.getFragmentEntriesCountByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		int originalDraftFragmentEntryCount =
			_fragmentEntryService.getFragmentEntriesCountByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_DRAFT);

		List<FragmentEntry> approvedFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED);

		List<FragmentEntry> draftFragmentEntries =
			_fragmentEntryService.getFragmentEntriesByStatus(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
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
	public void testGetFragmentEntriesCountByType() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT);

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_COMPONENT);

		FragmentEntryTestUtil.addFragmentEntryByType(
			_fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_SECTION);

		int actualFragmentCollectionsCount =
			_fragmentEntryService.getFragmentEntriesCountByType(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				FragmentConstants.TYPE_COMPONENT);

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentEntriesOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			Timestamp.valueOf(localDateTime));

		FragmentEntryCreateDateComparator fragmentEntryCreateDateComparatorAsc =
			new FragmentEntryCreateDateComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
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
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryCreateDateComparatorDesc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesOrderByNameComparator() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry");

		FragmentEntryNameComparator fragmentEntryNameComparatorAsc =
			new FragmentEntryNameComparator(true);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentEntryNameComparatorAsc);

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		FragmentEntryNameComparator fragmentEntryNameComparatorDesc =
			new FragmentEntryNameComparator(false);

		fragmentEntries = _fragmentEntryService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			fragmentEntryNameComparatorDesc);

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testMoveFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentEntryService.moveFragmentEntry(
			fragmentEntry.getFragmentEntryId(),
			targetFragmentCollection.getFragmentCollectionId());

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			targetFragmentCollection.getFragmentCollectionId(),
			persistedFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testUpdateFragmentEntryName() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(),
			"Fragment Name Original");

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Name Updated");

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"Fragment Name Updated", persistedFragmentEntry.getName());
	}

	@Test
	public void testUpdateFragmentEntryValues() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry Original", null,
			"<div>Original</div>", null, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Entry Updated",
			"div {\ncolor: red;\n}", "<div>Updated</div>", "alert(\"test\");",
			"{\n\t\"fieldSets\": [\n\t]\n}", WorkflowConstants.STATUS_APPROVED);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"Fragment Entry Updated", persistedFragmentEntry.getName());
		Assert.assertEquals(
			"div {\ncolor: red;\n}", persistedFragmentEntry.getCss());
		Assert.assertEquals(
			"<div>Updated</div>", persistedFragmentEntry.getHtml());
		Assert.assertEquals("alert(\"test\");", persistedFragmentEntry.getJs());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			persistedFragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryValuesAndPreviewFileEntryId()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"FRAGMENTENTRYKEY", "Fragment Entry Original", null,
			"<div>Original</div>", null, WorkflowConstants.STATUS_DRAFT,
			serviceContext);

		Assert.assertEquals(0, fragmentEntry.getPreviewFileEntryId());

		fragmentEntry = _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Entry Updated",
			"div {\ncolor: red;\n}", "<div>Updated</div>", "alert(\"test\");",
			"{\n\t\"fieldSets\": [\n\t]\n}", 1,
			WorkflowConstants.STATUS_APPROVED);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"Fragment Entry Updated", persistedFragmentEntry.getName());
		Assert.assertEquals(
			"div {\ncolor: red;\n}", persistedFragmentEntry.getCss());
		Assert.assertEquals(
			"<div>Updated</div>", persistedFragmentEntry.getHtml());
		Assert.assertEquals("alert(\"test\");", persistedFragmentEntry.getJs());
		Assert.assertEquals(1, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			persistedFragmentEntry.getStatus());
	}

	@Test
	public void testUpdatePreviewFileEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), previewFileEntryId + 1);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			previewFileEntryId + 1,
			persistedFragmentEntry.getPreviewFileEntryId());
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

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentEntryPersistence _fragmentEntryPersistence;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

}