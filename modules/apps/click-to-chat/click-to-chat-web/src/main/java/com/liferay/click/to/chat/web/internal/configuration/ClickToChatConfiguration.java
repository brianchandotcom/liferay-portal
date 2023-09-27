/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "click-to-chat", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration",
	localization = "content/Language", name = "click-to-chat-configuration-name"
)
public interface ClickToChatConfiguration {

	public boolean enabled();

	public String chatProviderId();

	public String chatProviderAccountId();

	public String chatProviderKeyId();

	public String chatProviderSecretKey();

	public boolean guestUsersAllowed();

	public boolean hideInControlPanel();

	public String siteSettingsStrategy();

}