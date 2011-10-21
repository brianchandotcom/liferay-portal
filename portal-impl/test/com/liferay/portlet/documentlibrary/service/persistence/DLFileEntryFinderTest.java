/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class DLFileEntryFinderTest extends BasePersistenceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		SessionFactory sessionFactory =
			(SessionFactory)PortalBeanLocatorUtil.locate(
				"liferaySessionFactory");

		_finderImpl = (DLFileEntryFinderImpl)PortalBeanLocatorUtil.locate(
			DLFileEntryFinder.class.getName());

		_finderImpl.setSessionFactory(
			new SQLRecorderSessionFactoryWrapper(sessionFactory));
	}

	public void testDoCountByG_F_S() throws Exception {
		// status = STATUS_ANY, inlineSQLHelper= false
		String expectSQL1 = "SELECT COUNT(DISTINCT DLFileEntry.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileEntry WHERE(DLFileEntry.groupId = ?)" +
			"AND DLFileEntry.folderId = ?";

		_finderImpl.doCountByG_F_S(
			0, Arrays.asList(0L), WorkflowConstants.STATUS_ANY, false);

		String actualSQL1 = SQLRecorderThreadLocal.getSQL();
		actualSQL1 = SQLRecorderUtil.stripSQL(actualSQL1);

		assertEquals(expectSQL1, actualSQL1);

		// status = STATUS_APPROVED, inlineSQLHelper= false
		String expectSQL2 = "SELECT COUNT(DISTINCT DLFileVersion.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileVersion WHERE" +
			"(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)AND " +
			"DLFileVersion.folderId = ?";

		_finderImpl.doCountByG_F_S(
			0, Arrays.asList(0L), WorkflowConstants.STATUS_APPROVED, false);

		String actualSQL2 = SQLRecorderThreadLocal.getSQL();
		actualSQL2 = SQLRecorderUtil.stripSQL(actualSQL2);

		assertEquals(expectSQL2, actualSQL2);

		// status = STATUS_APPROVED, inlineSQLHelper= true
		String expectSQL3 = "SELECT COUNT(DISTINCT DLFileVersion.fileEntryId)" +
			"AS COUNT_VALUE FROM DLFileVersion WHERE" +
			"(DLFileVersion.groupId = ?)AND(DLFileVersion.status = ?)AND " +
			"DLFileVersion.folderId = ?";;

		_finderImpl.doCountByG_F_S(
			0, Arrays.asList(0L), WorkflowConstants.STATUS_APPROVED, true);

		String actualSQL3 = SQLRecorderThreadLocal.getSQL();
		actualSQL3 = SQLRecorderUtil.stripSQL(actualSQL3);

		assertEquals(expectSQL3, actualSQL3);
	}

	private DLFileEntryFinderImpl _finderImpl;

}