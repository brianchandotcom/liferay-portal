/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactory;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactory;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.BaseModelListener;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TableMappingTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.clear();

				assertClasses.add(TableMappingImpl.class);
				assertClasses.add(ReverseTableMapping.class);
				assertClasses.add(TableMappingFactory.class);
			}

		};

	@Before
	public void setUp() {
		Class<?> clazz = TableMappingTest.class;

		ClassLoader classLoader = clazz.getClassLoader();

		_mockDataSource = (DataSource)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {DataSource.class}, null);

		_leftPersistence = new MockPersistence<Left>(Left.class);

		_leftPersistence.setDataSource(_mockDataSource);

		_rightPersistence = new MockPersistence<Right>(Right.class);

		_rightPersistence.setDataSource(_mockDataSource);

		SqlUpdateFactoryUtil sqlUpdateFactoryUtil = new SqlUpdateFactoryUtil();

		sqlUpdateFactoryUtil.setSqlUpdateFactory(new MockSqlUpdateFactory());

		MappingSqlQueryFactoryUtil mappingSqlQueryFactoryUtil =
			new MappingSqlQueryFactoryUtil();

		mappingSqlQueryFactoryUtil.setMappingSqlQueryFactory(
			new MockMappingSqlQueryFactory());

		MultiVMPoolUtil multiVMPoolUtil = new MultiVMPoolUtil();

		multiVMPoolUtil.setMultiVMPool(new MockMultiVMPool());

		_tableMappingImpl = new TableMappingImpl<Left, Right>(
			_mappingTableName, _leftColumnName, _rightColumnName,
			_leftPersistence, _rightPersistence);
	}

	@Test
	public void testAddMapping() throws SystemException {

		// Success, no model listener

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertTrue(
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey));

		// Fail, no model listener

		Assert.assertFalse(
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey));

		// Error, no model listener

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		leftToRightPortalCache.put(leftPrimaryKey, new long[0]);

		try {
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());
			Assert.assertEquals(
				"Unique key violation, leftPrimaryKey : " + leftPrimaryKey +
					", rightPrimaryKey : " + rightPrimaryKey,
				cause.getMessage());
		}

		// Auto recover after error

		Assert.assertFalse(
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey));

		// Success, with model listener

		leftToRightPortalCache.remove(leftPrimaryKey);
		_mappingStore.remove(leftPrimaryKey);

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertTrue(
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey));

		leftModelListener.assertOnBeforeAddAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeAddAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterAddAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnAfterAddAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);

		// Error, no model listener

		leftToRightPortalCache.put(leftPrimaryKey, new long[0]);

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		try {
			_tableMappingImpl.addMapping(leftPrimaryKey, rightPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());
			Assert.assertEquals(
				"Unique key violation, leftPrimaryKey : " + leftPrimaryKey +
					", rightPrimaryKey : " + rightPrimaryKey,
				cause.getMessage());
		}

		leftModelListener.assertOnBeforeAddAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeAddAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterAddAssociation(false, null, null, null);

		rightModelListener.assertOnAfterAddAssociation(false, null, null, null);
	}

	@Test
	public void testConstructor() {
		Assert.assertEquals(_leftColumnName, _tableMappingImpl.leftColumnName);
		Assert.assertEquals(
			_rightColumnName, _tableMappingImpl.rightColumnName);
		Assert.assertSame(
			_leftPersistence, _tableMappingImpl.leftBasePersistence);
		Assert.assertSame(
			_rightPersistence, _tableMappingImpl.rightBasePersistence);

		Assert.assertTrue(
			_tableMappingImpl.addMappingSqlUpdate
				instanceof MockAddMappingSqlUpdate);
		Assert.assertTrue(
			_tableMappingImpl.deleteMappingSqlUpdate
				instanceof MockDeleteMappingSqlUpdate);
		Assert.assertTrue(
			_tableMappingImpl.deleteMappingsByLeftPrimaryKeySqlUpdate
				instanceof MockDeleteMappingsByLeftPrimaryKeySqlUpdate);
		Assert.assertTrue(
			_tableMappingImpl.deleteMappingsByRightPrimaryKeySqlUpdate
				instanceof MockDeleteMappingsByRightPrimaryKeySqlUpdate);

		Assert.assertTrue(
			_tableMappingImpl.getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery
				instanceof
					MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery);
		Assert.assertTrue(
			_tableMappingImpl.getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery
				instanceof
					MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery);

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		Assert.assertTrue(leftToRightPortalCache instanceof MemoryPortalCache);
		Assert.assertEquals(
			_mappingTableName + "-Left->Right",
			leftToRightPortalCache.getName());

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_tableMappingImpl.rightToLeftPortalCache;

		Assert.assertTrue(rightToLeftPortalCache instanceof MemoryPortalCache);
		Assert.assertEquals(
			_mappingTableName + "-Right->Left",
			rightToLeftPortalCache.getName());
	}

	@Test
	public void testContainsMapping() throws SystemException {

		// No such mapping

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertFalse(
			_tableMappingImpl.containsMapping(leftPrimaryKey, rightPrimaryKey));

		// Has mapping

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPrimaryKey);

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey});

		Assert.assertTrue(
			_tableMappingImpl.containsMapping(leftPrimaryKey, rightPrimaryKey));
	}

	@Test
	public void testDeleteMapping() throws SystemException {

		// No such mapping

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertFalse(
			_tableMappingImpl.deleteMapping(leftPrimaryKey, rightPrimaryKey));

		// Success, without model listener

		_mappingStore.put(leftPrimaryKey, new long[]{rightPrimaryKey});

		Assert.assertTrue(
			_tableMappingImpl.deleteMapping(leftPrimaryKey, rightPrimaryKey));

		// Success, with model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey, new long[]{rightPrimaryKey});

		Assert.assertTrue(
			_tableMappingImpl.deleteMapping(leftPrimaryKey, rightPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey, new long[]{rightPrimaryKey});

		MockDeleteMappingSqlUpdate mockDeleteSqlUpdate =
			(MockDeleteMappingSqlUpdate)
				_tableMappingImpl.deleteMappingSqlUpdate;

		mockDeleteSqlUpdate.setDatabaseError(true);

		try {
			_tableMappingImpl.deleteMapping(leftPrimaryKey, rightPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteSqlUpdate.setDatabaseError(false);
			_mappingStore.remove(leftPrimaryKey);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);

		// Phantom delete, with model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		leftToRightPortalCache.put(leftPrimaryKey, new long[]{rightPrimaryKey});

		Assert.assertFalse(
			_tableMappingImpl.deleteMapping(leftPrimaryKey, rightPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);
	}

	@Test
	public void testDeleteMappingsByLeftPrimaryKey() throws SystemException {

		// Delete 0 entry

		long leftPrimaryKey = 1;

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		// Delete 1 entry

		long rightPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey1});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		// Delete 2 entries

		long rightPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey, new long[] {rightPrimaryKey1, rightPrimaryKey2});

		Assert.assertEquals(
			2,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_rightPersistence.unregisterListener(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey1});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey1);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey1);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey1});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey));

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey1, Left.class.getName(), leftPrimaryKey);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey1, Left.class.getName(), leftPrimaryKey);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with both left and right model listeners

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey1});

		MockDeleteMappingsByLeftPrimaryKeySqlUpdate mockDeleteByLeftSqlUpdate =
			(MockDeleteMappingsByLeftPrimaryKeySqlUpdate)
				_tableMappingImpl.deleteMappingsByLeftPrimaryKeySqlUpdate;

		mockDeleteByLeftSqlUpdate.setDatabaseError(true);

		try {
			_tableMappingImpl.deleteMappingsByLeftPrimaryKey(leftPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteByLeftSqlUpdate.setDatabaseError(false);

			_mappingStore.remove(leftPrimaryKey);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey1);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey1, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);
	}

	@Test
	public void testDeleteMappingsByRightPrimaryKey() throws SystemException {

		// Delete 0 entry

		long rightPrimaryKey = 1;

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		// Delete 1 entry

		long leftPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		// Delete 2 entries

		long leftPrimaryKey2 = 3;

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey2, new long[] {rightPrimaryKey});

		Assert.assertEquals(
			2,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertEquals(
			0,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_rightPersistence.unregisterListener(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey1, Right.class.getName(), rightPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey1, Right.class.getName(), rightPrimaryKey);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});

		Assert.assertEquals(
			1,
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey));

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey1);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey1);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with both left and right model listeners

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});

		MockDeleteMappingsByRightPrimaryKeySqlUpdate
			mockDeleteByRightSqlUpdate =
				(MockDeleteMappingsByRightPrimaryKeySqlUpdate)
					_tableMappingImpl.deleteMappingsByRightPrimaryKeySqlUpdate;

		mockDeleteByRightSqlUpdate.setDatabaseError(true);

		try {
			_tableMappingImpl.deleteMappingsByRightPrimaryKey(rightPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteByRightSqlUpdate.setDatabaseError(false);

			_mappingStore.remove(rightPrimaryKey);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey1, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey1);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);
	}

	@Test
	public void testGetLeftBaseModelsByRightPrimaryKey()
		throws SystemException {

		// Get 0 result

		long rightPrimaryKey = 1;

		List<Left> lefts = _tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), lefts);

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_tableMappingImpl.rightToLeftPortalCache;

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// Get 1 result

		long leftPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});

		lefts = _tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, lefts.size());

		Left left1 = lefts.get(0);

		Assert.assertEquals(leftPrimaryKey1, left1.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// Get 2 results, unsorted

		long leftPrimaryKey2 = 3;

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey2, new long[] {rightPrimaryKey});

		lefts = _tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, lefts.size());

		left1 = lefts.get(0);
		Left left2 = lefts.get(1);

		Assert.assertEquals(leftPrimaryKey1, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPrimaryKey2, left2.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// Get 2 results, sorted

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey2, new long[] {rightPrimaryKey});

		lefts = _tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrderByComparator() {

				@Override
				public int compare(Object obj1, Object obj2) {
					Left left1= (Left)obj1;
					Left left2 = (Left)obj2;

					Long leftPrimaryKey1 = (Long)left1.getPrimaryKeyObj();
					Long leftPrimaryKey2 = (Long)left2.getPrimaryKeyObj();

					return leftPrimaryKey2.compareTo(leftPrimaryKey1);
				}
			});

		Assert.assertEquals(2, lefts.size());

		left1 = lefts.get(0);
		left2 = lefts.get(1);

		Assert.assertEquals(leftPrimaryKey2, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPrimaryKey1, left2.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// Get 3 results, paginated

		long leftPrimaryKey3 = 4;

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey2, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey3, new long[] {rightPrimaryKey});

		lefts = _tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
			rightPrimaryKey, 1, 2, null);

		Assert.assertEquals(1, lefts.size());

		Left left = lefts.get(0);

		Assert.assertEquals(leftPrimaryKey2, left.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// No such model exception

		_leftPersistence.setNoSuchModelException(true);

		try {
			_tableMappingImpl.getLeftBaseModelsByRightPrimaryKey(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(
				String.valueOf(leftPrimaryKey1), cause.getMessage());
		}
		finally {
			_leftPersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testGetLeftPrimaryKeysByRightPrimaryKey()
		throws SystemException {

		// Get 0 result

		long rightPrimaryKey = 1;

		long[] leftPrimaryKeys =
			_tableMappingImpl.getLeftPrimaryKeysByRightPrimaryKey(
				rightPrimaryKey);

		Assert.assertEquals(0, leftPrimaryKeys.length);

		// Hit cache

		Assert.assertSame(
			leftPrimaryKeys,
			_tableMappingImpl.getLeftPrimaryKeysByRightPrimaryKey(
				rightPrimaryKey));

		// Get 2 results, ensure ordered

		long leftPrimaryKey1 = 3;
		long leftPrimaryKey2 = 2;

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_tableMappingImpl.rightToLeftPortalCache;

		rightToLeftPortalCache.remove(rightPrimaryKey);

		_mappingStore.put(leftPrimaryKey1, new long[] {rightPrimaryKey});
		_mappingStore.put(leftPrimaryKey2, new long[] {rightPrimaryKey});

		leftPrimaryKeys = _tableMappingImpl.getLeftPrimaryKeysByRightPrimaryKey(
			rightPrimaryKey);

		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey2, leftPrimaryKey1}, leftPrimaryKeys);

		// Database error

		rightToLeftPortalCache.remove(rightPrimaryKey);

		MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery
			mockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery =
				(MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery)
					_tableMappingImpl.
						getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery;

		mockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery.setDatabaseError(
			true);

		try {
			_tableMappingImpl.getLeftPrimaryKeysByRightPrimaryKey(
				rightPrimaryKey);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery.
				setDatabaseError(false);
		}
	}

	@Test
	public void testGetRightBaseModelsByLeftPrimaryKey()
		throws SystemException {

		// Get 0 result

		long leftPrimaryKey = 1;

		List<Right> rights =
			_tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), rights);

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPrimaryKey);

		// Get 1 result

		long rightPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey, new long[] {rightPrimaryKey1});

		rights = _tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
			leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, rights.size());

		Right right1 = rights.get(0);

		Assert.assertEquals(rightPrimaryKey1, right1.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPrimaryKey);

		// Get 2 results, unsorted

		long rightPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey, new long[] {rightPrimaryKey2, rightPrimaryKey1});

		rights = _tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
			leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, rights.size());

		right1 = rights.get(0);
		Right right2 = rights.get(1);

		Assert.assertEquals(rightPrimaryKey1, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPrimaryKey2, right2.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPrimaryKey);

		// Get 2 results, sorted

		_mappingStore.put(
			leftPrimaryKey, new long[] {rightPrimaryKey2, rightPrimaryKey1});

		rights = _tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
			leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrderByComparator() {

				@Override
				public int compare(Object obj1, Object obj2) {
					Right right1 = (Right)obj1;
					Right right2 = (Right)obj2;

					Long rightPrimaryKey1 = (Long)right1.getPrimaryKeyObj();
					Long rightPrimaryKey2 = (Long)right2.getPrimaryKeyObj();

					return rightPrimaryKey2.compareTo(rightPrimaryKey1);
				}
			});

		Assert.assertEquals(2, rights.size());

		right1 = rights.get(0);
		right2 = rights.get(1);

		Assert.assertEquals(rightPrimaryKey2, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPrimaryKey1, right2.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPrimaryKey);

		// Get 3 results, paginated

		long rightPrimaryKey3 = 4;

		_mappingStore.put(
			leftPrimaryKey,
			new long[] {rightPrimaryKey3, rightPrimaryKey2, rightPrimaryKey1});

		rights = _tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
			leftPrimaryKey, 1, 2, null);

		Assert.assertEquals(1, rights.size());

		Right right = rights.get(0);

		Assert.assertEquals(rightPrimaryKey2, right.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPrimaryKey);

		// No such model exception

		_rightPersistence.setNoSuchModelException(true);

		try {
			_tableMappingImpl.getRightBaseModelsByLeftPrimaryKey(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(
				String.valueOf(rightPrimaryKey1), cause.getMessage());
		}
		finally {
			_rightPersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testGetRightPrimaryKeysByLeftPrimaryKey()
		throws SystemException {

		// Get 0 result

		long leftPrimaryKey = 1;

		long[] rightPrimaryKeys =
			_tableMappingImpl.getRightPrimaryKeysByLeftPrimaryKey(
				leftPrimaryKey);

		Assert.assertEquals(0, rightPrimaryKeys.length);

		// Hit cache

		Assert.assertSame(
			rightPrimaryKeys,
			_tableMappingImpl.getRightPrimaryKeysByLeftPrimaryKey(
				leftPrimaryKey));

		// Get 2 results, ensure ordered

		long rightPrimaryKey1 = 3;
		long rightPrimaryKey2 = 2;

		PortalCache<Long, long[]> leftToRightPortalCache =
			_tableMappingImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPrimaryKey);

		_mappingStore.put(
			leftPrimaryKey, new long[] {rightPrimaryKey1, rightPrimaryKey2});

		rightPrimaryKeys =
			_tableMappingImpl.getRightPrimaryKeysByLeftPrimaryKey(
				leftPrimaryKey);

		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey2, rightPrimaryKey1}, rightPrimaryKeys);

		// Database error

		leftToRightPortalCache.remove(leftPrimaryKey);

		MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery
			mockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery =
				(MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery)
					_tableMappingImpl.
						getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery;

		mockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery.setDatabaseError(
			true);

		try {
			_tableMappingImpl.getRightPrimaryKeysByLeftPrimaryKey(
				leftPrimaryKey);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery.
				setDatabaseError(false);
		}
	}

	@Test
	public void testGetSetReverseTableMapping() {
		TableMapping<Right, Left> tableMapping =
			new ReverseTableMapping<Right, Left>(_tableMappingImpl);

		_tableMappingImpl.setReverseTableMapping(tableMapping);

		Assert.assertSame(
			tableMapping, _tableMappingImpl.getReverseTableMapping());
	}

	@Test
	public void testMatches() {
		Assert.assertTrue(
			_tableMappingImpl.matches(_leftColumnName, _rightColumnName));

		Assert.assertFalse(
			_tableMappingImpl.matches(_leftColumnName, _leftColumnName));

		Assert.assertFalse(
			_tableMappingImpl.matches(_rightColumnName, _leftColumnName));
	}

	@Test
	public void testReverseTableMapping() throws SystemException {
		Class<?> clazz = TableMapping.class;
		ClassLoader classLoader = clazz.getClassLoader();

		RecordInvocationHandler recordInvocationHandler =
			new RecordInvocationHandler();

		TableMapping<Left, Right> tableMapping = (TableMapping<Left, Right>)
			ProxyUtil.newProxyInstance(
				classLoader, new Class<?>[] {TableMapping.class},
				recordInvocationHandler);

		ReverseTableMapping<Right, Left> reverseTableMapping =
			new ReverseTableMapping<Right, Left>(tableMapping);

		recordInvocationHandler.setTableMapping(reverseTableMapping);

		reverseTableMapping.addMapping(1, 2);

		recordInvocationHandler.assertCall("addMapping", 2L, 1L);

		reverseTableMapping.containsMapping(1, 2);

		recordInvocationHandler.assertCall("containsMapping", 2L, 1L);

		reverseTableMapping.deleteMappingsByLeftPrimaryKey(1);

		recordInvocationHandler.assertCall(
			"deleteMappingsByRightPrimaryKey", 1L);

		reverseTableMapping.deleteMappingsByRightPrimaryKey(2);

		recordInvocationHandler.assertCall(
			"deleteMappingsByLeftPrimaryKey", 2L);

		reverseTableMapping.deleteMapping(1, 2);

		recordInvocationHandler.assertCall("deleteMapping", 2L, 1L);

		reverseTableMapping.getRightPrimaryKeysByLeftPrimaryKey(1);

		recordInvocationHandler.assertCall(
			"getLeftPrimaryKeysByRightPrimaryKey", 1L);

		reverseTableMapping.getRightBaseModelsByLeftPrimaryKey(1, 2, 3, null);

		recordInvocationHandler.assertCall(
			"getLeftBaseModelsByRightPrimaryKey", 1L, 2, 3, null);

		reverseTableMapping.getLeftPrimaryKeysByRightPrimaryKey(2);

		recordInvocationHandler.assertCall(
			"getRightPrimaryKeysByLeftPrimaryKey", 2L);

		reverseTableMapping.getLeftBaseModelsByRightPrimaryKey(2, 2, 3, null);

		recordInvocationHandler.assertCall(
			"getRightBaseModelsByLeftPrimaryKey", 2L, 2, 3, null);

		Assert.assertSame(
			tableMapping, reverseTableMapping.getReverseTableMapping());

		reverseTableMapping.matches("left", "right");

		recordInvocationHandler.assertCall("matches", "right", "left");
	}

	@Test
	public void testTableMappingFactory() {

		// Initial empty

		Map<String, TableMapping<?, ?>> tableMappings =
			TableMappingFactory.tableMappings;

		Assert.assertTrue(tableMappings.isEmpty());

		// Create

		TableMapping<Left, Right> tableMapping =
			TableMappingFactory.getTableMapping(
				_mappingTableName, _leftColumnName, _rightColumnName,
				_leftPersistence, _rightPersistence);

		Assert.assertEquals(1, tableMappings.size());
		Assert.assertSame(tableMapping, tableMappings.get(_mappingTableName));

		TableMapping<Right, Left> reverseTableMapping =
			tableMapping.getReverseTableMapping();

		Assert.assertNotNull(reverseTableMapping);

		// Hit cache

		Assert.assertSame(
			tableMapping,
			TableMappingFactory.getTableMapping(
				_mappingTableName, _leftColumnName, _rightColumnName,
					_leftPersistence, _rightPersistence));

		// Reverse mapping table

		Assert.assertSame(
			reverseTableMapping,
			TableMappingFactory.getTableMapping(
				_mappingTableName, _rightColumnName, _leftColumnName,
				_rightPersistence, _leftPersistence));
	}

	private static interface Right extends RightModel {};

	private static interface RightModel extends BaseModel<Right> {};

	private static interface LeftModel extends BaseModel<Left> {};

	private static interface Left extends LeftModel {};

	private String _leftColumnName = "leftId";
	private MockPersistence<Left> _leftPersistence;
	private Map<Long, long[]> _mappingStore = new HashMap<Long, long[]>();
	private String _mappingTableName = "Lefts_Rights";
	private DataSource _mockDataSource;
	private String _rightColumnName = "rightId";
	private MockPersistence<Right> _rightPersistence;
	private TableMappingImpl<Left, Right> _tableMappingImpl;

	private static class GetPrimaryKeyObjInvocationHandler
		implements InvocationHandler {

		public GetPrimaryKeyObjInvocationHandler(Serializable primaryKey) {
			_primaryKey = primaryKey;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getPrimaryKeyObj")) {
				return _primaryKey;
			}

			throw new UnsupportedOperationException();
		}

		private Serializable _primaryKey;

	}

	private static class MockMultiVMPool implements MultiVMPool {

		@Override
		public void clear() {
			_portalCaches.clear();
		}

		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getCache(String name) {

			PortalCache<?, ?> portalCache = _portalCaches.get(name);

			if (portalCache == null) {
				portalCache = new MemoryPortalCache<Long, long[]>(name, 16);

				_portalCaches.put(name, portalCache);
			}

			return (PortalCache<? extends Serializable, ? extends Serializable>)
				portalCache;
		}

		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getCache(String name, boolean blocking) {

			return getCache(name);
		}

		@Override
		public void removeCache(String name) {
			_portalCaches.remove(name);
		}

		private Map<String, PortalCache<?, ?>> _portalCaches =
			new HashMap<String, PortalCache<?, ?>>();
	}

	private static class MockPersistence<T extends BaseModel<T>>
		extends BasePersistenceImpl<T> {

		public MockPersistence(Class<T> clazz) {
			super(clazz);
		}

		@Override
		public T findByPrimaryKey(Serializable primaryKey)
			throws NoSuchModelException {

			if (_noSuchModelException) {
				throw new NoSuchModelException(primaryKey.toString());
			}

			Class<T> modelClass = getModelClass();

			ClassLoader classLoader = modelClass.getClassLoader();

			return (T)ProxyUtil.newProxyInstance(
				classLoader, new Class<?>[] {modelClass},
				new GetPrimaryKeyObjInvocationHandler(primaryKey));
		}

		public void setNoSuchModelException(boolean noSuchModelException) {
			_noSuchModelException = noSuchModelException;
		}

		private boolean _noSuchModelException;

	}

	private static class RecorderModelListener<T extends BaseModel<T>>
		extends BaseModelListener<T> {

		public void assertOnAfterAddAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			assertCall(
				0, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnAfterRemoveAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			assertCall(
				1, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnBeforeAddAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			assertCall(
				2, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnBeforeRemoveAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			assertCall(
				3, called, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			record(0, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			record(1, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			record(2, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			record(3, classPK, associationClassName, associationClassPK);
		}

		private void assertCall(
			int index, boolean called, Object classPK,
			String associationClassName, Object associationClassPK) {

			if (called) {
				Assert.assertSame(_classPKs[index], classPK);
				Assert.assertEquals(
					_associationClassNames[index], associationClassName);
				Assert.assertSame(
					_associationClassPKs[index], associationClassPK);
			}
			else if (_markers[index]) {
				Assert.fail("Called onAfterAddAssociation.");
			}
		}

		private void record(
			int index, Object classPK, String associationClassName,
			Object associationClassPK) {

			_markers[index] = true;
			_classPKs[index] = classPK;
			_associationClassNames[index] = associationClassName;
			_associationClassPKs[index] = associationClassPK;
		}

		private boolean[] _markers = new boolean[4];
		private Object[] _classPKs = new Object[4];
		private String[] _associationClassNames = new String[4];
		private Object[] _associationClassPKs = new Object[4];

	}

	private static class RecordInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			_records.put(method.getName(), args);

			Class<?> returnType = method.getReturnType();

			if (returnType == boolean.class) {
				return false;
			}

			if (returnType == int.class) {
				return 0;
			}

			if (returnType == List.class) {
				return Collections.emptyList();
			}

			if (returnType == long[].class) {
				return new long[0];
			}

			if (returnType == TableMapping.class) {
				return _tableMapping;
			}

			return null;
		}

		public void assertCall(String methodName, Object... args) {
			Object[] record = _records.get(methodName);

			Assert.assertArrayEquals(record, args);
		}

		public void setTableMapping(TableMapping<?, ?> tableMapping) {
			_tableMapping = tableMapping;
		}

		private TableMapping<?, ?> _tableMapping;
		private Map<String, Object[]> _records = new
			HashMap<String, Object[]>();

	}

	private class MockAddMappingSqlUpdate implements SqlUpdate {

		public MockAddMappingSqlUpdate(
			DataSource dataSource, String sql, int[] types) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"INSERT INTO " + _mappingTableName + " (" + _leftColumnName +
					", " + _rightColumnName+ ") VALUES (?, ?)", sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
				types);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(2, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];

			long[] rightPrimaryKeys = _mappingStore.get(leftPrimaryKey);

			if (rightPrimaryKeys == null) {
				rightPrimaryKeys = new long[1];

				rightPrimaryKeys[0] = rightPrimaryKey;

				_mappingStore.put(leftPrimaryKey, rightPrimaryKeys);
			}
			else if (ArrayUtil.contains(rightPrimaryKeys, rightPrimaryKey)) {
				throw new RuntimeException(
					"Unique key violation, leftPrimaryKey : " + leftPrimaryKey +
						", rightPrimaryKey : " + rightPrimaryKey);
			}
			else {
				rightPrimaryKeys = ArrayUtil.append(
					rightPrimaryKeys, rightPrimaryKey);

				_mappingStore.put(leftPrimaryKey, rightPrimaryKeys);
			}

			return 1;
		}

	}

	private class MockDeleteMappingsByLeftPrimaryKeySqlUpdate
		implements SqlUpdate {

		public MockDeleteMappingsByLeftPrimaryKeySqlUpdate(
			DataSource dataSource, String sql, int[] types) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"DELETE FROM " + _mappingTableName + " WHERE " +
					_leftColumnName + " = ?", sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT }, types);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long leftPrimaryKey = (Long)params[0];

			long[] rightPrimaryKeys = _mappingStore.remove(leftPrimaryKey);

			if (rightPrimaryKeys == null) {
				return 0;
			}

			return rightPrimaryKeys.length;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockDeleteMappingsByRightPrimaryKeySqlUpdate
		implements SqlUpdate {

		public MockDeleteMappingsByRightPrimaryKeySqlUpdate(
			DataSource dataSource, String sql, int[] types) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"DELETE FROM " + _mappingTableName + " WHERE " +
					_rightColumnName + " = ?", sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT }, types);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long rightPrimaryKey = (Long)params[0];

			int count = 0;

			for (Map.Entry<Long, long[]> entry : _mappingStore.entrySet()) {
				long[] rightPrimaryKeys = entry.getValue();

				if (ArrayUtil.contains(rightPrimaryKeys, rightPrimaryKey)) {
					count++;

					rightPrimaryKeys = ArrayUtil.remove(
						rightPrimaryKeys, rightPrimaryKey);

					entry.setValue(rightPrimaryKeys);
				}
			}

			return count;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockDeleteMappingSqlUpdate implements SqlUpdate {

		public MockDeleteMappingSqlUpdate(
			DataSource dataSource, String sql, int[] types) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"DELETE FROM " + _mappingTableName + " WHERE " +
				_leftColumnName + " = ? AND " + _rightColumnName+ " = ?", sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
				types);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(2, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];

			long[] rightPrimaryKeys = _mappingStore.get(leftPrimaryKey);

			if (rightPrimaryKeys == null) {
				return 0;
			}

			if (ArrayUtil.contains(rightPrimaryKeys, rightPrimaryKey)) {
				rightPrimaryKeys = ArrayUtil.remove(
					rightPrimaryKeys, rightPrimaryKey);

				_mappingStore.put(leftPrimaryKey, rightPrimaryKeys);

				return 1;
			}

			return 0;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery(
			DataSource dataSource, String sql, int[] types,
			RowMapper<Long> rowMapper) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"SELECT " + _leftColumnName + " FROM " +
					_mappingTableName + " WHERE " + _rightColumnName + "=?",
				sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT }, types);
			Assert.assertSame(RowMapper.ID, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long rightPrimaryKey = (Long)params[0];

			List<Long> leftPrimaryKeyList = new ArrayList<Long>();

			for (Map.Entry<Long, long[]> entry : _mappingStore.entrySet()) {
				long[] rightPrimaryKeys = entry.getValue();

				if (ArrayUtil.contains(rightPrimaryKeys, rightPrimaryKey)) {
					leftPrimaryKeyList.add(entry.getKey());
				}
			}

			return leftPrimaryKeyList;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery(
			DataSource dataSource, String sql, int[] types,
			RowMapper<Long> rowMapper) {

			Assert.assertSame(_mockDataSource, dataSource);
			Assert.assertEquals(
				"SELECT " + _rightColumnName + " FROM " +
					_mappingTableName + " WHERE " + _leftColumnName + "=?",
				sql);
			Assert.assertArrayEquals(
				new int[] { java.sql.Types.BIGINT }, types);
			Assert.assertSame(RowMapper.ID, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long leftPrimaryKey = (Long)params[0];

			long[] rightPrimaryKeys = _mappingStore.get(leftPrimaryKey);

			if (rightPrimaryKeys == null) {
				return Collections.emptyList();
			}

			List<Long> rightPrimaryKeyList = new ArrayList<Long>(
				rightPrimaryKeys.length);

			for (long rightPrimaryKey : rightPrimaryKeys) {
				rightPrimaryKeyList.add(rightPrimaryKey);
			}

			return rightPrimaryKeyList;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockMappingSqlQueryFactory implements MappingSqlQueryFactory {

		@Override
		public <T> MappingSqlQuery<T> getMappingSqlQuery(
			DataSource dataSource, String sql, int[] types,
			RowMapper<T> rowMapper) {

			int count = _counter++;

			if (count == 0) {
				return (MappingSqlQuery<T>)
					new MockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery(
						dataSource, sql, types, RowMapper.ID);
			}

			if (count == 1) {
				return (MappingSqlQuery<T>)
					new MockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery(
						dataSource, sql, types, RowMapper.ID);
			}

			return null;
		}

		private int _counter;
	}

	private class MockSqlUpdateFactory implements SqlUpdateFactory {

		@Override
		public SqlUpdate getSqlUpdate(
			DataSource dataSource, String sql, int[] types) {

			int count = _counter++;

			if (count == 0) {
				return new MockAddMappingSqlUpdate(dataSource, sql, types);
			}

			if (count == 1) {
				return new MockDeleteMappingSqlUpdate(dataSource, sql, types);
			}

			if (count == 2) {
				return new MockDeleteMappingsByLeftPrimaryKeySqlUpdate(
					dataSource, sql, types);
			}

			if (count == 3) {
				return new MockDeleteMappingsByRightPrimaryKeySqlUpdate(
					dataSource, sql, types);
			}

			return null;
		}

		private int _counter;

	}

}