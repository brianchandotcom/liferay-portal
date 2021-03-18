/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.app.builder.internal.workflow;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowHandler.class)
public class AppBuilderAppWorkflowHandler
	extends BaseWorkflowHandler<DDLRecord> {

	@Override
	public String getClassName() {
		return ResourceActionsUtil.getCompositeModelName(
			AppBuilderApp.class.getName(), DDLRecord.class.getName());
	}

	@Override
	public String getNotificationLink(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException {

		long ddlRecordId = GetterUtil.getLong(
			serviceContext.getAttribute(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		AppBuilderApp appBuilderApp = _getAppBuilderApp(ddlRecordId);

		if (Objects.equals(
				appBuilderApp.getScope(),
				AppBuilderAppConstants.SCOPE_STANDARD)) {

			return super.getNotificationLink(workflowTaskId, serviceContext);
		}

		try {
			String portletURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					serviceContext.getRequest(),
					GetterUtil.getString(
						serviceContext.getAttribute("portletId")),
					GetterUtil.getLong(serviceContext.getAttribute("plid")),
					PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/view_app_entries.jsp"
			).setWindowState(
				WindowState.MAXIMIZED
			).buildString();

			return StringUtil.replaceFirst(
				portletURL, '?', "#/view-entry/" + ddlRecordId + "?");
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}
	}

	@Override
	public String getTitle(long classPK, Locale locale) {
		try {
			AppBuilderApp appBuilderApp = _getAppBuilderApp(classPK);

			return appBuilderApp.getName(locale);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getNotificationLink(long, ServiceContext)}}
	 */
	@Deprecated
	@Override
	public String getURLEditWorkflowTask(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException {

		long ddlRecordId = GetterUtil.getLong(
			serviceContext.getAttribute(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		AppBuilderApp appBuilderApp = _getAppBuilderApp(ddlRecordId);

		if (Objects.equals(
				appBuilderApp.getScope(),
				AppBuilderAppConstants.SCOPE_STANDARD)) {

			return super.getURLEditWorkflowTask(workflowTaskId, serviceContext);
		}

		try {
			return PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					serviceContext.getRequest(),
					GetterUtil.getString(
						serviceContext.getAttribute("portletId")),
					GetterUtil.getLong(serviceContext.getAttribute("plid")),
					PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/edit_app_entry.jsp"
			).setParameter(
				"dataRecordId", ddlRecordId
			).setWindowState(
				WindowState.MAXIMIZED
			).buildString();
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		AppBuilderApp appBuilderApp = _getAppBuilderApp(classPK);

		return _workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
			companyId, appBuilderApp.getGroupId(), getClassName(),
			appBuilderApp.getAppBuilderAppId(), 0);
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public DDLRecord updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		long ddlRecordId = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		DDLRecord ddlRecord = _ddlRecordLocalService.getRecord(ddlRecordId);

		DDLRecordVersion ddlRecordVersion = ddlRecord.getRecordVersion();

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		return _ddlRecordLocalService.updateStatus(
			userId, ddlRecordVersion.getRecordVersionId(), status,
			serviceContext);
	}

	private AppBuilderApp _getAppBuilderApp(long ddlRecordId)
		throws PortalException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				getDDLRecordAppBuilderAppDataRecordLink(ddlRecordId);

		return _appBuilderAppLocalService.getAppBuilderApp(
			appBuilderAppDataRecordLink.getAppBuilderAppId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppWorkflowHandler.class);

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}