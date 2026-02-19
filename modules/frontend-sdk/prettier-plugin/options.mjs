/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const options = {
	commentIgnoreAfterPatterns: {
		array: true,
		category: 'Format',
		default: [{value: []}], // idk why prettier expects this format. ¯\_(ツ)_/¯
		description:
			'Ignore line after comment node that contains a given pattern',
		since: '1.0.0',
		type: 'string',
	},
	commentIgnoreBeforePatterns: {
		array: true,
		category: 'Format',
		default: [{value: []}], // idk why prettier expects this format. ¯\_(ツ)_/¯
		description:
			'Ignore new line before comment node that contains a given pattern',
		since: '1.0.0',
		type: 'string',
	},
	commentIgnorePatterns: {
		array: true,
		category: 'Format',
		default: [{value: []}], // idk why prettier expects this format. ¯\_(ツ)_/¯
		description:
			'Ignore new lines line before and after comment nodes that contain a given pattern',
		since: '1.0.0',
		type: 'string',
	},
};
