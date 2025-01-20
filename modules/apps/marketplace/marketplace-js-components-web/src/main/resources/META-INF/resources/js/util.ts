/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const allowedAttributes = {a: ['href', 'target', 'rel']};

const allowedTags = ['a', 'b', 'br', 'em', 'i', 'p', 'span', 'strong'];

export function sanitizeHTML(html: string): string {
	const divElement = document.createElement('div');

	divElement.innerHTML = html;

	function cleanNode(node: ChildNode): void {
		if (node.nodeType === 1) {
			const element = node as HTMLElement;

			if (!allowedTags.includes(element.tagName.toLowerCase())) {
				element.parentNode?.removeChild(element);

				return;
			}

			Array.from(element.attributes).forEach((attr) => {
				const attrName = attr.name.toLowerCase();
				const tagName = element.tagName.toLowerCase();

				if (!(allowedAttributes as any)[tagName]?.includes(attrName)) {
					element.removeAttribute(attrName);
				}
			});
		}

		Array.from(node.childNodes).forEach(cleanNode);
	}

	Array.from(divElement.childNodes).forEach(cleanNode);

	return divElement.innerHTML;
}
