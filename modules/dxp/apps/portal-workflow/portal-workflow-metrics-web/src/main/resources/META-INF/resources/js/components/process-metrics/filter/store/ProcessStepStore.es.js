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
import React, {
	createContext,
	useContext,
	useEffect,
	useMemo,
	useState
} from 'react';

import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents.es';
import {ErrorContext} from '../../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../../shared/components/request/Loading.es';
import {compareArrays} from '../../../../shared/util/array.es';
import {AppContext} from '../../../AppContext.es';

const useProcessStep = (processId, processStepKeys, withAllSteps = false) => {
	const {client} = useContext(AppContext);
	const [processSteps, setProcessSteps] = useState([]);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const defaultProcessStep = useMemo(() => processSteps[0], [processSteps]);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		return client
			.get(`/processes/${processId}/tasks?page=0&pageSize=0`)
			.then(({data}) => {
				const items = data.items || [];
				let processSteps = [];

				if (withAllSteps) {
					processSteps.push(getAllStepsItem(processStepKeys));
				}

				processSteps = processSteps.concat(
					items.map(processStep => ({
						...processStep,
						active: processStepKeys.includes(processStep.key)
					}))
				);

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

	useEffect(() => {
		fetchData();
	}, []);

	const previousKeys = usePrevious(processStepKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, processStepKeys);

		if (filterChanged && processSteps.length) {
			updateData();
		}
	}, [processStepKeys]);

	return {
		defaultProcessStep,
		fetchData,
		getSelectedProcessSteps,
		processSteps
	};
};

const getAllStepsItem = processStepKeys => {
	const itemKey = 'allSteps';

	const allStepsItem = {
		active: processStepKeys.includes(itemKey),
		key: itemKey,
		name: Liferay.Language.get('all-steps')
	};

	return allStepsItem;
};

const ProcessStepContext = createContext(null);

const ProcessStepProvider = ({
	children,
	processId,
	processStepKeys,
	withAllSteps = false
}) => {
	return (
		<ProcessStepContext.Provider
			value={useProcessStep(processId, processStepKeys, withAllSteps)}
		>
			{children}
		</ProcessStepContext.Provider>
	);
};

export {ProcessStepContext, ProcessStepProvider, useProcessStep};
