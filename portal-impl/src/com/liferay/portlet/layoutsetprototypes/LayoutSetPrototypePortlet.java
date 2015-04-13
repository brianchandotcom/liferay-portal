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

package com.liferay.portlet.layoutsetprototypes;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypePortlet extends MVCPortlet {

	public void deleteLayoutSetPrototypes(ActionRequest actionRequest)
		throws Exception {

		long[] layoutSetPrototypeIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "layoutSetPrototypeIds"), 0L);

		for (long layoutSetPrototypeId : layoutSetPrototypeIds) {
			LayoutSetPrototypeServiceUtil.deleteLayoutSetPrototype(
				layoutSetPrototypeId);
		}
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				LayoutSetPrototype layoutSetPrototype =
					updateLayoutSetPrototype(actionRequest);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				ThemeDisplay siteThemeDisplay =
					(ThemeDisplay)themeDisplay.clone();

				siteThemeDisplay.setScopeGroupId(
					layoutSetPrototype.getGroupId());

				PortletURL siteAdministrationURL =
					PortalUtil.getSiteAdministrationURL(
						actionResponse, siteThemeDisplay,
						PortletKeys.SITE_TEMPLATE_SETTINGS);

				String controlPanelURL = HttpUtil.setParameter(
					themeDisplay.getURLControlPanel(), "p_p_id",
					PortletKeys.LAYOUT_SET_PROTOTYPE);

				controlPanelURL = HttpUtil.setParameter(
					controlPanelURL, "controlPanelCategory",
					themeDisplay.getControlPanelCategory());

				siteAdministrationURL.setParameter("redirect", controlPanelURL);

				redirect = siteAdministrationURL.toString();

				if (cmd.equals(Constants.ADD)) {
					hideDefaultSuccessMessage(actionRequest);

					MultiSessionMessages.add(
						actionRequest,
						PortletKeys.SITE_TEMPLATE_SETTINGS +
							"requestProcessed");
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayoutSetPrototypes(actionRequest);
			}
			else if (cmd.equals("reset_merge_fail_count")) {
				resetMergeFailCount(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest, "portlet.layout_set_prototypes.error");
			}
			else if (e instanceof RequiredLayoutSetPrototypeException) {
				SessionErrors.add(actionRequest, e.getClass());

				redirect = PortalUtil.escapeRedirect(redirect);

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
			ActionUtil.getLayoutSetPrototype(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutSetPrototypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.layout_set_prototypes.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.layout_set_prototypes.edit_layout_set_prototype"));
	}

	public void resetMergeFailCount(ActionRequest actionRequest)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(
				layoutSetPrototypeId);

		SitesUtil.setMergeFailCount(layoutSetPrototype, 0);
	}

	public LayoutSetPrototype updateLayoutSetPrototype(
			ActionRequest actionRequest)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		boolean layoutsUpdateable = ParamUtil.getBoolean(
			actionRequest, "layoutsUpdateable");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutSetPrototype layoutSetPrototype = null;

		if (layoutSetPrototypeId <= 0) {

			// Add layout prototoype

			layoutSetPrototype =
				LayoutSetPrototypeServiceUtil.addLayoutSetPrototype(
					nameMap, descriptionMap, active, layoutsUpdateable,
					serviceContext);
		}
		else {

			// Update layout prototoype

			layoutSetPrototype =
				LayoutSetPrototypeServiceUtil.updateLayoutSetPrototype(
					layoutSetPrototypeId, nameMap, descriptionMap, active,
					layoutsUpdateable, serviceContext);
		}

		// Custom JSPs

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName");

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		settingsProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		return LayoutSetPrototypeServiceUtil.updateLayoutSetPrototype(
			layoutSetPrototype.getLayoutSetPrototypeId(),
			settingsProperties.toString());
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}