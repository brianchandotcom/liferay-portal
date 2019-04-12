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
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
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
import com.liferay.portal.kernel.util.StringUtil;
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
public class FragmentCollectionServiceTest {

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
	public void testAddFragmentCollection() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(
			"Fragment Collection", fragmentCollection.getName());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), StringPool.BLANK, StringPool.BLANK,
			serviceContext);
	}

	@Test
	public void testAddFragmentCollectionWithFragmentCollectionKey()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "FRAGMENTCOLLECTIONKEY",
				"Fragment Collection", StringPool.BLANK, serviceContext);

		Assert.assertEquals(
			StringUtil.toLowerCase("FRAGMENTCOLLECTIONKEY"),
			fragmentCollection.getFragmentCollectionKey());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), null, StringPool.BLANK, serviceContext);
	}

	@Test
	public void testAddMultipleFragmentCollections() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalFragmentCollectionsCount =
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId());

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Fragment Collection 1", StringPool.BLANK,
			serviceContext);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Fragment Collection 2", StringPool.BLANK,
			serviceContext);

		int actualFragmentCollectionsCount =
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testCountFragmentCollectionsByName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalFragmentCollectionsCount =
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(), "Some");

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Other string", StringPool.BLANK,
			serviceContext);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Some string 1", StringPool.BLANK,
			serviceContext);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Some string 2", StringPool.BLANK,
			serviceContext);

		int actualFragmentCollectionsCount =
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				_group.getGroupId(), "Some");

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testDeleteFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		FragmentCollectionServiceUtil.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollections() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection1 =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection 1", StringPool.BLANK,
				serviceContext);

		FragmentCollection fragmentCollection2 =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection 2", StringPool.BLANK,
				serviceContext);

		long[] fragmentCollections = {
			fragmentCollection1.getFragmentCollectionId(),
			fragmentCollection2.getFragmentCollectionId()
		};

		FragmentCollectionServiceUtil.deleteFragmentCollections(
			fragmentCollections);

		Assert.assertNull(
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				fragmentCollection1.getFragmentCollectionId()));

		Assert.assertNull(
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				fragmentCollection2.getFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollectionWithFragmentEntries()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		FragmentCollectionServiceUtil.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testGetFragmentCollections() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<FragmentCollection> originalFragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Fragment Collection 1", StringPool.BLANK,
			serviceContext);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), "Fragment Collection 2", StringPool.BLANK,
			serviceContext);

		List<FragmentCollection> actualFragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(
			actualFragmentCollections.toString(),
			originalFragmentCollections.size() + 2,
			actualFragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsByKeywords() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String fragmentCollectionName = RandomTestUtil.randomString();

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName, StringPool.BLANK,
			serviceContext);

		FragmentCollectionServiceUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName, StringPool.BLANK,
			serviceContext);

		List<FragmentCollection> actualFragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				_group.getGroupId(), fragmentCollectionName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			actualFragmentCollections.toString(), 2,
			actualFragmentCollections.size());
	}

	@Test
	public void testUpdateFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		fragmentCollection =
			FragmentCollectionServiceUtil.updateFragmentCollection(
				fragmentCollection.getFragmentCollectionId(),
				"Fragment Collection New", "Fragment Description");

		Assert.assertEquals(
			"Fragment Collection New", fragmentCollection.getName());

		Assert.assertEquals(
			"Fragment Description", fragmentCollection.getDescription());
	}

	@DeleteAfterTestRun
	private Group _group;

}