/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

function createPrinter() {
	function main(path) {
		const {node} = path;

		if (node.type === 'FormattedText') {
			return node.body;
		}

		throw new Error(`Unknown node type: ${node?.type}`);
	}

	return {
		print: main,
	};
}

export const printers = {
	'liferay-style-ast': createPrinter(),
};
