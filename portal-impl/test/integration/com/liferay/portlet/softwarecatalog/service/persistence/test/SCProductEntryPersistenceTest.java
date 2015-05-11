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

package com.liferay.portlet.softwarecatalog.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class SCProductEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SCProductEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SCProductEntry> iterator = _scProductEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductEntry scProductEntry = _persistence.create(pk);

		Assert.assertNotNull(scProductEntry);

		Assert.assertEquals(scProductEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.remove(newSCProductEntry);

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertNull(existingSCProductEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCProductEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductEntry newSCProductEntry = _persistence.create(pk);

		newSCProductEntry.setGroupId(RandomTestUtil.nextLong());

		newSCProductEntry.setCompanyId(RandomTestUtil.nextLong());

		newSCProductEntry.setUserId(RandomTestUtil.nextLong());

		newSCProductEntry.setUserName(RandomTestUtil.randomString());

		newSCProductEntry.setCreateDate(RandomTestUtil.nextDate());

		newSCProductEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSCProductEntry.setName(RandomTestUtil.randomString());

		newSCProductEntry.setType(RandomTestUtil.randomString());

		newSCProductEntry.setTags(RandomTestUtil.randomString());

		newSCProductEntry.setShortDescription(RandomTestUtil.randomString());

		newSCProductEntry.setLongDescription(RandomTestUtil.randomString());

		newSCProductEntry.setPageURL(RandomTestUtil.randomString());

		newSCProductEntry.setAuthor(RandomTestUtil.randomString());

		newSCProductEntry.setRepoGroupId(RandomTestUtil.randomString());

		newSCProductEntry.setRepoArtifactId(RandomTestUtil.randomString());

		_scProductEntries.add(_persistence.update(newSCProductEntry));

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry.getProductEntryId(),
			newSCProductEntry.getProductEntryId());
		Assert.assertEquals(existingSCProductEntry.getGroupId(),
			newSCProductEntry.getGroupId());
		Assert.assertEquals(existingSCProductEntry.getCompanyId(),
			newSCProductEntry.getCompanyId());
		Assert.assertEquals(existingSCProductEntry.getUserId(),
			newSCProductEntry.getUserId());
		Assert.assertEquals(existingSCProductEntry.getUserName(),
			newSCProductEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getCreateDate()),
			Time.getShortTimestamp(newSCProductEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getModifiedDate()),
			Time.getShortTimestamp(newSCProductEntry.getModifiedDate()));
		Assert.assertEquals(existingSCProductEntry.getName(),
			newSCProductEntry.getName());
		Assert.assertEquals(existingSCProductEntry.getType(),
			newSCProductEntry.getType());
		Assert.assertEquals(existingSCProductEntry.getTags(),
			newSCProductEntry.getTags());
		Assert.assertEquals(existingSCProductEntry.getShortDescription(),
			newSCProductEntry.getShortDescription());
		Assert.assertEquals(existingSCProductEntry.getLongDescription(),
			newSCProductEntry.getLongDescription());
		Assert.assertEquals(existingSCProductEntry.getPageURL(),
			newSCProductEntry.getPageURL());
		Assert.assertEquals(existingSCProductEntry.getAuthor(),
			newSCProductEntry.getAuthor());
		Assert.assertEquals(existingSCProductEntry.getRepoGroupId(),
			newSCProductEntry.getRepoGroupId());
		Assert.assertEquals(existingSCProductEntry.getRepoArtifactId(),
			newSCProductEntry.getRepoArtifactId());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_U() throws Exception {
		_persistence.countByG_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_U(0L, 0L);
	}

	@Test
	public void testCountByRG_RA() throws Exception {
		_persistence.countByRG_RA(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByRG_RA(StringPool.NULL, StringPool.NULL);

		_persistence.countByRG_RA((String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test(expected = NoSuchProductEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SCProductEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCProductEntry",
			"productEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "type", true, "tags", true,
			"shortDescription", true, "longDescription", true, "pageURL", true,
			"author", true, "repoGroupId", true, "repoArtifactId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductEntry missingSCProductEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCProductEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SCProductEntry newSCProductEntry1 = addSCProductEntry();
		SCProductEntry newSCProductEntry2 = addSCProductEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductEntry1.getPrimaryKey());
		primaryKeys.add(newSCProductEntry2.getPrimaryKey());

		Map<Serializable, SCProductEntry> scProductEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, scProductEntries.size());
		Assert.assertEquals(newSCProductEntry1,
			scProductEntries.get(newSCProductEntry1.getPrimaryKey()));
		Assert.assertEquals(newSCProductEntry2,
			scProductEntries.get(newSCProductEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SCProductEntry> scProductEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SCProductEntry> scProductEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductEntries.size());
		Assert.assertEquals(newSCProductEntry,
			scProductEntries.get(newSCProductEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SCProductEntry> scProductEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scProductEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCProductEntry.getPrimaryKey());

		Map<Serializable, SCProductEntry> scProductEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scProductEntries.size());
		Assert.assertEquals(newSCProductEntry,
			scProductEntries.get(newSCProductEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCProductEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCProductEntry scProductEntry = (SCProductEntry)object;

					Assert.assertNotNull(scProductEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId",
				newSCProductEntry.getProductEntryId()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCProductEntry existingSCProductEntry = result.get(0);

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId",
				RandomTestUtil.nextLong()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		Object newProductEntryId = newSCProductEntry.getProductEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { newProductEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingProductEntryId = result.get(0);

		Assert.assertEquals(existingProductEntryId, newProductEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.clearCache();

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingSCProductEntry.getRepoGroupId(),
				ReflectionTestUtil.invoke(existingSCProductEntry,
					"getOriginalRepoGroupId", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingSCProductEntry.getRepoArtifactId(),
				ReflectionTestUtil.invoke(existingSCProductEntry,
					"getOriginalRepoArtifactId", new Class<?>[0])));
	}

	protected SCProductEntry addSCProductEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductEntry scProductEntry = _persistence.create(pk);

		scProductEntry.setGroupId(RandomTestUtil.nextLong());

		scProductEntry.setCompanyId(RandomTestUtil.nextLong());

		scProductEntry.setUserId(RandomTestUtil.nextLong());

		scProductEntry.setUserName(RandomTestUtil.randomString());

		scProductEntry.setCreateDate(RandomTestUtil.nextDate());

		scProductEntry.setModifiedDate(RandomTestUtil.nextDate());

		scProductEntry.setName(RandomTestUtil.randomString());

		scProductEntry.setType(RandomTestUtil.randomString());

		scProductEntry.setTags(RandomTestUtil.randomString());

		scProductEntry.setShortDescription(RandomTestUtil.randomString());

		scProductEntry.setLongDescription(RandomTestUtil.randomString());

		scProductEntry.setPageURL(RandomTestUtil.randomString());

		scProductEntry.setAuthor(RandomTestUtil.randomString());

		scProductEntry.setRepoGroupId(RandomTestUtil.randomString());

		scProductEntry.setRepoArtifactId(RandomTestUtil.randomString());

		_scProductEntries.add(_persistence.update(scProductEntry));

		return scProductEntry;
	}

	private List<SCProductEntry> _scProductEntries = new ArrayList<SCProductEntry>();
	private SCProductEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}