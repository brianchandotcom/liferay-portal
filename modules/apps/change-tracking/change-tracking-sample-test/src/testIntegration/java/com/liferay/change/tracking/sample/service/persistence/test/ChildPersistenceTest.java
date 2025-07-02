/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.sample.exception.NoSuchChildException;
import com.liferay.change.tracking.sample.model.Child;
import com.liferay.change.tracking.sample.service.ChildLocalServiceUtil;
import com.liferay.change.tracking.sample.service.persistence.ChildPersistence;
import com.liferay.change.tracking.sample.service.persistence.ChildUtil;
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
public class ChildPersistenceTest {

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
		_persistence = ChildUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Child> iterator = _childs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Child child = _persistence.create(pk);

		Assert.assertNotNull(child);

		Assert.assertEquals(child.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Child newChild = addChild();

		_persistence.remove(newChild);

		Child existingChild = _persistence.fetchByPrimaryKey(
			newChild.getPrimaryKey());

		Assert.assertNull(existingChild);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChild();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Child newChild = _persistence.create(pk);

		newChild.setMvccVersion(RandomTestUtil.nextLong());

		newChild.setCtCollectionId(RandomTestUtil.nextLong());

		newChild.setCompanyId(RandomTestUtil.nextLong());

		newChild.setName(RandomTestUtil.randomString());

		newChild.setGrandParentId(RandomTestUtil.nextLong());

		newChild.setParentChildId(RandomTestUtil.nextLong());

		newChild.setParentName(RandomTestUtil.randomString());

		_childs.add(_persistence.update(newChild));

		Child existingChild = _persistence.findByPrimaryKey(
			newChild.getPrimaryKey());

		Assert.assertEquals(
			existingChild.getMvccVersion(), newChild.getMvccVersion());
		Assert.assertEquals(
			existingChild.getCtCollectionId(), newChild.getCtCollectionId());
		Assert.assertEquals(existingChild.getChildId(), newChild.getChildId());
		Assert.assertEquals(
			existingChild.getCompanyId(), newChild.getCompanyId());
		Assert.assertEquals(existingChild.getName(), newChild.getName());
		Assert.assertEquals(
			existingChild.getGrandParentId(), newChild.getGrandParentId());
		Assert.assertEquals(
			existingChild.getParentChildId(), newChild.getParentChildId());
		Assert.assertEquals(
			existingChild.getParentName(), newChild.getParentName());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_G() throws Exception {
		_persistence.countByC_G(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_G(0L, 0L);
	}

	@Test
	public void testCountByC_P() throws Exception {
		_persistence.countByC_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_P(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Child newChild = addChild();

		Child existingChild = _persistence.findByPrimaryKey(
			newChild.getPrimaryKey());

		Assert.assertEquals(existingChild, newChild);
	}

	@Test(expected = NoSuchChildException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Child> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Child", "mvccVersion", true, "ctCollectionId", true, "childId",
			true, "companyId", true, "name", true, "grandParentId", true,
			"parentChildId", true, "parentName", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Child newChild = addChild();

		Child existingChild = _persistence.fetchByPrimaryKey(
			newChild.getPrimaryKey());

		Assert.assertEquals(existingChild, newChild);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Child missingChild = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChild);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Child newChild1 = addChild();
		Child newChild2 = addChild();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChild1.getPrimaryKey());
		primaryKeys.add(newChild2.getPrimaryKey());

		Map<Serializable, Child> childs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, childs.size());
		Assert.assertEquals(newChild1, childs.get(newChild1.getPrimaryKey()));
		Assert.assertEquals(newChild2, childs.get(newChild2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Child> childs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(childs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Child newChild = addChild();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChild.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Child> childs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, childs.size());
		Assert.assertEquals(newChild, childs.get(newChild.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Child> childs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(childs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Child newChild = addChild();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChild.getPrimaryKey());

		Map<Serializable, Child> childs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, childs.size());
		Assert.assertEquals(newChild, childs.get(newChild.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ChildLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Child>() {

				@Override
				public void performAction(Child child) {
					Assert.assertNotNull(child);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Child newChild = addChild();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Child.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("childId", newChild.getChildId()));

		List<Child> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Child existingChild = result.get(0);

		Assert.assertEquals(existingChild, newChild);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Child.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("childId", RandomTestUtil.nextLong()));

		List<Child> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Child newChild = addChild();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Child.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("childId"));

		Object newChildId = newChild.getChildId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("childId", new Object[] {newChildId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChildId = result.get(0);

		Assert.assertEquals(existingChildId, newChildId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Child.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("childId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"childId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Child addChild() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Child child = _persistence.create(pk);

		child.setMvccVersion(RandomTestUtil.nextLong());

		child.setCtCollectionId(RandomTestUtil.nextLong());

		child.setCompanyId(RandomTestUtil.nextLong());

		child.setName(RandomTestUtil.randomString());

		child.setGrandParentId(RandomTestUtil.nextLong());

		child.setParentChildId(RandomTestUtil.nextLong());

		child.setParentName(RandomTestUtil.randomString());

		_childs.add(_persistence.update(child));

		return child;
	}

	private List<Child> _childs = new ArrayList<Child>();
	private ChildPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}