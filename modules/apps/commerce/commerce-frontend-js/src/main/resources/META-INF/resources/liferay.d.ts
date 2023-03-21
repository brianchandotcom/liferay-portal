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

interface CommerceOrder {
	orderId: string;
	orderType: string;
}

interface CommerceCurrency {
	currencyCode: string;
	currencyId: string;
}
