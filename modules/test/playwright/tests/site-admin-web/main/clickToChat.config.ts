/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const clickToChatConfig = {
	password: {
		chatwoot: process.env.CLICK_TO_CHAT_CHATWOOT_PASSWORD || '',
		crisp: process.env.CLICK_TO_CHAT_CRISP_PASSWORD || '',
		hubspot: process.env.CLICK_TO_CHAT_HUBSPOT_PASSWORD || '',
		jivochat: process.env.CLICK_TO_CHAT_JIVOCHAT_PASSWORD || '',
		livechat: process.env.CLICK_TO_CHAT_LIVECHAT_PASSWORD || '',
		liveperson: process.env.CLICK_TO_CHAT_LIVEPERSON_PASSWORD || '',
		smartsupp: process.env.CLICK_TO_CHAT_SMARTSUPP_PASSWORD || '',
		tawkto: process.env.CLICK_TO_CHAT_TAWKTO_PASSWORD || '',
		tidio: process.env.CLICK_TO_CHAT_TIDIO_PASSWORD || '',
		zendesk: process.env.CLICK_TO_CHAT_ZENDESK_PASSWORD || '',
	},
};

export {clickToChatConfig};
