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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akos Thurzo
 */
public interface LayoutsTree {

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			String treeId)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			String treeId, LayoutSetBranch layoutSetBranch)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long layoutId, int max)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long layoutId, int max,
			LayoutSetBranch layoutSetBranch)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId,
			long[] expandedLayoutIds, boolean incomplete, String treeId)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId,
			long[] expandedLayoutIds, boolean incomplete, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId, String treeId)
		throws Exception;

	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception;

}