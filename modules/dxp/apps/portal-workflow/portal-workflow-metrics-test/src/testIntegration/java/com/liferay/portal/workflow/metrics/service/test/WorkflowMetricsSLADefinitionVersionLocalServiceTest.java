/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionVersionLocalServiceUtil;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsTestCase;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowMetricsSLADefinitionVersionLocalServiceTest
	extends BaseWorkflowMetricsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_company = CompanyTestUtil.addCompany();
	}

	@Test
	public void testGetWorkflowMetricsSLADefinitionVersions1()
		throws Exception {

		_addWorkflowMetricsSLADefinitionVersion(
			"Abc", 1, WorkflowConstants.STATUS_APPROVED, 1);

		_addWorkflowMetricsSLADefinitionVersion(
			"Cdf", 1, WorkflowConstants.STATUS_APPROVED, 1);

		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions =
				WorkflowMetricsSLADefinitionVersionLocalServiceUtil.
					getWorkflowMetricsSLADefinitionVersions(
						_company.getCompanyId(), new Date(), null,
						WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			workflowMetricsSLADefinitionVersions.toString(), 1,
			workflowMetricsSLADefinitionVersions.size());

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				workflowMetricsSLADefinitionVersions.get(0);

		Assert.assertEquals(
			"Cdf", workflowMetricsSLADefinitionVersion.getName());
	}

	@Test
	public void testGetWorkflowMetricsSLADefinitionVersions2()
		throws Exception {

		_addWorkflowMetricsSLADefinitionVersion(
			"Abc", 1, WorkflowConstants.STATUS_APPROVED, 1);

		_addWorkflowMetricsSLADefinitionVersion(
			"Cdf", 1, WorkflowConstants.STATUS_DRAFT, 1);

		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions =
				WorkflowMetricsSLADefinitionVersionLocalServiceUtil.
					getWorkflowMetricsSLADefinitionVersions(
						_company.getCompanyId(), new Date(), null,
						WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			workflowMetricsSLADefinitionVersions.toString(), 1,
			workflowMetricsSLADefinitionVersions.size());

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				workflowMetricsSLADefinitionVersions.get(0);

		Assert.assertEquals(
			"Abc", workflowMetricsSLADefinitionVersion.getName());
	}

	@Test
	public void testGetWorkflowMetricsSLADefinitionVersions3()
		throws Exception {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion1 =
				_addWorkflowMetricsSLADefinitionVersion(
					"Abc", 1, WorkflowConstants.STATUS_APPROVED, 1);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion2 =
				_addWorkflowMetricsSLADefinitionVersion(
					"Cdf", 1, WorkflowConstants.STATUS_APPROVED, 2);

		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions =
				WorkflowMetricsSLADefinitionVersionLocalServiceUtil.
					getWorkflowMetricsSLADefinitionVersions(
						_company.getCompanyId(), new Date(), null,
						WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			workflowMetricsSLADefinitionVersions.toString(), 2,
			workflowMetricsSLADefinitionVersions.size());
		Assert.assertTrue(
			workflowMetricsSLADefinitionVersions.contains(
				workflowMetricsSLADefinitionVersion1));
		Assert.assertTrue(
			workflowMetricsSLADefinitionVersions.contains(
				workflowMetricsSLADefinitionVersion2));
	}

	private WorkflowMetricsSLADefinitionVersion
			_addWorkflowMetricsSLADefinitionVersion(
				String name, long processId, int status,
				long workflowMetricsSLADefinitionId)
		throws Exception {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				WorkflowMetricsSLADefinitionVersionLocalServiceUtil.
					createWorkflowMetricsSLADefinitionVersion(
						CounterLocalServiceUtil.increment());

		Date now = new Date();

		workflowMetricsSLADefinitionVersion.setCreateDate(now);
		workflowMetricsSLADefinitionVersion.setModifiedDate(now);

		workflowMetricsSLADefinitionVersion.setCompanyId(
			_company.getCompanyId());
		workflowMetricsSLADefinitionVersion.setName(name);
		workflowMetricsSLADefinitionVersion.setCreateDate(now);
		workflowMetricsSLADefinitionVersion.setModifiedDate(now);
		workflowMetricsSLADefinitionVersion.setProcessId(processId);
		workflowMetricsSLADefinitionVersion.setStatus(status);
		workflowMetricsSLADefinitionVersion.setWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);

		return WorkflowMetricsSLADefinitionVersionLocalServiceUtil.
			addWorkflowMetricsSLADefinitionVersion(
				workflowMetricsSLADefinitionVersion);
	}

	private Company _company;

}