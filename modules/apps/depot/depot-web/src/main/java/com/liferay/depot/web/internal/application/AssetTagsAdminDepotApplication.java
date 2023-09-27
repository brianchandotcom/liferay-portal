/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.application;

import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.depot.application.DepotApplication;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DepotApplication.class)
public class AssetTagsAdminDepotApplication implements DepotApplication {

	@Override
	public String getPortletId() {
		return AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN;
	}

	@Override
	public boolean isCustomizable() {
		return false;
	}

}