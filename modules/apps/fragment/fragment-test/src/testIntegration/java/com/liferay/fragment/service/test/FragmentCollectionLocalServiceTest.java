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
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentCollectionUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.Date;
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
public class FragmentCollectionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE, PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFragmentCollection() throws PortalException {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection");

		FragmentCollection fragmentCollectionActual =
			FragmentCollectionUtil.fetchByUUID_G(
				fragmentCollection.getUuid(), fragmentCollection.getGroupId());

		Assert.assertEquals(fragmentCollection, fragmentCollectionActual);

		Assert.assertEquals(
			"Fragment Collection", fragmentCollectionActual.getName());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithEmptyName() throws Exception {
		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testAddFragmentCollectionWithFragmentCollectionKey()
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				"FRAGMENTCOLLECTIONKEY");

		FragmentCollection fragmentCollectionActual =
			FragmentCollectionUtil.fetchByUUID_G(
				fragmentCollection.getUuid(), fragmentCollection.getGroupId());

		Assert.assertEquals(fragmentCollection, fragmentCollectionActual);

		Assert.assertEquals(
			StringUtil.toLowerCase("FRAGMENTCOLLECTIONKEY"),
			fragmentCollectionActual.getFragmentCollectionKey());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithNullName() throws Exception {
		FragmentTestUtil.addFragmentCollection(_group.getGroupId(), null);
	}

	@Test
	public void testAddMultipleFragmentCollections() throws PortalException {
		int originalFragmentCollectionsCount =
			FragmentCollectionLocalServiceUtil.getFragmentCollectionsCount();

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		int actualFragmentCollectionsCount =
			FragmentCollectionLocalServiceUtil.getFragmentCollectionsCount();

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testDeleteFragmentCollection() throws PortalException {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollectionLocalServiceUtil.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollectionByFragmentCollectionId()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollectionLocalServiceUtil.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollectionWithFragmentEntries()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		FragmentCollectionLocalServiceUtil.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testFetchFragmentCollectionByFragmentCollectionId()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		Assert.assertEquals(
			fragmentCollection,
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testFetchFragmentCollectionByGroupIdAndFragmentCollectionKey()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				"FRAGMENTCOLLECTIONKEY");

		Assert.assertEquals(
			fragmentCollection,
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				_group.getGroupId(), "FRAGMENTCOLLECTIONKEY"));
	}

	@Test
	public void testGetFragmentCollectionsByKeywords() throws Exception {
		String fragmentCollectionName = RandomTestUtil.randomString();

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName);

		List<FragmentCollection> actualFragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), fragmentCollectionName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			actualFragmentCollections.toString(), 2,
			actualFragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorAsc()
		throws Exception {

		Date date = new Date();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				new Date(date.getTime() + Time.SECOND));

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			new Date(date.getTime() + Time.SECOND * 2));

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			new Date(date.getTime() + Time.SECOND * 3));

		FragmentCollectionCreateDateComparator
			ascFragmentCollectionCreateDateComparator =
				new FragmentCollectionCreateDateComparator(true);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				ascFragmentCollectionCreateDateComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(fragmentCollection, firstFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorDesc()
		throws Exception {

		Date date = new Date();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				new Date(date.getTime() + Time.SECOND));

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			new Date(date.getTime() + Time.SECOND * 2));

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			new Date(date.getTime() + Time.SECOND * 3));

		FragmentCollectionCreateDateComparator
			descFragmentCollectionCreateDateComparator =
				new FragmentCollectionCreateDateComparator(false);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				descFragmentCollectionCreateDateComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(fragmentCollection, lastFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentCollectionNameComparator ascFragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(true);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, ascFragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			firstFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentCollectionNameComparator descFragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(false);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, descFragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(1);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			lastFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");
		FragmentCollectionNameComparator ascFragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(true);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				ascFragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			firstFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");

		FragmentCollectionNameComparator descFragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(false);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				descFragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(1);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			lastFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByRange() throws Exception {
		int collectionSize = 5;

		for (int i = 0; i < collectionSize; i++) {
			String fragmentCollectionName =
				"Fragment Collection " + String.valueOf(i);

			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), fragmentCollectionName);
		}

		//get all fragments but the first and last

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_group.getGroupId(), 1, 4, null);

		Assert.assertEquals(
			fragmentCollections.toString(), collectionSize - 2,
			fragmentCollections.size());

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			"Fragment Collection 1", firstFragmentCollection.getName());

		Assert.assertEquals(
			"Fragment Collection 3", lastFragmentCollection.getName());
	}

	@Test
	public void testGetOSGIServiceIdentifier() {
		Assert.assertEquals(
			FragmentCollectionLocalServiceUtil.getOSGiServiceIdentifier(),
			FragmentCollectionLocalService.class.getName());
	}

	@Test
	public void testUpdateFragmentCollection() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection");

		fragmentCollection =
			FragmentCollectionLocalServiceUtil.updateFragmentCollection(
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