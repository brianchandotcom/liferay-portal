import React, {createContext, useEffect, useState} from 'react';
import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents';
import {compareArrays} from '../../../../shared/util/array';
import {usePrevious} from '../../../../shared/util/hooks';

const slaStatusConstants = {
	onTime: 'OnTime',
	overdue: 'Overdue',
	untracked: 'Untracked'
};

const onTimeStatus = {
	key: slaStatusConstants.onTime,
	name: Liferay.Language.get('on-time')
};

const overdueStatus = {
	key: slaStatusConstants.overdue,
	name: Liferay.Language.get('overdue')
};

const untrackedStatus = {
	key: slaStatusConstants.untracked,
	name: Liferay.Language.get('untracked')
};

const useSLAStatus = slaStatusKeys => {
	const [slaStatuses, setSLAStatuses] = useState([]);

	const fetchData = () => {
		const slaStatuses = [onTimeStatus, overdueStatus, untrackedStatus].map(
			slaStatus => ({
				...slaStatus,
				active: slaStatusKeys.includes(slaStatus.key)
			})
		);

		setSLAStatuses(slaStatuses);
	};

	const getSelectedSLAStatuses = fallbackKeys => {
		if (!slaStatuses || !slaStatuses.length) {
			return buildFallbackItems(fallbackKeys);
		}

		return slaStatuses.filter(item => item.active);
	};

	const updateData = () => {
		setSLAStatuses(
			slaStatuses.map(slaStatus => ({
				...slaStatus,
				active: slaStatusKeys.includes(slaStatus.key)
			}))
		);
	};

	useEffect(fetchData, []);

	const previousKeys = usePrevious(slaStatusKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, slaStatusKeys);

		if (filterChanged && slaStatuses.length) {
			updateData();
		}
	}, [slaStatusKeys]);

	return {
		getSelectedSLAStatuses,
		slaStatuses
	};
};

const SLAStatusContext = createContext(null);

const SLAStatusProvider = ({children, slaStatusKeys}) => {
	return (
		<SLAStatusContext.Provider value={useSLAStatus(slaStatusKeys)}>
			{children}
		</SLAStatusContext.Provider>
	);
};

export {slaStatusConstants, SLAStatusContext, SLAStatusProvider, useSLAStatus};
