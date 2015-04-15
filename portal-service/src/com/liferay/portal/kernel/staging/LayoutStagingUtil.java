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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetStagingHandler;
import com.liferay.portal.model.LayoutStagingHandler;

/**
 * @author Raymond Augé
 */
public class LayoutStagingUtil {

	public static LayoutRevision getLayoutRevision(Layout layout) {
		return getLayoutStaging().getLayoutRevision(layout);
	}

	public static LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		return getLayoutStaging().getLayoutSetBranch(layoutSet);
	}

	public static LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		return getLayoutStaging().getLayoutSetStagingHandler(layoutSet);
	}

	public static LayoutStaging getLayoutStaging() {
		PortalRuntimePermission.checkGetBeanProperty(LayoutStagingUtil.class);

		return _layoutStaging;
	}

	public static LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		return getLayoutStaging().getLayoutStagingHandler(layout);
	}

	public static boolean isBranchingLayout(Layout layout) {
		return getLayoutStaging().isBranchingLayout(layout);
	}

	public static boolean isBranchingLayoutSet(Group group) {
		return getLayoutStaging().isBranchingLayoutSet(group);
	}

	public void setLayoutStaging(LayoutStaging layoutStaging) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_layoutStaging = layoutStaging;
	}

	private static LayoutStaging _layoutStaging;

}