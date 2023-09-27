/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// These come from CommerceFrontendJsDynamicInclude

declare module Liferay {
	export const CommerceContext: {
		account?: CommerceAccount;
		accountEntryAllowedTypes: string[];
		commerceAccountGroupIds: string[];
		commerceChannelGroupId: string;
		commerceChannelId: string;
		commerceSiteType: number;
		currency?: CommerceCurrency;
		order?: CommerceOrder;
	};
}

interface CommerceAccount {
	accountId: string;
	accountName: string;
}

interface CommerceCurrency {
	currencyCode: string;
	currencyId: string;
}

interface CommerceOrder {
	orderId: string;
	orderType: string;
}
