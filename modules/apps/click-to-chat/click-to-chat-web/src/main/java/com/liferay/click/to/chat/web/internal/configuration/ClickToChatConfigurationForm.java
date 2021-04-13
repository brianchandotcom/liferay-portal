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

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

/**
 * @author José Abelenda
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('chatProviderId', false)",
				"setVisible('chatProviderAccountId', false)",
				"setVisible('guestUsersAllowed', false)"
			},
			condition = "equals(getValue('siteSettingsStrategy'), 'always-override')"
		),
		@DDMFormRule(
			actions = {
				"setRequired('chatProviderId', true)",
				"setRequired('chatProviderAccountId', true)"
			},
			condition = "equals(getValue('siteSettingsStrategy'), 'always-inherit')"
		)
	}
)
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{@DDMFormLayoutColumn(size = 12, value = "enabled")}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12, value = "siteSettingsStrategy"
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6, value = "chatProviderId"
						),
						@DDMFormLayoutColumn(
							size = 6, value = "chatProviderAccountId"
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12, value = "guestUsersAllowed"
						)
					}
				)
			}
		)
	}
)
public interface ClickToChatConfigurationForm {

	@DDMFormField(label = "%chat-provider-account-id", required = false)
	public String chatProviderAccountId();

	@DDMFormField(
		label = "%chat-provider",
		optionLabels = {
			"%chat-provider-chatwoot", "%chat-provider-crisp",
			"%chat-provider-hubspot", "%chat-provider-jivochat", "%chat-provider-livechat",
			"%chat-provider-liveperson", "%chat-provider-smartsupp",
			"%chat-provider-tawkto", "%chat-provider-tidio"
		},
		optionValues = {
			"chatwoot", "crisp", "hubspot", "jivochat", "livechat", "liveperson",
			"smartsupp", "tawkto", "tidio"
		},
		required = false, type = "select"
	)
	public String chatProviderId();

	@DDMFormField(
		label = "%enable-click-to-chat", properties = "showAsSwitcher=true"
	)
	public boolean enabled();

	@DDMFormField(
		label = "%guest-users-allowed", properties = "showAsSwitcher=true",
		required = false
	)
	public boolean guestUsersAllowed();

	@DDMFormField(
		label = "%site-settings-strategy",
		optionLabels = {
			"%site-settings-strategy-always-inherit",
			"%site-settings-strategy-always-override",
			"%site-settings-strategy-inherit-or-override"
		},
		optionValues = {
			"always-inherit", "always-override", "inherit-or-override"
		},
		tip = "%site-settings-strategy-description", type = "select"
	)
	public String siteSettingsStrategy();

}