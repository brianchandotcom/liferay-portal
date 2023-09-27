/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function currencyFormatter(value?: number) {
	const currency = new Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	});

	if (value) {
		return currency.format(value);
	}
}
