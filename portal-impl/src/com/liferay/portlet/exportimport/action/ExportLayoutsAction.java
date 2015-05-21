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

package com.liferay.portlet.exportimport.action;

import com.liferay.portal.LARFileNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.ExportImportServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ExportLayoutsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return;
		}

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "liveGroupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long[] layoutIds = getLayoutIds(actionRequest);

			String taskName = StringPool.BLANK;

			if (privateLayout) {
				taskName = LanguageUtil.get(
					actionRequest.getLocale(), "private-pages");
			}
			else {
				taskName = LanguageUtil.get(
					actionRequest.getLocale(), "public-pages");
			}

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
					themeDisplay.getUserId(), groupId, privateLayout, layoutIds,
					actionRequest.getParameterMap(), themeDisplay.getLocale(),
					themeDisplay.getTimeZone());

			ServiceContext serviceContext = new ServiceContext();

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addExportImportConfiguration(
						themeDisplay.getUserId(), groupId, taskName,
						StringPool.BLANK,
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						settingsMap, WorkflowConstants.STATUS_DRAFT,
						serviceContext);

			ExportImportServiceUtil.exportLayoutsAsFileInBackground(
				exportImportConfiguration);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			if (!(e instanceof LARFileNameException)) {
				_log.error(e, e);

				sendRedirect(actionRequest, actionResponse);
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
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.export_import.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.export_import.export_layouts"));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/export_import/" +
					"export_layouts_processes.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected long[] getLayoutIds(PortletRequest portletRequest)
		throws Exception {

		Set<Layout> layouts = new LinkedHashSet<>();

		Map<Long, Boolean> layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(
			portletRequest);

		for (Map.Entry<Long, Boolean> entry : layoutIdMap.entrySet()) {
			long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
			boolean includeChildren = entry.getValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			if (includeChildren) {
				layouts.addAll(layout.getAllChildren());
			}
		}

		return ExportImportHelperUtil.getLayoutIds(
			new ArrayList<Layout>(layouts));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportLayoutsAction.class);

}