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

package com.liferay.portlet.dynamicdatamapping.action;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class EditStructureAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		DDMStructure structure = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				structure = updateStructure(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteStructures(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (structure != null) {
					boolean saveAndContinue = ParamUtil.getBoolean(
						actionRequest, "saveAndContinue");

					if (saveAndContinue) {
						redirect = getSaveAndContinueRedirect(
							portletConfig, actionRequest, structure, redirect);
					}
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.dynamic_data_mapping.error");
			}
			else if (e instanceof LocaleException ||
					 e instanceof RequiredStructureException ||
					 e instanceof StructureDuplicateElementException ||
					 e instanceof StructureNameException ||
					 e instanceof StructureXsdException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				if (e instanceof RequiredStructureException) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
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
				ActionUtil.getStructure(renderRequest);
			}
		}
		catch (NoSuchStructureException nsse) {

			// Let this slide because the user can manually input a structure
			// key for a new structure that does not yet exist

		}
		catch (Exception e) {
			if (//e instanceof NoSuchStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.dynamic_data_mapping.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.dynamic_data_mapping.edit_structure"));
	}

	protected void deleteStructures(ActionRequest actionRequest)
		throws Exception {

		long[] deleteStructureIds = null;

		long structureId = ParamUtil.getLong(actionRequest, "classPK");

		if (structureId > 0) {
			deleteStructureIds = new long[] {structureId};
		}
		else {
			deleteStructureIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteStructureIds"), 0L);
		}

		for (long deleteStructureId : deleteStructureIds) {
			DDMStructureServiceUtil.deleteStructure(deleteStructureId);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			DDMStructure structure, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String availableFields = ParamUtil.getString(
			actionRequest, "availableFields");
		String eventName = ParamUtil.getString(actionRequest, "eventName");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter(
			"struts_action", "/dynamic_data_mapping/edit_structure");
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(structure.getGroupId()), false);

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		portletURL.setParameter(
			"classNameId", String.valueOf(classNameId), false);

		portletURL.setParameter(
			"classPK", String.valueOf(structure.getStructureId()), false);
		portletURL.setParameter("availableFields", availableFields, false);
		portletURL.setParameter("eventName", eventName, false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected DDMStructure updateStructure(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long scopeClassNameId = ParamUtil.getLong(
			actionRequest, "scopeClassNameId");
		long parentStructureId = ParamUtil.getLong(
			actionRequest, "parentStructureId",
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String xsd = ParamUtil.getString(actionRequest, "xsd");
		String storageType = ParamUtil.getString(actionRequest, "storageType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		DDMStructure structure = null;

		if (cmd.equals(Constants.ADD)) {
			structure = DDMStructureServiceUtil.addStructure(
				groupId, parentStructureId, scopeClassNameId, null, nameMap,
				descriptionMap, xsd, storageType,
				DDMStructureConstants.TYPE_DEFAULT, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			structure = DDMStructureServiceUtil.updateStructure(
				classPK, parentStructureId, nameMap, descriptionMap, xsd,
				serviceContext);
		}

		return structure;
	}

}