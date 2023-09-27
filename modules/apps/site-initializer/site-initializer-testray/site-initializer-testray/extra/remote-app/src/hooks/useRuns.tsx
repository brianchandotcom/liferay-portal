/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';
import {useSearchParams} from 'react-router-dom';

import {RunId, TestrayContext, TestrayTypes} from '../context/TestrayContext';

const useRuns = () => {
	const [{compareRuns}, dispatch] = useContext(TestrayContext);
	const [searchParams] = useSearchParams();

	const setRunA = useCallback(
		(runA: RunId) =>
			dispatch({payload: runA, type: TestrayTypes.SET_RUN_A}),
		[dispatch]
	);

	const setRunB = useCallback(
		(runB: RunId) =>
			dispatch({payload: runB, type: TestrayTypes.SET_RUN_B}),
		[dispatch]
	);

	return {
		compareRuns: {...compareRuns, runId: Number(searchParams.get('runId'))},
		setRunA,
		setRunB,
	};
};

export default useRuns;
