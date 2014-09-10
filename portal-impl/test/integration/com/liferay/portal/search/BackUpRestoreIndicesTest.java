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
package com.liferay.portal.search;

import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.test.GroupTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cristina González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BackUpRestoreIndicesTest {

	@Test
	public void testBackupAndRestore()
		throws Exception {

		Map<Long, String> indexNames = new HashMap<Long, String>();

			for (long companyId : PortalInstances.getCompanyIds()) {
				String backupName = null;

				backupName =
					"search-" + companyId + "-" + System.currentTimeMillis();

				SearchEngineUtil.backup(
					companyId, SearchEngineUtil.SYSTEM_ENGINE_ID, backupName);

				indexNames.put(companyId, backupName);
			}

			GroupTestUtil.addGroup();

			for (Map.Entry<Long, String> entry : indexNames.entrySet()) {
				String backupFileName = entry.getValue();

				SearchEngineUtil.restore(entry.getKey(), backupFileName);

				SearchEngineUtil.removeBackup(entry.getKey(), backupFileName);
			}
		}


}
