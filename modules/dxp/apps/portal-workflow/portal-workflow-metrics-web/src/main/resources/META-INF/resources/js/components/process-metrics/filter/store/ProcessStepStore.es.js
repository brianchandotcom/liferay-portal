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

import React, {createContext, useContext, useEffect, useState} from 'react';
import {AppContext} from '../../../AppContext.es';
import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents.es';
import {compareArrays} from '../../../../shared/util/array.es';
import {ErrorContext} from '../../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../../shared/components/request/Loading.es';
import {usePrevious} from '../../../../shared/util/hooks.es';

const useProcessStep = (processId, processStepKeys) => {
	const {client} = useContext(AppContext);
	const [processSteps, setProcessSteps] = useState([]);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		return client
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

	const getSelectedProcessSteps = fallbackKeys => {
		if (!processSteps || !processSteps.length) {
			return buildFallbackItems(fallbackKeys);
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

	useEffect(() => fetchData(), []);

	const previousKeys = usePrevious(processStepKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, processStepKeys);

		if (filterChanged && processSteps.length) {
			updateData();
		}
	}, [processStepKeys]);

	return {
		fetchData,
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
