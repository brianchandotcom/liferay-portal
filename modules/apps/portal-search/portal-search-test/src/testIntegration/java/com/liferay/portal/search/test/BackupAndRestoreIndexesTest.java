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

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.IndexAdminHelper;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class BackupAndRestoreIndexesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBackupAndRestore() throws Exception {
		Map<Long, String> backupNames = new HashMap<>();

		for (long companyId : PortalInstances.getCompanyIds()) {
			String backupName = StringUtil.lowerCase(
				BackupAndRestoreIndexesTest.class.getName());

			backupName = backupName + "-" + System.currentTimeMillis();

			_indexAdminHelper.backup(
				companyId, SearchEngineHelper.SYSTEM_ENGINE_ID, backupName);

			backupNames.put(companyId, backupName);
		}

		_group = GroupTestUtil.addGroup();

		for (Map.Entry<Long, String> entry : backupNames.entrySet()) {
			String backupName = entry.getValue();

			_indexAdminHelper.restore(entry.getKey(), backupName);

			_indexAdminHelper.removeBackup(entry.getKey(), backupName);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private IndexAdminHelper _indexAdminHelper;

}