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

package com.liferay.click.to.chat.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "click-to-chat",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration",
	localization = "content/Language", name = "click-to-chat-configuration-name"
)
public interface ClickToChatConfiguration {

	@Meta.AD(deflt = "false", name = "enable-click-to-chat", required = false)
	public boolean enabled();

	@Meta.AD(
		name = "chat-provider",
		optionLabels = {
			"chat-provider-chatwoot", "chat-provider-crisp",
			"chat-provider-jivochat", "chat-provider-liveperson",
			"chat-provider-smartsupp", "chat-provider-tidio"
		},
		optionValues = {
			"chatwoot", "crisp", "jivochat", "liveperson", "smartsupp", "tidio"
		},
		required = false
	)
	public String chatProviderId();

	@Meta.AD(deflt = "", name = "chat-provider-account-id", required = false)
	public String chatProviderAccountId();

	@Meta.AD(deflt = "false", name = "guest-users-allowed", required = false)
	public boolean guestUsersAllowed();

	@Meta.AD(
		deflt = "inherit-or-override",
		description = "site-settings-strategy-description",
		name = "site-settings-strategy",
		optionLabels = {
			"site-settings-strategy-always-inherit",
			"site-settings-strategy-always-override",
			"site-settings-strategy-inherit-or-override"
		},
		optionValues = {
			"always-inherit", "always-override", "inherit-or-override"
		},
		required = false
	)
	public String siteSettingsStrategy();

}