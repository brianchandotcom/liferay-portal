/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import createBackend from 'react-dnd-html5-backend';

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
					const backend = createBackend(manager, context);

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
