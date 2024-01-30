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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchEngineExportTaskServiceTest
	extends BaseBatchEngineTaskServiceTest {

	@Test
	public void testGetBatchEngineExportTask() throws Exception {
		BatchEngineExportTask batchEngineExportTask =
			_createBatchEngineExportTask(user.getCompanyId());

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());
	}

	@Test(expected = PrincipalException.class)
	public void testGetBatchEngineExportTaskOtherCompanyNotAllowed()
		throws Exception {

		BatchEngineExportTask batchEngineExportTask =
			_createBatchEngineExportTask(otherCompany.getCompanyId());

		User groupAdminUser = UserTestUtil.addGroupAdminUser(user.getGroup());

		try {
			UserTestUtil.setUser(groupAdminUser);

			_batchEngineExportTaskService.getBatchEngineExportTask(
				batchEngineExportTask.getBatchEngineExportTaskId());
		}
		finally {
			UserTestUtil.setUser(user);
		}
	}

	@Test
	public void testGetBatchEngineExportTaskOtherCompanyOmniadminAllowed()
		throws Exception {

		BatchEngineExportTask batchEngineExportTask =
			_createBatchEngineExportTask(otherCompany.getCompanyId());

		_batchEngineExportTaskService.getBatchEngineExportTask(
			batchEngineExportTask.getBatchEngineExportTaskId());
	}

	private BatchEngineExportTask _createBatchEngineExportTask(long companyId)
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