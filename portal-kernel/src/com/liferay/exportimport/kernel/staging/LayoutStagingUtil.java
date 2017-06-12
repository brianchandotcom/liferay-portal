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

package com.liferay.exportimport.kernel.staging;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
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