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
public class CreateSQLTest extends SQLTestHelper {

	@Test
	public void create_table_TestPACL_CreateFailure_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement(
				"create table TestPACL_CreateFailure (userId bigint)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void create_table_TestPACL_CreateFailure_Statement()
		throws Exception {

		try {
			executeStatement(
				"create table TestPACL_CreateFailure (userId bigint)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void create_table_TestPACL_CreateSuccess_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement(
				"create table TestPACL_CreateSuccess (userId bigint)");

			executePreparedStatement("drop table TestPACL_CreateSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void create_table_TestPACL_CreateSuccess_Statement()
		throws Exception {

		try {
			executeStatement(
				"create table TestPACL_CreateSuccess (userId bigint)");

			executeStatement("drop table TestPACL_CreateSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}