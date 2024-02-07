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
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
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

		_batchEngineImportTask1 = _createBatchEngineImportTask(
			company.getCompanyId(), user);
	}

	@Test(expected = PrincipalException.class)
	public void testAddBatchEngineImportTaskOtherCompany() throws Exception {
		UserTestUtil.setUser(user);

		_batchEngineImportTask1 = _createBatchEngineImportTask(
			otherCompany.getCompanyId(), user);
	}

	@Test
	public void testGetBatchEngineImportTask() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), omniadminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test
	public void testGetBatchEngineImportTaskByCompanyAdmin() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test
	public void testGetBatchEngineImportTaskByExternalReferenceCode()
		throws Exception {

		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				_batchEngineImportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineImportTaskByExternalReferenceCodeByCompanyAdmin()
		throws Exception {

		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				_batchEngineImportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineImportTaskByExternalReferenceCodeByNotOwner()
		throws Exception {

		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.
			getBatchEngineImportTaskByExternalReferenceCode(
				_batchEngineImportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineImportTaskByOwner() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineImportTaskByUser() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineImportTaskOtherCompany() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			otherCompany.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(user);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test
	public void testGetBatchEngineImportTaskOtherCompanyByOmniadmin()
		throws Exception {

		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			otherCompany.getCompanyId(), omniadminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_batchEngineImportTask1.getBatchEngineImportTaskId());
	}

	@Test
	public void testGetBatchEngineImportTasks() throws Exception {
		_batchEngineImportTask1 = _createTestBatchEngineImportTask(
			company.getCompanyId(), user);

		_batchEngineImportTask2 = _createTestBatchEngineImportTask(
			company.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(user);

		List<BatchEngineImportTask> batchEngineImportTasks =
			_batchEngineImportTaskService.getBatchEngineImportTasks(
				company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			batchEngineImportTasks.toString(), 1,
			batchEngineImportTasks.size());

		BatchEngineImportTask actualBatchEngineImportTask =
			batchEngineImportTasks.get(0);

		Assert.assertEquals(
			_batchEngineImportTask1.getBatchEngineImportTaskId(),
			actualBatchEngineImportTask.getBatchEngineImportTaskId());
	}

	private BatchEngineImportTask _createBatchEngineImportTask(
			long companyId, User user)
		throws Exception {

		return _batchEngineImportTaskService.addBatchEngineImportTask(
			null, companyId, user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	private BatchEngineImportTask _createTestBatchEngineImportTask(
			long companyId, User user)
		throws Exception {

		return _batchEngineImportTaskLocalService.addBatchEngineImportTask(
			null, companyId, user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	@DeleteAfterTestRun
	private BatchEngineImportTask _batchEngineImportTask1;

	@DeleteAfterTestRun
	private BatchEngineImportTask _batchEngineImportTask2;

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private BatchEngineImportTaskService _batchEngineImportTaskService;

}