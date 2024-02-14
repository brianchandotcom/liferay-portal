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

		try {
			_batchEngineExportTaskService.addBatchEngineExportTask(
				null, otherCompany.getCompanyId(), user.getUserId(), null,
				BlogPosting.class.getName(), "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				Collections.emptyList(),
				HashMapBuilder.<String, Serializable>put(
					"siteId", TestPropsValues.getGroupId()
				).build(),
				null);

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		_batchEngineExportTaskService.addBatchEngineExportTask(
			null, company.getCompanyId(), user.getUserId(), null,
			BlogPosting.class.getName(), "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(),
			Collections.emptyList(),
			HashMapBuilder.<String, Serializable>put(
				"siteId", TestPropsValues.getGroupId()
			).build(),
			null);
	}

	@Test
	public void testGetBatchEngineExportTask() throws Exception {
		BatchEngineExportTask batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), omniadminUser);

		UserTestUtil.setUser(omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());

		batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());

		batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(user);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				batchEngineExportTask.getExternalReferenceCode(),
				company.getCompanyId());

		batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());

		batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		try {
			_batchEngineExportTaskService.getBatchEngineExportTask(
				batchEngineExportTask.getBatchEngineExportTaskId());
			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		batchEngineExportTask = _addBatchEngineExportTask(
			otherCompany.getCompanyId(), omniadminUser);

		try {
			_batchEngineExportTaskService.getBatchEngineExportTask(
				batchEngineExportTask.getBatchEngineExportTaskId());
			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}

		UserTestUtil.setUser(omniadminUser);

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());
	}

	@Test
	public void testGetBatchEngineExportTaskByExternalReferenceCode()
		throws Exception {

		BatchEngineExportTask batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineExportTaskService.
			getBatchEngineExportTaskByExternalReferenceCode(
				batchEngineExportTask.getExternalReferenceCode(),
				company.getCompanyId());

		batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), companyAdminUser);

		UserTestUtil.setUser(user);

		try {
			_batchEngineExportTaskService.
				getBatchEngineExportTaskByExternalReferenceCode(
					batchEngineExportTask.getExternalReferenceCode(),
					company.getCompanyId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertNotNull(principalException);
		}
	}

	@Test
	public void testGetBatchEngineExportTasks() throws Exception {
		UserTestUtil.setUser(user);

		int batchEngineExportTasksCount =
			_batchEngineExportTaskService.getBatchEngineExportTasksCount(
				company.getCompanyId());

		BatchEngineExportTask batchEngineExportTask = _addBatchEngineExportTask(
			company.getCompanyId(), user);

		_addBatchEngineExportTask(company.getCompanyId(), omniadminUser);

		List<BatchEngineExportTask> batchEngineExportTasks =
			_batchEngineExportTaskService.getBatchEngineExportTasks(
				company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			batchEngineExportTasks.toString(), batchEngineExportTasksCount + 1,
			batchEngineExportTasks.size());

		BatchEngineExportTask actualBatchEngineExportTask =
			batchEngineExportTasks.get(batchEngineExportTasksCount);

		Assert.assertEquals(
			batchEngineExportTask.getBatchEngineExportTaskId(),
			actualBatchEngineExportTask.getBatchEngineExportTaskId());
	}

	private BatchEngineExportTask _addBatchEngineExportTask(
			long companyId, User user)
		throws Exception {

		return _batchEngineExportTaskLocalService.addBatchEngineExportTask(
			null, companyId, user.getUserId(), null,
			BlogPosting.class.getName(), "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(),
			Collections.emptyList(),
			HashMapBuilder.<String, Serializable>put(
				"siteId", TestPropsValues.getGroupId()
			).build(),
			null);
	}

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Inject
	private BatchEngineExportTaskService _batchEngineExportTaskService;

}