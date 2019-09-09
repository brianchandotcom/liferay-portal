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

import {createContext, useEffect, useState} from 'react';
import {
	durationAsMilliseconds,
	formatHours,
	getDurationValues
} from '../../../shared/util/duration.es';
import {START_NODE_KEYS} from '../Constants.es';

const useSLA = (fetchClient, slaId, processId) => {
	const [sla, setSLA] = useState({
		calendarKey: null,
		days: null,
		description: '',
		hours: '',
		name: '',
		pauseNodeKeys: {
			nodeKeys: [],
			status: 0
		},
		processId,
		startNodeKeys: {
			nodeKeys: [],
			status: 0
		},
		status: 0,
		stopNodeKeys: {
			nodeKeys: [],
			status: 0
		}
	});

	const changeNodesKeys = (type, nodeKeys, callback) => selectedNodeKeys => {
		const filteredNodeKeys = nodeKeys.filter(({compositeId}) =>
			selectedNodeKeys.includes(`${compositeId}`)
		);
		const attrString =
			type === START_NODE_KEYS ? 'startNodeKeys' : 'stopNodeKeys';

		setSLA({...sla, ...{[attrString]: {nodeKeys: filteredNodeKeys}}});
		callback(filteredNodeKeys);
	};

	const changePauseNodes = (pauseNodeKeys, callback) => nodeKeys => {
		const filteredPauseNodeKeys = pauseNodeKeys.filter(({compositeId}) =>
			nodeKeys.includes(`${compositeId}`)
		);

		setSLA({...sla, ...{pauseNodeKeys: {nodeKeys: filteredPauseNodeKeys}}});
		callback(filteredPauseNodeKeys);
	};

	const changeValue = (name, value) => {
		if (name === 'days' && isNaN(value)) {
			value = 0;
		}

		setSLA(oldSla => ({...oldSla, ...{[name]: value}}));
	};

	const fetchSLA = useCallback(slaId => {
		fetchClient.get(`/slas/${slaId}`).then(({data}) => {
			const {
				calendarKey,
				description = '',
				duration,
				name,
				status
			} = data;

			const {days, hours, minutes} = getDurationValues(duration);

			const formattedHours = formatHours(hours, minutes);

			const [pauseNodeKeys, startNodeKeys, stopNodeKeys] = [
				data.pauseNodeKeys,
				data.startNodeKeys,
				data.stopNodeKeys
			].map(node => {
				if (!node || !node.nodeKeys) {
					return {nodeKeys: []};
				}

				return node;
			});

			setSLA({
				calendarKey,
				days,
				description,
				hours: formattedHours,
				name,
				pauseNodeKeys,
				startNodeKeys,
				status,
				stopNodeKeys
			});
		});
	});

	const filterNodeTagIds = (nodes, nodeKeys = []) => {
		return nodes
			.filter(({compositeId}) =>
				nodeKeys.find(
					node => `${node.id}:${node.executionType}` === compositeId
				)
			)
			.map(({compositeId}) => `${compositeId}`);
	};

	const pauseNodeTagIds = (pauseNodes, pauseNodeKeys) => {
		const nodeKeys = pauseNodeKeys || [];

		return pauseNodes
			.filter(({id}) => nodeKeys.find(node => node.id == id))
			.map(({compositeId}) => `${compositeId}`);
	};

	const resetNodes = () => {
		setSLA({
			pauseNodeKeys: {
				nodeKeys: [],
				status: 0
			},
			startNodeKeys: {
				nodeKeys: [],
				status: 0
			},
			stopNodeKeys: {
				nodeKeys: [],
				status: 0
			}
		});
	};

	const saveSLA = (processId, slaId, calendarId) => {
		const {
			calendarKey = calendarId,
			days,
			description,
			hours,
			name,
			pauseNodeKeys,
			startNodeKeys,
			stopNodeKeys
		} = sla;

		const duration = durationAsMilliseconds(days, hours);

		let submit = body =>
			fetchClient.post(`/processes/${processId}/slas`, body);

		if (slaId) {
			submit = body => fetchClient.put(`/slas/${slaId}`, body);
		}

		return submit({
			calendarKey,
			description,
			duration,
			name,
			pauseNodeKeys: {
				nodeKeys: pauseNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id
				})),
				status: 0
			},
			processId,
			startNodeKeys: {
				nodeKeys: startNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id
				})),
				status: 0
			},
			status: 0,
			stopNodeKeys: {
				nodeKeys: stopNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id
				})),
				status: 0
			}
		});
	};

	useEffect(() => {
		if (slaId) {
			fetchSLA(slaId);
		}
	}, [fetchSLA, slaId]);

	return {
		changeNodesKeys,
		changePauseNodes,
		changeValue,
		fetchSLA,
		filterNodeTagIds,
		pauseNodeTagIds,
		resetNodes,
		saveSLA,
		sla
	};
};

const SLA = createContext({});

export {SLA, useSLA};
