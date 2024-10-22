/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @param {!Element} scriptNode
 * @return {Element}
 */
export default function findEventSourceNode(scriptNode) {
	let previousElement = scriptNode.previousSibling;

	while (
		previousElement !== null &&
		(previousElement.nodeName === '#text' ||
			previousElement.nodeName === '#comment' ||
			previousElement.nodeName === 'SCRIPT')
	) {
		previousElement = previousElement.previousSibling;
	}

	if (previousElement === null) {
		return document.currentScript.parentNode;
	}

	return previousElement;
}
