/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.exception.NoSuchCPSOListTypeDefinitionRelException;
import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.commerce.product.service.CPSOListTypeDefinitionRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPSOListTypeDefinitionRelPersistence;
import com.liferay.commerce.product.service.persistence.CPSOListTypeDefinitionRelUtil;
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
public class CPSOListTypeDefinitionRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPSOListTypeDefinitionRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPSOListTypeDefinitionRel> iterator =
			_cpsoListTypeDefinitionRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel =
			_persistence.create(pk);

		Assert.assertNotNull(cpsoListTypeDefinitionRel);

		Assert.assertEquals(cpsoListTypeDefinitionRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		_persistence.remove(newCPSOListTypeDefinitionRel);

		CPSOListTypeDefinitionRel existingCPSOListTypeDefinitionRel =
			_persistence.fetchByPrimaryKey(
				newCPSOListTypeDefinitionRel.getPrimaryKey());

		Assert.assertNull(existingCPSOListTypeDefinitionRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPSOListTypeDefinitionRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			_persistence.create(pk);

		newCPSOListTypeDefinitionRel.setMvccVersion(RandomTestUtil.nextLong());

		newCPSOListTypeDefinitionRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		newCPSOListTypeDefinitionRel.setCompanyId(RandomTestUtil.nextLong());

		newCPSOListTypeDefinitionRel.setCPSpecificationOptionId(
			RandomTestUtil.nextLong());

		newCPSOListTypeDefinitionRel.setListTypeDefinitionId(
			RandomTestUtil.nextLong());

		_cpsoListTypeDefinitionRels.add(
			_persistence.update(newCPSOListTypeDefinitionRel));

		CPSOListTypeDefinitionRel existingCPSOListTypeDefinitionRel =
			_persistence.findByPrimaryKey(
				newCPSOListTypeDefinitionRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getMvccVersion(),
			newCPSOListTypeDefinitionRel.getMvccVersion());
		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getCtCollectionId(),
			newCPSOListTypeDefinitionRel.getCtCollectionId());
		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getCPSOListTypeDefinitionRelId(),
			newCPSOListTypeDefinitionRel.getCPSOListTypeDefinitionRelId());
		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getCompanyId(),
			newCPSOListTypeDefinitionRel.getCompanyId());
		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getCPSpecificationOptionId(),
			newCPSOListTypeDefinitionRel.getCPSpecificationOptionId());
		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel.getListTypeDefinitionId(),
			newCPSOListTypeDefinitionRel.getListTypeDefinitionId());
	}

	@Test
	public void testCountByCPSpecificationOptionId() throws Exception {
		_persistence.countByCPSpecificationOptionId(RandomTestUtil.nextLong());

		_persistence.countByCPSpecificationOptionId(0L);
	}

	@Test
	public void testCountByListTypeDefinitionId() throws Exception {
		_persistence.countByListTypeDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByListTypeDefinitionId(0L);
	}

	@Test
	public void testCountByC_L() throws Exception {
		_persistence.countByC_L(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_L(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		CPSOListTypeDefinitionRel existingCPSOListTypeDefinitionRel =
			_persistence.findByPrimaryKey(
				newCPSOListTypeDefinitionRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel, newCPSOListTypeDefinitionRel);
	}

	@Test(expected = NoSuchCPSOListTypeDefinitionRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPSOListTypeDefinitionRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPSOListTypeDefinitionRel", "mvccVersion", true, "ctCollectionId",
			true, "CPSOListTypeDefinitionRelId", true, "companyId", true,
			"CPSpecificationOptionId", true, "listTypeDefinitionId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		CPSOListTypeDefinitionRel existingCPSOListTypeDefinitionRel =
			_persistence.fetchByPrimaryKey(
				newCPSOListTypeDefinitionRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel, newCPSOListTypeDefinitionRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSOListTypeDefinitionRel missingCPSOListTypeDefinitionRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPSOListTypeDefinitionRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel1 =
			addCPSOListTypeDefinitionRel();
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel2 =
			addCPSOListTypeDefinitionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSOListTypeDefinitionRel1.getPrimaryKey());
		primaryKeys.add(newCPSOListTypeDefinitionRel2.getPrimaryKey());

		Map<Serializable, CPSOListTypeDefinitionRel>
			cpsoListTypeDefinitionRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, cpsoListTypeDefinitionRels.size());
		Assert.assertEquals(
			newCPSOListTypeDefinitionRel1,
			cpsoListTypeDefinitionRels.get(
				newCPSOListTypeDefinitionRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCPSOListTypeDefinitionRel2,
			cpsoListTypeDefinitionRels.get(
				newCPSOListTypeDefinitionRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPSOListTypeDefinitionRel>
			cpsoListTypeDefinitionRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpsoListTypeDefinitionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSOListTypeDefinitionRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPSOListTypeDefinitionRel>
			cpsoListTypeDefinitionRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpsoListTypeDefinitionRels.size());
		Assert.assertEquals(
			newCPSOListTypeDefinitionRel,
			cpsoListTypeDefinitionRels.get(
				newCPSOListTypeDefinitionRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPSOListTypeDefinitionRel>
			cpsoListTypeDefinitionRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpsoListTypeDefinitionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSOListTypeDefinitionRel.getPrimaryKey());

		Map<Serializable, CPSOListTypeDefinitionRel>
			cpsoListTypeDefinitionRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpsoListTypeDefinitionRels.size());
		Assert.assertEquals(
			newCPSOListTypeDefinitionRel,
			cpsoListTypeDefinitionRels.get(
				newCPSOListTypeDefinitionRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPSOListTypeDefinitionRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPSOListTypeDefinitionRel>() {

				@Override
				public void performAction(
					CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

					Assert.assertNotNull(cpsoListTypeDefinitionRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPSOListTypeDefinitionRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPSOListTypeDefinitionRelId",
				newCPSOListTypeDefinitionRel.getCPSOListTypeDefinitionRelId()));

		List<CPSOListTypeDefinitionRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPSOListTypeDefinitionRel existingCPSOListTypeDefinitionRel =
			result.get(0);

		Assert.assertEquals(
			existingCPSOListTypeDefinitionRel, newCPSOListTypeDefinitionRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPSOListTypeDefinitionRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPSOListTypeDefinitionRelId", RandomTestUtil.nextLong()));

		List<CPSOListTypeDefinitionRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPSOListTypeDefinitionRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPSOListTypeDefinitionRelId"));

		Object newCPSOListTypeDefinitionRelId =
			newCPSOListTypeDefinitionRel.getCPSOListTypeDefinitionRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPSOListTypeDefinitionRelId",
				new Object[] {newCPSOListTypeDefinitionRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPSOListTypeDefinitionRelId = result.get(0);

		Assert.assertEquals(
			existingCPSOListTypeDefinitionRelId,
			newCPSOListTypeDefinitionRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPSOListTypeDefinitionRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPSOListTypeDefinitionRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPSOListTypeDefinitionRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPSOListTypeDefinitionRel.getPrimaryKey()));
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

		CPSOListTypeDefinitionRel newCPSOListTypeDefinitionRel =
			addCPSOListTypeDefinitionRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPSOListTypeDefinitionRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPSOListTypeDefinitionRelId",
				newCPSOListTypeDefinitionRel.getCPSOListTypeDefinitionRelId()));

		List<CPSOListTypeDefinitionRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		Assert.assertEquals(
			Long.valueOf(
				cpsoListTypeDefinitionRel.getCPSpecificationOptionId()),
			ReflectionTestUtil.<Long>invoke(
				cpsoListTypeDefinitionRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPSpecificationOptionId"));
		Assert.assertEquals(
			Long.valueOf(cpsoListTypeDefinitionRel.getListTypeDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				cpsoListTypeDefinitionRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "listTypeDefinitionId"));
	}

	protected CPSOListTypeDefinitionRel addCPSOListTypeDefinitionRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel =
			_persistence.create(pk);

		cpsoListTypeDefinitionRel.setMvccVersion(RandomTestUtil.nextLong());

		cpsoListTypeDefinitionRel.setCtCollectionId(RandomTestUtil.nextLong());

		cpsoListTypeDefinitionRel.setCompanyId(RandomTestUtil.nextLong());

		cpsoListTypeDefinitionRel.setCPSpecificationOptionId(
			RandomTestUtil.nextLong());

		cpsoListTypeDefinitionRel.setListTypeDefinitionId(
			RandomTestUtil.nextLong());

		_cpsoListTypeDefinitionRels.add(
			_persistence.update(cpsoListTypeDefinitionRel));

		return cpsoListTypeDefinitionRel;
	}

	private List<CPSOListTypeDefinitionRel> _cpsoListTypeDefinitionRels =
		new ArrayList<CPSOListTypeDefinitionRel>();
	private CPSOListTypeDefinitionRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}