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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.test.GroupTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@ExecutionTestListeners(
	listeners = {
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BackupAndRestoreIndexesTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Test
	public void testBackupAndRestore() throws Exception {
		Map<Long, String> backupNames = new HashMap<Long, String>();

		for (long companyId : PortalInstances.getCompanyIds()) {
			String backupName = StringUtil.lowerCase(
				BackupAndRestoreIndexesTest.class.getName());

			backupName = backupName + "-" + System.currentTimeMillis();

			SearchEngineUtil.backup(
				companyId, SearchEngineUtil.SYSTEM_ENGINE_ID, backupName);

			backupNames.put(companyId, backupName);
		}

		_group = GroupTestUtil.addGroup();

		for (Map.Entry<Long, String> entry : backupNames.entrySet()) {
			String backupName = entry.getValue();

			SearchEngineUtil.restore(entry.getKey(), backupName);

			SearchEngineUtil.removeBackup(entry.getKey(), backupName);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}