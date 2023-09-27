/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Toggles disabled class on received element
 * @param nodes
 * @param state
 */
export default function toggleDisabled(nodes, state) {
	if (typeof nodes === 'string') {
		nodes = document.querySelectorAll(nodes);
	}
	else if (nodes._node) {
		nodes = [nodes._node];
	}
	else if (nodes._nodes) {
		nodes = nodes._nodes;
	}
	else if (nodes.nodeType === Node.ELEMENT_NODE) {
		nodes = [nodes];
	}

	nodes.forEach((node) => {
		node.disabled = state;

		if (state) {
			node.classList.add('disabled');
		}
		else {
			node.classList.remove('disabled');
		}
	});
}
