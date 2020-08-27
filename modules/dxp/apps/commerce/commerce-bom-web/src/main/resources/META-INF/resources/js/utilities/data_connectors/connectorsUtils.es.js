/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const connectors = new Map();
export const connections = new Map();

export function getConnectorById(connectorId) {
	return connectors.get(connectorId);
}

export function getEmittersValues(id) {
	const emitters = Array.from(connections.keys());

	const connectedEmittersIds = emitters.reduce(
		(connectedEmitters, emitterId) => {
			return connections.get(emitterId).includes(id)
				? connectedEmitters.concat(emitterId)
				: connectedEmitters;
		},
		[]
	);

	return connectedEmittersIds.reduce(
		(acc, id) => ({
			...acc,
			[id]: connectors.get(id).getValue() || null,
		}),
		{}
	);
}

export function emit(id) {
	const listenersId = connections.get(id);

	if (!listenersId) {
		return null;
	}

	const listeners = listenersId.map((listenerId) =>
		connectors.get(listenerId)
	);

	return listeners.map((listener) =>
		listener.notified ? listener.notified() : null
	);
}

function addConnection(listenerId, emitterId) {
	const addedListeners = connections.get(emitterId) || [];
	connections.set(emitterId, addedListeners.concat(listenerId));
}

function addConnections(listenerId, connectorsId) {
	return connectorsId.map((emitterId) =>
		addConnection(listenerId, emitterId)
	);
}

export function subscribe(id, emittersIds, getValue, notified) {
	if (emittersIds) {
		addConnections(id, emittersIds);
	}

	return connectors.set(id, {
		getValue,
		notified,
	});
}

export function getStore() {
	return connectors;
}

const dataConnectorOrchestrator = {
	connectors,
	getConnectorById,
	getStore,
	subscribe,
};

window.dataConnectorOrchestrator = dataConnectorOrchestrator;
