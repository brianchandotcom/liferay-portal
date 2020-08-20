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

package com.liferay.app.builder.workflow.web.internal.portlet.tab;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTabContext;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.app.tab.name=workflow",
	service = AppBuilderAppPortletTab.class
)
public class WorkflowAppBuilderAppPortletTab
	implements AppBuilderAppPortletTab {

	@Override
	public AppBuilderAppPortletTabContext getAppBuilderAppPortletTabContext(
		AppBuilderApp appBuilderApp, long dataRecordId) {

		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext =
			new AppBuilderAppPortletTabContext();

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				appBuilderApp.getCompanyId(), _getGroupId(dataRecordId),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				dataRecordId);

		if (workflowInstanceLink == null) {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.fetchDDMStructureLayout(
					appBuilderApp.getDdmStructureLayoutId());

			return appBuilderAppPortletTabContext.addDataLayoutProperties(
				appBuilderApp.getDdmStructureLayoutId(),
				HashMapBuilder.<String, Object>put(
					"nameMap", ddmStructureLayout.getNameMap()
				).put(
					"readOnly", false
				).build());
		}

		try {
			WorkflowInstance workflowInstance =
				_workflowInstanceManager.getWorkflowInstance(
					workflowInstanceLink.getCompanyId(),
					workflowInstanceLink.getWorkflowInstanceId());

			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				_appBuilderAppDataRecordLinkLocalService.
					fetchDDLRecordAppBuilderAppDataRecordLink(dataRecordId);

			if (workflowInstance.isComplete()) {
				_populateContext(
					appBuilderAppPortletTabContext,
					_appBuilderWorkflowTaskLinkLocalService.
						getAppBuilderWorkflowTaskLinks(
							appBuilderApp.getAppBuilderAppId(),
							appBuilderAppDataRecordLink.
								getAppBuilderAppVersionId()),
					appBuilderWorkflowTaskLink -> Boolean.TRUE);

				return appBuilderAppPortletTabContext;
			}

			_populateContext(
				appBuilderAppPortletTabContext,
				_appBuilderWorkflowTaskLinkLocalService.
					getAppBuilderWorkflowTaskLinks(
						appBuilderApp.getAppBuilderAppId(),
						appBuilderAppDataRecordLink.getAppBuilderAppVersionId(),
						_getWorkflowTaskName(
							appBuilderApp.getCompanyId(),
							appBuilderApp.getUserId(),
							workflowInstanceLink.getWorkflowInstanceId())),
				appBuilderWorkflowTaskLink ->
					appBuilderWorkflowTaskLink.getReadOnly());
		}
		catch (WorkflowException workflowException) {
			if (_log.isDebugEnabled()) {
				_log.debug(workflowException, workflowException);
			}
		}

		return appBuilderAppPortletTabContext;
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/entry/EditEntry.es");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/entry/ListEntries.es");
	}

	@Override
	public String getViewEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-workflow-web/js/pages/entry/ViewEntry.es");
	}

	private long _getGroupId(long dataRecordId) {
		if (dataRecordId == 0) {
			return 0;
		}

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				fetchDDLRecordAppBuilderAppDataRecordLink(dataRecordId);

		if (appBuilderAppDataRecordLink == null) {
			return 0;
		}

		return appBuilderAppDataRecordLink.getGroupId();
	}

	private String _getWorkflowTaskName(
		long companyId, long userId, long workflowInstanceId) {

		try {
			return Stream.of(
				_workflowTaskManager.search(
					companyId, userId, null, null, null, null, null, null, null,
					null, false, null, null, new Long[] {workflowInstanceId},
					true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
			).flatMap(
				List::stream
			).map(
				WorkflowTask::getName
			).findFirst(
			).orElse(
				StringPool.BLANK
			);
		}
		catch (WorkflowException workflowException) {
			if (_log.isDebugEnabled()) {
				_log.debug(workflowException, workflowException);
			}
		}

		return StringPool.BLANK;
	}

	private void _populateContext(
		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext,
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks,
		Function<AppBuilderWorkflowTaskLink, Boolean> readOnlyFunction) {

		Stream.of(
			appBuilderWorkflowTaskLinks
		).flatMap(
			List::stream
		).forEach(
			appBuilderWorkflowTaskLink -> {
				DDMStructureLayout ddmStructureLayout =
					_ddmStructureLayoutLocalService.fetchDDMStructureLayout(
						appBuilderWorkflowTaskLink.getDdmStructureLayoutId());

				appBuilderAppPortletTabContext.addDataLayoutProperties(
					ddmStructureLayout.getStructureLayoutId(),
					HashMapBuilder.<String, Object>put(
						"nameMap", ddmStructureLayout.getNameMap()
					).put(
						"readOnly",
						readOnlyFunction.apply(appBuilderWorkflowTaskLink)
					).build());
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowAppBuilderAppPortletTab.class);

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}