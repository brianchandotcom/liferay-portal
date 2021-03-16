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

package com.liferay.portal.search.tuning.blueprints.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchBlueprintException;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalServiceUtil;
import com.liferay.portal.search.tuning.blueprints.service.persistence.BlueprintPersistence;
import com.liferay.portal.search.tuning.blueprints.service.persistence.BlueprintUtil;
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
public class BlueprintPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.search.tuning.blueprints.service"));

	@Before
	public void setUp() {
		_persistence = BlueprintUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Blueprint> iterator = _blueprints.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Blueprint blueprint = _persistence.create(pk);

		Assert.assertNotNull(blueprint);

		Assert.assertEquals(blueprint.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		_persistence.remove(newBlueprint);

		Blueprint existingBlueprint = _persistence.fetchByPrimaryKey(
			newBlueprint.getPrimaryKey());

		Assert.assertNull(existingBlueprint);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBlueprint();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Blueprint newBlueprint = _persistence.create(pk);

		newBlueprint.setMvccVersion(RandomTestUtil.nextLong());

		newBlueprint.setUuid(RandomTestUtil.randomString());

		newBlueprint.setGroupId(RandomTestUtil.nextLong());

		newBlueprint.setCompanyId(RandomTestUtil.nextLong());

		newBlueprint.setUserId(RandomTestUtil.nextLong());

		newBlueprint.setUserName(RandomTestUtil.randomString());

		newBlueprint.setCreateDate(RandomTestUtil.nextDate());

		newBlueprint.setModifiedDate(RandomTestUtil.nextDate());

		newBlueprint.setStatus(RandomTestUtil.nextInt());

		newBlueprint.setStatusByUserId(RandomTestUtil.nextLong());

		newBlueprint.setStatusByUserName(RandomTestUtil.randomString());

		newBlueprint.setStatusDate(RandomTestUtil.nextDate());

		newBlueprint.setTitle(RandomTestUtil.randomString());

		newBlueprint.setDescription(RandomTestUtil.randomString());

		newBlueprint.setConfiguration(RandomTestUtil.randomString());

		newBlueprint.setSelectedElements(RandomTestUtil.randomString());

		newBlueprint.setType(RandomTestUtil.nextInt());

		_blueprints.add(_persistence.update(newBlueprint));

		Blueprint existingBlueprint = _persistence.findByPrimaryKey(
			newBlueprint.getPrimaryKey());

		Assert.assertEquals(
			existingBlueprint.getMvccVersion(), newBlueprint.getMvccVersion());
		Assert.assertEquals(
			existingBlueprint.getUuid(), newBlueprint.getUuid());
		Assert.assertEquals(
			existingBlueprint.getBlueprintId(), newBlueprint.getBlueprintId());
		Assert.assertEquals(
			existingBlueprint.getGroupId(), newBlueprint.getGroupId());
		Assert.assertEquals(
			existingBlueprint.getCompanyId(), newBlueprint.getCompanyId());
		Assert.assertEquals(
			existingBlueprint.getUserId(), newBlueprint.getUserId());
		Assert.assertEquals(
			existingBlueprint.getUserName(), newBlueprint.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingBlueprint.getCreateDate()),
			Time.getShortTimestamp(newBlueprint.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingBlueprint.getModifiedDate()),
			Time.getShortTimestamp(newBlueprint.getModifiedDate()));
		Assert.assertEquals(
			existingBlueprint.getStatus(), newBlueprint.getStatus());
		Assert.assertEquals(
			existingBlueprint.getStatusByUserId(),
			newBlueprint.getStatusByUserId());
		Assert.assertEquals(
			existingBlueprint.getStatusByUserName(),
			newBlueprint.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingBlueprint.getStatusDate()),
			Time.getShortTimestamp(newBlueprint.getStatusDate()));
		Assert.assertEquals(
			existingBlueprint.getTitle(), newBlueprint.getTitle());
		Assert.assertEquals(
			existingBlueprint.getDescription(), newBlueprint.getDescription());
		Assert.assertEquals(
			existingBlueprint.getConfiguration(),
			newBlueprint.getConfiguration());
		Assert.assertEquals(
			existingBlueprint.getSelectedElements(),
			newBlueprint.getSelectedElements());
		Assert.assertEquals(
			existingBlueprint.getType(), newBlueprint.getType());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_T(0L, 0);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_T(0L, 0);
	}

	@Test
	public void testCountByG_S_T() throws Exception {
		_persistence.countByG_S_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_S_T(0L, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		Blueprint existingBlueprint = _persistence.findByPrimaryKey(
			newBlueprint.getPrimaryKey());

		Assert.assertEquals(existingBlueprint, newBlueprint);
	}

	@Test(expected = NoSuchBlueprintException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Blueprint> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Blueprint", "mvccVersion", true, "uuid", true, "blueprintId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate",
			true, "title", true, "description", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		Blueprint existingBlueprint = _persistence.fetchByPrimaryKey(
			newBlueprint.getPrimaryKey());

		Assert.assertEquals(existingBlueprint, newBlueprint);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Blueprint missingBlueprint = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBlueprint);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Blueprint newBlueprint1 = addBlueprint();
		Blueprint newBlueprint2 = addBlueprint();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlueprint1.getPrimaryKey());
		primaryKeys.add(newBlueprint2.getPrimaryKey());

		Map<Serializable, Blueprint> blueprints =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, blueprints.size());
		Assert.assertEquals(
			newBlueprint1, blueprints.get(newBlueprint1.getPrimaryKey()));
		Assert.assertEquals(
			newBlueprint2, blueprints.get(newBlueprint2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Blueprint> blueprints =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blueprints.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Blueprint newBlueprint = addBlueprint();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlueprint.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Blueprint> blueprints =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blueprints.size());
		Assert.assertEquals(
			newBlueprint, blueprints.get(newBlueprint.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Blueprint> blueprints =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blueprints.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlueprint.getPrimaryKey());

		Map<Serializable, Blueprint> blueprints =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blueprints.size());
		Assert.assertEquals(
			newBlueprint, blueprints.get(newBlueprint.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			BlueprintLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Blueprint>() {

				@Override
				public void performAction(Blueprint blueprint) {
					Assert.assertNotNull(blueprint);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Blueprint.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blueprintId", newBlueprint.getBlueprintId()));

		List<Blueprint> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Blueprint existingBlueprint = result.get(0);

		Assert.assertEquals(existingBlueprint, newBlueprint);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Blueprint.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blueprintId", RandomTestUtil.nextLong()));

		List<Blueprint> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Blueprint.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("blueprintId"));

		Object newBlueprintId = newBlueprint.getBlueprintId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"blueprintId", new Object[] {newBlueprintId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBlueprintId = result.get(0);

		Assert.assertEquals(existingBlueprintId, newBlueprintId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Blueprint.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("blueprintId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"blueprintId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Blueprint newBlueprint = addBlueprint();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newBlueprint.getPrimaryKey()));
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

		Blueprint newBlueprint = addBlueprint();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Blueprint.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"blueprintId", newBlueprint.getBlueprintId()));

		List<Blueprint> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Blueprint blueprint) {
		Assert.assertEquals(
			blueprint.getUuid(),
			ReflectionTestUtil.invoke(
				blueprint, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(blueprint.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				blueprint, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected Blueprint addBlueprint() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Blueprint blueprint = _persistence.create(pk);

		blueprint.setMvccVersion(RandomTestUtil.nextLong());

		blueprint.setUuid(RandomTestUtil.randomString());

		blueprint.setGroupId(RandomTestUtil.nextLong());

		blueprint.setCompanyId(RandomTestUtil.nextLong());

		blueprint.setUserId(RandomTestUtil.nextLong());

		blueprint.setUserName(RandomTestUtil.randomString());

		blueprint.setCreateDate(RandomTestUtil.nextDate());

		blueprint.setModifiedDate(RandomTestUtil.nextDate());

		blueprint.setStatus(RandomTestUtil.nextInt());

		blueprint.setStatusByUserId(RandomTestUtil.nextLong());

		blueprint.setStatusByUserName(RandomTestUtil.randomString());

		blueprint.setStatusDate(RandomTestUtil.nextDate());

		blueprint.setTitle(RandomTestUtil.randomString());

		blueprint.setDescription(RandomTestUtil.randomString());

		blueprint.setConfiguration(RandomTestUtil.randomString());

		blueprint.setSelectedElements(RandomTestUtil.randomString());

		blueprint.setType(RandomTestUtil.nextInt());

		_blueprints.add(_persistence.update(blueprint));

		return blueprint;
	}

	private List<Blueprint> _blueprints = new ArrayList<Blueprint>();
	private BlueprintPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}