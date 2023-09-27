/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.constants;

/**
 * @author Brian Wing Shun Chan
 */
public interface ClickToChatConstants {

	public static final String CHAT_PROVIDER_ID_ZENDESK_WEB_WIDGET =
		"zendesk_web_widget";

	public static final String[] CHAT_PROVIDER_IDS = {
		"chatwoot", "crisp", "hubspot", "intercom", "jivochat", "livechat",
		"liveperson", "smartsupp", "tawkto", "tidio", "zendesk_web_widget",
		"zendesk_web_widget_classic"
	};

	public static final String[] SITE_SETTINGS_STRATEGIES = {
		"always-inherit", "always-override", "inherit-or-override"
	};

}