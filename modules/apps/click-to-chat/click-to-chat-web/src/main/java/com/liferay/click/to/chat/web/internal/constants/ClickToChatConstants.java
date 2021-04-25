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

package com.liferay.click.to.chat.web.internal.constants;

/**
 * @author Brian Wing Shun Chan
 */
public interface ClickToChatConstants {

	public static final String[] CLICK_TO_CHAT_CHAT_PROVIDER_IDS = {
		"chatwoot", "crisp", "hubspot", "jivochat", "livechat", "liveperson",
		"smartsupp", "tawkto", "tidio", "tolvnow", "zendesk"
	};

	public static final String[] CLICK_TO_CHAT_SITE_SETTINGS_STRATEGIES = {
		"always-inherit", "always-override", "inherit-or-override"
	};

}