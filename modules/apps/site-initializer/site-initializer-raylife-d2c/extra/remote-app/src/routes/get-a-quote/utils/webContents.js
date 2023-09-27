/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toSlug} from '../../../common/utils';

const PRODUCT_QUOTE = [
	'general-liability',
	'professional-liability',
	'workers-compensation',
	'business-owners-policy',
];

export function allowedProductQuote(title) {
	return PRODUCT_QUOTE.includes(toSlug(title));
}
