/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutprototypes.action;

import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;

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
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
public class EditLayoutPrototypeAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLayoutPrototype(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayoutPrototypes(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.layout_prototypes.error");
			}
			else if (e instanceof RequiredLayoutPrototypeException) {
				SessionErrors.add(actionRequest, e.getClass());

				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getLayoutPrototype(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutPrototypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.layout_prototypes.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest,
				"portlet.layout_prototypes.edit_layout_prototype"));
	}

	protected void deleteLayoutPrototypes(ActionRequest actionRequest)
		throws Exception {

		long[] layoutPrototypeIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "layoutPrototypeIds"), 0L);

		for (long layoutPrototypeId : layoutPrototypeIds) {
			LayoutPrototypeServiceUtil.deleteLayoutPrototype(layoutPrototypeId);
		}
	}

	protected void updateLayoutPrototype(ActionRequest actionRequest)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (layoutPrototypeId <= 0) {

			// Add layout prototoype

			LayoutPrototypeServiceUtil.addLayoutPrototype(
				nameMap, description, active);
		}
		else {

			// Update layout prototoype

			LayoutPrototypeServiceUtil.updateLayoutPrototype(
				layoutPrototypeId, nameMap, description, active);
		}
	}

}