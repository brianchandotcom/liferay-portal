/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.test.BlogPosting;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class CompanyModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void test() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		BatchEngineImportTask batchEngineImportTask = null;

		try {
			UserTestUtil.setUser(user);

			batchEngineImportTask =
				_batchEngineImportTaskLocalService.addBatchEngineImportTask(
					null, company.getCompanyId(), user.getUserId(), 10, null,
					BlogPosting.class.getName(), new byte[0], "JSON",
					BatchEngineTaskExecuteStatus.INITIAL.name(), null,
					BatchEngineImportTaskConstants.
						IMPORT_STRATEGY_ON_ERROR_FAIL,
					BatchEngineTaskOperation.CREATE.name(), new HashMap<>(),
					null);
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}

		Assert.assertNull(
			_batchEngineImportTaskLocalService.fetchBatchEngineImportTask(
				batchEngineImportTask.getBatchEngineImportTaskId()));
		Assert.assertEquals(
			0,
			_batchEngineImportTaskErrorLocalService.
				getBatchEngineImportTaskErrorsCount(
					batchEngineImportTask.getBatchEngineImportTaskId()));
	}

	@Inject
	private BatchEngineImportTaskErrorLocalService
		_batchEngineImportTaskErrorLocalService;

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

}