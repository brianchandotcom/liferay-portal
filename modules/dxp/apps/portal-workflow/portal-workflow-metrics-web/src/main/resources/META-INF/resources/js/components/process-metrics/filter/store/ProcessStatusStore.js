import React, {createContext, useEffect, useState} from 'react';
import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents';
import {compareArrays} from '../../../../shared/util/array';
import {usePrevious} from '../../../../shared/util/hooks';

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
