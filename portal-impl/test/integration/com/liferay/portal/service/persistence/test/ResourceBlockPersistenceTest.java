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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchResourceBlockException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.persistence.ResourceBlockPersistence;
import com.liferay.portal.service.persistence.ResourceBlockUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class ResourceBlockPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ResourceBlockUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ResourceBlock> iterator = _resourceBlocks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		Assert.assertNotNull(resourceBlock);

		Assert.assertEquals(resourceBlock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.remove(newResourceBlock);

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertNull(existingResourceBlock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceBlock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlock newResourceBlock = _persistence.create(pk);

		newResourceBlock.setMvccVersion(RandomTestUtil.nextLong());

		newResourceBlock.setCompanyId(RandomTestUtil.nextLong());

		newResourceBlock.setGroupId(RandomTestUtil.nextLong());

		newResourceBlock.setName(RandomTestUtil.randomString());

		newResourceBlock.setPermissionsHash(RandomTestUtil.randomString());

		newResourceBlock.setReferenceCount(RandomTestUtil.nextLong());

		_resourceBlocks.add(_persistence.update(newResourceBlock));

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock.getMvccVersion(),
			newResourceBlock.getMvccVersion());
		Assert.assertEquals(existingResourceBlock.getResourceBlockId(),
			newResourceBlock.getResourceBlockId());
		Assert.assertEquals(existingResourceBlock.getCompanyId(),
			newResourceBlock.getCompanyId());
		Assert.assertEquals(existingResourceBlock.getGroupId(),
			newResourceBlock.getGroupId());
		Assert.assertEquals(existingResourceBlock.getName(),
			newResourceBlock.getName());
		Assert.assertEquals(existingResourceBlock.getPermissionsHash(),
			newResourceBlock.getPermissionsHash());
		Assert.assertEquals(existingResourceBlock.getReferenceCount(),
			newResourceBlock.getReferenceCount());
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_N(0L, StringPool.NULL);

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testCountByC_G_N() throws Exception {
		_persistence.countByC_G_N(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_G_N(0L, 0L, StringPool.NULL);

		_persistence.countByC_G_N(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_G_N_P() throws Exception {
		_persistence.countByC_G_N_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK, StringPool.BLANK);

		_persistence.countByC_G_N_P(0L, 0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByC_G_N_P(0L, 0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test(expected = NoSuchResourceBlockException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ResourceBlock> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ResourceBlock",
			"mvccVersion", true, "resourceBlockId", true, "companyId", true,
			"groupId", true, "name", true, "permissionsHash", true,
			"referenceCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlock missingResourceBlock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceBlock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourceBlock newResourceBlock1 = addResourceBlock();
		ResourceBlock newResourceBlock2 = addResourceBlock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock1.getPrimaryKey());
		primaryKeys.add(newResourceBlock2.getPrimaryKey());

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock1,
			resourceBlocks.get(newResourceBlock1.getPrimaryKey()));
		Assert.assertEquals(newResourceBlock2,
			resourceBlocks.get(newResourceBlock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock,
			resourceBlocks.get(newResourceBlock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceBlocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceBlock.getPrimaryKey());

		Map<Serializable, ResourceBlock> resourceBlocks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceBlocks.size());
		Assert.assertEquals(newResourceBlock,
			resourceBlocks.get(newResourceBlock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourceBlockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourceBlock resourceBlock = (ResourceBlock)object;

					Assert.assertNotNull(resourceBlock);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				newResourceBlock.getResourceBlockId()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceBlock existingResourceBlock = result.get(0);

		Assert.assertEquals(existingResourceBlock, newResourceBlock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				RandomTestUtil.nextLong()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		Object newResourceBlockId = newResourceBlock.getResourceBlockId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { newResourceBlockId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceBlockId = result.get(0);

		Assert.assertEquals(existingResourceBlockId, newResourceBlockId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.clearCache();

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		Assert.assertEquals(existingResourceBlock.getCompanyId(),
			ReflectionTestUtil.invoke(existingResourceBlock,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(existingResourceBlock.getGroupId(),
			ReflectionTestUtil.invoke(existingResourceBlock,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingResourceBlock.getName(),
				ReflectionTestUtil.invoke(existingResourceBlock,
					"getOriginalName", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingResourceBlock.getPermissionsHash(),
				ReflectionTestUtil.invoke(existingResourceBlock,
					"getOriginalPermissionsHash", new Class<?>[0])));
	}

	protected ResourceBlock addResourceBlock() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		resourceBlock.setMvccVersion(RandomTestUtil.nextLong());

		resourceBlock.setCompanyId(RandomTestUtil.nextLong());

		resourceBlock.setGroupId(RandomTestUtil.nextLong());

		resourceBlock.setName(RandomTestUtil.randomString());

		resourceBlock.setPermissionsHash(RandomTestUtil.randomString());

		resourceBlock.setReferenceCount(RandomTestUtil.nextLong());

		_resourceBlocks.add(_persistence.update(resourceBlock));

		return resourceBlock;
	}

	private List<ResourceBlock> _resourceBlocks = new ArrayList<ResourceBlock>();
	private ResourceBlockPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}