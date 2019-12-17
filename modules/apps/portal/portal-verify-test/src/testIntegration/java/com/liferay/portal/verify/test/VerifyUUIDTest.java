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

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portal.verify.model.AssetTagVerifiableModel;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class VerifyUUIDTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testVerifyModel() {
		_testDoVerify(new AssetTagVerifiableModel());
	}

	@Test
	public void testVerifyModelWithUnknownPKColumnName() {
		try {
			ReflectionTestUtil.invoke(
				_verifyUUID, "verifyUUID",
				new Class<?>[] {VerifiableUUIDModel.class},
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
		catch (Exception e) {
			_verifyException("testVerifyModelWithUnknownPKColumnName", e);
		}
	}

	@Test
	public void testVerifyParallelUnknownModelWithUnknownPKColumnName() {
		VerifiableUUIDModel[] verifiableUUIDModels = new VerifiableUUIDModel
			[PropsValues.VERIFY_PROCESS_CONCURRENCY_THRESHOLD];

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

		try {
			_testDoVerify(verifiableUUIDModels);
		}
		catch (Exception e) {
			_verifyException(
				"testVerifyParallelUnknownModelWithUnknownPKColumnName", e);
		}
	}

	@Test
	public void testVerifyUnknownModelWithUnknownPKColumnName() {
		try {
			_testDoVerify(
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
		catch (Exception e) {
			_verifyException(
				"testVerifyUnknownModelWithUnknownPKColumnName", e);
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return _verifyUUID;
	}

	private static void _verifyException(String methodName, Exception e) {
		String message = e.getMessage();

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		String expectedMessagePrefix = "";

		if (methodName.equals("testVerifyModelWithUnknownPKColumnName")) {
			if (dbType == DBType.DB2) {
				expectedMessagePrefix = "DB2 SQL Error: SQLCODE=";
			}
			else if (dbType == DBType.HYPERSONIC) {
				expectedMessagePrefix =
					"user lacks privilege or object not found:";
			}
			else if (dbType == DBType.ORACLE) {
				expectedMessagePrefix =
					"ORA-00904: \"UNKNOWN\": invalid identifie";
			}
			else if (dbType == DBType.POSTGRESQL) {
				expectedMessagePrefix =
					"ERROR: column \"unknown\" does not exist";
			}
			else {
				expectedMessagePrefix = "Invalid column name 'Unknown";
			}
		}
		else {
			if (dbType == DBType.DB2) {
				expectedMessagePrefix = "DB2 SQL Error: SQLCODE=";
			}
			else if (dbType == DBType.HYPERSONIC) {
				expectedMessagePrefix =
					"user lacks privilege or object not found:";
			}
			else if ((dbType == DBType.MARIADB) || (dbType == DBType.MYSQL)) {
				expectedMessagePrefix = "Table ";
			}
			else if (dbType == DBType.ORACLE) {
				expectedMessagePrefix =
					"ORA-00942: table or view does not exist";
			}
			else if (dbType == DBType.POSTGRESQL) {
				expectedMessagePrefix =
					"ERROR: relation \"unknown\" does not exist";
			}
			else {
				expectedMessagePrefix = "Unknown not found.";
			}
		}

		Assert.assertTrue(
			message + " does not start " + expectedMessagePrefix,
			message.startsWith(expectedMessagePrefix));
	}

	private void _testDoVerify(VerifiableUUIDModel... verifiableUUIDModels) {
		ReflectionTestUtil.invoke(
			_verifyUUID, "doVerify",
			new Class<?>[] {VerifiableUUIDModel[].class},
			new Object[] {verifiableUUIDModels});
	}

	private static final String _UNKNOWN = "Unknown";

	private final VerifyUUID _verifyUUID = new VerifyUUID() {

		@Override
		protected void doVerify(
			Collection<? extends Callable<Void>> callables) {

			try {
				UnsafeConsumer.accept(callables, Callable<Void>::call);
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
		}

	};

}