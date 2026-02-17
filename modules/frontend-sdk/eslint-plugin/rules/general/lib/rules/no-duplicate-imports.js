/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const {getSource, isTypeImport} = require('../common/imports');

const DESCRIPTION = 'modules must be imported only once';

module.exports = {
	create(context) {
		const imports = new Set();
		const typeImports = new Set();

		return {
			ImportDeclaration(node) {
				const isType = isTypeImport(node);

				const source = getSource(node);

				if (
					(typeImports.has(source) && isType) ||
					(imports.has(source) && !isType)
				) {
					context.report({
						message: `${DESCRIPTION} (duplicate import: ${JSON.stringify(
							source
						)})`,
						node,
					});
				}
				else {
					if (isType) {
						typeImports.add(source);
					}
					else {
						imports.add(source);
					}
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
				'https://github.com/liferay/liferay-frontend-guidelines/issues/60',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};
