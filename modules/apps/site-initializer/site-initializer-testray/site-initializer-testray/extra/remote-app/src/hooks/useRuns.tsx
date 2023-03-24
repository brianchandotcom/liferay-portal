/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useCallback, useContext} from 'react';

import {RunId, TestrayContext, TestrayTypes} from '../context/TestrayContext';

const useRuns = () => {
	const [{compareRuns}, dispatch] = useContext(TestrayContext);

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
		compareRuns,
		setRunA,
		setRunB,
	};
};

export default useRuns;
