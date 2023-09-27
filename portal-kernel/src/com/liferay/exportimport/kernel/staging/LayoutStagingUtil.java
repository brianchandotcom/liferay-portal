/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Raymond Augé
 */
public class LayoutStagingUtil {

	public static LayoutRevision getLayoutRevision(Layout layout) {
		return _layoutStaging.getLayoutRevision(layout);
	}

	public static LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		return _layoutStaging.getLayoutSetBranch(layoutSet);
	}

	public static LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		return _layoutStaging.getLayoutSetStagingHandler(layoutSet);
	}

	public static LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		return _layoutStaging.getLayoutStagingHandler(layout);
	}

	public static boolean isBranchingLayout(Layout layout) {
		return _layoutStaging.isBranchingLayout(layout);
	}

	public static boolean isBranchingLayoutSet(
		Group group, boolean privateLayout) {

		return _layoutStaging.isBranchingLayoutSet(group, privateLayout);
	}

	public static Layout mergeLayoutRevisionIntoLayout(Layout layout) {
		return _layoutStaging.mergeLayoutRevisionIntoLayout(layout);
	}

	public static LayoutSet mergeLayoutSetRevisionIntoLayoutSet(
		LayoutSet layoutSet) {

		return _layoutStaging.mergeLayoutSetRevisionIntoLayoutSet(layoutSet);
	}

	public static boolean prepareLayoutStagingHandler(
		PortletDataContext portletDataContext, Layout layout) {

		return _layoutStaging.prepareLayoutStagingHandler(
			portletDataContext, layout);
	}

	private static volatile LayoutStaging _layoutStaging =
		ServiceProxyFactory.newServiceTrackedInstance(
			LayoutStaging.class, LayoutStagingUtil.class, "_layoutStaging",
			false);

}