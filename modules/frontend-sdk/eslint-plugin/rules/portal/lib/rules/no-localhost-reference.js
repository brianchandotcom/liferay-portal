/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

const DESCRIPTION = 'do not make explicit references to localhost.';

module.exports = {
	create(context) {
		const filePath = context.getFilename();
		const filename = path.basename(filePath);

		if (
			filename.includes('jest') ||
			filePath.includes('dev') ||
			filePath.includes('test') ||
			filePath.includes('webpack')
		) {
			return {};
		}

		return {
			Literal(node) {
				if (
					node &&
					node.value &&
					typeof node.value === 'string' &&
					node.value.indexOf('localhost') !== -1
				) {
					context.report({
						message: DESCRIPTION,
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/issues/644',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};
