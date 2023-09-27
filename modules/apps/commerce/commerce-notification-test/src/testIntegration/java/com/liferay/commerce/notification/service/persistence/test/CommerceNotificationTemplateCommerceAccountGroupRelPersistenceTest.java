/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateCommerceAccountGroupRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateCommerceAccountGroupRelLocalServiceUtil;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateCommerceAccountGroupRelPersistence;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateCommerceAccountGroupRelUtil;
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
public class
	CommerceNotificationTemplateCommerceAccountGroupRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.notification.service"));

	@Before
	public void setUp() {
		_persistence =
			CommerceNotificationTemplateCommerceAccountGroupRelUtil.
				getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceNotificationTemplateCommerceAccountGroupRel> iterator =
			_commerceNotificationTemplateCommerceAccountGroupRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.create(pk);

		Assert.assertNotNull(
			commerceNotificationTemplateCommerceAccountGroupRel);

		Assert.assertEquals(
			commerceNotificationTemplateCommerceAccountGroupRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		_persistence.remove(
			newCommerceNotificationTemplateCommerceAccountGroupRel);

		CommerceNotificationTemplateCommerceAccountGroupRel
			existingCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.fetchByPrimaryKey(
					newCommerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKey());

		Assert.assertNull(
			existingCommerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceNotificationTemplateCommerceAccountGroupRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.create(pk);

		newCommerceNotificationTemplateCommerceAccountGroupRel.setMvccVersion(
			RandomTestUtil.nextLong());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setGroupId(
			RandomTestUtil.nextLong());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setUserId(
			RandomTestUtil.nextLong());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setUserName(
			RandomTestUtil.randomString());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationTemplateCommerceAccountGroupRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceNotificationTemplateId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceAccountGroupId(RandomTestUtil.nextLong());

		_commerceNotificationTemplateCommerceAccountGroupRels.add(
			_persistence.update(
				newCommerceNotificationTemplateCommerceAccountGroupRel));

		CommerceNotificationTemplateCommerceAccountGroupRel
			existingCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.findByPrimaryKey(
					newCommerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getMvccVersion(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getMvccVersion());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateCommerceAccountGroupRelId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateCommerceAccountGroupRelId());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getGroupId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getGroupId());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getCompanyId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getCompanyId());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getUserId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.getUserId());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getUserName(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationTemplateCommerceAccountGroupRel.
					getCreateDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationTemplateCommerceAccountGroupRel.
					getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getModifiedDate()));
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateId());
		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceAccountGroupId(),
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceAccountGroupId());
	}

	@Test
	public void testCountByCommerceNotificationTemplateId() throws Exception {
		_persistence.countByCommerceNotificationTemplateId(
			RandomTestUtil.nextLong());

		_persistence.countByCommerceNotificationTemplateId(0L);
	}

	@Test
	public void testCountByCommerceAccountGroupId() throws Exception {
		_persistence.countByCommerceAccountGroupId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountGroupId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		CommerceNotificationTemplateCommerceAccountGroupRel
			existingCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.findByPrimaryKey(
					newCommerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel,
			newCommerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Test(
		expected = NoSuchNotificationTemplateCommerceAccountGroupRelException.class
	)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator
		<CommerceNotificationTemplateCommerceAccountGroupRel>
			getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CNTemplateCAccountGroupRel", "mvccVersion", true,
			"commerceNotificationTemplateCommerceAccountGroupRelId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true,
			"commerceNotificationTemplateId", true, "commerceAccountGroupId",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		CommerceNotificationTemplateCommerceAccountGroupRel
			existingCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.fetchByPrimaryKey(
					newCommerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel,
			newCommerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateCommerceAccountGroupRel
			missingCommerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(
			missingCommerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel1 =
				addCommerceNotificationTemplateCommerceAccountGroupRel();
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel2 =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceNotificationTemplateCommerceAccountGroupRel1.
				getPrimaryKey());
		primaryKeys.add(
			newCommerceNotificationTemplateCommerceAccountGroupRel2.
				getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(
			2, commerceNotificationTemplateCommerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceNotificationTemplateCommerceAccountGroupRel1,
			commerceNotificationTemplateCommerceAccountGroupRels.get(
				newCommerceNotificationTemplateCommerceAccountGroupRel1.
					getPrimaryKey()));
		Assert.assertEquals(
			newCommerceNotificationTemplateCommerceAccountGroupRel2,
			commerceNotificationTemplateCommerceAccountGroupRels.get(
				newCommerceNotificationTemplateCommerceAccountGroupRel2.
					getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(
			commerceNotificationTemplateCommerceAccountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(
			1, commerceNotificationTemplateCommerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceNotificationTemplateCommerceAccountGroupRel,
			commerceNotificationTemplateCommerceAccountGroupRels.get(
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(
			commerceNotificationTemplateCommerceAccountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(
			1, commerceNotificationTemplateCommerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceNotificationTemplateCommerceAccountGroupRel,
			commerceNotificationTemplateCommerceAccountGroupRels.get(
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceNotificationTemplateCommerceAccountGroupRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceNotificationTemplateCommerceAccountGroupRel>() {

				@Override
				public void performAction(
					CommerceNotificationTemplateCommerceAccountGroupRel
						commerceNotificationTemplateCommerceAccountGroupRel) {

					Assert.assertNotNull(
						commerceNotificationTemplateCommerceAccountGroupRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateCommerceAccountGroupRelId",
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceNotificationTemplateCommerceAccountGroupRelId()));

		List<CommerceNotificationTemplateCommerceAccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceNotificationTemplateCommerceAccountGroupRel
			existingCommerceNotificationTemplateCommerceAccountGroupRel =
				result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRel,
			newCommerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateCommerceAccountGroupRelId",
				RandomTestUtil.nextLong()));

		List<CommerceNotificationTemplateCommerceAccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceNotificationTemplateCommerceAccountGroupRelId"));

		Object newCommerceNotificationTemplateCommerceAccountGroupRelId =
			newCommerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateCommerceAccountGroupRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateCommerceAccountGroupRelId",
				new Object[] {
					newCommerceNotificationTemplateCommerceAccountGroupRelId
				}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceNotificationTemplateCommerceAccountGroupRelId =
			result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationTemplateCommerceAccountGroupRelId,
			newCommerceNotificationTemplateCommerceAccountGroupRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceNotificationTemplateCommerceAccountGroupRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateCommerceAccountGroupRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getPrimaryKey()));
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

		CommerceNotificationTemplateCommerceAccountGroupRel
			newCommerceNotificationTemplateCommerceAccountGroupRel =
				addCommerceNotificationTemplateCommerceAccountGroupRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateCommerceAccountGroupRelId",
				newCommerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceNotificationTemplateCommerceAccountGroupRelId()));

		List<CommerceNotificationTemplateCommerceAccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		Assert.assertEquals(
			Long.valueOf(
				commerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceNotificationTemplateId()),
			ReflectionTestUtil.<Long>invoke(
				commerceNotificationTemplateCommerceAccountGroupRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"commerceNotificationTemplateId"));
		Assert.assertEquals(
			Long.valueOf(
				commerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceAccountGroupId()),
			ReflectionTestUtil.<Long>invoke(
				commerceNotificationTemplateCommerceAccountGroupRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"commerceAccountGroupId"));
	}

	protected CommerceNotificationTemplateCommerceAccountGroupRel
			addCommerceNotificationTemplateCommerceAccountGroupRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				_persistence.create(pk);

		commerceNotificationTemplateCommerceAccountGroupRel.setMvccVersion(
			RandomTestUtil.nextLong());

		commerceNotificationTemplateCommerceAccountGroupRel.setGroupId(
			RandomTestUtil.nextLong());

		commerceNotificationTemplateCommerceAccountGroupRel.setCompanyId(
			RandomTestUtil.nextLong());

		commerceNotificationTemplateCommerceAccountGroupRel.setUserId(
			RandomTestUtil.nextLong());

		commerceNotificationTemplateCommerceAccountGroupRel.setUserName(
			RandomTestUtil.randomString());

		commerceNotificationTemplateCommerceAccountGroupRel.setCreateDate(
			RandomTestUtil.nextDate());

		commerceNotificationTemplateCommerceAccountGroupRel.setModifiedDate(
			RandomTestUtil.nextDate());

		commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceNotificationTemplateId(RandomTestUtil.nextLong());

		commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceAccountGroupId(RandomTestUtil.nextLong());

		_commerceNotificationTemplateCommerceAccountGroupRels.add(
			_persistence.update(
				commerceNotificationTemplateCommerceAccountGroupRel));

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	private List<CommerceNotificationTemplateCommerceAccountGroupRel>
		_commerceNotificationTemplateCommerceAccountGroupRels =
			new ArrayList
				<CommerceNotificationTemplateCommerceAccountGroupRel>();
	private CommerceNotificationTemplateCommerceAccountGroupRelPersistence
		_persistence;
	private ClassLoader _dynamicQueryClassLoader;

}