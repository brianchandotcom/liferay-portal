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
public class SelectSQLTest extends SQLTestHelper {

	@Test
	public void select_STAR_from_Counter_inner_join_User_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement(
				"select * from Counter inner join User_ on User_.userId = " +
					"Counter.currentId");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void select_STAR_from_Counter_inner_join_User_Statement()
		throws Exception {

		try {
			executeStatement(
				"select * from Counter inner join User_ on User_.userId = " +
					"Counter.currentId");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void select_STAR_from_Counter_PreparedStatement() throws Exception {
		try {
			executePreparedStatement("select * from Counter");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void select_STAR_from_Counter_Statement() throws Exception {
		try {
			executeStatement("select * from Counter");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void select_STAR_from_TestPACL_Bar_PreparedStatement()
		throws Exception {

		try {
			executePreparedStatement("select * from TestPACL_Bar");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void select_STAR_from_TestPACL_Bar_Statement() throws Exception {
		try {
			executeStatement("select * from TestPACL_Bar");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void select_STAR_from_User__PreparedStatement() throws Exception {
		try {
			executePreparedStatement("select * from User_");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void select_STAR_from_User__Statement() throws Exception {
		try {
			executeStatement("select * from User_");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}