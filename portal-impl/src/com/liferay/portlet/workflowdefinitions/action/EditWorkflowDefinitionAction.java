/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflowdefinitions.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Farache
 */
public class EditWorkflowDefinitionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateWorkflowDefinition(actionRequest);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.RESTORE)) {

				deleteWorkflowDefinition(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof RequiredWorkflowDefinitionException) {
				SessionErrors.add(actionRequest, e.getClass());

				hideDefaultErrorMessage(actionRequest);

				setForward(actionRequest, "portlet.workflow_definitions.view");
			}
			else if (e instanceof WorkflowDefinitionFileException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof WorkflowException) {
				_log.error(e, e);

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.workflow_definitions.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getWorkflowDefinition(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof WorkflowException) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.workflow_definitions.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.workflow_definitions.edit_workflow_definition"));
	}

	protected void deleteWorkflowDefinition(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		int version = ParamUtil.getInteger(actionRequest, "version");

		if (cmd.equals(Constants.DEACTIVATE) || cmd.equals(Constants.RESTORE)) {
			boolean active = !cmd.equals(Constants.DEACTIVATE);

			WorkflowDefinitionManagerUtil.updateActive(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, active);
		}
		else {
			WorkflowDefinitionManagerUtil.undeployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version);
		}
	}

	protected String getTitle(Map<Locale, String> titleMap) {
		if (titleMap == null) {
			return null;
		}

		String value = StringPool.BLANK;

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);
			String title = titleMap.get(locale);

			if (Validator.isNotNull(title)) {
				value = LocalizationUtil.updateLocalization(
					value, "Title", title, languageId);
			}
			else {
				value = LocalizationUtil.removeLocalization(
					value, "Title", languageId);
			}
		}

		return value;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void updateWorkflowDefinition(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		File file = uploadPortletRequest.getFile("file");

		WorkflowDefinition workflowDefinition = null;

		if (file == null) {
			String name = ParamUtil.getString(actionRequest, "name");
			int version = ParamUtil.getInteger(actionRequest, "version");

			workflowDefinition =
				WorkflowDefinitionManagerUtil.getWorkflowDefinition(
					themeDisplay.getCompanyId(), name, version);

			WorkflowDefinitionManagerUtil.updateTitle(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, getTitle(titleMap));
		}
		else {
			workflowDefinition =
				WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					getTitle(titleMap), FileUtil.getBytes(file));
		}

		actionRequest.setAttribute(
			WebKeys.WORKFLOW_DEFINITION, workflowDefinition);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(
		EditWorkflowDefinitionAction.class);

}