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
public class TruncateSQLTest extends SQLTestHelper {

	@Test
	public void truncate_table_TestPACL_TruncateFailure_PreparedStatement()
		throws Exception {

		if (!isMySQL()) {
			return;
		}

		try {
			executePreparedStatement("truncate table TestPACL_TruncateFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void truncate_table_TestPACL_TruncateFailure_Statement()
		throws Exception {

		if (!isMySQL()) {
			return;
		}

		try {
			executeStatement("truncate table TestPACL_TruncateFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void truncate_table_TestPACL_TruncateSuccess_PreparedStatement()
		throws Exception {

		if (!isMySQL()) {
			return;
		}

		try {
			executePreparedStatement(
				"create table TestPACL_TruncateSuccess (userId bigint)");

			executePreparedStatement("truncate table TestPACL_TruncateSuccess");

			executePreparedStatement("drop table TestPACL_TruncateSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void truncate_table_TestPACL_TruncateSuccess_Statement()
		throws Exception {

		if (!isMySQL()) {
			return;
		}

		try {
			executeStatement(
				"create table TestPACL_TruncateSuccess (userId bigint)");

			executeStatement("truncate table TestPACL_TruncateSuccess");

			executeStatement("drop table TestPACL_TruncateSuccess");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}