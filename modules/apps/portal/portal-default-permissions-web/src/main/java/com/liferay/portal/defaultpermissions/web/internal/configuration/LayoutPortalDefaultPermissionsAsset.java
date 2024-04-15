/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.defaultpermissions.asset.PortalDefaultPermissionsAsset;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = "portal.default.permissions.asset.key=" + LayoutPortalDefaultPermissionsAsset.ASSET_KEY,
	service = PortalDefaultPermissionsAsset.class
)
public class LayoutPortalDefaultPermissionsAsset
	implements PortalDefaultPermissionsAsset {

	public static final String ASSET_KEY =
		"com.liferay.portal.kernel.model.Layout";

	@Override
	public String getClassName() {
		return ASSET_KEY;
	}

	@Override
	public String getLabel() {
		return "page";
	}

	@Override
	public ExtendedObjectClassDefinition.Scope getScope() {
		return ExtendedObjectClassDefinition.Scope.GROUP;
	}

}