/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.change.tracking.sql.CTSQLTransformer;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public abstract class BaseCTSQLTransformerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_ctCollections = new ArrayList<>();

		db = DBManagerUtil.getDB();

		CTModelRegistry.registerCTModel(
			new CTModelRegistration(
				MainTable.class, "MainTable", "mainTableId"));

		createCTEntries(1, MainTable.class, 6L, null, null);
		createCTEntries(2, MainTable.class, null, 1L, null);
		createCTEntries(3, MainTable.class, null, 1L, null);
		createCTEntries(4, MainTable.class, null, null, 4L);
		createCTEntries(5, MainTable.class, 7L, null, 4L);
		createCTEntries(6, MainTable.class, null, null, null);

		db.runSQL(
			StringBundler.concat(
				"create table MainTable (mainTableId LONG not null, ",
				"ctCollectionId LONG not null, companyId LONG, groupId LONG, ",
				"name VARCHAR(20), primary key (mainTableId, ",
				"ctCollectionId));"));

		db.runSQL("insert into MainTable values (1, 0, 2, 3, 'mt1 v1')");
		db.runSQL("insert into MainTable values (2, 0, 2, 3, 'mt2 v1')");
		db.runSQL("insert into MainTable values (3, 0, 2, 3, 'mt3 v1')");
		db.runSQL("insert into MainTable values (4, 0, 2, 3, 'mt4 v1')");
		db.runSQL("insert into MainTable values (5, 0, 2, 4, 'mt5 v1')");

		db.runSQL(
			"insert into MainTable values (6, " + getCTCollectionId(1) +
				" , 2, 3, 'mt6 add')");

		db.runSQL(
			"insert into MainTable values (1, " + getCTCollectionId(2) +
				" , 2, 3, 'mt1 modify')");

		db.runSQL(
			"insert into MainTable values (1, " + getCTCollectionId(3) +
				" , 2, 4, 'mt1 moved')");

		db.runSQL(
			"insert into MainTable values (7, " + getCTCollectionId(5) +
				" , 2, 3, 'mt7 add')");

		CTModelRegistry.registerCTModel(
			new CTModelRegistration(
				ReferenceTable.class, "ReferenceTable", "referenceTableId"));

		createCTEntries(1, ReferenceTable.class, 6L, null, null);
		createCTEntries(2, ReferenceTable.class, null, 1L, null);
		createCTEntries(3, ReferenceTable.class, null, 1L, null);
		createCTEntries(4, ReferenceTable.class, null, null, 5L);
		createCTEntries(5, ReferenceTable.class, null, 1L, 4L);
		createCTEntries(6, ReferenceTable.class, null, null, null);

		db.runSQL(
			StringBundler.concat(
				"create table ReferenceTable (referenceTableId LONG not null, ",
				"ctCollectionId LONG not null, mainTableId LONG, name ",
				"VARCHAR(20), primary key (referenceTableId, ",
				"ctCollectionId))"));

		db.runSQL("insert into ReferenceTable values (1, 0, 1, 'rt1 v1')");
		db.runSQL("insert into ReferenceTable values (2, 0, 1, 'rt2 v1')");
		db.runSQL("insert into ReferenceTable values (3, 0, 2, 'rt3 v1')");
		db.runSQL("insert into ReferenceTable values (4, 0, 2, 'rt4 v1')");
		db.runSQL("insert into ReferenceTable values (5, 0, 2, 'rt5 v1')");

		db.runSQL(
			"insert into ReferenceTable values (6, " + getCTCollectionId(1) +
				" , 1, 'rt6 add')");

		db.runSQL(
			"insert into ReferenceTable values (1, " + getCTCollectionId(2) +
				" , 1, 'rt1 modify')");

		db.runSQL(
			"insert into ReferenceTable values (1, " + getCTCollectionId(3) +
				" , 2, 'rt1 moved')");

		db.runSQL(
			"insert into ReferenceTable values (1, " + getCTCollectionId(5) +
				" , 2, 'rt1 modify2')");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);

		db.runSQL("drop table MainTable");

		CTModelRegistry.unregisterCTModel("MainTable");

		db.runSQL("drop table ReferenceTable");

		CTModelRegistry.unregisterCTModel("ReferenceTable");

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.change.tracking.service.impl." +
					"CTCollectionLocalServiceImpl",
				LoggerTestUtil.WARN);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.change.tracking.internal.search." +
					"CTSearchEventListener",
				LoggerTestUtil.WARN)) {

			for (CTCollection ctCollection : _ctCollections) {
				_ctCollectionLocalService.deleteCTCollection(ctCollection);
			}
		}
	}

	protected static long createCTEntries(
			int ctCollectionIndex, Class<?> modelClass, Long addedPK,
			Long modifiedPK, Long removedPK)
		throws Exception {

		CTCollection ctCollection = null;

		if (ctCollectionIndex <= _ctCollections.size()) {
			ctCollection = _ctCollections.get(ctCollectionIndex - 1);
		}

		if (ctCollection == null) {
			ctCollection = _ctCollectionLocalService.addCTCollection(
				null, TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), 0, RandomTestUtil.randomString(),
				null);

			_ctCollections.add(ctCollection);
		}

		if (addedPK != null) {
			_ctEntryLocalService.addCTEntry(
				null, ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(addedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_ADDITION);
		}

		if (modifiedPK != null) {
			_ctEntryLocalService.addCTEntry(
				null, ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(modifiedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		}

		if (removedPK != null) {
			_ctEntryLocalService.addCTEntry(
				null, ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(removedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_DELETION);
		}

		return ctCollection.getCtCollectionId();
	}

	protected static long getCTCollectionId(int ctCollectionIndex) {
		if (ctCollectionIndex == 0) {
			return 0;
		}

		CTCollection ctCollection = _ctCollections.get(ctCollectionIndex - 1);

		return ctCollection.getCtCollectionId();
	}

	protected void assertPerformance(
			String inputSQLFile, long ctCollectionId, int maxTime)
		throws Exception {

		String inputSQL = StreamUtil.toString(
			BaseCTSQLTransformerTestCase.class.getResourceAsStream(
				"dependencies/" + inputSQLFile));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.change.tracking.internal." +
					"CTSQLTransformerImpl",
				LoggerTestUtil.WARN);
			PerformanceTimer performanceTimer = new PerformanceTimer(maxTime);
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			_ctSQLTransformer.transform(inputSQL);
		}
	}

	@SafeVarargs
	protected final void assertQuery(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId,
			UnsafeConsumer<PreparedStatement, Exception>
				preparedStatementUnsafeConsumer,
			UnsafeConsumer<ResultSet, Exception>... resultSetUnsafeConsumers)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long userId = TestPropsValues.getUserId();

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setCtCollectionId(ctCollectionId);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);

		long originalUserId = PrincipalThreadLocal.getUserId();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					companyId, ctCollectionId);
			Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_getSQL(inputSQLFile, expectedOutputSQLFile, ctCollectionId))) {

			PrincipalThreadLocal.setName(userId);

			preparedStatementUnsafeConsumer.accept(preparedStatement);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				for (UnsafeConsumer<ResultSet, Exception> unsafeConsumer :
						resultSetUnsafeConsumers) {

					Assert.assertTrue(resultSet.next());

					unsafeConsumer.accept(resultSet);
				}

				Assert.assertFalse(resultSet.next());
			}
		}
		finally {
			PrincipalThreadLocal.setName(originalUserId);
		}
	}

	protected void assertQuery(
			String sql, UnsafeConsumer<ResultSet, Exception> unsafeConsumer)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			unsafeConsumer.accept(resultSet);
		}
	}

	protected void assertUpdate(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId,
			UnsafeConsumer<PreparedStatement, Exception>
				preparedStatementUnsafeConsumer)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long userId = TestPropsValues.getUserId();

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setCtCollectionId(ctCollectionId);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);

		long originalUserId = PrincipalThreadLocal.getUserId();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					companyId, ctCollectionId);
			Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_getSQL(inputSQLFile, expectedOutputSQLFile, ctCollectionId))) {

			PrincipalThreadLocal.setName(userId);

			preparedStatementUnsafeConsumer.accept(preparedStatement);

			Assert.assertEquals(1, preparedStatement.executeUpdate());
		}
		finally {
			PrincipalThreadLocal.setName(originalUserId);
		}
	}

	protected static DB db;

	protected static class MainTable {
	}

	protected static class ReferenceTable {
	}

	private static CTModel<?> _getCTModelProxy(long primaryKey) {
		return (CTModel<?>)ProxyUtil.newProxyInstance(
			CTSQLTransformer.class.getClassLoader(),
			new Class<?>[] {CTModel.class},
			(proxy, method, args) -> {
				String methodName = method.getName();

				if (methodName.equals("getMvccVersion")) {
					return 0L;
				}

				if (methodName.equals("getPrimaryKey")) {
					return primaryKey;
				}

				throw new UnsupportedOperationException(method.toString());
			});
	}

	private String _getSQL(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId)
		throws Exception {

		String inputSQL = StreamUtil.toString(
			BaseCTSQLTransformerTestCase.class.getResourceAsStream(
				"dependencies/" + inputSQLFile));

		String expectedOutputSQL = _normalizeSQL(
			StreamUtil.toString(
				BaseCTSQLTransformerTestCase.class.getResourceAsStream(
					"dependencies/" + expectedOutputSQLFile)));

		expectedOutputSQL = StringUtil.replace(
			expectedOutputSQL, "[$", "$]",
			HashMapBuilder.put(
				"CT_COLLECTION_ID", String.valueOf(ctCollectionId)
			).put(
				"MAIN_TABLE_CLASS_NAME_ID",
				String.valueOf(
					_classNameLocalService.getClassNameId(MainTable.class))
			).put(
				"REFERENCE_TABLE_CLASS_NAME_ID",
				String.valueOf(
					_classNameLocalService.getClassNameId(ReferenceTable.class))
			).build());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.change.tracking.internal." +
					"CTSQLTransformerImpl",
				LoggerTestUtil.WARN)) {

			String newSQL = _ctSQLTransformer.transform(inputSQL);

			Assert.assertEquals(expectedOutputSQL, newSQL);

			return newSQL;
		}
	}

	private String _normalizeSQL(String sql) {
		return StringUtil.replace(
			sql.trim(), new String[] {"\n", "    ", "   ", "  ", "( ", " )"},
			new String[] {" ", " ", " ", " ", "(", ")"});
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	private static List<CTCollection> _ctCollections;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private static CTSQLTransformer _ctSQLTransformer;

}