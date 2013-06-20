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

package com.liferay.portlet.dynamicdatalists.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.RecordSetDDMStructureIdException;
import com.liferay.portlet.dynamicdatalists.RecordSetNameException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marcellus Tavares
 */
public class EditRecordSetAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRecordSet(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRecordSet(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRecordSetException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.dynamic_data_lists.error");
			}
			else if (e instanceof RecordSetDDMStructureIdException ||
					 e instanceof RecordSetNameException) {

				SessionErrors.add(actionRequest, e.getClass());
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
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getRecordSet(renderRequest);
			}
		}
		catch (NoSuchRecordSetException nsrse) {

			// Let this slide because the user can manually input an record set
			// key for a new record set that does not yet exist

		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.dynamic_data_lists.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.dynamic_data_lists.edit_record_set"));
	}

	protected void deleteRecordSet(ActionRequest actionRequest)
		throws Exception {

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		DDLRecordSetServiceUtil.deleteRecordSet(recordSetId);
	}

	protected DDLRecordSet updateRecordSet(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		int scope = ParamUtil.getInteger(actionRequest, "scope");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), actionRequest);

		DDLRecordSet recordSet = null;

		if (cmd.equals(Constants.ADD)) {
			recordSet = DDLRecordSetServiceUtil.addRecordSet(
				groupId, ddmStructureId, null, nameMap, descriptionMap,
				DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, scope,
				serviceContext);
		}
		else {
			recordSet = DDLRecordSetServiceUtil.updateRecordSet(
				recordSetId, ddmStructureId, nameMap, descriptionMap,
				DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, serviceContext);
		}

		String workflowDefinition = ParamUtil.getString(
			actionRequest, "workflowDefinition");

		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			serviceContext.getUserId(), serviceContext.getCompanyId(), groupId,
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0,
			workflowDefinition);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					actionRequest, portletResource);

			portletPreferences.reset("displayDDMTemplateId");
			portletPreferences.reset("editable");
			portletPreferences.reset("formDDMTemplateId");
			portletPreferences.reset("spreadsheet");

			portletPreferences.setValue(
				"recordSetId", String.valueOf(recordSet.getRecordSetId()));

			portletPreferences.store();
		}

		return recordSet;
	}

}