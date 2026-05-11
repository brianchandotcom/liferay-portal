/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getSettingValue(
	settings: NameValueObject[] | undefined,
	name: string
): string | undefined {
	return settings?.find((setting) => setting.name === name)?.value;
}

export function setSettingValue(
	settings: NameValueObject[] | undefined,
	name: string,
	value: string
): NameValueObject[] {
	const current = settings ?? [];
	const index = current.findIndex((setting) => setting.name === name);

	if (index === -1) {
		return [...current, {name, value}];
	}

	const next = [...current];

	next[index] = {name, value};

	return next;
}
