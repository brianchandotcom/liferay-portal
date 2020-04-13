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

import {useCallback, useMemo, useState} from 'react';

import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {usePost} from '../../../../shared/hooks/usePost.es';
import {usePut} from '../../../../shared/hooks/usePut.es';
import {
	durationAsMilliseconds,
	formatHours,
	getDurationValues,
} from '../../../../shared/util/duration.es';
import {START_NODE_KEYS, STOP_NODE_KEYS} from '../SLAFormConstants.es';

const useSLAFormState = ({errors, id, processId, setErrors}) => {
	const [sla, setSLA] = useState({
		calendarKey: null,
		days: null,
		description: '',
		hours: '',
		name: '',
		pauseNodeKeys: {
			nodeKeys: [],
			status: 0,
		},
		processId,
		startNodeKeys: {
			nodeKeys: [],
			status: 0,
		},
		status: 0,
		stopNodeKeys: {
			nodeKeys: [],
			status: 0,
		},
	});

	const body = useMemo(() => {
		const {
			calendarKey,
			days,
			description,
			hours,
			name,
			pauseNodeKeys,
			startNodeKeys,
			stopNodeKeys,
		} = sla;

		const duration = durationAsMilliseconds(days, hours);

		return {
			calendarKey,
			description,
			duration,
			name,
			pauseNodeKeys: {
				nodeKeys: pauseNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id,
				})),
				status: 0,
			},
			processId,
			startNodeKeys: {
				nodeKeys: startNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id,
				})),
				status: 0,
			},
			status: 0,
			stopNodeKeys: {
				nodeKeys: stopNodeKeys.nodeKeys.map(({executionType, id}) => ({
					executionType,
					id,
				})),
				status: 0,
			},
		};
	}, [processId, sla]);

	const parseSLA = ({
		calendarKey,
		description = '',
		duration,
		name,
		status,
		...data
	}) => {
		const {days, hours, minutes} = getDurationValues(duration);

		const formattedHours = formatHours(hours, minutes);

		const [pauseNodeKeys, startNodeKeys, stopNodeKeys] = [
			data.pauseNodeKeys,
			data.startNodeKeys,
			data.stopNodeKeys,
		].map(node => {
			if (!node || !node.nodeKeys) {
				return {nodeKeys: []};
			}

			return node;
		});

		if (status === 2) {
			if (!startNodeKeys.nodeKeys.length) {
				errors[START_NODE_KEYS] = Liferay.Language.get(
					'selected-option-is-no-longer-available'
				);
			}

			if (!stopNodeKeys.nodeKeys.length) {
				errors[STOP_NODE_KEYS] = Liferay.Language.get(
					'selected-option-is-no-longer-available'
				);
			}

			setErrors(errors);
		}

		setSLA({
			calendarKey,
			days,
			description,
			hours: formattedHours,
			name,
			pauseNodeKeys,
			startNodeKeys,
			status,
			stopNodeKeys,
		});
	};

	const {fetchData: fetchSLA} = useFetch({
		callback: parseSLA,
		url: `/slas/${id}`,
	});

	const {postData: createSLA} = usePost({
		body,
		url: `/processes/${processId}/slas`,
	});

	const updateSLA = usePut({body, url: `/slas/${id}`});

	const changeNodesKeys = (type, nodeKeys, callback) => selectedNodes => {
		const selectedNodeKeys = getNodeKeys(selectedNodes);

		const filteredNodeKeys = nodeKeys.filter(({compositeId}) =>
			selectedNodeKeys.includes(`${compositeId}`)
		);
		const attrString =
			type === START_NODE_KEYS ? 'startNodeKeys' : 'stopNodeKeys';

		setSLA({...sla, ...{[attrString]: {nodeKeys: filteredNodeKeys}}});
		callback(filteredNodeKeys);
	};

	const changePauseNodes = (pauseNodeKeys, callback) => nodes => {
		const nodeKeys = getNodeKeys(nodes);

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

	const getNodeTags = (nodes, nodeKeys = []) => {
		return nodes.filter(({compositeId}) =>
			nodeKeys.find(
				node => `${node.id}:${node.executionType}` === compositeId
			)
		);
	};

	const getNodeKeys = nodes => nodes.map(({compositeId}) => compositeId);

	const getPauseNodeTags = (pauseNodes, pauseNodeKeys) => {
		const nodeKeys = pauseNodeKeys || [];

		return pauseNodes.filter(({id}) =>
			nodeKeys.find(node => node.id == id)
		);
	};

	const resetNodes = () => {
		setSLA({
			pauseNodeKeys: {
				nodeKeys: [],
				status: 0,
			},
			startNodeKeys: {
				nodeKeys: [],
				status: 0,
			},
			stopNodeKeys: {
				nodeKeys: [],
				status: 0,
			},
		});
	};

	const saveSLA = useCallback(() => (id ? updateSLA() : createSLA()), [
		createSLA,
		id,
		updateSLA,
	]);

	return {
		changeNodesKeys,
		changePauseNodes,
		changeValue,
		fetchSLA,
		getNodeTags,
		getPauseNodeTags,
		resetNodes,
		saveSLA,
		sla,
	};
};

export {useSLAFormState};
