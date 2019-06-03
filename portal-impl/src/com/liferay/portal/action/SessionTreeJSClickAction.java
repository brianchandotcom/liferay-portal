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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.taglib.ui.util.SessionTreeJSClicks;

import java.util.ConcurrentModificationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionTreeJSClickAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

			String treeId = ParamUtil.getString(httpServletRequest, "treeId");

			if (cmd.equals("collapse")) {
				SessionTreeJSClicks.closeNodes(httpServletRequest, treeId);
			}
			else if (cmd.equals("expand")) {
				String[] nodeIds = StringUtil.split(
					ParamUtil.getString(httpServletRequest, "nodeIds"));

				SessionTreeJSClicks.openNodes(
					httpServletRequest, treeId, nodeIds);
			}
			else if (cmd.equals("layoutCheck")) {
				long plid = ParamUtil.getLong(httpServletRequest, "plid");

				if (plid == LayoutConstants.DEFAULT_PLID) {
					boolean privateLayout = ParamUtil.getBoolean(
						httpServletRequest, "privateLayout");

					SessionTreeJSClicks.openLayoutNodes(
						httpServletRequest, treeId, privateLayout,
						LayoutConstants.DEFAULT_PLID, true);
				}
				else {
					boolean recursive = ParamUtil.getBoolean(
						httpServletRequest, "recursive");

					Layout layout = LayoutLocalServiceUtil.getLayout(plid);

					SessionTreeJSClicks.openLayoutNodes(
						httpServletRequest, treeId, layout.isPrivateLayout(),
						layout.getLayoutId(), recursive);
				}
			}
			else if (cmd.equals("layoutCollapse")) {
			}
			else if (cmd.equals("layoutUncheck")) {
				long plid = ParamUtil.getLong(httpServletRequest, "plid");

				if (plid == LayoutConstants.DEFAULT_PLID) {
					SessionTreeJSClicks.closeNodes(httpServletRequest, treeId);
				}
				else {
					boolean recursive = ParamUtil.getBoolean(
						httpServletRequest, "recursive");

					Layout layout = LayoutLocalServiceUtil.getLayout(plid);

					SessionTreeJSClicks.closeLayoutNodes(
						httpServletRequest, treeId, layout.isPrivateLayout(),
						layout.getLayoutId(), recursive);
				}
			}
			else if (cmd.equals("layoutUncollapse")) {
			}
			else {
				String nodeId = ParamUtil.getString(
					httpServletRequest, "nodeId");
				boolean openNode = ParamUtil.getBoolean(
					httpServletRequest, "openNode");

				if (openNode) {
					SessionTreeJSClicks.openNode(
						httpServletRequest, treeId, nodeId);
				}
				else {
					SessionTreeJSClicks.closeNode(
						httpServletRequest, treeId, nodeId);
				}
			}

			if (!cmd.isEmpty()) {
				updateCheckedLayoutPlids(httpServletRequest, treeId);
			}

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			String json = portalPreferences.getValue(
				SessionTreeJSClicks.class.getName(), treeId + "Plid");

			if (Validator.isNotNull(json)) {
				ServletResponseUtil.write(httpServletResponse, json);
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	protected void updateCheckedLayoutPlids(
		HttpServletRequest httpServletRequest, String treeId) {

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		while (true) {
			try {
				PortalPreferences portalPreferences =
					PortletPreferencesFactoryUtil.getPortalPreferences(
						httpServletRequest);

				long[] checkedLayoutIds = StringUtil.split(
					portalPreferences.getValue(
						SessionTreeJSClicks.class.getName(), treeId),
					0L);

				for (long checkedLayoutId : checkedLayoutIds) {
					if (checkedLayoutId == LayoutConstants.DEFAULT_PLID) {
						jsonArray.put(
							String.valueOf(LayoutConstants.DEFAULT_PLID));
					}

					Layout checkedLayout = LayoutLocalServiceUtil.fetchLayout(
						groupId, privateLayout, checkedLayoutId);

					if (checkedLayout == null) {
						continue;
					}

					jsonArray.put(String.valueOf(checkedLayout.getPlid()));
				}

				portalPreferences.setValue(
					SessionTreeJSClicks.class.getName(), treeId + "Plid",
					jsonArray.toString());

				return;
			}
			catch (ConcurrentModificationException cme) {
			}
		}
	}

}