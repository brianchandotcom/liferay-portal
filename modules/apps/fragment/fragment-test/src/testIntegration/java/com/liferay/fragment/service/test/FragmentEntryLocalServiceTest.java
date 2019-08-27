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
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
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
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLocalServiceTest {

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
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsConfigurationPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, configuration, previewFileEntryId, type,
				status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			configuration, persistedFragmentEntry.getConfiguration());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsPreviewFileEntryIdAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, previewFileEntryId, status,
				serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, previewFileEntryId, type, status,
				serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, type, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNamePreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, previewFileEntryId, type, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			persistedFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithHtmlWithAmpersand() throws Exception {
		String html = "<H1>A&B&amp;C</H1>";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), html,
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomLong(), FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), name, css, html,
				js, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsPreviewFileEntryIdAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), name, css, html,
				js, previewFileEntryId, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), name, css, html,
				js, previewFileEntryId, type, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsTypeAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_PENDING;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), name, css, html,
				js, type, status, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(type, persistedFragmentEntry.getType());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testCopyFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryLocalService.copyFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				fragmentEntry.getFragmentCollectionId(), serviceContext);

		_assertCopyFragmentEntry(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			fragmentEntry.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testCopyFragmentEntryToDifferentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryLocalService.copyFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				targetFragmentCollection.getFragmentCollectionId(),
				serviceContext);

		_assertCopyFragmentEntry(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			targetFragmentCollection.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryLocalService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testDeleteFragmentEntryByFragmentEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryLocalService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testFetchFragmentEntryByFragmentEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(fragmentEntry, persistedFragmentEntry);
	}

	@Test
	public void testFetchFragmentEntryByGroupIdAndFragmentEntryKey()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				StringUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"{fieldSets: []}", 0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				_group.getGroupId(), fragmentEntryKey);

		Assert.assertEquals(fragmentEntry, persistedFragmentEntry);
	}

	@Test
	public void testGenerateFragmentEntryKey() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_fragmentCollection.getFragmentCollectionId(), "test-key",
			StringUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			"{fieldSets: []}", 0, FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		String fragmentEntryKey =
			_fragmentEntryLocalService.generateFragmentEntryKey(
				_group.getGroupId(), "Test Key");

		Assert.assertEquals("test-key-0", fragmentEntryKey);
	}

	@Test
	public void testGetFragmentEntriesByFragmentCollectionId()
		throws Exception {

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdAndFragmentCollectionIdOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			Timestamp.valueOf(localDateTime));

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new FragmentEntryCreateDateComparator(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new FragmentEntryCreateDateComparator(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdAndFragmentCollectionIdOrderByNameComparator()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry");

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new FragmentEntryNameComparator(true));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new FragmentEntryNameComparator(false));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndNameOrderByCreateDateComparator()
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

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new FragmentEntryCreateDateComparator(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new FragmentEntryCreateDateComparator(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndNameOrderByNameComparator()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment");
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry");

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new FragmentEntryNameComparator(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new FragmentEntryNameComparator(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndStatus()
		throws Exception {

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED);
		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_DRAFT);
		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_DRAFT);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesCount() throws Exception {
		int originalCount = _fragmentEntryLocalService.getFragmentEntriesCount(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		int actualCount = _fragmentEntryLocalService.getFragmentEntriesCount(
			_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(originalCount + 2, actualCount);
	}

	@Test
	public void testMoveFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentEntryLocalService.moveFragmentEntry(
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
			RandomTestUtil.randomString());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Name Updated");

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			"Fragment Name Updated", persistedFragmentEntry.getName());
	}

	@Test
	public void testUpdateFragmentEntryNameCssHtmlJsConfigurationAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		int status = WorkflowConstants.STATUS_PENDING;

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			name, css, html, js, configuration, status);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			configuration, persistedFragmentEntry.getConfiguration());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryNameCssHtmlJsConfigurationPreviewFileEntryIdAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_PENDING;

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			name, css, html, js, configuration, previewFileEntryId, status);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(name, persistedFragmentEntry.getName());
		Assert.assertEquals(css, persistedFragmentEntry.getCss());
		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
		Assert.assertEquals(js, persistedFragmentEntry.getJs());
		Assert.assertEquals(
			configuration, persistedFragmentEntry.getConfiguration());
		Assert.assertEquals(
			previewFileEntryId, persistedFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, persistedFragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryPreviewFileEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		_fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), previewFileEntryId + 1);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(
			previewFileEntryId + 1,
			persistedFragmentEntry.getPreviewFileEntryId());
	}

	@Test
	public void testUpdateFragmentEntryWithHtmlWithAmpersand()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "<H1>A</H1>",
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomLong(), FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		String html = "<H1>A&B&amp;C</H1>";

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), fragmentEntry.getCss(), html,
			fragmentEntry.getJs(), null, WorkflowConstants.STATUS_APPROVED);

		FragmentEntry persistedFragmentEntry =
			_fragmentEntryPersistence.fetchByPrimaryKey(
				fragmentEntry.getFragmentEntryId());

		Assert.assertEquals(html, persistedFragmentEntry.getHtml());
	}

	private void _assertCopyFragmentEntry(
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
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentEntryPersistence _fragmentEntryPersistence;

	@DeleteAfterTestRun
	private Group _group;

}