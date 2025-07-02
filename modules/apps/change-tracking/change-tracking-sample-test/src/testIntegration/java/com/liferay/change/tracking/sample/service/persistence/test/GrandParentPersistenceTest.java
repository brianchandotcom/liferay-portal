/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.sample.exception.NoSuchGrandParentException;
import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.change.tracking.sample.service.GrandParentLocalServiceUtil;
import com.liferay.change.tracking.sample.service.persistence.GrandParentPersistence;
import com.liferay.change.tracking.sample.service.persistence.GrandParentUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class GrandParentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.change.tracking.sample.service"));

	@Before
	public void setUp() {
		_persistence = GrandParentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<GrandParent> iterator = _grandParents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GrandParent grandParent = _persistence.create(pk);

		Assert.assertNotNull(grandParent);

		Assert.assertEquals(grandParent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		_persistence.remove(newGrandParent);

		GrandParent existingGrandParent = _persistence.fetchByPrimaryKey(
			newGrandParent.getPrimaryKey());

		Assert.assertNull(existingGrandParent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGrandParent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GrandParent newGrandParent = _persistence.create(pk);

		newGrandParent.setMvccVersion(RandomTestUtil.nextLong());

		newGrandParent.setCompanyId(RandomTestUtil.nextLong());

		newGrandParent.setName(RandomTestUtil.randomString());

		newGrandParent.setParentGrandParentId(RandomTestUtil.nextLong());

		_grandParents.add(_persistence.update(newGrandParent));

		GrandParent existingGrandParent = _persistence.findByPrimaryKey(
			newGrandParent.getPrimaryKey());

		Assert.assertEquals(
			existingGrandParent.getMvccVersion(),
			newGrandParent.getMvccVersion());
		Assert.assertEquals(
			existingGrandParent.getGrandParentId(),
			newGrandParent.getGrandParentId());
		Assert.assertEquals(
			existingGrandParent.getCompanyId(), newGrandParent.getCompanyId());
		Assert.assertEquals(
			existingGrandParent.getName(), newGrandParent.getName());
		Assert.assertEquals(
			existingGrandParent.getParentGrandParentId(),
			newGrandParent.getParentGrandParentId());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		GrandParent existingGrandParent = _persistence.findByPrimaryKey(
			newGrandParent.getPrimaryKey());

		Assert.assertEquals(existingGrandParent, newGrandParent);
	}

	@Test(expected = NoSuchGrandParentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<GrandParent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"GrandParent", "mvccVersion", true, "grandParentId", true,
			"companyId", true, "name", true, "parentGrandParentId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		GrandParent existingGrandParent = _persistence.fetchByPrimaryKey(
			newGrandParent.getPrimaryKey());

		Assert.assertEquals(existingGrandParent, newGrandParent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GrandParent missingGrandParent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGrandParent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		GrandParent newGrandParent1 = addGrandParent();
		GrandParent newGrandParent2 = addGrandParent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGrandParent1.getPrimaryKey());
		primaryKeys.add(newGrandParent2.getPrimaryKey());

		Map<Serializable, GrandParent> grandParents =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, grandParents.size());
		Assert.assertEquals(
			newGrandParent1, grandParents.get(newGrandParent1.getPrimaryKey()));
		Assert.assertEquals(
			newGrandParent2, grandParents.get(newGrandParent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, GrandParent> grandParents =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(grandParents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		GrandParent newGrandParent = addGrandParent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGrandParent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, GrandParent> grandParents =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, grandParents.size());
		Assert.assertEquals(
			newGrandParent, grandParents.get(newGrandParent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, GrandParent> grandParents =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(grandParents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGrandParent.getPrimaryKey());

		Map<Serializable, GrandParent> grandParents =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, grandParents.size());
		Assert.assertEquals(
			newGrandParent, grandParents.get(newGrandParent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			GrandParentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<GrandParent>() {

				@Override
				public void performAction(GrandParent grandParent) {
					Assert.assertNotNull(grandParent);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GrandParent.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"grandParentId", newGrandParent.getGrandParentId()));

		List<GrandParent> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		GrandParent existingGrandParent = result.get(0);

		Assert.assertEquals(existingGrandParent, newGrandParent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GrandParent.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"grandParentId", RandomTestUtil.nextLong()));

		List<GrandParent> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		GrandParent newGrandParent = addGrandParent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GrandParent.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("grandParentId"));

		Object newGrandParentId = newGrandParent.getGrandParentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"grandParentId", new Object[] {newGrandParentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingGrandParentId = result.get(0);

		Assert.assertEquals(existingGrandParentId, newGrandParentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GrandParent.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("grandParentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"grandParentId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected GrandParent addGrandParent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GrandParent grandParent = _persistence.create(pk);

		grandParent.setMvccVersion(RandomTestUtil.nextLong());

		grandParent.setCompanyId(RandomTestUtil.nextLong());

		grandParent.setName(RandomTestUtil.randomString());

		grandParent.setParentGrandParentId(RandomTestUtil.nextLong());

		_grandParents.add(_persistence.update(grandParent));

		return grandParent;
	}

	private List<GrandParent> _grandParents = new ArrayList<GrandParent>();
	private GrandParentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}