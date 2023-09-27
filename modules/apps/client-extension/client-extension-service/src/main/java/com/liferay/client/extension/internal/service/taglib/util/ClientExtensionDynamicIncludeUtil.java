/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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