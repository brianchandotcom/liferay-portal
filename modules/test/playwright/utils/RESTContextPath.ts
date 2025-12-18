/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Mirrors Liferay's TextFormatter.formatPlural logic
 */
export function formatPlural(s: string): string {
	if (!s) {
		return s;
	}

	if (
		s.endsWith('ch') ||
		s.endsWith('s') ||
		s.endsWith('sh') ||
		s.endsWith('x') ||
		s.endsWith('z')
	) {
		return `${s}es`;
	}

	if (
		s.endsWith('y') &&
		!s.endsWith('ay') &&
		!s.endsWith('ey') &&
		!s.endsWith('oy') &&
		!s.endsWith('uy')
	) {
		return `${s.slice(0, -1)}ies`;
	}

	return `${s}s`;
}

/**
 * Mirrors ObjectDefinitionUtil.getShortName logic
 */
export function getShortName(name: string): string {
	if (name.startsWith('C_')) {
		return name.substring(2);
	}

	return name;
}

/**
 * Equivalent of:
 * "/c/" + TextFormatter.formatPlural(StringUtil.toLowerCase(getShortName()))
 */
export function getRESTContextPath(name: string): string {
	const shortName = getShortName(name).toLowerCase();

	return `/c/${formatPlural(shortName)}`;
}
