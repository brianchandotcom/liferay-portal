/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = function (node) {
	let parent = node.parent;

	while (parent && parent.type !== 'Program') {
		if (
			parent.type === 'CallExpression' &&
			parent.callee.type === 'MemberExpression' &&
			(parent.callee.object.name === 'AUI' ||
				(parent.callee.object.type === 'CallExpression' &&
					parent.callee.object.callee.name === 'AUI'))
		) {
			return true;
		}

		parent = parent.parent;
	}

	return false;
};
