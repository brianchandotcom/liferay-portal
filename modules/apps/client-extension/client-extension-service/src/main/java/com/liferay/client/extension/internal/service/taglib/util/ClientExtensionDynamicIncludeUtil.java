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

package com.liferay.client.extension.internal.service.taglib.util;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ClientExtensionDynamicIncludeUtil {

	public static List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		Layout layout, String type) {

		LayoutSet layoutSet = layout.getLayoutSet();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			new ArrayList<>(
				ClientExtensionEntryRelLocalServiceUtil.
					getClientExtensionEntryRels(
						PortalUtil.getClassNameId(LayoutSet.class),
						layoutSet.getLayoutSetId(), type));

		Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout != null) {
			clientExtensionEntryRels.addAll(
				ClientExtensionEntryRelLocalServiceUtil.
					getClientExtensionEntryRels(
						PortalUtil.getClassNameId(Layout.class),
						masterLayout.getPlid(), type));
		}

		clientExtensionEntryRels.addAll(
			ClientExtensionEntryRelLocalServiceUtil.getClientExtensionEntryRels(
				PortalUtil.getClassNameId(Layout.class), layout.getPlid(),
				type));

		return clientExtensionEntryRels;
	}

}