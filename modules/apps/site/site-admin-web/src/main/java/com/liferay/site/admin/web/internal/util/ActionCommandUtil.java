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

package com.liferay.site.admin.web.internal.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.portlet.ActionRequest;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class ActionCommandUtil {

	public static void updateLayoutSetPrototypesLinks(
			ActionRequest actionRequest, Group liveGroup)
		throws Exception {

		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled");

		if ((privateLayoutSetPrototypeId == 0) &&
			(publicLayoutSetPrototypeId == 0) &&
			!privateLayoutSetPrototypeLinkEnabled &&
			!publicLayoutSetPrototypeLinkEnabled) {

			long layoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "layoutSetPrototypeId");
			int layoutSetVisibility = ParamUtil.getInteger(
				actionRequest, "layoutSetVisibility");
			boolean layoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "layoutSetPrototypeLinkEnabled",
				layoutSetPrototypeId > 0);
			boolean layoutSetVisibilityPrivate = ParamUtil.getBoolean(
				actionRequest, "layoutSetVisibilityPrivate");

			if ((layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) ||
				layoutSetVisibilityPrivate) {

				privateLayoutSetPrototypeId = layoutSetPrototypeId;

				privateLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
			else {
				publicLayoutSetPrototypeId = layoutSetPrototypeId;

				publicLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
		}

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();
		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		if ((privateLayoutSetPrototypeId ==
				privateLayoutSet.getLayoutSetPrototypeId()) &&
			(publicLayoutSetPrototypeId ==
				publicLayoutSet.getLayoutSetPrototypeId()) &&
			(privateLayoutSetPrototypeLinkEnabled ==
				privateLayoutSet.isLayoutSetPrototypeLinkEnabled()) &&
			(publicLayoutSetPrototypeLinkEnabled ==
				publicLayoutSet.isLayoutSetPrototypeLinkEnabled())) {

			return;
		}

		Group group = liveGroup.getStagingGroup();

		if (!liveGroup.isStaged() || liveGroup.isStagedRemotely()) {
			group = liveGroup;
		}

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, publicLayoutSetPrototypeId, privateLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled,
			privateLayoutSetPrototypeLinkEnabled);
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

}