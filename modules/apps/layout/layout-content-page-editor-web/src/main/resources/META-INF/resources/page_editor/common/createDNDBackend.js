/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HTML5Backend} from 'react-dnd-html5-backend';

export default function createDNDBackend(manager, mainContext) {

	/**
	 * @type {Set<Window>}
	 */
	const contexts = new Set();

	/**
	 * @type {Map<Window, {backend: object, nodes: Map<string, HTMLElement>}>}
	 */
	const connections = new Map();

	const handleDragAndDropEvent = (methodName) => (itemId, node, options) => {
		contexts.forEach((context) => {
			if (context.document.body && context.document.body.contains(node)) {
				const connection = connections.get(context);

				if (connection.nodes.get(itemId) !== node) {
					connection.nodes.set(itemId, node);
					connection.backend[methodName](itemId, node, options);
				}
			}
		});
	};

	return {
		connectDragPreview: handleDragAndDropEvent('connectDragPreview'),
		connectDragSource: handleDragAndDropEvent('connectDragSource'),
		connectDropTarget: handleDragAndDropEvent('connectDropTarget'),

		setup() {
			contexts.add(mainContext);

			Array.from(mainContext.document.querySelectorAll('iframe')).forEach(
				(iframe) => {
					if (iframe.contentWindow) {
						contexts.add(iframe.contentWindow);
					}
				}
			);

			contexts.forEach((context) => {
				if (!connections.has(context)) {
					const backend = HTML5Backend(manager, context);

					backend.setup();

					connections.set(context, {
						backend,
						nodes: new Map(),
					});
				}
			});
		},

		teardown() {
			contexts.forEach((context) => {
				const connection = connections.get(context);

				if (connection) {
					connection.backend.teardown();
					connections.delete(context);
				}
			});

			contexts.clear();
		},
	};
}
