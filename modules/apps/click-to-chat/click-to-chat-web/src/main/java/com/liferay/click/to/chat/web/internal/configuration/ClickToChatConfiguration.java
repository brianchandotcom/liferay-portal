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
	public boolean enable();
	
	@Meta.AD(deflt = "", name = "account-token", required = false)
	public String accountToken();
	
	@Meta.AD(
		name = "provider",
		optionLabels = {
			"CHATWOOT", "CRISP", "JIVOCHAT", "LIVEPERSON", "SMARTSUPP", "TIDIO"
		},
		required = false
	)
	public ProviderOptions provider();
	
	@Meta.AD(
		deflt = "PROVIDE_OR_INHERIT",
		description = "strategy-description",
		name = "strategy",
		optionLabels = {
			"group-provider-site-strategy.ALWAYS_INHERIT",
			"group-provider-site-strategy.PROVIDE",
			"group-provider-site-strategy.PROVIDE_OR_INHERIT"
		},
		required = false
	)
	public ClickToChatProviderSiteStrategy groupProviderSiteStrategy();
}