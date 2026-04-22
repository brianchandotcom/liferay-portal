/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jose Luis Navarro
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class BatchEngineExportTaskExecutorPerformanceTest
	extends BaseBatchEngineTaskExecutorTest {

	@Test
	@TestInfo("LPD-85155")
	public void testExportBlogPostingsPerformance() throws Exception {
		Group group = GroupTestUtil.addGroup();

		for (int i = 0; i < 3000; i++) {
			blogsEntryLocalService.addEntry(
				user.getUserId(), "headline" + i, "alternativeHeadline" + i,
				null, "articleBody" + i, new Date(baseDate.getTime()), false,
				false, null, null, null, null,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(), group.getGroupId(),
					user.getUserId()));
		}

		batchReadDurations.clear();

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.addBatchEngineExportTask(
				null, user.getCompanyId(), user.getUserId(), null,
				BlogPosting.class.getName(), "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				Arrays.asList("headline", "id"),
				HashMapBuilder.<String, Serializable>put(
					"siteId", group.getGroupId()
				).build(),
				null);

		_batchEngineExportTaskExecutor.execute(batchEngineExportTask);

		int batchCount = batchReadDurations.size();

		long first5Average = 0;

		for (int i = 0; i < 5; i++) {
			first5Average += batchReadDurations.get(i);
		}

		first5Average = first5Average / 5;

		long last5Average = 0;

		for (int i = batchCount - 5; i < batchCount; i++) {
			last5Average += batchReadDurations.get(i);
		}

		last5Average = last5Average / 5;

		Assert.assertTrue(last5Average <= (first5Average * 2));
	}

	@Inject
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

}