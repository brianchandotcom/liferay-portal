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

package com.liferay.portal.workflow.kaleo.forms.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsActionKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLinkLocalService;
import com.liferay.portal.workflow.kaleo.forms.service.permission.KaleoProcessPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Inácio Nery
 */
public class KaleoProcessAssetRenderer
	extends BaseJSPAssetRenderer<KaleoProcess> {

	public KaleoProcessAssetRenderer(
		KaleoProcess kaleoProcess, DDLRecord ddlRecord,
		DDLRecordVersion ddlRecordVersion) {

		_kaleoProcess = kaleoProcess;
		_ddlRecord = ddlRecord;
		_ddlRecordVersion = ddlRecordVersion;
	}

	@Override
	public KaleoProcess getAssetObject() {
		return _kaleoProcess;
	}

	@Override
	public AssetRendererFactory<KaleoProcess> getAssetRendererFactory() {
		return new KaleoProcessAssetRendererFactory();
	}

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public long getClassPK() {
		return _ddlRecord.getRecordId();
	}

	@Override
	public long getGroupId() {
		return _kaleoProcess.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/full_content.jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _ddlRecordVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		String kaleoProcessName = StringPool.BLANK;

		try {
			kaleoProcessName = _kaleoProcess.getName(locale);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		return kaleoProcessName;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/admin/edit_record.jsp");
		portletURL.setParameter(
			"kaleoProcessId",
			String.valueOf(_kaleoProcess.getKaleoProcessId()));
		portletURL.setParameter(
			"ddlRecordId", String.valueOf(_ddlRecord.getRecordId()));

		return portletURL;
	}

	@Override
	public long getUserId() {
		return _kaleoProcess.getUserId();
	}

	@Override
	public String getUserName() {
		return _kaleoProcess.getUserName();
	}

	@Override
	public String getUuid() {
		return _kaleoProcess.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return KaleoProcessPermission.contains(
			permissionChecker, _kaleoProcess,
			KaleoFormsActionKeys.COMPLETE_FORM);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return KaleoProcessPermission.contains(
			permissionChecker, _kaleoProcess,
			KaleoFormsActionKeys.COMPLETE_FORM);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD, _ddlRecord);
		request.setAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION, _ddlRecordVersion);
		request.setAttribute(KaleoFormsWebKeys.KALEO_PROCESS, _kaleoProcess);

		KaleoProcessLink kaleoProcessLink = fetchKaleoProcessLink(request);

		request.setAttribute(
			KaleoFormsWebKeys.KALEO_PROCESS_LINK, kaleoProcessLink);

		return super.include(request, response, template);
	}

	protected KaleoProcessLink fetchKaleoProcessLink(HttpServletRequest request)
		throws Exception {

		KaleoProcessLink kaleoProcessLink = null;

		WorkflowTask workflowTask = getWorkflowTask(request);

		if (workflowTask != null) {
			kaleoProcessLink =
				_kaKaleoProcessLinkLocalService.fetchKaleoProcessLink(
					_kaleoProcess.getKaleoProcessId(), workflowTask.getName());
		}

		return kaleoProcessLink;
	}

	protected WorkflowTask getWorkflowTask(HttpServletRequest request)
		throws Exception {

		WorkflowTask workflowTask = null;

		long workflowTaskId = ParamUtil.getLong(request, "workflowTaskId");

		if (workflowTaskId > 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
				themeDisplay.getCompanyId(), workflowTaskId);
		}

		return workflowTask;
	}

	protected void setKaleoProcessLinkLocalService(
		KaleoProcessLinkLocalService kaleoProcessLinkLocalService) {

		_kaKaleoProcessLinkLocalService = kaleoProcessLinkLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoProcessAssetRenderer.class);

	private final DDLRecord _ddlRecord;
	private final DDLRecordVersion _ddlRecordVersion;
	private KaleoProcessLinkLocalService _kaKaleoProcessLinkLocalService;
	private final KaleoProcess _kaleoProcess;

}