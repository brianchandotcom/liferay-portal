/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.sample.exception.NoSuchParentException;
import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.change.tracking.sample.service.ParentLocalServiceUtil;
import com.liferay.change.tracking.sample.service.persistence.ParentPersistence;
import com.liferay.change.tracking.sample.service.persistence.ParentUtil;
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
public class ParentPersistenceTest {

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
		_persistence = ParentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Parent> iterator = _parents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Parent parent = _persistence.create(pk);

		Assert.assertNotNull(parent);

		Assert.assertEquals(parent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Parent newParent = addParent();

		_persistence.remove(newParent);

		Parent existingParent = _persistence.fetchByPrimaryKey(
			newParent.getPrimaryKey());

		Assert.assertNull(existingParent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addParent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Parent newParent = _persistence.create(pk);

		newParent.setMvccVersion(RandomTestUtil.nextLong());

		newParent.setCtCollectionId(RandomTestUtil.nextLong());

		newParent.setCompanyId(RandomTestUtil.nextLong());

		newParent.setName(RandomTestUtil.randomString());

		newParent.setGrandParentId(RandomTestUtil.nextLong());

		_parents.add(_persistence.update(newParent));

		Parent existingParent = _persistence.findByPrimaryKey(
			newParent.getPrimaryKey());

		Assert.assertEquals(
			existingParent.getMvccVersion(), newParent.getMvccVersion());
		Assert.assertEquals(
			existingParent.getCtCollectionId(), newParent.getCtCollectionId());
		Assert.assertEquals(
			existingParent.getParentId(), newParent.getParentId());
		Assert.assertEquals(
			existingParent.getCompanyId(), newParent.getCompanyId());
		Assert.assertEquals(existingParent.getName(), newParent.getName());
		Assert.assertEquals(
			existingParent.getGrandParentId(), newParent.getGrandParentId());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		Parent newParent = addParent();

		Parent existingParent = _persistence.findByPrimaryKey(
			newParent.getPrimaryKey());

		Assert.assertEquals(existingParent, newParent);
	}

	@Test(expected = NoSuchParentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Parent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Parent", "mvccVersion", true, "ctCollectionId", true, "parentId",
			true, "companyId", true, "name", true, "grandParentId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Parent newParent = addParent();

		Parent existingParent = _persistence.fetchByPrimaryKey(
			newParent.getPrimaryKey());

		Assert.assertEquals(existingParent, newParent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Parent missingParent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingParent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Parent newParent1 = addParent();
		Parent newParent2 = addParent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newParent1.getPrimaryKey());
		primaryKeys.add(newParent2.getPrimaryKey());

		Map<Serializable, Parent> parents = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, parents.size());
		Assert.assertEquals(
			newParent1, parents.get(newParent1.getPrimaryKey()));
		Assert.assertEquals(
			newParent2, parents.get(newParent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Parent> parents = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(parents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Parent newParent = addParent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newParent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Parent> parents = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, parents.size());
		Assert.assertEquals(newParent, parents.get(newParent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Parent> parents = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(parents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Parent newParent = addParent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newParent.getPrimaryKey());

		Map<Serializable, Parent> parents = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, parents.size());
		Assert.assertEquals(newParent, parents.get(newParent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ParentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Parent>() {

				@Override
				public void performAction(Parent parent) {
					Assert.assertNotNull(parent);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Parent newParent = addParent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Parent.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("parentId", newParent.getParentId()));

		List<Parent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Parent existingParent = result.get(0);

		Assert.assertEquals(existingParent, newParent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Parent.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("parentId", RandomTestUtil.nextLong()));

		List<Parent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Parent newParent = addParent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Parent.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("parentId"));

		Object newParentId = newParent.getParentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("parentId", new Object[] {newParentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingParentId = result.get(0);

		Assert.assertEquals(existingParentId, newParentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Parent.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("parentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"parentId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Parent addParent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Parent parent = _persistence.create(pk);

		parent.setMvccVersion(RandomTestUtil.nextLong());

		parent.setCtCollectionId(RandomTestUtil.nextLong());

		parent.setCompanyId(RandomTestUtil.nextLong());

		parent.setName(RandomTestUtil.randomString());

		parent.setGrandParentId(RandomTestUtil.nextLong());

		_parents.add(_persistence.update(parent));

		return parent;
	}

	private List<Parent> _parents = new ArrayList<Parent>();
	private ParentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}