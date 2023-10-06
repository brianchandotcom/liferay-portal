/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationException;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.service.PermissionPropagationLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationPersistence;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationUtil;
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
public class PermissionPropagationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PermissionPropagationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PermissionPropagation> iterator =
			_permissionPropagations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagation permissionPropagation = _persistence.create(pk);

		Assert.assertNotNull(permissionPropagation);

		Assert.assertEquals(permissionPropagation.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		_persistence.remove(newPermissionPropagation);

		PermissionPropagation existingPermissionPropagation =
			_persistence.fetchByPrimaryKey(
				newPermissionPropagation.getPrimaryKey());

		Assert.assertNull(existingPermissionPropagation);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPermissionPropagation();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagation newPermissionPropagation = _persistence.create(
			pk);

		newPermissionPropagation.setMvccVersion(RandomTestUtil.nextLong());

		newPermissionPropagation.setCtCollectionId(RandomTestUtil.nextLong());

		newPermissionPropagation.setGroupId(RandomTestUtil.nextLong());

		newPermissionPropagation.setCompanyId(RandomTestUtil.nextLong());

		newPermissionPropagation.setClassNameId(RandomTestUtil.nextLong());

		newPermissionPropagation.setClassPK(RandomTestUtil.nextLong());

		newPermissionPropagation.setPropagate(RandomTestUtil.randomBoolean());

		_permissionPropagations.add(
			_persistence.update(newPermissionPropagation));

		PermissionPropagation existingPermissionPropagation =
			_persistence.findByPrimaryKey(
				newPermissionPropagation.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagation.getMvccVersion(),
			newPermissionPropagation.getMvccVersion());
		Assert.assertEquals(
			existingPermissionPropagation.getCtCollectionId(),
			newPermissionPropagation.getCtCollectionId());
		Assert.assertEquals(
			existingPermissionPropagation.getPermissionPropagationId(),
			newPermissionPropagation.getPermissionPropagationId());
		Assert.assertEquals(
			existingPermissionPropagation.getGroupId(),
			newPermissionPropagation.getGroupId());
		Assert.assertEquals(
			existingPermissionPropagation.getCompanyId(),
			newPermissionPropagation.getCompanyId());
		Assert.assertEquals(
			existingPermissionPropagation.getClassNameId(),
			newPermissionPropagation.getClassNameId());
		Assert.assertEquals(
			existingPermissionPropagation.getClassPK(),
			newPermissionPropagation.getClassPK());
		Assert.assertEquals(
			existingPermissionPropagation.isPropagate(),
			newPermissionPropagation.isPropagate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		PermissionPropagation existingPermissionPropagation =
			_persistence.findByPrimaryKey(
				newPermissionPropagation.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagation, newPermissionPropagation);
	}

	@Test(expected = NoSuchPermissionPropagationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<PermissionPropagation> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"PermissionPropagation", "mvccVersion", true, "ctCollectionId",
			true, "permissionPropagationId", true, "groupId", true, "companyId",
			true, "classNameId", true, "classPK", true, "propagate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		PermissionPropagation existingPermissionPropagation =
			_persistence.fetchByPrimaryKey(
				newPermissionPropagation.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagation, newPermissionPropagation);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagation missingPermissionPropagation =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPermissionPropagation);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		PermissionPropagation newPermissionPropagation1 =
			addPermissionPropagation();
		PermissionPropagation newPermissionPropagation2 =
			addPermissionPropagation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagation1.getPrimaryKey());
		primaryKeys.add(newPermissionPropagation2.getPrimaryKey());

		Map<Serializable, PermissionPropagation> permissionPropagations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, permissionPropagations.size());
		Assert.assertEquals(
			newPermissionPropagation1,
			permissionPropagations.get(
				newPermissionPropagation1.getPrimaryKey()));
		Assert.assertEquals(
			newPermissionPropagation2,
			permissionPropagations.get(
				newPermissionPropagation2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PermissionPropagation> permissionPropagations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(permissionPropagations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagation.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PermissionPropagation> permissionPropagations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, permissionPropagations.size());
		Assert.assertEquals(
			newPermissionPropagation,
			permissionPropagations.get(
				newPermissionPropagation.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PermissionPropagation> permissionPropagations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(permissionPropagations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagation.getPrimaryKey());

		Map<Serializable, PermissionPropagation> permissionPropagations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, permissionPropagations.size());
		Assert.assertEquals(
			newPermissionPropagation,
			permissionPropagations.get(
				newPermissionPropagation.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			PermissionPropagationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<PermissionPropagation>() {

				@Override
				public void performAction(
					PermissionPropagation permissionPropagation) {

					Assert.assertNotNull(permissionPropagation);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagation.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"permissionPropagationId",
				newPermissionPropagation.getPermissionPropagationId()));

		List<PermissionPropagation> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		PermissionPropagation existingPermissionPropagation = result.get(0);

		Assert.assertEquals(
			existingPermissionPropagation, newPermissionPropagation);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagation.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"permissionPropagationId", RandomTestUtil.nextLong()));

		List<PermissionPropagation> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		PermissionPropagation newPermissionPropagation =
			addPermissionPropagation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagation.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("permissionPropagationId"));

		Object newPermissionPropagationId =
			newPermissionPropagation.getPermissionPropagationId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"permissionPropagationId",
				new Object[] {newPermissionPropagationId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPermissionPropagationId = result.get(0);

		Assert.assertEquals(
			existingPermissionPropagationId, newPermissionPropagationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagation.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("permissionPropagationId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"permissionPropagationId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected PermissionPropagation addPermissionPropagation()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		PermissionPropagation permissionPropagation = _persistence.create(pk);

		permissionPropagation.setMvccVersion(RandomTestUtil.nextLong());

		permissionPropagation.setCtCollectionId(RandomTestUtil.nextLong());

		permissionPropagation.setGroupId(RandomTestUtil.nextLong());

		permissionPropagation.setCompanyId(RandomTestUtil.nextLong());

		permissionPropagation.setClassNameId(RandomTestUtil.nextLong());

		permissionPropagation.setClassPK(RandomTestUtil.nextLong());

		permissionPropagation.setPropagate(RandomTestUtil.randomBoolean());

		_permissionPropagations.add(_persistence.update(permissionPropagation));

		return permissionPropagation;
	}

	private List<PermissionPropagation> _permissionPropagations =
		new ArrayList<PermissionPropagation>();
	private PermissionPropagationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}