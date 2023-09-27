/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useCalendars} from './hooks/useCalendars.es';
import {useSLAFormState} from './hooks/useSLAFormState.es';
import {useSLANodes} from './hooks/useSLANodes.es';

const SLAFormContext = createContext({});

function SLAFormPageProvider({children, id, processId}) {
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

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [id]);

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
}

export {SLAFormContext};
export default SLAFormPageProvider;
