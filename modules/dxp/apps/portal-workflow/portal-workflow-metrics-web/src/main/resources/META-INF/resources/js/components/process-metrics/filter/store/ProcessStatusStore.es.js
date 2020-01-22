/* eslint-disable react-hooks/exhaustive-deps */
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

import {usePrevious} from 'frontend-js-react-web';
import React, {createContext, useEffect, useState} from 'react';

import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents.es';
import {compareArrays} from '../../../../shared/util/array.es';

const processStatusConstants = {
	completed: 'Completed',
	pending: 'Pending'
};

const completedStatus = {
	key: processStatusConstants.completed,
	name: Liferay.Language.get('completed')
};

const pendingStatus = {
	key: processStatusConstants.pending,
	name: Liferay.Language.get('pending')
};

const useProcessStatus = processStatusKeys => {
	const [processStatuses, setProcessStatuses] = useState([]);

	const fetchData = () => {
		const processStatuses = [completedStatus, pendingStatus].map(
			processStatus => ({
				...processStatus,
				active: processStatusKeys.includes(processStatus.key)
			})
		);

		setProcessStatuses(processStatuses);
	};

	const getSelectedProcessStatuses = fallbackKeys => {
		if (!processStatuses || !processStatuses.length) {
			return buildFallbackItems(fallbackKeys);
		}

		return processStatuses.filter(item => item.active);
	};

	const isCompletedStatusSelected = fallbackKeys => {
		const selectedProcessStatuses = getSelectedProcessStatuses(
			fallbackKeys
		);

		if (selectedProcessStatuses) {
			return selectedProcessStatuses
				.map(processStatus => processStatus.key)
				.includes(completedStatus.key);
		}

		return false;
	};

	const updateData = () => {
		setProcessStatuses(
			processStatuses.map(processStatus => ({
				...processStatus,
				active: processStatusKeys.includes(processStatus.key)
			}))
		);
	};

	useEffect(fetchData, []);

	const previousKeys = usePrevious(processStatusKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, processStatusKeys);

		if (filterChanged && processStatuses.length) {
			updateData();
		}
	}, [processStatusKeys]);

	return {
		getSelectedProcessStatuses,
		isCompletedStatusSelected,
		processStatuses
	};
};

const ProcessStatusContext = createContext(null);

const ProcessStatusProvider = ({children, processStatusKeys}) => {
	return (
		<ProcessStatusContext.Provider
			value={useProcessStatus(processStatusKeys)}
		>
			{children}
		</ProcessStatusContext.Provider>
	);
};

export {
	processStatusConstants,
	ProcessStatusContext,
	ProcessStatusProvider,
	useProcessStatus
};
