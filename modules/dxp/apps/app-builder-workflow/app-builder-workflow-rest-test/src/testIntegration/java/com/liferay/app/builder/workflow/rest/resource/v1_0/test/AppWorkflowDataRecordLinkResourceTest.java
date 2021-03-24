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

package com.liferay.app.builder.workflow.rest.resource.v1_0.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataRecordLink;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.DataRecordIds;
import com.liferay.app.builder.workflow.rest.client.pagination.Page;
import com.liferay.app.builder.workflow.rest.client.resource.v1_0.AppWorkflowResource;
import com.liferay.app.builder.workflow.rest.resource.v1_0.test.helper.AppWorkflowTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
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
public class AppWorkflowDataRecordLinkResourceTest
	extends BaseAppWorkflowDataRecordLinkResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_appBuilderApp = _appWorkflowTestHelper.addAppBuilderApp(
			testCompany.getCompanyId(), testGroup);

		AppWorkflowResource.Builder builder = AppWorkflowResource.builder();

		_appWorkflowResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@Override
	@Test
	public void testPostAppAppWorkflowDataRecordLinksPage() throws Exception {
		AppWorkflow postAppWorkflow = _appWorkflowResource.postAppWorkflow(
			_appBuilderApp.getAppBuilderAppId(),
			_appWorkflowTestHelper.createAppWorkflow(_appBuilderApp));

		DDLRecord ddlRecord1 = _appWorkflowTestHelper.addDDLRecord(
			_appBuilderApp, testGroup);
		DDLRecord ddlRecord2 = _appWorkflowTestHelper.addDDLRecord(
			_appBuilderApp, testGroup);

		Page<AppWorkflowDataRecordLink> page =
			appWorkflowDataRecordLinkResource.
				postAppAppWorkflowDataRecordLinksPage(
					_appBuilderApp.getAppBuilderAppId(),
					new DataRecordIds() {
						{
							dataRecordIds = new Long[] {
								ddlRecord1.getRecordId(),
								ddlRecord2.getRecordId()
							};
						}
					});

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new AppWorkflowDataRecordLink() {
					{
						appWorkflow = postAppWorkflow;
						dataRecordId = ddlRecord1.getRecordId();
					}
				},
				new AppWorkflowDataRecordLink() {
					{
						appWorkflow = postAppWorkflow;
						dataRecordId = ddlRecord2.getRecordId();
					}
				}),
			(List<AppWorkflowDataRecordLink>)page.getItems());

		assertValid(page);

		_appWorkflowTestHelper.deleteWorkflowInstance(ddlRecord2);

		page =
			appWorkflowDataRecordLinkResource.
				postAppAppWorkflowDataRecordLinksPage(
					_appBuilderApp.getAppBuilderAppId(),
					new DataRecordIds() {
						{
							dataRecordIds = new Long[] {
								ddlRecord1.getRecordId(),
								ddlRecord2.getRecordId()
							};
						}
					});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AppWorkflowDataRecordLink() {
					{
						appWorkflow = postAppWorkflow;
						dataRecordId = ddlRecord1.getRecordId();
					}
				}),
			(List<AppWorkflowDataRecordLink>)page.getItems());

		assertValid(page);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"appWorkflow", "dataRecordId"};
	}

	private AppBuilderApp _appBuilderApp;

	@Inject
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	private AppWorkflowResource _appWorkflowResource;

	@Inject
	private AppWorkflowTestHelper _appWorkflowTestHelper;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}