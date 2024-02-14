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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchEngineImportTaskServiceTest
	extends BaseBatchEngineTaskServiceTest {

	@Test
	public void testAddBatchEngineImportTask() throws Exception {
		UserTestUtil.setUser(user);

		try {
			_batchEngineImportTaskService.addBatchEngineImportTask(
				null, otherCompany.getCompanyId(), user.getUserId(), 10, null,
				BlogPosting.class.getName(), new byte[0], "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(), null,
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		_batchEngineImportTaskService.addBatchEngineImportTask(
			null, company.getCompanyId(), user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	@Test
	public void testGetBatchEngineImportTask() throws Exception {
		BatchEngineImportTask batchEngineImportTask =
			_addTestBatchEngineImportTask(
				company.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(omniadminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());

		batchEngineImportTask = _addTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());

		batchEngineImportTask = _addTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());

		batchEngineImportTask = _addTestBatchEngineImportTask(
			company.getCompanyId(), companyAdminUser);

		try {
			_batchEngineImportTaskService.getBatchEngineImportTask(
				batchEngineImportTask.getBatchEngineImportTaskId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		batchEngineImportTask = _addTestBatchEngineImportTask(
			otherCompany.getCompanyId(), omniadminUser);

		try {
			_batchEngineImportTaskService.getBatchEngineImportTask(
				batchEngineImportTask.getBatchEngineImportTaskId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		UserTestUtil.setUser(omniadminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			batchEngineImportTask.getBatchEngineImportTaskId());
	}

	@Test
	public void testGetBatchEngineImportTaskByExternalReferenceCode()
		throws Exception {

		BatchEngineImportTask batchEngineImportTask =
			_addTestBatchEngineImportTask(company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				batchEngineImportTask.getExternalReferenceCode(),
				company.getCompanyId());

		batchEngineImportTask = _addTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				batchEngineImportTask.getExternalReferenceCode(),
				company.getCompanyId());

		batchEngineImportTask = _addTestBatchEngineImportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		try {
			_batchEngineImportTaskService.
				getBatchEngineImportTaskByExternalReferenceCode(
					batchEngineImportTask.getExternalReferenceCode(),
					company.getCompanyId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}
	}

	@Test
	public void testGetBatchEngineImportTasks() throws Exception {
		UserTestUtil.setUser(user);

		int batchEngineImportTasksCount =
			_batchEngineImportTaskService.getBatchEngineImportTasksCount(
				company.getCompanyId());

		BatchEngineImportTask batchEngineImportTask =
			_addTestBatchEngineImportTask(company.getCompanyId(), user);

		_addTestBatchEngineImportTask(company.getCompanyId(), omniadminUser);

		List<BatchEngineImportTask> batchEngineImportTasks =
			_batchEngineImportTaskService.getBatchEngineImportTasks(
				company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			batchEngineImportTasks.toString(), batchEngineImportTasksCount + 1,
			batchEngineImportTasks.size());

		BatchEngineImportTask actualBatchEngineImportTask =
			batchEngineImportTasks.get(batchEngineImportTasksCount);

		Assert.assertEquals(
			batchEngineImportTask.getBatchEngineImportTaskId(),
			actualBatchEngineImportTask.getBatchEngineImportTaskId());
	}

	private BatchEngineImportTask _addTestBatchEngineImportTask(
			long companyId, User user)
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