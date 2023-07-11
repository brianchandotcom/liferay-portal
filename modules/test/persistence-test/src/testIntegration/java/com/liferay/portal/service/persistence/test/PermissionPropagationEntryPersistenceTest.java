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
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationEntryException;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.service.PermissionPropagationEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationEntryPersistence;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationEntryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
public class PermissionPropagationEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PermissionPropagationEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PermissionPropagationEntry> iterator =
			_permissionPropagationEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagationEntry permissionPropagationEntry =
			_persistence.create(pk);

		Assert.assertNotNull(permissionPropagationEntry);

		Assert.assertEquals(permissionPropagationEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		_persistence.remove(newPermissionPropagationEntry);

		PermissionPropagationEntry existingPermissionPropagationEntry =
			_persistence.fetchByPrimaryKey(
				newPermissionPropagationEntry.getPrimaryKey());

		Assert.assertNull(existingPermissionPropagationEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPermissionPropagationEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagationEntry newPermissionPropagationEntry =
			_persistence.create(pk);

		newPermissionPropagationEntry.setMvccVersion(RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setCtCollectionId(
			RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setGroupId(RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setCompanyId(RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setClassNameId(RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setClassPK(RandomTestUtil.nextLong());

		newPermissionPropagationEntry.setPropagation(
			RandomTestUtil.randomBoolean());

		_permissionPropagationEntries.add(
			_persistence.update(newPermissionPropagationEntry));

		PermissionPropagationEntry existingPermissionPropagationEntry =
			_persistence.findByPrimaryKey(
				newPermissionPropagationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagationEntry.getMvccVersion(),
			newPermissionPropagationEntry.getMvccVersion());
		Assert.assertEquals(
			existingPermissionPropagationEntry.getCtCollectionId(),
			newPermissionPropagationEntry.getCtCollectionId());
		Assert.assertEquals(
			existingPermissionPropagationEntry.
				getPermissionPropagationEntryId(),
			newPermissionPropagationEntry.getPermissionPropagationEntryId());
		Assert.assertEquals(
			existingPermissionPropagationEntry.getGroupId(),
			newPermissionPropagationEntry.getGroupId());
		Assert.assertEquals(
			existingPermissionPropagationEntry.getCompanyId(),
			newPermissionPropagationEntry.getCompanyId());
		Assert.assertEquals(
			existingPermissionPropagationEntry.getClassNameId(),
			newPermissionPropagationEntry.getClassNameId());
		Assert.assertEquals(
			existingPermissionPropagationEntry.getClassPK(),
			newPermissionPropagationEntry.getClassPK());
		Assert.assertEquals(
			existingPermissionPropagationEntry.isPropagation(),
			newPermissionPropagationEntry.isPropagation());
	}

	@Test
	public void testCountByG_C_C_C() throws Exception {
		_persistence.countByG_C_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C_C(0L, 0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		PermissionPropagationEntry existingPermissionPropagationEntry =
			_persistence.findByPrimaryKey(
				newPermissionPropagationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagationEntry, newPermissionPropagationEntry);
	}

	@Test(expected = NoSuchPermissionPropagationEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<PermissionPropagationEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"PermissionPropagationEntry", "mvccVersion", true, "ctCollectionId",
			true, "permissionPropagationEntryId", true, "groupId", true,
			"companyId", true, "classNameId", true, "classPK", true,
			"propagation", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		PermissionPropagationEntry existingPermissionPropagationEntry =
			_persistence.fetchByPrimaryKey(
				newPermissionPropagationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingPermissionPropagationEntry, newPermissionPropagationEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PermissionPropagationEntry missingPermissionPropagationEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPermissionPropagationEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		PermissionPropagationEntry newPermissionPropagationEntry1 =
			addPermissionPropagationEntry();
		PermissionPropagationEntry newPermissionPropagationEntry2 =
			addPermissionPropagationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagationEntry1.getPrimaryKey());
		primaryKeys.add(newPermissionPropagationEntry2.getPrimaryKey());

		Map<Serializable, PermissionPropagationEntry>
			permissionPropagationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, permissionPropagationEntries.size());
		Assert.assertEquals(
			newPermissionPropagationEntry1,
			permissionPropagationEntries.get(
				newPermissionPropagationEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newPermissionPropagationEntry2,
			permissionPropagationEntries.get(
				newPermissionPropagationEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PermissionPropagationEntry>
			permissionPropagationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(permissionPropagationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagationEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PermissionPropagationEntry>
			permissionPropagationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, permissionPropagationEntries.size());
		Assert.assertEquals(
			newPermissionPropagationEntry,
			permissionPropagationEntries.get(
				newPermissionPropagationEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PermissionPropagationEntry>
			permissionPropagationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(permissionPropagationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPermissionPropagationEntry.getPrimaryKey());

		Map<Serializable, PermissionPropagationEntry>
			permissionPropagationEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, permissionPropagationEntries.size());
		Assert.assertEquals(
			newPermissionPropagationEntry,
			permissionPropagationEntries.get(
				newPermissionPropagationEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			PermissionPropagationEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<PermissionPropagationEntry>() {

				@Override
				public void performAction(
					PermissionPropagationEntry permissionPropagationEntry) {

					Assert.assertNotNull(permissionPropagationEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"permissionPropagationEntryId",
				newPermissionPropagationEntry.
					getPermissionPropagationEntryId()));

		List<PermissionPropagationEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PermissionPropagationEntry existingPermissionPropagationEntry =
			result.get(0);

		Assert.assertEquals(
			existingPermissionPropagationEntry, newPermissionPropagationEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"permissionPropagationEntryId", RandomTestUtil.nextLong()));

		List<PermissionPropagationEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("permissionPropagationEntryId"));

		Object newPermissionPropagationEntryId =
			newPermissionPropagationEntry.getPermissionPropagationEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"permissionPropagationEntryId",
				new Object[] {newPermissionPropagationEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPermissionPropagationEntryId = result.get(0);

		Assert.assertEquals(
			existingPermissionPropagationEntryId,
			newPermissionPropagationEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("permissionPropagationEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"permissionPropagationEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newPermissionPropagationEntry.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		PermissionPropagationEntry newPermissionPropagationEntry =
			addPermissionPropagationEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PermissionPropagationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"permissionPropagationEntryId",
				newPermissionPropagationEntry.
					getPermissionPropagationEntryId()));

		List<PermissionPropagationEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		PermissionPropagationEntry permissionPropagationEntry) {

		Assert.assertEquals(
			Long.valueOf(permissionPropagationEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				permissionPropagationEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			Long.valueOf(permissionPropagationEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				permissionPropagationEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			Long.valueOf(permissionPropagationEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				permissionPropagationEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(permissionPropagationEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				permissionPropagationEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
	}

	protected PermissionPropagationEntry addPermissionPropagationEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		PermissionPropagationEntry permissionPropagationEntry =
			_persistence.create(pk);

		permissionPropagationEntry.setMvccVersion(RandomTestUtil.nextLong());

		permissionPropagationEntry.setCtCollectionId(RandomTestUtil.nextLong());

		permissionPropagationEntry.setGroupId(RandomTestUtil.nextLong());

		permissionPropagationEntry.setCompanyId(RandomTestUtil.nextLong());

		permissionPropagationEntry.setClassNameId(RandomTestUtil.nextLong());

		permissionPropagationEntry.setClassPK(RandomTestUtil.nextLong());

		permissionPropagationEntry.setPropagation(
			RandomTestUtil.randomBoolean());

		_permissionPropagationEntries.add(
			_persistence.update(permissionPropagationEntry));

		return permissionPropagationEntry;
	}

	private List<PermissionPropagationEntry> _permissionPropagationEntries =
		new ArrayList<PermissionPropagationEntry>();
	private PermissionPropagationEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}