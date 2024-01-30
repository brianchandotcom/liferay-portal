/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.test.BlogPosting;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchEngineImportTaskServiceTest
	extends BaseBatchEngineTaskServiceTest {

	@Test
	public void testGetBatchEngineImportTask() throws Exception {
		BatchEngineImportTask batchEngineImportTask =
			_createBatchEngineImportTask(user.getCompanyId());

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineImportTaskOtherCompanyNotAllowed()
		throws Exception {

		BatchEngineImportTask batchEngineImportTask =
			_createBatchEngineImportTask(otherCompany.getCompanyId());

		User groupAdminUser = UserTestUtil.addGroupAdminUser(user.getGroup());

		try {
			UserTestUtil.setUser(groupAdminUser);

			_batchEngineImportTaskService.getBatchEngineImportTask(
				batchEngineImportTask.getBatchEngineImportTaskId());
		}
		finally {
			UserTestUtil.setUser(user);
		}
	}

	@Test
	public void testGetBatchEngineImportTaskOtherCompanyOmniadminAllowed()
		throws Exception {

		BatchEngineImportTask batchEngineImportTask =
			_createBatchEngineImportTask(otherCompany.getCompanyId());

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());
	}

	private BatchEngineImportTask _createBatchEngineImportTask(long companyId)
		throws Exception {

		return _batchEngineImportTaskLocalService.addBatchEngineImportTask(
			null, companyId, user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private BatchEngineImportTaskService _batchEngineImportTaskService;

}