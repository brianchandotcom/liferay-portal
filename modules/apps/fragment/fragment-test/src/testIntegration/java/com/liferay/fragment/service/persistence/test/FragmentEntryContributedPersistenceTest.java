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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.NoSuchEntryContributedException;
import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.fragment.service.FragmentEntryContributedLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentEntryContributedPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryContributedUtil;
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
public class FragmentEntryContributedPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentEntryContributedUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentEntryContributed> iterator =
			_fragmentEntryContributeds.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryContributed fragmentEntryContributed = _persistence.create(
			pk);

		Assert.assertNotNull(fragmentEntryContributed);

		Assert.assertEquals(fragmentEntryContributed.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		_persistence.remove(newFragmentEntryContributed);

		FragmentEntryContributed existingFragmentEntryContributed =
			_persistence.fetchByPrimaryKey(
				newFragmentEntryContributed.getPrimaryKey());

		Assert.assertNull(existingFragmentEntryContributed);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentEntryContributed();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryContributed newFragmentEntryContributed =
			_persistence.create(pk);

		newFragmentEntryContributed.setMvccVersion(RandomTestUtil.nextLong());

		newFragmentEntryContributed.setCtCollectionId(
			RandomTestUtil.nextLong());

		newFragmentEntryContributed.setCreateDate(RandomTestUtil.nextDate());

		newFragmentEntryContributed.setModifiedDate(RandomTestUtil.nextDate());

		newFragmentEntryContributed.setFragmentEntryKey(
			RandomTestUtil.randomString());

		newFragmentEntryContributed.setCss(RandomTestUtil.randomString());

		newFragmentEntryContributed.setHtml(RandomTestUtil.randomString());

		newFragmentEntryContributed.setJs(RandomTestUtil.randomString());

		newFragmentEntryContributed.setConfiguration(
			RandomTestUtil.randomString());

		newFragmentEntryContributed.setType(RandomTestUtil.nextInt());

		_fragmentEntryContributeds.add(
			_persistence.update(newFragmentEntryContributed));

		FragmentEntryContributed existingFragmentEntryContributed =
			_persistence.findByPrimaryKey(
				newFragmentEntryContributed.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentEntryContributed.getMvccVersion(),
			newFragmentEntryContributed.getMvccVersion());
		Assert.assertEquals(
			existingFragmentEntryContributed.getCtCollectionId(),
			newFragmentEntryContributed.getCtCollectionId());
		Assert.assertEquals(
			existingFragmentEntryContributed.getFragmentEntryContributedId(),
			newFragmentEntryContributed.getFragmentEntryContributedId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentEntryContributed.getCreateDate()),
			Time.getShortTimestamp(
				newFragmentEntryContributed.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentEntryContributed.getModifiedDate()),
			Time.getShortTimestamp(
				newFragmentEntryContributed.getModifiedDate()));
		Assert.assertEquals(
			existingFragmentEntryContributed.getFragmentEntryKey(),
			newFragmentEntryContributed.getFragmentEntryKey());
		Assert.assertEquals(
			existingFragmentEntryContributed.getCss(),
			newFragmentEntryContributed.getCss());
		Assert.assertEquals(
			existingFragmentEntryContributed.getHtml(),
			newFragmentEntryContributed.getHtml());
		Assert.assertEquals(
			existingFragmentEntryContributed.getJs(),
			newFragmentEntryContributed.getJs());
		Assert.assertEquals(
			existingFragmentEntryContributed.getConfiguration(),
			newFragmentEntryContributed.getConfiguration());
		Assert.assertEquals(
			existingFragmentEntryContributed.getType(),
			newFragmentEntryContributed.getType());
	}

	@Test
	public void testCountByFragmentEntryKey() throws Exception {
		_persistence.countByFragmentEntryKey("");

		_persistence.countByFragmentEntryKey("null");

		_persistence.countByFragmentEntryKey((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		FragmentEntryContributed existingFragmentEntryContributed =
			_persistence.findByPrimaryKey(
				newFragmentEntryContributed.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentEntryContributed, newFragmentEntryContributed);
	}

	@Test(expected = NoSuchEntryContributedException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FragmentEntryContributed>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"FragmentEntryContributed", "mvccVersion", true, "ctCollectionId",
			true, "fragmentEntryContributedId", true, "createDate", true,
			"modifiedDate", true, "fragmentEntryKey", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		FragmentEntryContributed existingFragmentEntryContributed =
			_persistence.fetchByPrimaryKey(
				newFragmentEntryContributed.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentEntryContributed, newFragmentEntryContributed);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryContributed missingFragmentEntryContributed =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentEntryContributed);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FragmentEntryContributed newFragmentEntryContributed1 =
			addFragmentEntryContributed();
		FragmentEntryContributed newFragmentEntryContributed2 =
			addFragmentEntryContributed();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryContributed1.getPrimaryKey());
		primaryKeys.add(newFragmentEntryContributed2.getPrimaryKey());

		Map<Serializable, FragmentEntryContributed> fragmentEntryContributeds =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentEntryContributeds.size());
		Assert.assertEquals(
			newFragmentEntryContributed1,
			fragmentEntryContributeds.get(
				newFragmentEntryContributed1.getPrimaryKey()));
		Assert.assertEquals(
			newFragmentEntryContributed2,
			fragmentEntryContributeds.get(
				newFragmentEntryContributed2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentEntryContributed> fragmentEntryContributeds =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryContributeds.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryContributed.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentEntryContributed> fragmentEntryContributeds =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryContributeds.size());
		Assert.assertEquals(
			newFragmentEntryContributed,
			fragmentEntryContributeds.get(
				newFragmentEntryContributed.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentEntryContributed> fragmentEntryContributeds =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryContributeds.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryContributed.getPrimaryKey());

		Map<Serializable, FragmentEntryContributed> fragmentEntryContributeds =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryContributeds.size());
		Assert.assertEquals(
			newFragmentEntryContributed,
			fragmentEntryContributeds.get(
				newFragmentEntryContributed.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FragmentEntryContributedLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FragmentEntryContributed>() {

				@Override
				public void performAction(
					FragmentEntryContributed fragmentEntryContributed) {

					Assert.assertNotNull(fragmentEntryContributed);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryContributed.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryContributedId",
				newFragmentEntryContributed.getFragmentEntryContributedId()));

		List<FragmentEntryContributed> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentEntryContributed existingFragmentEntryContributed = result.get(
			0);

		Assert.assertEquals(
			existingFragmentEntryContributed, newFragmentEntryContributed);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryContributed.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryContributedId", RandomTestUtil.nextLong()));

		List<FragmentEntryContributed> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryContributed.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryContributedId"));

		Object newFragmentEntryContributedId =
			newFragmentEntryContributed.getFragmentEntryContributedId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryContributedId",
				new Object[] {newFragmentEntryContributedId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentEntryContributedId = result.get(0);

		Assert.assertEquals(
			existingFragmentEntryContributedId, newFragmentEntryContributedId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryContributed.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryContributedId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryContributedId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newFragmentEntryContributed.getPrimaryKey()));
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

		FragmentEntryContributed newFragmentEntryContributed =
			addFragmentEntryContributed();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryContributed.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryContributedId",
				newFragmentEntryContributed.getFragmentEntryContributedId()));

		List<FragmentEntryContributed> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		FragmentEntryContributed fragmentEntryContributed) {

		Assert.assertEquals(
			fragmentEntryContributed.getFragmentEntryKey(),
			ReflectionTestUtil.invoke(
				fragmentEntryContributed, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "fragmentEntryKey"));
	}

	protected FragmentEntryContributed addFragmentEntryContributed()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		FragmentEntryContributed fragmentEntryContributed = _persistence.create(
			pk);

		fragmentEntryContributed.setMvccVersion(RandomTestUtil.nextLong());

		fragmentEntryContributed.setCtCollectionId(RandomTestUtil.nextLong());

		fragmentEntryContributed.setCreateDate(RandomTestUtil.nextDate());

		fragmentEntryContributed.setModifiedDate(RandomTestUtil.nextDate());

		fragmentEntryContributed.setFragmentEntryKey(
			RandomTestUtil.randomString());

		fragmentEntryContributed.setCss(RandomTestUtil.randomString());

		fragmentEntryContributed.setHtml(RandomTestUtil.randomString());

		fragmentEntryContributed.setJs(RandomTestUtil.randomString());

		fragmentEntryContributed.setConfiguration(
			RandomTestUtil.randomString());

		fragmentEntryContributed.setType(RandomTestUtil.nextInt());

		_fragmentEntryContributeds.add(
			_persistence.update(fragmentEntryContributed));

		return fragmentEntryContributed;
	}

	private List<FragmentEntryContributed> _fragmentEntryContributeds =
		new ArrayList<FragmentEntryContributed>();
	private FragmentEntryContributedPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}