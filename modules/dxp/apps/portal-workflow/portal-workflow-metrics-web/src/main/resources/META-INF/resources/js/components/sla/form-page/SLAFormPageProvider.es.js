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

import React, {createContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useCalendars} from './hooks/useCalendars.es';
import {useSLAFormState} from './hooks/useSLAFormState.es';
import {useSLANodes} from './hooks/useSLANodes.es';

const SLAFormContext = createContext({});

const SLAFormPageProvider = ({children, id, processId}) => {
	const [errors, setErrors] = useState({});

	const {fetchCalendars, ...calendarsData} = useCalendars();
	const {fetchNodes, ...SLANodes} = useSLANodes(processId);
	const {fetchSLA, ...SLAFormState} = useSLAFormState({
		errors,
		id,
		processId,
		setErrors,
	});

	const promises = useMemo(() => {
		const promises = [fetchCalendars(), fetchNodes()];

		if (id) {
			promises.push(fetchSLA());
		}

		return promises;
	}, [fetchCalendars, fetchNodes, fetchSLA, id]);

	return (
		<PromisesResolver promises={promises}>
			<SLAFormContext.Provider
				value={{
					...calendarsData,
					errors,
					setErrors,
					...SLAFormState,
					...SLANodes,
				}}
			>
				{children}
			</SLAFormContext.Provider>
		</PromisesResolver>
	);
};

export {SLAFormContext};
export default SLAFormPageProvider;
