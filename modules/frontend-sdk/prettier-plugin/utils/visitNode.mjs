/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VISITOR_KEYS as babelVisitorKeys} from '@babel/types';
import {visitorKeys as tsVisitorKeys} from '@typescript-eslint/visitor-keys';

const visitorKeys = {
	...babelVisitorKeys,
	...tsVisitorKeys,
	PropertyDefinition: ['value'],
};

/*
 * This function traverses the AST tree one node at a time. The callback is
 * run from a "bottom-up" perspective. Meaning that it runs the callback on the
 * child node before calling it on the parent node.
 *
 * This also benefits us because as we modify the original content of the file
 * we don't have to account for the new lines added as they will only be "below"
 * the node we are currently at.
 */
export default function visitNode(node, fn) {
	if (!(node !== null && typeof node === 'object')) {
		return node;
	}

	if (Array.isArray(node)) {
		for (let i = node.length - 1; i >= 0; i--) {
			node[i] = visitNode(node[i], fn);
		}

		return node;
	}

	const keys = visitorKeys[node.type] || [];

	for (let i = keys.length - 1; i >= 0; i--) {
		node[keys[i]] = visitNode(node[keys[i]], fn);
	}

	return fn(node) || node;
}
