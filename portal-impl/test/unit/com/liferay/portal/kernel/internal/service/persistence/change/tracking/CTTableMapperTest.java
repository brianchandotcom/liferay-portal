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

package com.liferay.portal.kernel.internal.service.persistence.change.tracking;

import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactory;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.ParamSetter;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactory;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.util.PropsImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Samuel Trong Tran
 */
public class CTTableMapperTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ToolDependencies.wireBasic();

		DBManagerUtil.setDB(DBType.HYPERSONIC, null);
	}

	@Before
	public void setUp() {
		PortalCacheHelperUtil.clearPortalCaches(
			PortalCacheManagerNames.MULTI_VM);

		MappingSqlQueryFactoryUtil mappingSqlQueryFactoryUtil =
			new MappingSqlQueryFactoryUtil();

		mappingSqlQueryFactoryUtil.setMappingSqlQueryFactory(
			new MockMappingSqlQueryFactory());

		PropsUtil.setProps(new PropsImpl());

		SqlUpdateFactoryUtil sqlUpdateFactoryUtil = new SqlUpdateFactoryUtil();

		sqlUpdateFactoryUtil.setSqlUpdateFactory(new MockSqlUpdateFactory());

		_dataSource = (DataSource)ProxyUtil.newProxyInstance(
			CTTableMapperTest.class.getClassLoader(),
			new Class<?>[] {DataSource.class},
			(proxy, method, args) -> {
				throw new UnsupportedOperationException();
			});

		_leftBasePersistence = new MockBasePersistence<>(Left.class);

		_leftBasePersistence.setDataSource(_dataSource);

		_rightBasePersistence = new MockBasePersistence<>(Right.class);

		_rightBasePersistence.setDataSource(_dataSource);

		_ctTableMapper = new CTTableMapper<>(
			_TABLE_NAME, _COMPANY_COLUMN_NAME, _LEFT_COLUMN_NAME,
			_RIGHT_COLUMN_NAME, Left.class, Right.class, _leftBasePersistence,
			_rightBasePersistence, false);
	}

	@Test
	public void testAddTableMapping() {

		// Success (production)

		long companyId = 0;
		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertTrue(
			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey, rightPrimaryKey));

		// Fail (production)

		Assert.assertFalse(
			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey, rightPrimaryKey));

		// Fail, exists in production

		long ctCollectionId = 3;

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertFalse(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, 0L);

		// Success

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));

			// Fail

			Assert.assertFalse(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}

		// Success, previously deleted

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);
		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey, ctCollectionId, false);

		try (SafeClosable safeClosable3 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, 0L);
		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, ctCollectionId);

		// Success, with model listener

		RecorderModelListener<Left> leftModelListener =
			new LeftRecorderModelListener();

		ModelListenerRegistrationUtil.register(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RightRecorderModelListener();

		ModelListenerRegistrationUtil.register(rightModelListener);

		try (SafeClosable safeClosable4 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}

		leftModelListener.assertOnBeforeAddAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeAddAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterAddAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnAfterAddAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		ModelListenerRegistrationUtil.unregister(leftModelListener);
		ModelListenerRegistrationUtil.unregister(rightModelListener);

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, ctCollectionId);

		// Database error

		MockAddCTTableMappingSqlUpdate mockAddCTTableMappingSqlUpdate =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "_addCTTableMappingSqlUpdate");

		mockAddCTTableMappingSqlUpdate.setDatabaseError(true);

		try (SafeClosable safeClosable5 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey, rightPrimaryKey);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockAddCTTableMappingSqlUpdate.setDatabaseError(false);
		}
	}

	@Test
	public void testAddTableMappings() {
		long companyId = 0;
		long leftPrimaryKey1 = 1;
		long leftPrimaryKey2 = 2;
		long rightPrimaryKey1 = 3;
		long rightPrimaryKey2 = 4;

		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey1},
			_ctTableMapper.addTableMappings(
				companyId, leftPrimaryKey1, new long[] {rightPrimaryKey1}));
		Assert.assertArrayEquals(
			new long[0],
			_ctTableMapper.addTableMappings(
				companyId, leftPrimaryKey1, new long[] {rightPrimaryKey1}));
		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey2},
			_ctTableMapper.addTableMappings(
				companyId, leftPrimaryKey1,
				new long[] {rightPrimaryKey1, rightPrimaryKey2}));
		Assert.assertEquals(
			2,
			_ctTableMapper.deleteLeftPrimaryKeyTableMappings(leftPrimaryKey1));
		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey1},
			_ctTableMapper.addTableMappings(
				companyId, new long[] {leftPrimaryKey1}, rightPrimaryKey1));
		Assert.assertArrayEquals(
			new long[0],
			_ctTableMapper.addTableMappings(
				companyId, new long[] {leftPrimaryKey1}, rightPrimaryKey1));
		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey2},
			_ctTableMapper.addTableMappings(
				companyId, new long[] {leftPrimaryKey1, leftPrimaryKey2},
				rightPrimaryKey1));
		Assert.assertEquals(
			2,
			_ctTableMapper.deleteRightPrimaryKeyTableMappings(
				rightPrimaryKey1));

		long ctCollectionId = 5;

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertArrayEquals(
				new long[] {rightPrimaryKey1},
				_ctTableMapper.addTableMappings(
					companyId, leftPrimaryKey1, new long[] {rightPrimaryKey1}));
			Assert.assertArrayEquals(
				new long[0],
				_ctTableMapper.addTableMappings(
					companyId, leftPrimaryKey1, new long[] {rightPrimaryKey1}));
			Assert.assertArrayEquals(
				new long[] {rightPrimaryKey2},
				_ctTableMapper.addTableMappings(
					companyId, leftPrimaryKey1,
					new long[] {rightPrimaryKey1, rightPrimaryKey2}));
			Assert.assertEquals(
				2,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {rightPrimaryKey1, rightPrimaryKey2},
				_ctTableMapper.addTableMappings(
					companyId, leftPrimaryKey1,
					new long[] {rightPrimaryKey1, rightPrimaryKey2}));
			Assert.assertEquals(
				2,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {leftPrimaryKey1},
				_ctTableMapper.addTableMappings(
					companyId, new long[] {leftPrimaryKey1}, rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[0],
				_ctTableMapper.addTableMappings(
					companyId, new long[] {leftPrimaryKey1}, rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {leftPrimaryKey2},
				_ctTableMapper.addTableMappings(
					companyId, new long[] {leftPrimaryKey1, leftPrimaryKey2},
					rightPrimaryKey1));
			Assert.assertEquals(
				2,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {leftPrimaryKey1, leftPrimaryKey2},
				_ctTableMapper.addTableMappings(
					companyId, new long[] {leftPrimaryKey1, leftPrimaryKey2},
					rightPrimaryKey1));
			Assert.assertEquals(
				2,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey1));
		}
	}

	@Test
	public void testBooleanParamSetter() throws SQLException {
		ParamSetter booleanParamSetter = ReflectionTestUtil.getFieldValue(
			_ctTableMapper, "_booleanParamSetter");

		MappingSqlQuery mappingSqlQuery = new MockBooleanParamSetterSqlQuery(
			booleanParamSetter);

		mappingSqlQuery.execute(true);
	}

	@Test
	public void testCachelessTableMapper() {
		_ctTableMapper = new CTTableMapper<>(
			_TABLE_NAME, _COMPANY_COLUMN_NAME, _LEFT_COLUMN_NAME,
			_RIGHT_COLUMN_NAME, Left.class, Right.class, _leftBasePersistence,
			_rightBasePersistence, true);

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertFalse(
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey));

		// Contains table mapping (production)

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

		Assert.assertTrue(
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey));

		MockContainsTableMappingSQLQuery mockContainsTableMappingSQLQuery =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "containsTableMappingSQL");

		mockContainsTableMappingSQLQuery.setDatabaseError(true);

		try {
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey);

			Assert.fail();
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockContainsTableMappingSQLQuery.setDatabaseError(false);
		}

		mockContainsTableMappingSQLQuery.setEmptyResultSet(true);

		Assert.assertFalse(
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey));
	}

	@Test
	public void testContainsCTTableMapping() {

		// Database error

		long companyId = 0;
		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;
		long ctCollectionId = 3;

		MockContainsCTTableMappingSQLQuery mockContainsCTTableMappingSQLQuery =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "_containsCTTableMappingSQL");

		mockContainsCTTableMappingSQLQuery.setDatabaseError(true);

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey, rightPrimaryKey);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals("Database error", cause.getMessage());
		}
		finally {
			mockContainsCTTableMappingSQLQuery.setDatabaseError(false);
		}

		// Empty results

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertFalse(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}

		mockContainsCTTableMappingSQLQuery.setEmptyResultSet(true);

		try (SafeClosable safeClosable3 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertFalse(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey));
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(RuntimeException.class, cause.getClass());

			Assert.assertEquals(
				StringBundler.concat(
					"Unique key violation for left primary key ",
					leftPrimaryKey, " and right primary key ", rightPrimaryKey,
					" and ctcollectionId ", ctCollectionId),
				cause.getMessage());
		}
		finally {
			mockContainsCTTableMappingSQLQuery.setEmptyResultSet(false);
		}
	}

	@Test
	public void testContainsTableMapping() {

		// Does not contain table mapping (production)

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertFalse(
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey));

		// Contains table mapping (production)

		PortalCache<Long, long[]> leftToRightPortalCache =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "leftToRightPortalCache");

		leftToRightPortalCache.remove(leftPrimaryKey);

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

		Assert.assertTrue(
			_ctTableMapper.containsTableMapping(
				leftPrimaryKey, rightPrimaryKey));
	}

	@Test
	public void testDeleteLeftPrimaryKeyTableMappings() {

		// Delete 0 entry (production)

		long leftPrimaryKey = 1;

		Assert.assertEquals(
			0,
			_ctTableMapper.deleteLeftPrimaryKeyTableMappings(leftPrimaryKey));

		// Delete 1 entry (production)

		long rightPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey1, 0L, null);

		Assert.assertEquals(
			1,
			_ctTableMapper.deleteLeftPrimaryKeyTableMappings(leftPrimaryKey));

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey1, 0L);

		// Delete 0 entry

		long ctCollectionId = 4;

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		// Delete 1 entry

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		// Delete 2 entries

		long rightPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey2, ctCollectionId, true);

		try (SafeClosable safeClosable3 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				2,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new LeftRecorderModelListener();

		ModelListenerRegistrationUtil.register(leftModelListener);

		try (SafeClosable safeClosable4 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		ModelListenerRegistrationUtil.unregister(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RightRecorderModelListener();

		ModelListenerRegistrationUtil.register(rightModelListener);

		try (SafeClosable safeClosable5 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		ModelListenerRegistrationUtil.unregister(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new LeftRecorderModelListener();

		ModelListenerRegistrationUtil.register(leftModelListener);

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable6 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey1);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey1);

		ModelListenerRegistrationUtil.unregister(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RightRecorderModelListener();

		ModelListenerRegistrationUtil.register(rightModelListener);

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable7 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteLeftPrimaryKeyTableMappings(
					leftPrimaryKey));
		}

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey1, Left.class.getName(), leftPrimaryKey);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey1, Left.class.getName(), leftPrimaryKey);

		ModelListenerRegistrationUtil.unregister(rightModelListener);
	}

	@Test
	public void testDeleteRightPrimaryKeyTableMappings() {

		// Delete 0 entry (production)

		long rightPrimaryKey = 1;

		Assert.assertEquals(
			0,
			_ctTableMapper.deleteRightPrimaryKeyTableMappings(rightPrimaryKey));

		// Delete 1 entry (production)

		long leftPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey1, rightPrimaryKey, 0L, null);

		Assert.assertEquals(
			1,
			_ctTableMapper.deleteRightPrimaryKeyTableMappings(rightPrimaryKey));

		_mappingStore.remove(leftPrimaryKey1, rightPrimaryKey, 0L);

		// Delete 0 entry

		long ctCollectionId = 4;

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		// Delete 1 entry

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		// Delete 2 entries

		long leftPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey2, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable3 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				2,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		// Delete 0 entry, with left model listener

		RecorderModelListener<Left> leftModelListener =
			new LeftRecorderModelListener();

		ModelListenerRegistrationUtil.register(leftModelListener);

		try (SafeClosable safeClosable4 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		leftModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		ModelListenerRegistrationUtil.unregister(leftModelListener);

		// Delete 0 entry, with right model listener

		RecorderModelListener<Right> rightModelListener =
			new RightRecorderModelListener();

		ModelListenerRegistrationUtil.register(rightModelListener);

		try (SafeClosable safeClosable5 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				0,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		rightModelListener.assertOnBeforeRemoveAssociation(
			false, null, null, null);

		rightModelListener.assertOnAfterRemoveAssociation(
			false, null, null, null);

		ModelListenerRegistrationUtil.unregister(rightModelListener);

		// Delete 1 entry, with left model listener

		leftModelListener = new LeftRecorderModelListener();

		ModelListenerRegistrationUtil.register(leftModelListener);

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable6 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey1, Right.class.getName(), rightPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey1, Right.class.getName(), rightPrimaryKey);

		ModelListenerRegistrationUtil.unregister(leftModelListener);

		// Delete 1 entry, with right model listener

		rightModelListener = new RightRecorderModelListener();

		ModelListenerRegistrationUtil.register(rightModelListener);

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable7 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertEquals(
				1,
				_ctTableMapper.deleteRightPrimaryKeyTableMappings(
					rightPrimaryKey));
		}

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey1);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey1);

		ModelListenerRegistrationUtil.unregister(rightModelListener);
	}

	@Test
	public void testDeleteTableMapping() {

		// No such table mapping (production)

		long leftPrimaryKey = 1;
		long rightPrimaryKey = 2;

		Assert.assertFalse(
			_ctTableMapper.deleteTableMapping(leftPrimaryKey, rightPrimaryKey));

		// Success (production)

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

		Assert.assertTrue(
			_ctTableMapper.deleteTableMapping(leftPrimaryKey, rightPrimaryKey));

		Assert.assertFalse(
			_ctTableMapper.deleteTableMapping(leftPrimaryKey, rightPrimaryKey));

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, 0L, null);

		// No such table mapping

		long ctCollectionId = 3;

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertFalse(
				_ctTableMapper.deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey));
		}

		// Success

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey));
		}

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, ctCollectionId);

		// Success, delete from production

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

		try (SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey));
		}

		// Fail, already deleted

		try (SafeClosable safeClosable3 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertFalse(
				_ctTableMapper.deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey));
		}

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, ctCollectionId);

		// Success, with model listener

		RecorderModelListener<Left> leftModelListener =
			new LeftRecorderModelListener();

		_leftBasePersistence.registerListener(leftModelListener);

		RecorderModelListener<Right> rightModelListener =
			new RightRecorderModelListener();

		_rightBasePersistence.registerListener(rightModelListener);

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable4 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertTrue(
				_ctTableMapper.deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey));
		}

		leftModelListener.assertOnBeforeRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnBeforeRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		leftModelListener.assertOnAfterRemoveAssociation(
			true, leftPrimaryKey, Right.class.getName(), rightPrimaryKey);

		rightModelListener.assertOnAfterRemoveAssociation(
			true, rightPrimaryKey, Left.class.getName(), leftPrimaryKey);

		_leftBasePersistence.unregisterListener(leftModelListener);

		_rightBasePersistence.unregisterListener(rightModelListener);

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, ctCollectionId);
	}

	@Test
	public void testDeleteTableMappings() {
		long companyId = 0;
		long leftPrimaryKey1 = 1;
		long leftPrimaryKey2 = 2;
		long rightPrimaryKey1 = 3;
		long rightPrimaryKey2 = 4;
		long ctCollectionId = 5;

		Assert.assertArrayEquals(
			new long[0],
			_ctTableMapper.deleteTableMappings(
				leftPrimaryKey1, new long[] {rightPrimaryKey1}));
		Assert.assertTrue(
			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey1, rightPrimaryKey1));
		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey1},
			_ctTableMapper.deleteTableMappings(
				leftPrimaryKey1,
				new long[] {rightPrimaryKey1, rightPrimaryKey2}));
		Assert.assertArrayEquals(
			new long[0],
			_ctTableMapper.deleteTableMappings(
				new long[] {leftPrimaryKey1}, rightPrimaryKey1));
		Assert.assertTrue(
			_ctTableMapper.addTableMapping(
				companyId, leftPrimaryKey1, rightPrimaryKey1));
		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey1},
			_ctTableMapper.deleteTableMappings(
				new long[] {leftPrimaryKey1, leftPrimaryKey2},
				rightPrimaryKey1));

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			Assert.assertArrayEquals(
				new long[0],
				_ctTableMapper.deleteTableMappings(
					leftPrimaryKey1, new long[] {rightPrimaryKey1}));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey1, rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {rightPrimaryKey1},
				_ctTableMapper.deleteTableMappings(
					leftPrimaryKey1,
					new long[] {rightPrimaryKey1, rightPrimaryKey2}));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey1, rightPrimaryKey1));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey1, rightPrimaryKey2));
			Assert.assertArrayEquals(
				new long[] {rightPrimaryKey1, rightPrimaryKey2},
				_ctTableMapper.deleteTableMappings(
					leftPrimaryKey1,
					new long[] {rightPrimaryKey1, rightPrimaryKey2}));
			Assert.assertArrayEquals(
				new long[0],
				_ctTableMapper.deleteTableMappings(
					new long[] {leftPrimaryKey1}, rightPrimaryKey1));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey1, rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {leftPrimaryKey1},
				_ctTableMapper.deleteTableMappings(
					new long[] {leftPrimaryKey1, leftPrimaryKey2},
					rightPrimaryKey1));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey1, rightPrimaryKey1));
			Assert.assertTrue(
				_ctTableMapper.addTableMapping(
					companyId, leftPrimaryKey2, rightPrimaryKey1));
			Assert.assertArrayEquals(
				new long[] {leftPrimaryKey1, leftPrimaryKey2},
				_ctTableMapper.deleteTableMappings(
					new long[] {leftPrimaryKey1, leftPrimaryKey2},
					rightPrimaryKey1));
		}
	}

	@Test
	public void testGetLeftBaseModels() {

		// Get 0 result (production)

		long rightPrimaryKey = 1;

		List<Left> lefts = _ctTableMapper.getLeftBaseModels(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), lefts);

		PortalCache<Long, long[]> rightToLeftPortalCache =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "rightToLeftPortalCache");

		rightToLeftPortalCache.remove(rightPrimaryKey);

		// Get 1 result (production)

		long leftPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey1, rightPrimaryKey, 0L, null);

		lefts = _ctTableMapper.getLeftBaseModels(
			rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(lefts.toString(), 1, lefts.size());

		Left left1 = lefts.get(0);

		Assert.assertEquals(leftPrimaryKey1, left1.getPrimaryKeyObj());

		rightToLeftPortalCache.remove(rightPrimaryKey);

		_mappingStore.remove(leftPrimaryKey1, rightPrimaryKey, 0L);

		// Get 0 result

		long ctCollectionId = 5;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			lefts = _ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertSame(Collections.emptyList(), lefts);

		// Get 1 result

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			lefts = _ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertEquals(lefts.toString(), 1, lefts.size());

		left1 = lefts.get(0);

		Assert.assertEquals(leftPrimaryKey1, left1.getPrimaryKeyObj());

		// Get 2 results, unsorted

		long leftPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey2, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			lefts = _ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertEquals(lefts.toString(), 2, lefts.size());

		left1 = lefts.get(0);
		Left left2 = lefts.get(1);

		Assert.assertEquals(leftPrimaryKey1, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPrimaryKey2, left2.getPrimaryKeyObj());

		// Get 2 results, sorted

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey2, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			lefts = _ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new OrderByComparator<Left>() {

					@Override
					public int compare(Left left1, Left left2) {
						Long leftPrimaryKey1 = (Long)left1.getPrimaryKeyObj();
						Long leftPrimaryKey2 = (Long)left2.getPrimaryKeyObj();

						return leftPrimaryKey2.compareTo(leftPrimaryKey1);
					}

				});
		}

		Assert.assertEquals(lefts.toString(), 2, lefts.size());

		left1 = lefts.get(0);
		left2 = lefts.get(1);

		Assert.assertEquals(leftPrimaryKey2, left1.getPrimaryKeyObj());
		Assert.assertEquals(leftPrimaryKey1, left2.getPrimaryKeyObj());

		// Get 3 results, paginated

		long leftPrimaryKey3 = 4;

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey2, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey3, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			lefts = _ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, 1, 2, null);
		}

		Assert.assertEquals(lefts.toString(), 1, lefts.size());

		Left left = lefts.get(0);

		Assert.assertEquals(leftPrimaryKey2, left.getPrimaryKeyObj());

		// No such model exception

		_leftBasePersistence.setNoSuchModelException(true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.getLeftBaseModels(
				rightPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(
				String.valueOf(leftPrimaryKey1), cause.getMessage());
		}
		finally {
			_leftBasePersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testGetLeftPrimaryKeys() {

		// Get 0 result (production)

		long rightPrimaryKey = 1;

		long[] leftPrimaryKeys = _ctTableMapper.getLeftPrimaryKeys(
			rightPrimaryKey);

		Assert.assertEquals(
			Arrays.toString(leftPrimaryKeys), 0, leftPrimaryKeys.length);

		// Hit cache (production)

		Assert.assertSame(
			leftPrimaryKeys,
			_ctTableMapper.getLeftPrimaryKeys(rightPrimaryKey));

		// Get 2 results, ensure ordered (production)

		long leftPrimaryKey1 = 3;
		long leftPrimaryKey2 = 2;

		PortalCache<Long, long[]> rightToLeftPortalCache =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "rightToLeftPortalCache");

		rightToLeftPortalCache.remove(rightPrimaryKey);

		_mappingStore.put(leftPrimaryKey1, rightPrimaryKey, 0L, null);
		_mappingStore.put(leftPrimaryKey2, rightPrimaryKey, 0L, null);

		leftPrimaryKeys = _ctTableMapper.getLeftPrimaryKeys(rightPrimaryKey);

		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey2, leftPrimaryKey1}, leftPrimaryKeys);

		_mappingStore.remove(leftPrimaryKey1, rightPrimaryKey, 0L);
		_mappingStore.remove(leftPrimaryKey2, rightPrimaryKey, 0L);

		// Get 0 result

		long ctCollectionId = 5;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			leftPrimaryKeys = _ctTableMapper.getLeftPrimaryKeys(
				rightPrimaryKey);
		}

		Assert.assertEquals(
			Arrays.toString(leftPrimaryKeys), 0, leftPrimaryKeys.length);

		// Get 2 results, ensure ordered

		_mappingStore.put(
			leftPrimaryKey1, rightPrimaryKey, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey2, rightPrimaryKey, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			leftPrimaryKeys = _ctTableMapper.getLeftPrimaryKeys(
				rightPrimaryKey);
		}

		Assert.assertArrayEquals(
			new long[] {leftPrimaryKey2, leftPrimaryKey1}, leftPrimaryKeys);

		// Database error

		MockGetCTLeftPrimaryKeysSqlQuery
			mockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery =
				ReflectionTestUtil.getFieldValue(
					_ctTableMapper, "_getCTLeftPrimaryKeysSqlQuery");

		mockGetLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery.setDatabaseError(
			true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.getLeftPrimaryKeys(rightPrimaryKey);
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
	public void testGetRightBaseModels() {

		// Get 0 result (production)

		long leftPrimaryKey = 1;

		List<Right> rights = _ctTableMapper.getRightBaseModels(
			leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertSame(Collections.emptyList(), rights);

		PortalCache<Long, long[]> leftToRightPortalCache =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "leftToRightPortalCache");

		leftToRightPortalCache.remove(leftPrimaryKey);

		// Get 1 result (production)

		long rightPrimaryKey1 = 2;

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey1, 0L, null);

		rights = _ctTableMapper.getRightBaseModels(
			leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(rights.toString(), 1, rights.size());

		Right right1 = rights.get(0);

		Assert.assertEquals(rightPrimaryKey1, right1.getPrimaryKeyObj());

		leftToRightPortalCache.remove(leftPrimaryKey);

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey1, 0L);

		// Get 0 result

		long ctCollectionId = 5;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rights = _ctTableMapper.getRightBaseModels(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertSame(Collections.emptyList(), rights);

		// Get 1 result

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rights = _ctTableMapper.getRightBaseModels(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertEquals(rights.toString(), 1, rights.size());

		right1 = rights.get(0);

		Assert.assertEquals(rightPrimaryKey1, right1.getPrimaryKeyObj());

		// Get 2 results, unsorted

		long rightPrimaryKey2 = 3;

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey2, ctCollectionId, true);

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rights = _ctTableMapper.getRightBaseModels(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		Assert.assertEquals(rights.toString(), 2, rights.size());

		right1 = rights.get(0);
		Right right2 = rights.get(1);

		Assert.assertEquals(rightPrimaryKey1, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPrimaryKey2, right2.getPrimaryKeyObj());

		// Get 2 results, sorted

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey2, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rights = _ctTableMapper.getRightBaseModels(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new OrderByComparator<Right>() {

					@Override
					public int compare(Right right1, Right right2) {
						Long rightPrimaryKey1 = (Long)right1.getPrimaryKeyObj();
						Long rightPrimaryKey2 = (Long)right2.getPrimaryKeyObj();

						return rightPrimaryKey2.compareTo(rightPrimaryKey1);
					}

				});
		}

		Assert.assertEquals(rights.toString(), 2, rights.size());

		right1 = rights.get(0);
		right2 = rights.get(1);

		Assert.assertEquals(rightPrimaryKey2, right1.getPrimaryKeyObj());
		Assert.assertEquals(rightPrimaryKey1, right2.getPrimaryKeyObj());

		// Get 3 results, paginated

		long rightPrimaryKey3 = 4;

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey3, ctCollectionId, true);

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey2, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rights = _ctTableMapper.getRightBaseModels(
				leftPrimaryKey, 1, 2, null);
		}

		Assert.assertEquals(rights.toString(), 1, rights.size());

		Right right = rights.get(0);

		Assert.assertEquals(rightPrimaryKey2, right.getPrimaryKeyObj());

		// No such model exception

		_rightBasePersistence.setNoSuchModelException(true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.getRightBaseModels(
				leftPrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (SystemException se) {
			Throwable cause = se.getCause();

			Assert.assertSame(NoSuchModelException.class, cause.getClass());

			Assert.assertEquals(
				String.valueOf(rightPrimaryKey1), cause.getMessage());
		}
		finally {
			_rightBasePersistence.setNoSuchModelException(false);
		}
	}

	@Test
	public void testGetRightPrimaryKeys() {

		// Get 0 result (production)

		long leftPrimaryKey = 1;

		long[] rightPrimaryKeys = _ctTableMapper.getRightPrimaryKeys(
			leftPrimaryKey);

		Assert.assertEquals(
			Arrays.toString(rightPrimaryKeys), 0, rightPrimaryKeys.length);

		// Hit cache (production)

		Assert.assertSame(
			rightPrimaryKeys,
			_ctTableMapper.getRightPrimaryKeys(leftPrimaryKey));

		// Get 2 results, ensure ordered (production)

		long rightPrimaryKey1 = 3;
		long rightPrimaryKey2 = 2;

		PortalCache<Long, long[]> leftToRightPortalCache =
			ReflectionTestUtil.getFieldValue(
				_ctTableMapper, "leftToRightPortalCache");

		leftToRightPortalCache.remove(leftPrimaryKey);

		_mappingStore.put(leftPrimaryKey, rightPrimaryKey1, 0L, null);
		_mappingStore.put(leftPrimaryKey, rightPrimaryKey2, 0L, null);

		rightPrimaryKeys = _ctTableMapper.getRightPrimaryKeys(leftPrimaryKey);

		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey2, rightPrimaryKey1}, rightPrimaryKeys);

		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey1, 0L);
		_mappingStore.remove(leftPrimaryKey, rightPrimaryKey2, 0L);

		// Get 0 result

		long ctCollectionId = 5;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rightPrimaryKeys = _ctTableMapper.getRightPrimaryKeys(
				leftPrimaryKey);
		}

		Assert.assertEquals(
			Arrays.toString(rightPrimaryKeys), 0, rightPrimaryKeys.length);

		// Get 2 results, ensure ordered

		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey1, ctCollectionId, true);
		_mappingStore.put(
			leftPrimaryKey, rightPrimaryKey2, ctCollectionId, true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			rightPrimaryKeys = _ctTableMapper.getRightPrimaryKeys(
				leftPrimaryKey);
		}

		Assert.assertArrayEquals(
			new long[] {rightPrimaryKey2, rightPrimaryKey1}, rightPrimaryKeys);

		// Database error

		MockGetCTRightPrimaryKeysSqlQuery
			mockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery =
				ReflectionTestUtil.getFieldValue(
					_ctTableMapper, "_getCTRightPrimaryKeysSqlQuery");

		mockGetRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery.setDatabaseError(
			true);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			_ctTableMapper.getRightPrimaryKeys(leftPrimaryKey);
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

	private static final String _COMPANY_COLUMN_NAME = "companyId";

	private static final String _LEFT_COLUMN_NAME = "leftId";

	private static final String _RIGHT_COLUMN_NAME = "rightId";

	private static final String _TABLE_NAME = "Lefts_Rights";

	private CTTableMapper<Left, Right> _ctTableMapper;
	private DataSource _dataSource;
	private MockBasePersistence<Left> _leftBasePersistence;
	private final MultiKeyMap _mappingStore = new MultiKeyMap();
	private MockBasePersistence<Right> _rightBasePersistence;

	private static class LeftRecorderModelListener
		extends RecorderModelListener<Left> {
	}

	private static class RecorderModelListener<T extends BaseModel<T>>
		extends BaseModelListener<T> {

		public void assertOnAfterAddAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			_assertCall(
				0, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnAfterRemoveAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			_assertCall(
				1, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnBeforeAddAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			_assertCall(
				2, called, classPK, associationClassName, associationClassPK);
		}

		public void assertOnBeforeRemoveAssociation(
			boolean called, Object classPK, String associationClassName,
			Object associationClassPK) {

			_assertCall(
				3, called, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			_record(0, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			_record(1, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			_record(2, classPK, associationClassName, associationClassPK);
		}

		@Override
		public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK) {

			_record(3, classPK, associationClassName, associationClassPK);
		}

		private void _assertCall(
			int index, boolean called, Object classPK,
			String associationClassName, Object associationClassPK) {

			if (called) {
				Assert.assertSame(_classPKs[index], classPK);
				Assert.assertEquals(
					_associationClassNames[index], associationClassName);
				Assert.assertSame(
					_associationClassPKs[index], associationClassPK);
			}
			else {
				Assert.assertFalse(
					"Called onAfterAddAssociation", _markers[index]);
			}
		}

		private void _record(
			int index, Object classPK, String associationClassName,
			Object associationClassPK) {

			_markers[index] = true;
			_classPKs[index] = classPK;
			_associationClassNames[index] = associationClassName;
			_associationClassPKs[index] = associationClassPK;
		}

		private final String[] _associationClassNames = new String[4];
		private final Object[] _associationClassPKs = new Object[4];
		private final Object[] _classPKs = new Object[4];
		private final boolean[] _markers = new boolean[4];

	}

	private static class RightRecorderModelListener
		extends RecorderModelListener<Right> {
	}

	private class GetPrimaryKeyObjInvocationHandler
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

			if (methodName.equals("toString")) {
				return String.valueOf(_primaryKey);
			}

			throw new UnsupportedOperationException();
		}

		private final Serializable _primaryKey;

	}

	private interface Left extends LeftModel {
	}

	private interface LeftModel extends BaseModel<Left> {
	}

	private class MockAddCTTableMappingSqlUpdate implements SqlUpdate {

		public MockAddCTTableMappingSqlUpdate(DataSource dataSource) {
			Assert.assertSame(_dataSource, dataSource);
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(5, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());
			Assert.assertSame(Long.class, params[3].getClass());
			Assert.assertSame(Boolean.class, params[4].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long leftPrimaryKey = (Long)params[1];
			Long rightPrimaryKey = (Long)params[2];
			Long ctCollectionId = (Long)params[3];

			if (_mappingStore.containsKey(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

				throw new RuntimeException(
					StringBundler.concat(
						"Unique key violation for left primary key ",
						leftPrimaryKey, " and right primary key ",
						rightPrimaryKey, " and ctcollectionId ",
						ctCollectionId));
			}

			Boolean changeType = (Boolean)params[4];

			_mappingStore.put(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId, changeType);

			return 1;
		}

		private boolean _databaseError;

	}

	private class MockAddTableMappingSqlUpdate implements SqlUpdate {

		public MockAddTableMappingSqlUpdate(
			DataSource dataSource, ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {
					ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT
				},
				paramSetters);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(3, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());

			Long leftPrimaryKey = (Long)params[1];
			Long rightPrimaryKey = (Long)params[2];

			if (_mappingStore.containsKey(
					leftPrimaryKey, rightPrimaryKey, 0L)) {

				throw new RuntimeException(
					StringBundler.concat(
						"Unique key violation for left primary key ",
						leftPrimaryKey, " and right primary key ",
						rightPrimaryKey, "and ctcollectionId 0"));
			}

			_mappingStore.put(leftPrimaryKey, rightPrimaryKey, 0L, null);

			return 1;
		}

	}

	private class MockBasePersistence<T extends BaseModel<T>>
		extends BasePersistenceImpl<T> {

		public MockBasePersistence(Class<T> clazz) {
			setModelClass(clazz);
		}

		@Override
		public T findByPrimaryKey(Serializable primaryKey)
			throws NoSuchModelException {

			if (_noSuchModelException) {
				throw new NoSuchModelException(primaryKey.toString());
			}

			Class<T> modelClass = getModelClass();

			return (T)ProxyUtil.newProxyInstance(
				modelClass.getClassLoader(), new Class<?>[] {modelClass},
				new GetPrimaryKeyObjInvocationHandler(primaryKey));
		}

		public void setNoSuchModelException(boolean noSuchModelException) {
			_noSuchModelException = noSuchModelException;
		}

		private boolean _noSuchModelException;

	}

	private class MockBooleanParamSetterSqlQuery implements MappingSqlQuery {

		public MockBooleanParamSetterSqlQuery(ParamSetter... paramSetters) {
			_paramSetters = paramSetters;
		}

		@Override
		public List execute(Object... params) throws SQLException {
			Assert.assertEquals(1, params.length);

			PreparedStatement preparedStatement =
				(PreparedStatement)ProxyUtil.newProxyInstance(
					CTTableMapperTest.class.getClassLoader(),
					new Class<?>[] {PreparedStatement.class},
					(proxy, method, args) -> {
						Assert.assertEquals(
							PreparedStatement.class.getMethod(
								"setBoolean", int.class, boolean.class),
							method);

						Assert.assertEquals(1, args[0]);
						Assert.assertEquals(args[1], true);

						return null;
					});

			ParamSetter paramSetter = _paramSetters[0];

			paramSetter.set(preparedStatement, 1, params[0]);

			return null;
		}

		private final ParamSetter[] _paramSetters;

	}

	private class MockContainsCTTableMappingSQLQuery
		implements MappingSqlQuery<Integer> {

		public MockContainsCTTableMappingSQLQuery(
			DataSource dataSource, RowMapper<Integer> rowMapper,
			ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {
					ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT,
					ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT
				},
				paramSetters);
			Assert.assertSame(RowMapper.COUNT, rowMapper);
		}

		@Override
		public List<Integer> execute(Object... params) {
			Assert.assertEquals(6, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			if (_emptyResultSet) {
				return Collections.emptyList();
			}

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];

			Long ctCollectionId = (Long)params[2];

			Boolean changeType = (Boolean)_mappingStore.get(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId);

			boolean inProduction = _mappingStore.containsKey(
				leftPrimaryKey, rightPrimaryKey, 0L);

			if (((changeType != null) && changeType) ||
				(inProduction && (changeType == null))) {

				return Collections.singletonList(1);
			}

			return Collections.singletonList(0);
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		public void setEmptyResultSet(boolean emptyResultSet) {
			_emptyResultSet = emptyResultSet;
		}

		private boolean _databaseError;
		private boolean _emptyResultSet;

	}

	private class MockContainsTableMappingSQLQuery
		implements MappingSqlQuery<Integer> {

		public MockContainsTableMappingSQLQuery(
			DataSource dataSource, ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT, ParamSetter.BIGINT},
				paramSetters);
		}

		@Override
		public List<Integer> execute(Object... params) {
			Assert.assertEquals(2, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			if (_emptyResultSet) {
				return Collections.emptyList();
			}

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];

			if (_mappingStore.containsKey(
					leftPrimaryKey, rightPrimaryKey, 0L)) {

				return Collections.singletonList(1);
			}

			return Collections.singletonList(0);
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		public void setEmptyResultSet(boolean emptyResultSet) {
			_emptyResultSet = emptyResultSet;
		}

		private boolean _databaseError;
		private boolean _emptyResultSet;

	}

	private class MockDeleteLeftPrimaryKeyTableMappingsSqlUpdate
		implements SqlUpdate {

		public MockDeleteLeftPrimaryKeyTableMappingsSqlUpdate(
			DataSource dataSource, ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT}, paramSetters);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			Long leftPrimaryKey = (Long)params[0];

			List<MultiKey> removeKeys = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				Long ctCollectionId = (Long)multiKey.getKey(2);

				if (leftPrimaryKey.equals(multiKey.getKey(0)) &&
					ctCollectionId.equals(0L)) {

					removeKeys.add(multiKey);
				}
			}

			for (MultiKey removeKey : removeKeys) {
				_mappingStore.remove(removeKey);
			}

			return removeKeys.size();
		}

	}

	private class MockDeleteRightPrimaryKeyTableMappingsSqlUpdate
		implements SqlUpdate {

		public MockDeleteRightPrimaryKeyTableMappingsSqlUpdate(
			DataSource dataSource, ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT}, paramSetters);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			Long rightPrimaryKey = (Long)params[0];

			List<MultiKey> removeKeys = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				Long ctCollectionId = (Long)multiKey.getKey(2);

				if (rightPrimaryKey.equals(multiKey.getKey(1)) &&
					ctCollectionId.equals(0L)) {

					removeKeys.add(multiKey);
				}
			}

			for (MultiKey removeKey : removeKeys) {
				_mappingStore.remove(removeKey);
			}

			return removeKeys.size();
		}

	}

	private class MockDeleteTableMappingSqlUpdate implements SqlUpdate {

		public MockDeleteTableMappingSqlUpdate(
			DataSource dataSource, ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT, ParamSetter.BIGINT},
				paramSetters);
		}

		@Override
		public int update(Object... params) {
			Assert.assertEquals(2, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];

			if (_mappingStore.containsKey(
					leftPrimaryKey, rightPrimaryKey, 0L)) {

				_mappingStore.remove(leftPrimaryKey, rightPrimaryKey, 0L);

				return 1;
			}

			return 0;
		}

	}

	private class MockGetCTLeftPrimaryKeysSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetCTLeftPrimaryKeysSqlQuery(
			DataSource dataSource, RowMapper<Long> rowMapper,
			ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {
					ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT,
					ParamSetter.BIGINT
				},
				paramSetters);
			Assert.assertSame(RowMapper.PRIMARY_KEY, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(4, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());
			Assert.assertSame(Long.class, params[3].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long rightPrimaryKey = (Long)params[0];
			Long ctCollectionId = (Long)params[2];

			List<Long> leftPrimaryKeysList = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				if (rightPrimaryKey.equals(multiKey.getKey(1))) {
					Long leftPrimaryKey = (Long)multiKey.getKey(0);

					Boolean changeType = (Boolean)_mappingStore.get(
						leftPrimaryKey, rightPrimaryKey, ctCollectionId);
					boolean inProduction = _mappingStore.containsKey(
						leftPrimaryKey, rightPrimaryKey, 0L);

					if ((((changeType != null) && changeType) ||
						 (inProduction && (changeType == null))) &&
						!leftPrimaryKeysList.contains(leftPrimaryKey)) {

						leftPrimaryKeysList.add(leftPrimaryKey);
					}
				}
			}

			return leftPrimaryKeysList;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockGetCTRightPrimaryKeysSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetCTRightPrimaryKeysSqlQuery(
			DataSource dataSource, RowMapper<Long> rowMapper,
			ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {
					ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT,
					ParamSetter.BIGINT
				},
				paramSetters);
			Assert.assertSame(RowMapper.PRIMARY_KEY, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(4, params.length);
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());
			Assert.assertSame(Long.class, params[3].getClass());

			if (_databaseError) {
				throw new RuntimeException("Database error");
			}

			Long leftPrimaryKey = (Long)params[0];
			Long ctCollectionId = (Long)params[2];

			List<Long> rightPrimaryKeysList = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				if (leftPrimaryKey.equals(multiKey.getKey(0))) {
					Long rightPrimaryKey = (Long)multiKey.getKey(1);

					Boolean changeType = (Boolean)_mappingStore.get(
						leftPrimaryKey, rightPrimaryKey, ctCollectionId);
					boolean inProduction = _mappingStore.containsKey(
						leftPrimaryKey, rightPrimaryKey, 0L);

					if ((((changeType != null) && changeType) ||
						 (inProduction && (changeType == null))) &&
						!rightPrimaryKeysList.contains(rightPrimaryKey)) {

						rightPrimaryKeysList.add(rightPrimaryKey);
					}
				}
			}

			return rightPrimaryKeysList;
		}

		public void setDatabaseError(boolean databaseError) {
			_databaseError = databaseError;
		}

		private boolean _databaseError;

	}

	private class MockGetLeftPrimaryKeysSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetLeftPrimaryKeysSqlQuery(
			DataSource dataSource, RowMapper<Long> rowMapper,
			ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT}, paramSetters);
			Assert.assertSame(RowMapper.PRIMARY_KEY, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			Long rightPrimaryKey = (Long)params[0];

			List<Long> leftPrimaryKeysList = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				Long ctCollectionId = (Long)multiKey.getKey(2);

				if (rightPrimaryKey.equals(multiKey.getKey(1)) &&
					ctCollectionId.equals(0L)) {

					leftPrimaryKeysList.add((Long)multiKey.getKey(0));
				}
			}

			return leftPrimaryKeysList;
		}

	}

	private class MockGetRightPrimaryKeysSqlQuery
		implements MappingSqlQuery<Long> {

		public MockGetRightPrimaryKeysSqlQuery(
			DataSource dataSource, RowMapper<Long> rowMapper,
			ParamSetter... paramSetters) {

			Assert.assertSame(_dataSource, dataSource);
			Assert.assertArrayEquals(
				new ParamSetter[] {ParamSetter.BIGINT}, paramSetters);
			Assert.assertSame(RowMapper.PRIMARY_KEY, rowMapper);
		}

		@Override
		public List<Long> execute(Object... params) {
			Assert.assertEquals(1, params.length);
			Assert.assertSame(Long.class, params[0].getClass());

			Long leftPrimaryKey = (Long)params[0];

			List<Long> rightPrimaryKeysList = new ArrayList<>();

			for (Object object : _mappingStore.keySet()) {
				MultiKey multiKey = (MultiKey)object;

				Long ctCollectionId = (Long)multiKey.getKey(2);

				if (leftPrimaryKey.equals(multiKey.getKey(0)) &&
					ctCollectionId.equals(0L)) {

					rightPrimaryKeysList.add((Long)multiKey.getKey(1));
				}
			}

			return rightPrimaryKeysList;
		}

	}

	private class MockMappingSqlQueryFactory implements MappingSqlQueryFactory {

		@Override
		public <T> MappingSqlQuery<T> getMappingSqlQuery(
			DataSource dataSource, String sql, RowMapper<T> rowMapper,
			ParamSetter... paramSetters) {

			if (sql.equals(
					StringBundler.concat(
						"SELECT ", _LEFT_COLUMN_NAME, " FROM ", _TABLE_NAME,
						" WHERE ", _RIGHT_COLUMN_NAME, " = ? AND ",
						"ctCollectionId = 0"))) {

				return (MappingSqlQuery<T>)new MockGetLeftPrimaryKeysSqlQuery(
					dataSource, RowMapper.PRIMARY_KEY, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"SELECT ", _RIGHT_COLUMN_NAME, " FROM ", _TABLE_NAME,
						" WHERE ", _LEFT_COLUMN_NAME, " = ? AND ",
						"ctCollectionId = 0"))) {

				return (MappingSqlQuery<T>)new MockGetRightPrimaryKeysSqlQuery(
					dataSource, RowMapper.PRIMARY_KEY, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"SELECT * FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ", _RIGHT_COLUMN_NAME,
						" = ? AND ctCollectionId = 0"))) {

				return (MappingSqlQuery<T>)new MockContainsTableMappingSQLQuery(
					dataSource, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"SELECT * FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ", _RIGHT_COLUMN_NAME,
						" = ? AND (ctCollectionId = 0 OR ctCollectionId = ?) ",
						"AND (changeType is NULL or changeType = true) AND ",
						"NOT EXISTS (SELECT * FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ", _RIGHT_COLUMN_NAME,
						" = ? AND ctCollectionId = ? AND changeType = ",
						"false)"))) {

				return (MappingSqlQuery<T>)
					new MockContainsCTTableMappingSQLQuery(
						dataSource, RowMapper.COUNT, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"SELECT DISTINCT (", _LEFT_COLUMN_NAME, ") FROM ",
						_TABLE_NAME, " WHERE (", _RIGHT_COLUMN_NAME,
						" = ? AND ", _LEFT_COLUMN_NAME, " NOT IN (SELECT ",
						_LEFT_COLUMN_NAME, " FROM ", _TABLE_NAME, " WHERE ",
						_RIGHT_COLUMN_NAME, " = ? AND ctCollectionId = ? AND ",
						"changeType = false) AND ctCollectionId = 0) OR ",
						"(ctCollectionId = ? AND changeType = true)"))) {

				return (MappingSqlQuery<T>)new MockGetCTLeftPrimaryKeysSqlQuery(
					dataSource, RowMapper.PRIMARY_KEY, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"SELECT DISTINCT (", _RIGHT_COLUMN_NAME, ") FROM ",
						_TABLE_NAME, " WHERE (", _LEFT_COLUMN_NAME, " = ? AND ",
						_RIGHT_COLUMN_NAME, " NOT IN (SELECT ",
						_RIGHT_COLUMN_NAME, " FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ctCollectionId = ? AND ",
						"changeType = false) AND ctCollectionId = 0) OR ",
						"(ctCollectionId = ? AND changeType = true)"))) {

				return (MappingSqlQuery<T>)
					new MockGetCTRightPrimaryKeysSqlQuery(
						dataSource, RowMapper.PRIMARY_KEY, paramSetters);
			}

			throw new UnsupportedOperationException(sql);
		}

	}

	private class MockSqlUpdateFactory implements SqlUpdateFactory {

		@Override
		public SqlUpdate getSqlUpdate(
			DataSource dataSource, String sql, ParamSetter... paramSetters) {

			if (sql.equals(
					StringBundler.concat(
						"INSERT INTO ", _TABLE_NAME, " (", _COMPANY_COLUMN_NAME,
						", ", _LEFT_COLUMN_NAME, ", ", _RIGHT_COLUMN_NAME,
						", ctCollectionId, changeType) VALUES (?, ?, ?, ?, ",
						"?)"))) {

				return new MockAddCTTableMappingSqlUpdate(dataSource);
			}

			if (sql.equals(
					StringBundler.concat(
						"INSERT INTO ", _TABLE_NAME, " (", _COMPANY_COLUMN_NAME,
						", ", _LEFT_COLUMN_NAME, ", ", _RIGHT_COLUMN_NAME,
						", ctCollectionId) VALUES (?, ?, ?, 0)"))) {

				return new MockAddTableMappingSqlUpdate(
					dataSource, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"DELETE FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ctCollectionId = 0"))) {

				return new MockDeleteLeftPrimaryKeyTableMappingsSqlUpdate(
					dataSource, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"DELETE FROM ", _TABLE_NAME, " WHERE ",
						_RIGHT_COLUMN_NAME, " = ? AND ctCollectionId = 0"))) {

				return new MockDeleteRightPrimaryKeyTableMappingsSqlUpdate(
					dataSource, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"DELETE FROM ", _TABLE_NAME, " WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ", _RIGHT_COLUMN_NAME,
						" = ? AND ctCollectionId = 0"))) {

				return new MockDeleteTableMappingSqlUpdate(
					dataSource, paramSetters);
			}

			if (sql.equals(
					StringBundler.concat(
						"UPDATE ", _TABLE_NAME, " SET changeType = ? WHERE ",
						_LEFT_COLUMN_NAME, " = ? AND ", _RIGHT_COLUMN_NAME,
						" = ? AND ctCollectionId = ?"))) {

				return new MockUpdateCTTableMappingSqlUpdate(dataSource);
			}

			throw new UnsupportedOperationException(sql);
		}

	}

	private class MockUpdateCTTableMappingSqlUpdate implements SqlUpdate {

		public MockUpdateCTTableMappingSqlUpdate(DataSource dataSource) {
			Assert.assertSame(_dataSource, dataSource);
		}

		@Override
		public int update(Object... params) {
			Assert.assertSame(Long.class, params[0].getClass());
			Assert.assertSame(Long.class, params[1].getClass());
			Assert.assertSame(Long.class, params[2].getClass());
			Assert.assertSame(Boolean.class, params[3].getClass());

			Long leftPrimaryKey = (Long)params[0];
			Long rightPrimaryKey = (Long)params[1];
			Long ctCollectionId = (Long)params[2];
			Boolean changeType = (Boolean)params[3];

			if (_mappingStore.containsKey(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

				Boolean currentChangeType = (Boolean)_mappingStore.get(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId);

				if (currentChangeType != changeType) {
					_mappingStore.put(
						leftPrimaryKey, rightPrimaryKey, ctCollectionId,
						changeType);

					return 1;
				}
			}

			return 0;
		}

	}

	private interface Right extends RightModel {
	}

	private interface RightModel extends BaseModel<Right> {
	}

}