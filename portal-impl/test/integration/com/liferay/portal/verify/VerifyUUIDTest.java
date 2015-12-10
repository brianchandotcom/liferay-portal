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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.concurrent.ThrowableAwareRunnable;
import com.liferay.portal.kernel.exception.BulkException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.model.LayoutVerifiableModel;
import com.liferay.portal.verify.model.VerifiableUUIDModel;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class VerifyUUIDTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testVerifyModel() throws Exception {
		VerifyUUID.verify(new LayoutVerifiableModel());
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Unable to process runnable:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog =
					"Unable to process runnable: user lacks privilege or " +
						"object not found:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog =
					"Unable to process runnable: Unknown column 'Unknown' " +
						"in 'field list'",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog =
					"Unable to process runnable: ORA-00904: \"UNKNOWN\":" +
						" invalid identifier",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog =
					"Unable to process runnable: ERROR: column \"unknown\" " +
						"does not exist",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog =
					"Unable to process runnable: Invalid column name " +
						"'Unknown'.",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "ERROR", loggerClass = ThrowableAwareRunnable.class
	)
	@Test(expected = BulkException.class)
	public void testVerifyModelWithUnknownPKColumnName() throws Exception {
		VerifyUUID.verify(
			new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return _UNKNOWN;
				}

				@Override
				public String getTableName() {
					return "Layout";
				}

			});
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Unable to process runnable:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog =
					"Unable to process runnable: user lacks privilege or " +
						"object not found:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Unable to process runnable: Table ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog =
					"Unable to process runnable: ORA-00942: table or view" +
						" does not exist",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog =
					"Unable to process runnable: ERROR: relation " +
						"\"unknown\" does not exist",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog =
					"Unable to process runnable: Unknown not found.",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "ERROR", loggerClass = ThrowableAwareRunnable.class
	)
	@Test(expected = BulkException.class)
	public void testVerifyParallelUnknownModelWithUnknownPKColumnName()
		throws Exception {

		VerifiableUUIDModel[] verifiableUUIDModels =
			new VerifiableUUIDModel[
				PropsValues.VERIFY_PROCESS_CONCURRENCY_THRESHOLD];

		for (int i = 0; i < PropsValues.VERIFY_PROCESS_CONCURRENCY_THRESHOLD;
				i++) {

			verifiableUUIDModels[i] = new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return _UNKNOWN;
				}

				@Override
				public String getTableName() {
					return _UNKNOWN;
				}

			};
		}

		VerifyUUID.verify(verifiableUUIDModels);
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog =
					"Unable to process runnable:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog =
					"Unable to process runnable: user lacks privilege or " +
						"object not found:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Unable to process runnable: Table ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog =
					"Unable to process runnable: ORA-00942: table or view" +
						" does not exist",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog =
					"Unable to process runnable: ERROR: relation \"unknown\" " +
						"does not exist",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog =
					"Unable to process runnable: Unknown not found.",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "ERROR", loggerClass = ThrowableAwareRunnable.class
	)
	@Test(expected = BulkException.class)
	public void testVerifyUnknownModelWithUnknownPKColumnName()
		throws Exception {

		VerifyUUID.verify(
			new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return _UNKNOWN;
				}

				@Override
				public String getTableName() {
					return _UNKNOWN;
				}

			});
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyUUID();
	}

	private static final String _UNKNOWN = "Unknown";

}