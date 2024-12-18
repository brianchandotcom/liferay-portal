/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Keven Leone
 */
@ExtendedObjectClassDefinition(
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.marketplace.settings.web.internal.configuration.MarketplaceConfiguration",
	localization = "content/Language", name = "marketplace-configuration-name"
)
public interface MarketplaceConfiguration {

	public String accessToken();

	public String clientId();

	public String marketplaceSettings();

	public String code();

	public String refreshToken();

	public long expiresIn();

	public String redirect();

	public String url();

}