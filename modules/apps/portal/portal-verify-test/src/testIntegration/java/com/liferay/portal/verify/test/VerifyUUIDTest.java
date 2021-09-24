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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portal.verify.model.AssetTagVerifiableModel;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.util.Collection;
import java.util.Map;
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
	public void testVerifyModel() throws Exception {
		VerifyUUID.verify(new AssetTagVerifiableModel());
	}

	@Test
	public void testVerifyModelWithUnknownPKColumnName() throws Exception {
		try {
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
		catch (Exception exception) {
			_verifyException(
				exception,
				HashMapBuilder.put(
					DBType.DB2, "DB2 SQL Error: SQLCODE="
				).put(
					DBType.HYPERSONIC,
					"user lacks privilege or object not found:"
				).put(
					DBType.ORACLE, "ORA-00904: \"UNKNOWN\": invalid identifier"
				).put(
					DBType.POSTGRESQL,
					"ERROR: column \"unknown\" does not exist"
				).build());
		}
	}

	@Test
	public void testVerifyParallelUnknownModelWithUnknownPKColumnName()
		throws Exception {

		VerifiableUUIDModel[] verifiableUUIDModels = new VerifiableUUIDModel[3];

		for (int i = 0; i < verifiableUUIDModels.length; i++) {
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
			VerifyUUID.verify(verifiableUUIDModels);
		}
		catch (Exception exception) {
			_verifyException(
				exception,
				HashMapBuilder.put(
					DBType.DB2, "DB2 SQL Error: SQLCODE="
				).put(
					DBType.HYPERSONIC,
					"user lacks privilege or object not found:"
				).put(
					DBType.MARIADB, "Table "
				).put(
					DBType.MYSQL, "Table "
				).put(
					DBType.ORACLE, "ORA-00942: table or view does not exist"
				).put(
					DBType.POSTGRESQL,
					"ERROR: relation \"unknown\" does not exist"
				).put(
					DBType.SQLSERVER, "Invalid object name 'Unknown'"
				).put(
					DBType.SYBASE, "Unknown not found."
				).build());
		}
	}

	@Test
	public void testVerifyUnknownModelWithUnknownPKColumnName()
		throws Exception {

		try {
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
		catch (Exception exception) {
			_verifyException(
				exception,
				HashMapBuilder.put(
					DBType.DB2, "DB2 SQL Error: SQLCODE="
				).put(
					DBType.HYPERSONIC,
					"user lacks privilege or object not found:"
				).put(
					DBType.MARIADB, "Table "
				).put(
					DBType.MYSQL, "Table "
				).put(
					DBType.ORACLE, "ORA-00942: table or view does not exist"
				).put(
					DBType.POSTGRESQL,
					"ERROR: relation \"unknown\" does not exist"
				).put(
					DBType.SQLSERVER, "Invalid object name 'Unknown'"
				).put(
					DBType.SYBASE, "Unknown not found."
				).build());
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return _verifyUUID;
	}

	private void _verifyException(
			Exception exception, Map<DBType, String> expectedMessages)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		String expectedMessagePrefix = expectedMessages.get(db.getDBType());

		if (expectedMessagePrefix == null) {
			throw exception;
		}

		String message = exception.getMessage();

		Assert.assertTrue(
			message + " does not contain " + expectedMessagePrefix,
			message.contains(expectedMessagePrefix));
	}

	private static final String _UNKNOWN = "Unknown";

	private final VerifyUUID _verifyUUID = new VerifyUUID() {

		@Override
		protected void doVerify(
			Collection<? extends Callable<Void>> callables) {

			try {
				UnsafeConsumer.accept(callables, Callable<Void>::call);
			}
			catch (Throwable throwable) {
				ReflectionUtil.throwException(throwable);
			}
		}

	};

}