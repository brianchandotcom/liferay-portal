/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const ABBREVIATIONS_REPLACEMENTS = new Map([
	['arr', ['array']],
	['btn', ['button']],
	['cb', ['callback']],
	['desc', ['description']],
	['e', ['event', 'error']],
	['el', ['element']],
	['err', ['error']],
	['evt', ['event']],
	['fm', ['form']],
	['fmt', ['format']],
	['idx', ['index']],
	['img', ['image']],
	['lg', ['large']],
	['md', ['medium']],
	['obj', ['object']],
	['opts', ['options']],
	['prj', ['project']],
	['sm', ['small']],
]);

const formatMessage = function (abbreviation, replacements) {
	if (replacements.length > 1) {
		return `Avoid "${abbreviation}" abbreviation, preferred alternatives are [${replacements
			.map((replacement) => `"${replacement}"`)
			.join(',')}]`;
	}
	else {
		return `Avoid "${abbreviation}" abbreviation, preferred alternative is "${replacements}"`;
	}
};

module.exports = {
	create(context) {
		return {
			Identifier(node) {
				if (
					node === node.parent.property ||
					node.parent.type === 'Property'
				) {
					return;
				}

				if (ABBREVIATIONS_REPLACEMENTS.has(node.name)) {
					context.report({
						message: formatMessage(
							node.name,
							ABBREVIATIONS_REPLACEMENTS.get(node.name)
						),
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'Avoid abbreviations',
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/blob/master/guidelines/general/naming.md#avoid-abbreviations',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};
