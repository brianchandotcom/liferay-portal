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

package com.liferay.client.extension.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.exception.NoSuchClientExtensionEntryRelException;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalServiceUtil;
import com.liferay.client.extension.service.persistence.ClientExtensionEntryRelPersistence;
import com.liferay.client.extension.service.persistence.ClientExtensionEntryRelUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
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
public class ClientExtensionEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.client.extension.service"));

	@Before
	public void setUp() {
		_persistence = ClientExtensionEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ClientExtensionEntryRel> iterator =
			_clientExtensionEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClientExtensionEntryRel clientExtensionEntryRel = _persistence.create(
			pk);

		Assert.assertNotNull(clientExtensionEntryRel);

		Assert.assertEquals(clientExtensionEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		_persistence.remove(newClientExtensionEntryRel);

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			_persistence.fetchByPrimaryKey(
				newClientExtensionEntryRel.getPrimaryKey());

		Assert.assertNull(existingClientExtensionEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addClientExtensionEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClientExtensionEntryRel newClientExtensionEntryRel =
			_persistence.create(pk);

		newClientExtensionEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setCtCollectionId(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setUuid(RandomTestUtil.randomString());

		newClientExtensionEntryRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newClientExtensionEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setUserId(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setUserName(RandomTestUtil.randomString());

		newClientExtensionEntryRel.setCreateDate(RandomTestUtil.nextDate());

		newClientExtensionEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		newClientExtensionEntryRel.setClassNameId(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setClassPK(RandomTestUtil.nextLong());

		newClientExtensionEntryRel.setCETExternalReferenceCode(
			RandomTestUtil.randomString());

		newClientExtensionEntryRel.setType(RandomTestUtil.randomString());

		_clientExtensionEntryRels.add(
			_persistence.update(newClientExtensionEntryRel));

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			_persistence.findByPrimaryKey(
				newClientExtensionEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingClientExtensionEntryRel.getMvccVersion(),
			newClientExtensionEntryRel.getMvccVersion());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getCtCollectionId(),
			newClientExtensionEntryRel.getCtCollectionId());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getUuid(),
			newClientExtensionEntryRel.getUuid());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getExternalReferenceCode(),
			newClientExtensionEntryRel.getExternalReferenceCode());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getClientExtensionEntryRelId(),
			newClientExtensionEntryRel.getClientExtensionEntryRelId());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getCompanyId(),
			newClientExtensionEntryRel.getCompanyId());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getUserId(),
			newClientExtensionEntryRel.getUserId());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getUserName(),
			newClientExtensionEntryRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingClientExtensionEntryRel.getCreateDate()),
			Time.getShortTimestamp(newClientExtensionEntryRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingClientExtensionEntryRel.getModifiedDate()),
			Time.getShortTimestamp(
				newClientExtensionEntryRel.getModifiedDate()));
		Assert.assertEquals(
			existingClientExtensionEntryRel.getClassNameId(),
			newClientExtensionEntryRel.getClassNameId());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getClassPK(),
			newClientExtensionEntryRel.getClassPK());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getCETExternalReferenceCode(),
			newClientExtensionEntryRel.getCETExternalReferenceCode());
		Assert.assertEquals(
			existingClientExtensionEntryRel.getType(),
			newClientExtensionEntryRel.getType());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_T() throws Exception {
		_persistence.countByC_C_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByC_C_T(0L, 0L, "null");

		_persistence.countByC_C_T(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			_persistence.findByPrimaryKey(
				newClientExtensionEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingClientExtensionEntryRel, newClientExtensionEntryRel);
	}

	@Test(expected = NoSuchClientExtensionEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ClientExtensionEntryRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"ClientExtensionEntryRel", "mvccVersion", true, "ctCollectionId",
			true, "uuid", true, "externalReferenceCode", true,
			"clientExtensionEntryRelId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "cetExternalReferenceCode",
			true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		ClientExtensionEntryRel existingClientExtensionEntryRel =
			_persistence.fetchByPrimaryKey(
				newClientExtensionEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingClientExtensionEntryRel, newClientExtensionEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClientExtensionEntryRel missingClientExtensionEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingClientExtensionEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ClientExtensionEntryRel newClientExtensionEntryRel1 =
			addClientExtensionEntryRel();
		ClientExtensionEntryRel newClientExtensionEntryRel2 =
			addClientExtensionEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClientExtensionEntryRel1.getPrimaryKey());
		primaryKeys.add(newClientExtensionEntryRel2.getPrimaryKey());

		Map<Serializable, ClientExtensionEntryRel> clientExtensionEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, clientExtensionEntryRels.size());
		Assert.assertEquals(
			newClientExtensionEntryRel1,
			clientExtensionEntryRels.get(
				newClientExtensionEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newClientExtensionEntryRel2,
			clientExtensionEntryRels.get(
				newClientExtensionEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ClientExtensionEntryRel> clientExtensionEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(clientExtensionEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClientExtensionEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ClientExtensionEntryRel> clientExtensionEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, clientExtensionEntryRels.size());
		Assert.assertEquals(
			newClientExtensionEntryRel,
			clientExtensionEntryRels.get(
				newClientExtensionEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ClientExtensionEntryRel> clientExtensionEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(clientExtensionEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClientExtensionEntryRel.getPrimaryKey());

		Map<Serializable, ClientExtensionEntryRel> clientExtensionEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, clientExtensionEntryRels.size());
		Assert.assertEquals(
			newClientExtensionEntryRel,
			clientExtensionEntryRels.get(
				newClientExtensionEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ClientExtensionEntryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ClientExtensionEntryRel>() {

				@Override
				public void performAction(
					ClientExtensionEntryRel clientExtensionEntryRel) {

					Assert.assertNotNull(clientExtensionEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ClientExtensionEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"clientExtensionEntryRelId",
				newClientExtensionEntryRel.getClientExtensionEntryRelId()));

		List<ClientExtensionEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ClientExtensionEntryRel existingClientExtensionEntryRel = result.get(0);

		Assert.assertEquals(
			existingClientExtensionEntryRel, newClientExtensionEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ClientExtensionEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"clientExtensionEntryRelId", RandomTestUtil.nextLong()));

		List<ClientExtensionEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ClientExtensionEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("clientExtensionEntryRelId"));

		Object newClientExtensionEntryRelId =
			newClientExtensionEntryRel.getClientExtensionEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"clientExtensionEntryRelId",
				new Object[] {newClientExtensionEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClientExtensionEntryRelId = result.get(0);

		Assert.assertEquals(
			existingClientExtensionEntryRelId, newClientExtensionEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ClientExtensionEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("clientExtensionEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"clientExtensionEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newClientExtensionEntryRel.getPrimaryKey()));
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

		ClientExtensionEntryRel newClientExtensionEntryRel =
			addClientExtensionEntryRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ClientExtensionEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"clientExtensionEntryRelId",
				newClientExtensionEntryRel.getClientExtensionEntryRelId()));

		List<ClientExtensionEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		Assert.assertEquals(
			Long.valueOf(clientExtensionEntryRel.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				clientExtensionEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			clientExtensionEntryRel.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				clientExtensionEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected ClientExtensionEntryRel addClientExtensionEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		ClientExtensionEntryRel clientExtensionEntryRel = _persistence.create(
			pk);

		clientExtensionEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setCtCollectionId(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setUuid(RandomTestUtil.randomString());

		clientExtensionEntryRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		clientExtensionEntryRel.setCompanyId(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setUserId(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setUserName(RandomTestUtil.randomString());

		clientExtensionEntryRel.setCreateDate(RandomTestUtil.nextDate());

		clientExtensionEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		clientExtensionEntryRel.setClassNameId(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setClassPK(RandomTestUtil.nextLong());

		clientExtensionEntryRel.setCETExternalReferenceCode(
			RandomTestUtil.randomString());

		clientExtensionEntryRel.setType(RandomTestUtil.randomString());

		_clientExtensionEntryRels.add(
			_persistence.update(clientExtensionEntryRel));

		return clientExtensionEntryRel;
	}

	private List<ClientExtensionEntryRel> _clientExtensionEntryRels =
		new ArrayList<ClientExtensionEntryRel>();
	private ClientExtensionEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}