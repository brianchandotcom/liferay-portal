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

package com.liferay.portal.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionTreeJSClicks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionTreeJSClickAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(request, Constants.CMD);

			String treeId = ParamUtil.getString(request, "treeId");

			if (cmd.equals("collapse")) {
				SessionTreeJSClicks.closeNodes(request, treeId);
			}
			else if (cmd.equals("expand")) {
				String[] nodeIds = StringUtil.split(
					ParamUtil.getString(request, "nodeIds"));

				SessionTreeJSClicks.openNodes(request, treeId, nodeIds);
			}
			else if (cmd.equals("layoutCheck")) {
				long plid = ParamUtil.getLong(request, "plid");

				if (plid == LayoutConstants.DEFAULT_PLID) {
					long groupId = ParamUtil.getLong(request, "groupId");
					boolean privateLayout = ParamUtil.getBoolean(
						request, "privateLayout");

					List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
						groupId, privateLayout,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					for (Layout layout : layouts) {
						SessionTreeJSClicks.openLayoutNodes(
							request, treeId, layout.isPrivateLayout(),
							layout.getLayoutId(), true);
					}
				}
				else {
					boolean recursive = ParamUtil.getBoolean(
						request, "recursive");

					Layout layout = LayoutLocalServiceUtil.getLayout(plid);

					SessionTreeJSClicks.openLayoutNodes(
						request, treeId, layout.isPrivateLayout(),
						layout.getLayoutId(), recursive);
				}
			}
			else if (cmd.equals("layoutCollapse")) {
			}
			else if (cmd.equals("layoutUncheck")) {
				long plid = ParamUtil.getLong(request, "plid");

				if (plid == LayoutConstants.DEFAULT_PLID) {
					long groupId = ParamUtil.getLong(request, "groupId");
					boolean privateLayout = ParamUtil.getBoolean(
						request, "privateLayout");

					List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
						groupId, privateLayout,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					for (Layout layout : layouts) {
						SessionTreeJSClicks.closeLayoutNodes(
							request, treeId, layout.isPrivateLayout(),
							layout.getLayoutId(), true);
					}
				}
				else {
					boolean recursive = ParamUtil.getBoolean(
						request, "recursive");

					Layout layout = LayoutLocalServiceUtil.getLayout(plid);

					SessionTreeJSClicks.closeLayoutNodes(
						request, treeId, layout.isPrivateLayout(),
						layout.getLayoutId(), recursive);
				}
			}
			else if (cmd.equals("layoutUncollapse")) {
			}
			else {
				String nodeId = ParamUtil.getString(request, "nodeId");
				boolean openNode = ParamUtil.getBoolean(request, "openNode");

				if (openNode) {
					SessionTreeJSClicks.openNode(request, treeId, nodeId);
				}
				else {
					SessionTreeJSClicks.closeNode(request, treeId, nodeId);
				}
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

}