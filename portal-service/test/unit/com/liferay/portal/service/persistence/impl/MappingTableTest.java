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
public class MappingTableTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.clear();

				assertClasses.add(MappingTableImpl.class);
				assertClasses.add(ReverseMappingTable.class);
				assertClasses.add(MappingTableUtil.class);
			}

		};

	@Before
	public void setUp() {
		Class<?> clazz = MappingTableTest.class;
		final ClassLoader classLoader = clazz.getClassLoader();

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

		_mappingTableImpl = new MappingTableImpl<Left, Right>(
			_mappingTableName, _leftColumnName, _rightColumnName,
			_leftPersistence, _rightPersistence);
	}

	@Test
	public void testAddMapping() throws SystemException {

		// Success, no model listener

		long leftPk = 1;
		long rightPk = 2;

		Assert.assertTrue(_mappingTableImpl.addMapping(leftPk, rightPk));

		// Fail, no model listener

		Assert.assertFalse(_mappingTableImpl.addMapping(leftPk, rightPk));

		// Error, no model listener

		PortalCache<Long, long[]> leftToRightPortalCache =
			_mappingTableImpl.leftToRightPortalCache;

		leftToRightPortalCache.put(leftPk, new long[0]);

		try {
			_mappingTableImpl.addMapping(leftPk, rightPk);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());
			Assert.assertEquals(
				"Unique key violation, leftPk : " + leftPk + ", rightOk : " +
					rightPk, cause.getMessage());
		}

		// Auto recover after error

		Assert.assertFalse(_mappingTableImpl.addMapping(leftPk, rightPk));

		// Success, with model listener

		leftToRightPortalCache.remove(leftPk);
		_mappingStore.remove(leftPk);

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertTrue(_mappingTableImpl.addMapping(leftPk, rightPk));

		leftModelListener.assertOnBeforeAddAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeAddAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		leftModelListener.assertOnAfterAddAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnAfterAddAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);

		// Error, no model listener

		leftToRightPortalCache.put(leftPk, new long[0]);

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		try {
			_mappingTableImpl.addMapping(leftPk, rightPk);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());
			Assert.assertEquals(
				"Unique key violation, leftPk : " + leftPk + ", rightOk : " +
					rightPk, cause.getMessage());
		}

		leftModelListener.assertOnBeforeAddAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeAddAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		leftModelListener.assertOnAfterAddAssociation(false, null, null, null);

		rightModelListener.assertOnAfterAddAssociation(false, null, null, null);
	}

	@Test
	public void testConstructor() {
		Assert.assertEquals(_leftColumnName, _mappingTableImpl.leftColumnName);
		Assert.assertEquals(
			_rightColumnName, _mappingTableImpl.rightColumnName);
		Assert.assertSame(_leftPersistence, _mappingTableImpl.leftPersistence);
		Assert.assertSame(
			_rightPersistence, _mappingTableImpl.rightPersistence);

		Assert.assertTrue(
			_mappingTableImpl.addSqlUpdate instanceof MockAddSqlUpdate);
		Assert.assertTrue(
			_mappingTableImpl.deleteSqlUpdate instanceof MockDeleteSqlUpdate);
		Assert.assertTrue(
			_mappingTableImpl.deleteByLeftSqlUpdate instanceof
				MockDeleteByLeftSqlUpdate);
		Assert.assertTrue(
			_mappingTableImpl.deleteByRightSqlUpdate instanceof
				MockDeleteByRightSqlUpdate);

		Assert.assertTrue(
			_mappingTableImpl.findByLeftMappingSqlQuery instanceof
				MockFindByLeftMappingSqlQuery);
		Assert.assertTrue(
			_mappingTableImpl.findByRightMappingSqlQuery instanceof
				MockFindByRightMappingSqlQuery);

		PortalCache<Long, long[]> leftToRightPortalCache =
			_mappingTableImpl.leftToRightPortalCache;

		Assert.assertTrue(leftToRightPortalCache instanceof MemoryPortalCache);
		Assert.assertEquals(
			_mappingTableName + "-Left->Right",
			leftToRightPortalCache.getName());

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_mappingTableImpl.rightToLeftPortalCache;

		Assert.assertTrue(rightToLeftPortalCache instanceof MemoryPortalCache);
		Assert.assertEquals(
			_mappingTableName + "-Right->Left",
			rightToLeftPortalCache.getName());
	}

	@Test
	public void testContainsMapping() throws SystemException {

		// No such mapping

		long leftPk = 1;
		long rightPk = 2;

		Assert.assertFalse(_mappingTableImpl.containsMapping(leftPk, rightPk));

		// Has mapping

		PortalCache<Long, long[]> leftToRightPortalCache =
			_mappingTableImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPk);

		_mappingStore.put(leftPk, new long[] {rightPk});

		Assert.assertTrue(_mappingTableImpl.containsMapping(leftPk, rightPk));
	}

	@Test
	public void testDeleteByLeftPk() throws SystemException {

		// Delete 0 entry

		long leftPk = 1;

		Assert.assertEquals(0, _mappingTableImpl.deleteByLeftPk(leftPk));

		// Delete 1 entry

		long rightPk1 = 2;

		_mappingStore.put(leftPk, new long[] {rightPk1});

		Assert.assertEquals(1, _mappingTableImpl.deleteByLeftPk(leftPk));

		// Delete 2 entries

		long rightPk2 = 3;

		_mappingStore.put(leftPk, new long[] {rightPk1, rightPk2});

		Assert.assertEquals(2, _mappingTableImpl.deleteByLeftPk(leftPk));

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		Assert.assertEquals(0, _mappingTableImpl.deleteByLeftPk(leftPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertEquals(0, _mappingTableImpl.deleteByLeftPk(leftPk));

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_rightPersistence.unregisterListener(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		_mappingStore.put(leftPk, new long[] {rightPk1});

		Assert.assertEquals(1, _mappingTableImpl.deleteByLeftPk(leftPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk1);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk1);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk, new long[] {rightPk1});

		Assert.assertEquals(1, _mappingTableImpl.deleteByLeftPk(leftPk));

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk1, Left.class.getName(), leftPk);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPk1, Left.class.getName(), leftPk);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with both left and right model listeners

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk, new long[] {rightPk1});

		MockDeleteByLeftSqlUpdate mockDeleteByLeftSqlUpdate =
			(MockDeleteByLeftSqlUpdate)_mappingTableImpl.deleteByLeftSqlUpdate;

		mockDeleteByLeftSqlUpdate.setDatabaseError(true);

		try {
			_mappingTableImpl.deleteByLeftPk(leftPk);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteByLeftSqlUpdate.setDatabaseError(false);

			_mappingStore.remove(leftPk);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk1);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk1, Left.class.getName(), leftPk);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);
	}

	@Test
	public void testDeleteByRightPk() throws SystemException {

		// Delete 0 entry

		long rightPk = 1;

		Assert.assertEquals(0, _mappingTableImpl.deleteByRightPk(rightPk));

		// Delete 1 entry

		long leftPk1 = 2;

		_mappingStore.put(leftPk1, new long[] {rightPk});

		Assert.assertEquals(1, _mappingTableImpl.deleteByRightPk(rightPk));

		// Delete 2 entries

		long leftPk2 = 3;

		_mappingStore.put(leftPk1, new long[] {rightPk});
		_mappingStore.put(leftPk2, new long[] {rightPk});

		Assert.assertEquals(2, _mappingTableImpl.deleteByRightPk(rightPk));

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		Assert.assertEquals(0, _mappingTableImpl.deleteByRightPk(rightPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		Assert.assertEquals(0, _mappingTableImpl.deleteByRightPk(rightPk));

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_rightPersistence.unregisterListener(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		_mappingStore.put(leftPk1, new long[] {rightPk});

		Assert.assertEquals(1, _mappingTableImpl.deleteByRightPk(rightPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk1, Right.class.getName(), rightPk);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPk1, Right.class.getName(), rightPk);

		_leftPersistence.unregisterListener(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk1, new long[] {rightPk});

		Assert.assertEquals(1, _mappingTableImpl.deleteByRightPk(rightPk));

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk1);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk1);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with both left and right model listeners

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk1, new long[] {rightPk});

		MockDeleteByRightSqlUpdate mockDeleteByRightSqlUpdate =
			(MockDeleteByRightSqlUpdate)
				_mappingTableImpl.deleteByRightSqlUpdate;

		mockDeleteByRightSqlUpdate.setDatabaseError(true);

		try {
			_mappingTableImpl.deleteByRightPk(rightPk);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteByRightSqlUpdate.setDatabaseError(false);

			_mappingStore.remove(rightPk);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk1, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk1);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);
	}

	@Test
	public void testDeleteMapping() throws SystemException {

		// No such mapping

		long leftPk = 1;
		long rightPk = 2;

		Assert.assertFalse(_mappingTableImpl.deleteMapping(leftPk, rightPk));

		// Success, without model listener

		_mappingStore.put(leftPk, new long[]{rightPk});

		Assert.assertTrue(_mappingTableImpl.deleteMapping(leftPk, rightPk));

		// Success, with model listener

		RecorderModelListener<Left> leftModelListener =
			new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk, new long[]{rightPk});

		Assert.assertTrue(_mappingTableImpl.deleteMapping(leftPk, rightPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);

		// Database error, with model listener

		leftModelListener = new RecorderModelListener<Left>();

		_leftPersistence.registerListener(leftModelListener);

		rightModelListener = new RecorderModelListener<Right>();

		_rightPersistence.registerListener(rightModelListener);

		_mappingStore.put(leftPk, new long[]{rightPk});

		MockDeleteSqlUpdate mockDeleteSqlUpdate =
			(MockDeleteSqlUpdate)_mappingTableImpl.deleteSqlUpdate;

		mockDeleteSqlUpdate.setDatabaseError(true);

		try {
			_mappingTableImpl.deleteMapping(leftPk, rightPk);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockDeleteSqlUpdate.setDatabaseError(false);
			_mappingStore.remove(leftPk);
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk);

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
			_mappingTableImpl.leftToRightPortalCache;

		leftToRightPortalCache.put(leftPk, new long[]{rightPk});

		Assert.assertFalse(_mappingTableImpl.deleteMapping(leftPk, rightPk));

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPk, Right.class.getName(), rightPk);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPk, Left.class.getName(), leftPk);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		_leftPersistence.unregisterListener(leftModelListener);

		_rightPersistence.unregisterListener(rightModelListener);
	}

	@Test
	public void testFindByLeftPk() throws SystemException {

		// Find 0 result

		long leftPk = 1;

		long[] rightPks = _mappingTableImpl.findByLeftPk(leftPk);

		Assert.assertEquals(0, rightPks.length);

		// Hit cache

		Assert.assertSame(rightPks, _mappingTableImpl.findByLeftPk(leftPk));

		// Find 2 results, ensure ordered

		long rightPk1 = 3;
		long rightPk2 = 2;

		PortalCache<Long, long[]> leftToRightPortalCache =
			_mappingTableImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPk);

		_mappingStore.put(leftPk, new long[] {rightPk1, rightPk2});

		rightPks = _mappingTableImpl.findByLeftPk(leftPk);

		Assert.assertArrayEquals(new long[] {rightPk2, rightPk1}, rightPks);

		// Database error

		leftToRightPortalCache.remove(leftPk);

		MockFindByLeftMappingSqlQuery mockFindByLeftMappingSqlQuery =
			(MockFindByLeftMappingSqlQuery)
				_mappingTableImpl.findByLeftMappingSqlQuery;

		mockFindByLeftMappingSqlQuery.setDatabaseError(true);

		try {
			_mappingTableImpl.findByLeftPk(leftPk);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockFindByLeftMappingSqlQuery.setDatabaseError(false);
		}
	}

	@Test
	public void testFindByLeftPkAsEntities() throws SystemException {

		// Find 0 result

		long leftPk = 1;

		List<Right> rights = _mappingTableImpl.findByLeftPkAsEntities(
			leftPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), rights);

		PortalCache<Long, long[]> leftToRightPortalCache =
			_mappingTableImpl.leftToRightPortalCache;

		leftToRightPortalCache.remove(leftPk);

		// Find 1 result

		long rightPk1 = 2;

		_mappingStore.put(leftPk, new long[] {rightPk1});

		rights = _mappingTableImpl.findByLeftPkAsEntities(
			leftPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, rights.size());

		Right right1 = rights.get(0);

		Assert.assertEquals(rightPk1, right1.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPk);

		// Find 2 results, unsorted

		long rightPk2 = 3;

		_mappingStore.put(leftPk, new long[] {rightPk2, rightPk1});

		rights = _mappingTableImpl.findByLeftPkAsEntities(
			leftPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, rights.size());

		right1 = rights.get(0);
		Right right2 = rights.get(1);

		Assert.assertEquals(rightPk1, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPk2, right2.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPk);

		// Find 2 results, sorted

		_mappingStore.put(leftPk, new long[] {rightPk2, rightPk1});

		rights = _mappingTableImpl.findByLeftPkAsEntities(
			leftPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrderByComparator() {

				@Override
				public int compare(Object obj1, Object obj2) {
					Right right1 = (Right)obj1;
					Right right2 = (Right)obj2;

					Long rightPk1 = (Long)right1.getPrimaryKeyObj();
					Long rightPk2 = (Long)right2.getPrimaryKeyObj();

					return rightPk2.compareTo(rightPk1);
				}
			});

		Assert.assertEquals(2, rights.size());

		right1 = rights.get(0);
		right2 = rights.get(1);

		Assert.assertEquals(rightPk2, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPk1, right2.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPk);

		// Find 3 results, paginated

		long rightPk3 = 4;

		_mappingStore.put(leftPk, new long[] {rightPk3, rightPk2, rightPk1});

		rights = _mappingTableImpl.findByLeftPkAsEntities(leftPk, 1, 2, null);

		Assert.assertEquals(1, rights.size());

		Right right = rights.get(0);

		Assert.assertEquals(rightPk2, right.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPk);

		// No such model exception

		_rightPersistence.setNoSuchModelException(true);

		try {
			_mappingTableImpl.findByLeftPkAsEntities(
				leftPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(String.valueOf(rightPk1), cause.getMessage());
		}
		finally {
			_rightPersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testFindByRightPk() throws SystemException {

		// Find 0 result

		long rightPk = 1;

		long[] leftPks = _mappingTableImpl.findByRightPk(rightPk);

		Assert.assertEquals(0, leftPks.length);

		// Hit cache

		Assert.assertSame(leftPks, _mappingTableImpl.findByRightPk(rightPk));

		// Find 2 results, ensure ordered

		long leftPk1 = 3;
		long leftPk2 = 2;

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_mappingTableImpl.rightToLeftPortalCache;

		rightToLeftPortalCache.remove(rightPk);

		_mappingStore.put(leftPk1, new long[] {rightPk});
		_mappingStore.put(leftPk2, new long[] {rightPk});

		leftPks = _mappingTableImpl.findByRightPk(rightPk);

		Assert.assertArrayEquals(new long[] {leftPk2, leftPk1}, leftPks);

		// Database error

		rightToLeftPortalCache.remove(rightPk);

		MockFindByRightMappingSqlQuery mockFindByRightMappingSqlQuery =
			(MockFindByRightMappingSqlQuery)
				_mappingTableImpl.findByRightMappingSqlQuery;

		mockFindByRightMappingSqlQuery.setDatabaseError(true);

		try {
			_mappingTableImpl.findByRightPk(rightPk);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockFindByRightMappingSqlQuery.setDatabaseError(false);
		}
	}

	@Test
	public void testFindByRightPkAsEntities() throws SystemException {

		// Find 0 result

		long rightPk = 1;

		List<Left> lefts = _mappingTableImpl.findByRightPkAsEntities(
			rightPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), lefts);

		PortalCache<Long, long[]> rightToLeftPortalCache =
			_mappingTableImpl.rightToLeftPortalCache;

		rightToLeftPortalCache.remove(rightPk);

		// Find 1 result

		long leftPk1 = 2;

		_mappingStore.put(leftPk1, new long[] {rightPk});

		lefts = _mappingTableImpl.findByRightPkAsEntities(
			rightPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, lefts.size());

		Left left1 = lefts.get(0);

		Assert.assertEquals(leftPk1, left1.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPk);

		// Find 2 results, unsorted

		long leftPk2 = 3;

		_mappingStore.put(leftPk1, new long[] {rightPk});
		_mappingStore.put(leftPk2, new long[] {rightPk});

		lefts = _mappingTableImpl.findByRightPkAsEntities(
			rightPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, lefts.size());

		left1 = lefts.get(0);
		Left left2 = lefts.get(1);

		Assert.assertEquals(leftPk1, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPk2, left2.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPk);

		// Find 2 results, sorted

		_mappingStore.put(leftPk1, new long[] {rightPk});
		_mappingStore.put(leftPk2, new long[] {rightPk});

		lefts = _mappingTableImpl.findByRightPkAsEntities(
			rightPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrderByComparator() {

				@Override
				public int compare(Object obj1, Object obj2) {
					Left left1= (Left)obj1;
					Left left2 = (Left)obj2;

					Long leftPk1 = (Long)left1.getPrimaryKeyObj();
					Long leftPk2 = (Long)left2.getPrimaryKeyObj();

					return leftPk2.compareTo(leftPk1);
				}
			});

		Assert.assertEquals(2, lefts.size());

		left1 = lefts.get(0);
		left2 = lefts.get(1);

		Assert.assertEquals(leftPk2, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPk1, left2.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPk);

		// Find 3 results, paginated

		long leftPk3 = 4;

		_mappingStore.put(leftPk1, new long[] {rightPk});
		_mappingStore.put(leftPk2, new long[] {rightPk});
		_mappingStore.put(leftPk3, new long[] {rightPk});

		lefts = _mappingTableImpl.findByRightPkAsEntities(rightPk, 1, 2, null);

		Assert.assertEquals(1, lefts.size());

		Left left = lefts.get(0);

		Assert.assertEquals(leftPk2, left.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPk);

		// No such model exception

		_leftPersistence.setNoSuchModelException(true);

		try {
			_mappingTableImpl.findByRightPkAsEntities(
				rightPk, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(String.valueOf(leftPk1), cause.getMessage());
		}
		finally {
			_leftPersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testGetSetReverseMappingTable() {
		MappingTable<Right, Left> mappingTable =
			new ReverseMappingTable<Right, Left>(_mappingTableImpl);

		_mappingTableImpl.setReverseMappingTable(mappingTable);

		Assert.assertSame(
			mappingTable, _mappingTableImpl.getReverseMappingTable());
	}

	@Test
	public void testMappingTableUtil() {

		// Initial empty

		Map<String, MappingTable<?, ?>> mappingTables =
			MappingTableUtil.mappingTables;

		Assert.assertTrue(mappingTables.isEmpty());

		// Create

		MappingTable<Left, Right> mappingTable =
			MappingTableUtil.getMappingTable(
				_mappingTableName, _leftColumnName, _rightColumnName,
				_leftPersistence, _rightPersistence);

		Assert.assertEquals(1, mappingTables.size());
		Assert.assertSame(mappingTable, mappingTables.get(_mappingTableName));

		MappingTable<Right, Left> reverseMappingTable =
			mappingTable.getReverseMappingTable();

		Assert.assertNotNull(reverseMappingTable);

		// Hit cache

		Assert.assertSame(
			mappingTable,
			MappingTableUtil.getMappingTable(
				_mappingTableName, _leftColumnName, _rightColumnName,
					_leftPersistence, _rightPersistence));

		// Reverse mapping table

		Assert.assertSame(
			reverseMappingTable,
			MappingTableUtil.getMappingTable(
				_mappingTableName, _rightColumnName, _leftColumnName,
				_rightPersistence, _leftPersistence));
	}

	@Test
	public void testMatches() {
		Assert.assertTrue(
			_mappingTableImpl.matches(_leftColumnName, _rightColumnName));

		Assert.assertFalse(
			_mappingTableImpl.matches(_leftColumnName, _leftColumnName));

		Assert.assertFalse(
			_mappingTableImpl.matches(_rightColumnName, _leftColumnName));
	}

	@Test
	public void testReverseMappingTable() throws SystemException {
		Class<?> clazz = MappingTable.class;
		ClassLoader classLoader = clazz.getClassLoader();

		RecordInvocationHandler recordInvocationHandler =
			new RecordInvocationHandler();

		MappingTable<Left, Right> mappingTable = (MappingTable<Left, Right>)
			ProxyUtil.newProxyInstance(
				classLoader, new Class<?>[] {MappingTable.class},
				recordInvocationHandler);

		ReverseMappingTable<Right, Left> reverseMappingTable =
			new ReverseMappingTable<Right, Left>(mappingTable);

		recordInvocationHandler.setMappingTable(reverseMappingTable);

		reverseMappingTable.addMapping(1, 2);

		recordInvocationHandler.assertCall("addMapping", 2L, 1L);

		reverseMappingTable.containsMapping(1, 2);

		recordInvocationHandler.assertCall("containsMapping", 2L, 1L);

		reverseMappingTable.deleteByLeftPk(1);

		recordInvocationHandler.assertCall("deleteByRightPk", 1L);

		reverseMappingTable.deleteByRightPk(2);

		recordInvocationHandler.assertCall("deleteByLeftPk", 2L);

		reverseMappingTable.deleteMapping(1, 2);

		recordInvocationHandler.assertCall("deleteMapping", 2L, 1L);

		reverseMappingTable.findByLeftPk(1);

		recordInvocationHandler.assertCall("findByRightPk", 1L);

		reverseMappingTable.findByLeftPkAsEntities(1, 2, 3, null);

		recordInvocationHandler.assertCall(
			"findByRightPkAsEntities", 1L, 2, 3, null);

		reverseMappingTable.findByRightPkAsEntities(2, 2, 3, null);

		reverseMappingTable.findByRightPk(2);

		recordInvocationHandler.assertCall("findByLeftPk", 2L);

		recordInvocationHandler.assertCall(
			"findByLeftPkAsEntities", 2L, 2, 3, null);

		Assert.assertSame(
			mappingTable, reverseMappingTable.getReverseMappingTable());

		reverseMappingTable.matches("left", "right");

		recordInvocationHandler.assertCall("matches", "right", "left");
	}

	private static interface Right extends RightModel {};

	private static interface RightModel extends BaseModel<Right> {};

	private static interface LeftModel extends BaseModel<Left> {};

	private static interface Left extends LeftModel {};

	private String _leftColumnName = "leftId";
	private MockPersistence<Left> _leftPersistence;
	private Map<Long, long[]> _mappingStore = new HashMap<Long, long[]>();
	private MappingTableImpl<Left, Right> _mappingTableImpl;
	private String _mappingTableName = "Lefts_Rights";
	private DataSource _mockDataSource;
	private String _rightColumnName = "rightId";
	private MockPersistence<Right> _rightPersistence;

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

			if (returnType == MappingTable.class) {
				return _mappingTable;
			}

			return null;
		}

		public void assertCall(String methodName, Object... args) {
			Object[] record = _records.get(methodName);

			Assert.assertArrayEquals(record, args);
		}

		public void setMappingTable(MappingTable<?, ?> mappingTable) {
			_mappingTable = mappingTable;
		}

		private MappingTable<?, ?> _mappingTable;
		private Map<String, Object[]> _records = new
			HashMap<String, Object[]>();

	}

	private class MockAddSqlUpdate implements SqlUpdate {

		public MockAddSqlUpdate(
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

			Long leftPk = (Long)params[0];
			Long rightPk = (Long)params[1];

			long[] rightPks = _mappingStore.get(leftPk);

			if (rightPks == null) {
				rightPks = new long[1];

				rightPks[0] = rightPk;

				_mappingStore.put(leftPk, rightPks);
			}
			else if (ArrayUtil.contains(rightPks, rightPk)) {
				throw new RuntimeException(
					"Unique key violation, leftPk : " + leftPk +
						", rightOk : " + rightPk);
			}
			else {
				rightPks = ArrayUtil.append(rightPks, rightPk);

				_mappingStore.put(leftPk, rightPks);
			}

			return 1;
		}

	}

	private class MockDeleteByLeftSqlUpdate implements SqlUpdate {

		public MockDeleteByLeftSqlUpdate(
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

			Long leftPk = (Long)params[0];

			long[] rightPks = _mappingStore.remove(leftPk);

			if (rightPks == null) {
				return 0;
			}

			return rightPks.length;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockDeleteByRightSqlUpdate implements SqlUpdate {

		public MockDeleteByRightSqlUpdate(
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

			Long rightPk = (Long)params[0];

			int count = 0;

			for (Map.Entry<Long, long[]> entry : _mappingStore.entrySet()) {
				long[] rightPks = entry.getValue();

				if (ArrayUtil.contains(rightPks, rightPk)) {
					count++;

					rightPks = ArrayUtil.remove(rightPks, rightPk);

					entry.setValue(rightPks);
				}
			}

			return count;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockDeleteSqlUpdate implements SqlUpdate {

		public MockDeleteSqlUpdate(
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

			Long leftPk = (Long)params[0];
			Long rightPk = (Long)params[1];

			long[] rightPks = _mappingStore.get(leftPk);

			if (rightPks == null) {
				return 0;
			}

			if (ArrayUtil.contains(rightPks, rightPk)) {
				rightPks = ArrayUtil.remove(rightPks, rightPk);

				_mappingStore.put(leftPk, rightPks);

				return 1;
			}

			return 0;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockFindByLeftMappingSqlQuery
		implements MappingSqlQuery<Long> {

		public MockFindByLeftMappingSqlQuery(
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

			Long leftPk = (Long)params[0];

			long[] rightPks = _mappingStore.get(leftPk);

			if (rightPks == null) {
				return Collections.emptyList();
			}

			List<Long> rightPkList = new ArrayList<Long>(rightPks.length);

			for (long rightPk : rightPks) {
				rightPkList.add(rightPk);
			}

			return rightPkList;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockFindByRightMappingSqlQuery
		implements MappingSqlQuery<Long> {

		public MockFindByRightMappingSqlQuery(
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

			Long rightPk = (Long)params[0];

			List<Long> leftPkList = new ArrayList<Long>();

			for (Map.Entry<Long, long[]> entry : _mappingStore.entrySet()) {
				long[] rightPks = entry.getValue();

				if (ArrayUtil.contains(rightPks, rightPk)) {
					leftPkList.add(entry.getKey());
				}
			}

			return leftPkList;
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
				return (MappingSqlQuery<T>)new MockFindByLeftMappingSqlQuery(
					dataSource, sql, types, RowMapper.ID);
			}

			if (count == 1) {
				return (MappingSqlQuery<T>)new MockFindByRightMappingSqlQuery(
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
				return new MockAddSqlUpdate(dataSource, sql, types);
			}

			if (count == 1) {
				return new MockDeleteSqlUpdate(dataSource, sql, types);
			}

			if (count == 2) {
				return new MockDeleteByLeftSqlUpdate(dataSource, sql, types);
			}

			if (count == 3) {
				return new MockDeleteByRightSqlUpdate(dataSource, sql, types);
			}

			return null;
		}

		private int _counter;

	}

}