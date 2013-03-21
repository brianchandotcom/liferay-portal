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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class InsertSQLTest extends SQLTestHelper {

	@Test
	public void insert_into_TestPACL_InsertFailure_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement(
				"insert into TestPACL_InsertFailure values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void insert_into_TestPACL_InsertFailure_Statement()
		throws Exception {

		try {
			executeStatement("insert into TestPACL_InsertFailure values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void insert_into_TestPACL_InsertSuccess_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement(
				"create table TestPACL_InsertSuccess (userId bigint)");

			executePreparedStatement(
				"insert into TestPACL_InsertSuccess values (1)");

			executePreparedStatement("drop table TestPACL_InsertSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void insert_into_TestPACL_InsertSuccess_Statement()
		throws Exception {

		try {
			executeStatement(
				"create table TestPACL_InsertSuccess (userId bigint)");

			executeStatement("insert into TestPACL_InsertSuccess values (1)");

			executeStatement("drop table TestPACL_InsertSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}