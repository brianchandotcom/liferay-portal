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
public class UpdateSQLTest extends SQLTestHelper {

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_123_DB()
		throws Exception {

		try {
			executeDB(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"-123");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_123_PS()
		throws Exception {

		try {
			executePreparedStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"-123");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_123_S()
		throws Exception {

		try {
			executeStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"-123");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_userId_DB()
		throws Exception {

		try {
			executeDB(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_userId_PS()
		throws Exception {

		try {
			executePreparedStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void ListType_set_name_Test_PACL_where_listTypeId_userId_S()
		throws Exception {

		try {
			executeStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void User__set_firstName_Test_PACL_where_userId_123_DB()
		throws Exception {

		try {
			executeDB(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void User__set_firstName_Test_PACL_where_userId_123_PS()
		throws Exception {

		try {
			executePreparedStatement(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void User__set_firstName_Test_PACL_where_userId_123_S()
		throws Exception {

		try {
			executeStatement(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}