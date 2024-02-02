/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.internal.test.BlogPosting;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchEngineExportTaskServiceTest
	extends BaseBatchEngineTaskServiceTest {

	@Test
	public void testAddBatchEngineExportTask() throws Exception {
		UserTestUtil.setUser(normalUser);

		_batchEngineExportTask1 = _createBatchEngineExportTask(
			company.getCompanyId(), normalUser);
	}

	@Test(expected = PrincipalException.class)
	public void testAddBatchEngineExportTaskOtherCompanyNotAllowed()
		throws Exception {

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTask1 = _createBatchEngineExportTask(
			otherCompany.getCompanyId(), normalUser);
	}

	@Test
	public void testGetBatchEngineExportTask() throws Exception {
		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskByExternalReferenceCode()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), normalUser);

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineExportTaskByExternalReferenceCodeCompanyAdminAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), normalUser);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskByExternalReferenceCodeNotOwnerNotAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineExportTaskCompanyAdminAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), normalUser);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskNormalUserNotAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskNormalUserOwnerAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), normalUser);

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskOtherCompanyNotAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			otherCompany.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(normalUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskOtherCompanyOmniadminAllowed()
		throws Exception {

		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			otherCompany.getCompanyId(), omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTasks() throws Exception {
		_batchEngineExportTask1 = _createTestBatchEngineExportTask(
			company.getCompanyId(), normalUser);

		_batchEngineExportTask2 = _createTestBatchEngineExportTask(
			company.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(normalUser);

		List<BatchEngineExportTask> batchEngineExportTasks =
			_batchEngineExportTaskService.getBatchEngineExportTasks(
				company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			batchEngineExportTasks.toString(), 1,
			batchEngineExportTasks.size());

		BatchEngineExportTask actualBatchEngineExportTask =
			batchEngineExportTasks.get(0);

		Assert.assertEquals(
			_batchEngineExportTask1.getBatchEngineExportTaskId(),
			actualBatchEngineExportTask.getBatchEngineExportTaskId());
	}

	private BatchEngineExportTask _createBatchEngineExportTask(
			long companyId, User user)
		throws Exception {

		return _batchEngineExportTaskService.addBatchEngineExportTask(
			null, companyId, user.getUserId(), null,
			BlogPosting.class.getName(), "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(),
			Collections.emptyList(),
			HashMapBuilder.<String, Serializable>put(
				"siteId", TestPropsValues.getGroupId()
			).build(),
			null);
	}

	private BatchEngineExportTask _createTestBatchEngineExportTask(
			long companyId, User owner)
		throws Exception {

		return _batchEngineExportTaskLocalService.addBatchEngineExportTask(
			null, companyId, owner.getUserId(), null,
			BlogPosting.class.getName(), "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(),
			Collections.emptyList(),
			HashMapBuilder.<String, Serializable>put(
				"siteId", TestPropsValues.getGroupId()
			).build(),
			null);
	}

	@DeleteAfterTestRun
	private BatchEngineExportTask _batchEngineExportTask1;

	@DeleteAfterTestRun
	private BatchEngineExportTask _batchEngineExportTask2;

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Inject
	private BatchEngineExportTaskService _batchEngineExportTaskService;

}