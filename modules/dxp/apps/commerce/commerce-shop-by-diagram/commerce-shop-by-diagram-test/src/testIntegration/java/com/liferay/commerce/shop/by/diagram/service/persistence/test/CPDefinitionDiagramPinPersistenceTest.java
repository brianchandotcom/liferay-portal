/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramPinException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramPinPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramPinUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CPDefinitionDiagramPinPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.shop.by.diagram.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionDiagramPinUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionDiagramPin> iterator =
			_cpDefinitionDiagramPins.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramPin cpDefinitionDiagramPin = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionDiagramPin);

		Assert.assertEquals(cpDefinitionDiagramPin.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		_persistence.remove(newCPDefinitionDiagramPin);

		CPDefinitionDiagramPin existingCPDefinitionDiagramPin =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramPin.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionDiagramPin);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionDiagramPin();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramPin newCPDefinitionDiagramPin = _persistence.create(
			pk);

		newCPDefinitionDiagramPin.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramPin.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramPin.setUserName(RandomTestUtil.randomString());

		newCPDefinitionDiagramPin.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramPin.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramPin.setCPDefinitionId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramPin.setNumber(RandomTestUtil.nextInt());

		newCPDefinitionDiagramPin.setPositionX(RandomTestUtil.nextDouble());

		newCPDefinitionDiagramPin.setPositionY(RandomTestUtil.nextDouble());

		_cpDefinitionDiagramPins.add(
			_persistence.update(newCPDefinitionDiagramPin));

		CPDefinitionDiagramPin existingCPDefinitionDiagramPin =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramPin.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
			newCPDefinitionDiagramPin.getCPDefinitionDiagramPinId());
		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getCompanyId(),
			newCPDefinitionDiagramPin.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getUserId(),
			newCPDefinitionDiagramPin.getUserId());
		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getUserName(),
			newCPDefinitionDiagramPin.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramPin.getCreateDate()),
			Time.getShortTimestamp(newCPDefinitionDiagramPin.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramPin.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramPin.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getCPDefinitionId(),
			newCPDefinitionDiagramPin.getCPDefinitionId());
		Assert.assertEquals(
			existingCPDefinitionDiagramPin.getNumber(),
			newCPDefinitionDiagramPin.getNumber());
		AssertUtils.assertEquals(
			existingCPDefinitionDiagramPin.getPositionX(),
			newCPDefinitionDiagramPin.getPositionX());
		AssertUtils.assertEquals(
			existingCPDefinitionDiagramPin.getPositionY(),
			newCPDefinitionDiagramPin.getPositionY());
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		CPDefinitionDiagramPin existingCPDefinitionDiagramPin =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramPin.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramPin, newCPDefinitionDiagramPin);
	}

	@Test(expected = NoSuchCPDefinitionDiagramPinException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionDiagramPin> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CPDefinitionDiagramPin", "CPDefinitionDiagramPinId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPDefinitionId", true, "number", true,
			"positionX", true, "positionY", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		CPDefinitionDiagramPin existingCPDefinitionDiagramPin =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramPin.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramPin, newCPDefinitionDiagramPin);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramPin missingCPDefinitionDiagramPin =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionDiagramPin);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramPin newCPDefinitionDiagramPin1 =
			addCPDefinitionDiagramPin();
		CPDefinitionDiagramPin newCPDefinitionDiagramPin2 =
			addCPDefinitionDiagramPin();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramPin1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionDiagramPin2.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionDiagramPins.size());
		Assert.assertEquals(
			newCPDefinitionDiagramPin1,
			cpDefinitionDiagramPins.get(
				newCPDefinitionDiagramPin1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionDiagramPin2,
			cpDefinitionDiagramPins.get(
				newCPDefinitionDiagramPin2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramPins.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramPin.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramPins.size());
		Assert.assertEquals(
			newCPDefinitionDiagramPin,
			cpDefinitionDiagramPins.get(
				newCPDefinitionDiagramPin.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramPins.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramPin.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramPins.size());
		Assert.assertEquals(
			newCPDefinitionDiagramPin,
			cpDefinitionDiagramPins.get(
				newCPDefinitionDiagramPin.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionDiagramPinLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionDiagramPin>() {

				@Override
				public void performAction(
					CPDefinitionDiagramPin cpDefinitionDiagramPin) {

					Assert.assertNotNull(cpDefinitionDiagramPin);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramPin.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramPinId",
				newCPDefinitionDiagramPin.getCPDefinitionDiagramPinId()));

		List<CPDefinitionDiagramPin> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionDiagramPin existingCPDefinitionDiagramPin = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramPin, newCPDefinitionDiagramPin);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramPin.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramPinId", RandomTestUtil.nextLong()));

		List<CPDefinitionDiagramPin> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionDiagramPin newCPDefinitionDiagramPin =
			addCPDefinitionDiagramPin();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramPin.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramPinId"));

		Object newCPDefinitionDiagramPinId =
			newCPDefinitionDiagramPin.getCPDefinitionDiagramPinId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramPinId",
				new Object[] {newCPDefinitionDiagramPinId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionDiagramPinId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramPinId, newCPDefinitionDiagramPinId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramPin.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramPinId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramPinId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CPDefinitionDiagramPin addCPDefinitionDiagramPin()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramPin cpDefinitionDiagramPin = _persistence.create(pk);

		cpDefinitionDiagramPin.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionDiagramPin.setUserId(RandomTestUtil.nextLong());

		cpDefinitionDiagramPin.setUserName(RandomTestUtil.randomString());

		cpDefinitionDiagramPin.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramPin.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramPin.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionDiagramPin.setNumber(RandomTestUtil.nextInt());

		cpDefinitionDiagramPin.setPositionX(RandomTestUtil.nextDouble());

		cpDefinitionDiagramPin.setPositionY(RandomTestUtil.nextDouble());

		_cpDefinitionDiagramPins.add(
			_persistence.update(cpDefinitionDiagramPin));

		return cpDefinitionDiagramPin;
	}

	private List<CPDefinitionDiagramPin> _cpDefinitionDiagramPins =
		new ArrayList<CPDefinitionDiagramPin>();
	private CPDefinitionDiagramPinPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}