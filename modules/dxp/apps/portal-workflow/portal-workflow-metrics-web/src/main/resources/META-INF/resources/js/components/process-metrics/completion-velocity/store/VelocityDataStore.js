import {AppContext} from '../../../AppContext';
import React, {createContext, useContext, useEffect, useState} from 'react';
import {ErrorContext} from '../../../../shared/components/request/Error';
import {LoadingContext} from '../../../../shared/components/request/Loading';
import {TimeRangeContext} from './TimeRangeStore';
import {VelocityUnitContext} from './VelocityUnitStore';

const useVelocityData = processId => {
	const {client} = useContext(AppContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {getSelectedVelocityUnit} = useContext(VelocityUnitContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [velocityData, setVelocityData] = useState();

	const velocityTimeRange = getSelectedTimeRange();
	const velocityUnit = getSelectedVelocityUnit();

	const fetchData = (processId, dateEnd, dateStart, unitKey) => {
		setError(null);
		setLoading(true);

		client
			.get(
				`/processes/${processId}/metric?dateEnd=${dateEnd.toISOString()}&dateStart=${dateStart.toISOString()}&unit=${unitKey}`
			)
			.then(({data}) => {
				setVelocityData(data);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	useEffect(() => {
		if (
			processId &&
			velocityTimeRange &&
			velocityTimeRange.dateEnd &&
			velocityTimeRange.dateStart &&
			velocityUnit
		)
			fetchData(
				processId,
				velocityTimeRange.dateEnd,
				velocityTimeRange.dateStart,
				velocityUnit.key
			);
	}, [processId, velocityUnit]);

	return {
		velocityData
	};
};

const VelocityDataContext = createContext();

const VelocityDataProvider = ({children, processId}) => {
	return (
		<VelocityDataContext.Provider value={useVelocityData(processId)}>
			{children}
		</VelocityDataContext.Provider>
	);
};

export {VelocityDataProvider, VelocityDataContext, useVelocityData};
