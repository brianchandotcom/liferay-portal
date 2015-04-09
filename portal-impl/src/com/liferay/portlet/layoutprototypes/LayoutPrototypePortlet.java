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

package com.liferay.portlet.layoutprototypes;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPrototypePortlet extends MVCPortlet {

	public void deleteLayoutPrototypes(ActionRequest actionRequest)
		throws Exception {

		long[] layoutPrototypeIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "layoutPrototypeIds"), 0L);

		for (long layoutPrototypeId : layoutPrototypeIds) {
			LayoutPrototypeServiceUtil.deleteLayoutPrototype(layoutPrototypeId);
		}
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLayoutPrototype(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayoutPrototypes(actionRequest);
			}
			else if (cmd.equals("reset_merge_fail_count")) {
				resetMergeFailCount(actionRequest);
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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getLayoutPrototype(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutPrototypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.layout_prototypes.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.layout_prototypes.edit_layout_prototype"));
	}

	/**
	 * Resets the number of failed merge attempts for the page template, which
	 * is accessed from the action request's <code>layoutPrototypeId</code>
	 * param.
	 *
	 * <p>
	 * No merge from the page template to the actual page(s) is performed at
	 * this point.
	 * </p>
	 *
	 * @param  actionRequest the action request
	 * @throws Exception if an exception occurred
	 */
	public void resetMergeFailCount(ActionRequest actionRequest)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		LayoutPrototype layoutPrototype =
			LayoutPrototypeServiceUtil.getLayoutPrototype(layoutPrototypeId);

		SitesUtil.setMergeFailCount(layoutPrototype, 0);
	}

	public void updateLayoutPrototype(ActionRequest actionRequest)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			LayoutPrototype.class.getName(), actionRequest);

		if (layoutPrototypeId <= 0) {

			// Add layout prototoype

			LayoutPrototypeServiceUtil.addLayoutPrototype(
				nameMap, descriptionMap, active, serviceContext);
		}
		else {

			// Update layout prototoype

			LayoutPrototypeServiceUtil.updateLayoutPrototype(
				layoutPrototypeId, nameMap, descriptionMap, active,
				serviceContext);
		}
	}

}