/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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

	@Meta.AD(required = false)
	public String accessToken();

	@Meta.AD(required = false)
	public String clientId();

	@Meta.AD(required = false)
	public String code();

	@Meta.AD(required = false)
	public long expiresIn();

	@Meta.AD(required = false)
	public String redirect();

	@Meta.AD(required = false)
	public String refreshToken();

	@Meta.AD(required = false)
	public String settings();

	@Meta.AD(required = false)
	public String url();

}