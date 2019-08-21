import React, {createContext, useContext, useEffect, useState} from 'react';
import {AppContext} from '../../../AppContext';
import {compareArrays} from '../../../../shared/util/array';
import {ErrorContext} from '../../../../shared/components/request/Error';
import {LoadingContext} from '../../../../shared/components/request/Loading';
import {usePrevious} from '../../../../shared/util/hooks';

const useProcessStep = (processId, processStepKeys) => {
	const {client} = useContext(AppContext);
	const [processSteps, setProcessSteps] = useState([]);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		client
			.get(`/processes/${processId}/tasks?page=0&pageSize=0`)
			.then(({data}) => {
				const items = data.items || [];

				const processSteps = items.map(processStep => ({
					...processStep,
					active: processStepKeys.includes(processStep.key)
				}));

				setProcessSteps(processSteps);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	const getSelectedProcessSteps = () => {
		if (!processSteps || !processSteps.length) {
			return null;
		}

		return processSteps.filter(item => item.active);
	};

	const updateData = () => {
		setProcessSteps(
			processSteps.map(processStep => ({
				...processStep,
				active: processStepKeys.includes(processStep.key)
			}))
		);
	};

	useEffect(fetchData, []);

	const previousKeys = usePrevious(processStepKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(processStepKeys, previousKeys);

		if (filterChanged && processSteps.length) {
			updateData();
		}
	}, [processStepKeys]);

	return {
		getSelectedProcessSteps,
		processSteps
	};
};

const ProcessStepContext = createContext(null);

const ProcessStepProvider = ({children, processId, processStepKeys}) => {
	return (
		<ProcessStepContext.Provider
			value={useProcessStep(processId, processStepKeys)}
		>
			{children}
		</ProcessStepContext.Provider>
	);
};

export {ProcessStepContext, ProcessStepProvider, useProcessStep};
