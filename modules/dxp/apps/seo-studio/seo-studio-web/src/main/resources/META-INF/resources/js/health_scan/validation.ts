/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const INVALID_PATH_CHARACTERS = /[\s<>{}]/;

export function isValidPath(path: string): boolean {
	if (!path.startsWith('/')) {
		return false;
	}

	return !INVALID_PATH_CHARACTERS.test(path);
}

export function isValidPathList(value: string): boolean {
	const entries = value
		.split(',')
		.map((entry) => entry.trim())
		.filter(Boolean);

	if (!entries.length) {
		return false;
	}

	return entries.every(isValidPath);
}

export function getPathError(value: string): string | undefined {
	if (!value.trim()) {
		return Liferay.Language.get('this-field-is-required');
	}

	if (!isValidPathList(value)) {
		return Liferay.Language.get('invalid-path-format');
	}

	return undefined;
}
