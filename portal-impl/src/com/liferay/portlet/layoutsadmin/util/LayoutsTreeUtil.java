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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akos Thurzo
 */
public class LayoutsTreeUtil {

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			String treeId)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			incomplete, treeId);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			String treeId, LayoutSetBranch layoutSetBranch)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			incomplete, treeId, layoutSetBranch);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long layoutId, int max)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, layoutId, max);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long layoutId, int max,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, layoutId, max,
			layoutSetBranch);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId,
			long[] expandedLayoutIds, boolean incomplete, String treeId)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			expandedLayoutIds, incomplete, treeId);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId,
			long[] expandedLayoutIds, boolean incomplete, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			expandedLayoutIds, incomplete, treeId, layoutSetBranch);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId, String treeId)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(httpServletRequest, groupId, treeId);
	}

	public static String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		return _layoutsTree.getLayoutsJSON(
			httpServletRequest, groupId, treeId, layoutSetBranch);
	}

	private static volatile LayoutsTree _layoutsTree =
		ServiceProxyFactory.newServiceTrackedInstance(
			LayoutsTree.class, LayoutsTreeUtil.class, "_layoutsTree", false);

}