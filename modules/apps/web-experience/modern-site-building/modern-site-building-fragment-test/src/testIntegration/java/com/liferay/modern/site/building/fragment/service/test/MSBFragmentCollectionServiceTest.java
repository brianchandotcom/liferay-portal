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
import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentCollectionException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentCollectionNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
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
public class MSBFragmentCollectionServiceTest {

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

	@Test(expected = DuplicateMSBFragmentCollectionException.class)
	public void testAddDuplicateFragmentCollections() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), "Fragment Collection", null, serviceContext);

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), "Fragment Collection", null, serviceContext);
	}

	@Test
	public void testAddFragmentCollection() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(
			"Fragment Collection", fragmentCollection.getName());
	}

	@Test(expected = MSBFragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), StringPool.BLANK, StringPool.BLANK,
			serviceContext);
	}

	@Test(expected = MSBFragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), null, StringPool.BLANK, serviceContext);
	}

	@Test
	public void testAddMultipleFragmentCollections() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalFragmentCollectionsCount =
			MSBFragmentCollectionServiceUtil.getMSBFragmentCollectionsCount(
				_group.getGroupId());

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), "Fragment Collection 1", StringPool.BLANK,
			serviceContext);

		MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
			_group.getGroupId(), "Fragment Collection 2", StringPool.BLANK,
			serviceContext);

		int actualFragmentCollectionsCount =
			MSBFragmentCollectionServiceUtil.getMSBFragmentCollectionsCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testDeleteFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MSBFragmentCollection fragmentCollection =
			MSBFragmentCollectionServiceUtil.addMSBFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		MSBFragmentCollectionServiceUtil.deleteMSBFragmentCollection(
			fragmentCollection.getMsbFragmentCollectionId());

		Assert.assertNull(
			MSBFragmentCollectionServiceUtil.fetchMSBFragmentCollection(
				fragmentCollection.getMsbFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollectionWithFragmentEntries()
		throws Exception {

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
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			serviceContext);

		MSBFragmentCollectionServiceUtil.deleteMSBFragmentCollection(
			fragmentCollection.getMsbFragmentCollectionId());

		Assert.assertNull(
			MSBFragmentCollectionServiceUtil.fetchMSBFragmentCollection(
				fragmentCollection.getMsbFragmentCollectionId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}