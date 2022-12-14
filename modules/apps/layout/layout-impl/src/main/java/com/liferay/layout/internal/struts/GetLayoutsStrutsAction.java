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

package com.liferay.layout.internal.struts;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.layoutsadmin.util.LayoutsTreeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Lundgren
 * @author Zsolt Szabó
 * @author Tibor Lipusz
 */
@Component(property = "path=/portal/get_layouts", service = StrutsAction.class)
public class GetLayoutsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		String treeId = ParamUtil.getString(httpServletRequest, "treeId");

		if (cmd.equals("get")) {
			ServletResponseUtil.write(
				httpServletResponse,
				getLayoutsJSON(httpServletRequest, groupId, treeId));
		}
		else if (cmd.equals("getAll")) {
			ServletResponseUtil.write(
				httpServletResponse,
				LayoutsTreeUtil.getLayoutsJSON(
					httpServletRequest, groupId, treeId));
		}
		else if (cmd.equals("getSiblingLayoutsJSON")) {
			ServletResponseUtil.write(
				httpServletResponse,
				getSiblingLayoutsJSON(httpServletRequest, groupId));
		}

		return null;
	}

	protected String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId, String treeId)
		throws Exception {

		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		boolean incomplete = ParamUtil.getBoolean(
			httpServletRequest, "incomplete", true);

		return LayoutsTreeUtil.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			incomplete, treeId);
	}

	protected String getSiblingLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId)
		throws Exception {

		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(httpServletRequest, "layoutId");
		int max = ParamUtil.getInteger(httpServletRequest, "max");

		return LayoutsTreeUtil.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, layoutId, max);
	}

}