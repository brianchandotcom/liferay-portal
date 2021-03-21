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

import com.liferay.click.to.chat.web.internal.providers.ProviderOptions;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "click-to-chat-configuration",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = ClickToChatConfiguration.ID, localization = "content/Language",
	name = "click-to-chat-configuration"
)
public interface ClickToChatConfiguration {

	public String ID =
		"com.liferay.click.to.chat.web.internal.configuration." +
			"ClickToChatConfiguration";

	@Meta.AD(name = "enable-click-to-chat")
	public boolean enabled();

	@Meta.AD(
		name = "provider",
		optionLabels = {
			"CHATWOOT", "CRISP", "JIVOCHAT", "LIVEPERSON", "SMARTSUPP", "TIDIO"
		},
		required = false
	)
	public ProviderOptions provider();

	@Meta.AD(
		deflt = "", description = "provider-default-account-token-description",
		name = "provider-default-account-token", required = false
	)
	public String defaultAccountToken();

	@Meta.AD(
		deflt = "PROVIDE_OR_INHERIT",
		description = "group-provide-token-strategy-description",
		name = "group-provide-token-strategy",
		optionLabels = {
			"group-account-number-strategy.ALWAYS_INHERIT",
			"group-account-number-strategy.PROVIDE",
			"group-account-number-strategy.PROVIDE_OR_INHERIT"
		},
		required = false
	)
	public GroupProviderTokenStrategy groupProviderTokenStrategy();

}