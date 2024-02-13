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
		UserTestUtil.setUser(user);

		_batchEngineExportTask1 = _testBatchEngineExportTask(
			company.getCompanyId(), user);
	}

	@Test(expected = PrincipalException.class)
	public void testAddBatchEngineExportTaskOtherCompany() throws Exception {
		UserTestUtil.setUser(user);

		_batchEngineExportTask1 = _testBatchEngineExportTask(
			otherCompany.getCompanyId(), user);
	}

	@Test
	public void testGetBatchEngineExportTask() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskByCompanyAdmin() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskByExternalReferenceCode()
		throws Exception {

		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineExportTaskByExternalReferenceCodeByCompanyAdmin()
		throws Exception {

		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskByExternalReferenceCodeByNotOwner()
		throws Exception {

		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				_batchEngineExportTask1.getExternalReferenceCode(),
				company.getCompanyId());
	}

	@Test
	public void testGetBatchEngineExportTaskByOwner() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskByUser() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskOtherCompany() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			otherCompany.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskOtherCompanyByOmniadmin()
		throws Exception {

		_batchEngineExportTask1 = _addBatchEngineExportTask(
			otherCompany.getCompanyId(), omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			_batchEngineExportTask1.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTasks() throws Exception {
		_batchEngineExportTask1 = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		_batchEngineExportTask2 = _addBatchEngineExportTask(
			company.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(user);

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

	private BatchEngineExportTask _testBatchEngineExportTask(
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

	private BatchEngineExportTask _addBatchEngineExportTask(
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