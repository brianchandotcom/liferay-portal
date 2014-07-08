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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class GroupPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Group> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<Group> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Group group = _persistence.create(pk);

		Assert.assertNotNull(group);

		Assert.assertEquals(group.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Group newGroup = addGroup();

		_persistence.remove(newGroup);

		Group existingGroup = _persistence.fetchByPrimaryKey(newGroup.getPrimaryKey());

		Assert.assertNull(existingGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Group newGroup = _persistence.create(pk);

		newGroup.setMvccVersion(RandomTestUtil.nextLong());

		newGroup.setUuid(RandomTestUtil.randomString());

		newGroup.setCompanyId(RandomTestUtil.nextLong());

		newGroup.setCreatorUserId(RandomTestUtil.nextLong());

		newGroup.setClassNameId(RandomTestUtil.nextLong());

		newGroup.setClassPK(RandomTestUtil.nextLong());

		newGroup.setParentGroupId(RandomTestUtil.nextLong());

		newGroup.setLiveGroupId(RandomTestUtil.nextLong());

		newGroup.setTreePath(RandomTestUtil.randomString());

		newGroup.setName(RandomTestUtil.randomString());

		newGroup.setDescription(RandomTestUtil.randomString());

		newGroup.setType(RandomTestUtil.nextInt());

		newGroup.setTypeSettings(RandomTestUtil.randomString());

		newGroup.setManualMembership(RandomTestUtil.randomBoolean());

		newGroup.setMembershipRestriction(RandomTestUtil.nextInt());

		newGroup.setFriendlyURL(RandomTestUtil.randomString());

		newGroup.setSite(RandomTestUtil.randomBoolean());

		newGroup.setRemoteStagingGroupCount(RandomTestUtil.nextInt());

		newGroup.setActive(RandomTestUtil.randomBoolean());

		_persistence.update(newGroup);

		Group existingGroup = _persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		Assert.assertEquals(existingGroup.getMvccVersion(),
			newGroup.getMvccVersion());
		Assert.assertEquals(existingGroup.getUuid(), newGroup.getUuid());
		Assert.assertEquals(existingGroup.getGroupId(), newGroup.getGroupId());
		Assert.assertEquals(existingGroup.getCompanyId(),
			newGroup.getCompanyId());
		Assert.assertEquals(existingGroup.getCreatorUserId(),
			newGroup.getCreatorUserId());
		Assert.assertEquals(existingGroup.getClassNameId(),
			newGroup.getClassNameId());
		Assert.assertEquals(existingGroup.getClassPK(), newGroup.getClassPK());
		Assert.assertEquals(existingGroup.getParentGroupId(),
			newGroup.getParentGroupId());
		Assert.assertEquals(existingGroup.getLiveGroupId(),
			newGroup.getLiveGroupId());
		Assert.assertEquals(existingGroup.getTreePath(), newGroup.getTreePath());
		Assert.assertEquals(existingGroup.getName(), newGroup.getName());
		Assert.assertEquals(existingGroup.getDescription(),
			newGroup.getDescription());
		Assert.assertEquals(existingGroup.getType(), newGroup.getType());
		Assert.assertEquals(existingGroup.getTypeSettings(),
			newGroup.getTypeSettings());
		Assert.assertEquals(existingGroup.getManualMembership(),
			newGroup.getManualMembership());
		Assert.assertEquals(existingGroup.getMembershipRestriction(),
			newGroup.getMembershipRestriction());
		Assert.assertEquals(existingGroup.getFriendlyURL(),
			newGroup.getFriendlyURL());
		Assert.assertEquals(existingGroup.getSite(), newGroup.getSite());
		Assert.assertEquals(existingGroup.getRemoteStagingGroupCount(),
			newGroup.getRemoteStagingGroupCount());
		Assert.assertEquals(existingGroup.getActive(), newGroup.getActive());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLiveGroupId() {
		try {
			_persistence.countByLiveGroupId(RandomTestUtil.nextLong());

			_persistence.countByLiveGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_P() {
		try {
			_persistence.countByC_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N() {
		try {
			_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_N(0L, StringPool.NULL);

			_persistence.countByC_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_F() {
		try {
			_persistence.countByC_F(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_F(0L, StringPool.NULL);

			_persistence.countByC_F(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_S() {
		try {
			_persistence.countByC_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_S(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_A() {
		try {
			_persistence.countByT_A(RandomTestUtil.nextInt(),
				RandomTestUtil.randomBoolean());

			_persistence.countByT_A(0, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_P() {
		try {
			_persistence.countByG_C_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_C_P(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_C() {
		try {
			_persistence.countByC_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByC_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_P() {
		try {
			_persistence.countByC_C_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByC_C_P(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_P_S() {
		try {
			_persistence.countByC_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

			_persistence.countByC_P_S(0L, 0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_L_N() {
		try {
			_persistence.countByC_L_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_L_N(0L, 0L, StringPool.NULL);

			_persistence.countByC_L_N(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_L_N() {
		try {
			_persistence.countByC_C_L_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByC_C_L_N(0L, 0L, 0L, StringPool.NULL);

			_persistence.countByC_C_L_N(0L, 0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Group newGroup = addGroup();

		Group existingGroup = _persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		Assert.assertEquals(existingGroup, newGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchGroupException");
		}
		catch (NoSuchGroupException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Group_", "mvccVersion",
			true, "uuid", true, "groupId", true, "companyId", true,
			"creatorUserId", true, "classNameId", true, "classPK", true,
			"parentGroupId", true, "liveGroupId", true, "treePath", true,
			"name", true, "description", true, "type", true, "typeSettings",
			true, "manualMembership", true, "membershipRestriction", true,
			"friendlyURL", true, "site", true, "remoteStagingGroupCount", true,
			"active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Group newGroup = addGroup();

		Group existingGroup = _persistence.fetchByPrimaryKey(newGroup.getPrimaryKey());

		Assert.assertEquals(existingGroup, newGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Group missingGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Group newGroup1 = addGroup();
		Group newGroup2 = addGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroup1.getPrimaryKey());
		primaryKeys.add(newGroup2.getPrimaryKey());

		Map<Serializable, Group> groups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, groups.size());
		Assert.assertEquals(newGroup1, groups.get(newGroup1.getPrimaryKey()));
		Assert.assertEquals(newGroup2, groups.get(newGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Group> groups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Group newGroup = addGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Group> groups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groups.size());
		Assert.assertEquals(newGroup, groups.get(newGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Group> groups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Group newGroup = addGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroup.getPrimaryKey());

		Map<Serializable, Group> groups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groups.size());
		Assert.assertEquals(newGroup, groups.get(newGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Group group = (Group)object;

					Assert.assertNotNull(group);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Group newGroup = addGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId",
				newGroup.getGroupId()));

		List<Group> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Group existingGroup = result.get(0);

		Assert.assertEquals(existingGroup, newGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId",
				RandomTestUtil.nextLong()));

		List<Group> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Group newGroup = addGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("groupId"));

		Object newGroupId = newGroup.getGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupId",
				new Object[] { newGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingGroupId = result.get(0);

		Assert.assertEquals(existingGroupId, newGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("groupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Group newGroup = addGroup();

		_persistence.clearCache();

		GroupModelImpl existingGroupModelImpl = (GroupModelImpl)_persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingGroupModelImpl.getUuid(),
				existingGroupModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingGroupModelImpl.getGroupId(),
			existingGroupModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());

		Assert.assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));

		Assert.assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingGroupModelImpl.getFriendlyURL(),
				existingGroupModelImpl.getOriginalFriendlyURL()));

		Assert.assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingGroupModelImpl.getClassNameId(),
			existingGroupModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingGroupModelImpl.getClassPK(),
			existingGroupModelImpl.getOriginalClassPK());

		Assert.assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());
		Assert.assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));

		Assert.assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingGroupModelImpl.getClassNameId(),
			existingGroupModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());
		Assert.assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));
	}

	protected Group addGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Group group = _persistence.create(pk);

		group.setMvccVersion(RandomTestUtil.nextLong());

		group.setUuid(RandomTestUtil.randomString());

		group.setCompanyId(RandomTestUtil.nextLong());

		group.setCreatorUserId(RandomTestUtil.nextLong());

		group.setClassNameId(RandomTestUtil.nextLong());

		group.setClassPK(RandomTestUtil.nextLong());

		group.setParentGroupId(RandomTestUtil.nextLong());

		group.setLiveGroupId(RandomTestUtil.nextLong());

		group.setTreePath(RandomTestUtil.randomString());

		group.setName(RandomTestUtil.randomString());

		group.setDescription(RandomTestUtil.randomString());

		group.setType(RandomTestUtil.nextInt());

		group.setTypeSettings(RandomTestUtil.randomString());

		group.setManualMembership(RandomTestUtil.randomBoolean());

		group.setMembershipRestriction(RandomTestUtil.nextInt());

		group.setFriendlyURL(RandomTestUtil.randomString());

		group.setSite(RandomTestUtil.randomBoolean());

		group.setRemoteStagingGroupCount(RandomTestUtil.nextInt());

		group.setActive(RandomTestUtil.randomBoolean());

		_persistence.update(group);

		return group;
	}

	private static Log _log = LogFactoryUtil.getLog(GroupPersistenceTest.class);
	private ModelListener<Group>[] _modelListeners;
	private GroupPersistence _persistence = (GroupPersistence)PortalBeanLocatorUtil.locate(GroupPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}