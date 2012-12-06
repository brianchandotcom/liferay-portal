/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.journal.service.persistence.JournalStructurePersistence;
import com.liferay.portlet.journal.util.comparator.StructurePKComparator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalStructureOrderByTest {

	@Test
	public void testFilterFindOrderByPK() throws Exception {
		Group group = ServiceTestUtil.addGroup();

		User user = ServiceTestUtil.addUser(
			null, true, new long[] {group.getGroupId()});

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		OrderByComparator orderByComparator = new StructurePKComparator();

		_persistence.filterFindByG_P(
			group.getGroupId(), StringPool.BLANK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, orderByComparator);

		_persistence.filterFindByGroupId(
			group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);

		Assert.assertTrue(true);
	}

	private JournalStructurePersistence _persistence =
		(JournalStructurePersistence) PortalBeanLocatorUtil
			.locate(JournalStructurePersistence.class.getName());

}