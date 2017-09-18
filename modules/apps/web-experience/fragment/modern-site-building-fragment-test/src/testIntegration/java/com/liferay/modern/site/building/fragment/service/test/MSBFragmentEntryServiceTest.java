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

package com.liferay.modern.site.building.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentEntryException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentEntryNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionServiceUtil;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

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
@Sync
public class MSBFragmentEntryServiceTest {

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

	@Test(expected = DuplicateMSBFragmentEntryException.class)
	public void testAddDuplicateFragmentEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), "Fragment Entry",
			null, null, null, serviceContext);

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), "Fragment Entry",
			null, null, null, serviceContext);
	}

	@Test
	public void testAddFragmentEntry() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentEntry fragmentEntry =
			MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
				_group.getGroupId(),
				fragmentCollection.getMsbFragmentCollectionId(),
				"Fragment Entry", null, null, null, serviceContext);

		Assert.assertEquals("Fragment Entry", fragmentEntry.getName());
	}

	@Test(expected = MSBFragmentEntryNameException.class)
	public void testAddFragmentEntryWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), StringPool.BLANK,
			null, null, null, serviceContext);
	}

	@Test(expected = MSBFragmentEntryNameException.class)
	public void testAddFragmentEntryWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), null, null, null,
			null, serviceContext);
	}

	@Test
	public void testAddMultipleFragmentEntries() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		List<MSBFragmentEntry> originalFragmentEntries =
			MSBFragmentEntryServiceUtil.fetchMSBFragmentEntries(
				fragmentCollection.getMsbFragmentCollectionId());

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), "Fragment Entry 1",
			null, null, null, serviceContext);

		MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
			_group.getGroupId(),
			fragmentCollection.getMsbFragmentCollectionId(), "Fragment Entry 2",
			null, null, null, serviceContext);

		List<MSBFragmentEntry> actualFragmentEntries =
			MSBFragmentEntryServiceUtil.fetchMSBFragmentEntries(
				fragmentCollection.getMsbFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentEntry fragmentEntry =
			MSBFragmentEntryServiceUtil.addMSBFragmentEntry(
				_group.getGroupId(),
				fragmentCollection.getMsbFragmentCollectionId(),
				"Fragment Entry", null, null, null, serviceContext);

		MSBFragmentEntryServiceUtil.deleteMSBFragmentEntry(
			fragmentEntry.getMsbFragmentEntryId());

		Assert.assertNull(
			MSBFragmentEntryServiceUtil.fetchMSBFragmentEntry(
				fragmentEntry.getMsbFragmentEntryId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}