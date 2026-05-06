/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type DomainItem = {
	label: string;
	value: string;
};

export function domainsToItems(raw: string): DomainItem[] {
	return raw
		.split(',')
		.map((d) => d.trim())
		.filter(Boolean)
		.map((d) => ({label: d, value: d}));
}

export function itemsToDomains(items: DomainItem[]): string {
	return items.map((item) => item.value).join(',');
}
