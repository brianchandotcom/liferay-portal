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

import {KeyedMutator} from 'swr';

import fetcher from '../services/fetcher';
import {Liferay} from '../services/liferay';
import {TestrayCaseResult} from '../services/rest';
import {TEST_STATUS} from '../util/constants';

const useAssignCaseResult = (mutate?: KeyedMutator<any>) => {
	const onAssignToFetch = async (
		caseResult: TestrayCaseResult,
		userId: number | string | null
	) => {
		const data = {
			dueStatus: caseResult.dueStatus,
			r_userToCaseResults_userId: userId,
			startDate: caseResult.startDate,
		};

		const response = await fetcher.put(
			`/caseresults/${caseResult.id}`,
			data
		);

		if (mutate) {
			mutate(response);
		}

		return response;
	};

	const onAssignToMeFetch = async (caseResult: TestrayCaseResult) => {
		const data = {
			dueStatus: TEST_STATUS['In Progress'],
			r_userToCaseResults_userId: Liferay.ThemeDisplay.getUserId(),
			startDate: caseResult.startDate,
		};

		const response = await fetcher.put(
			`/caseresults/${caseResult.id}`,
			data
		);

		if (mutate) {
			mutate(response);
		}

		return response;
	};

	const onRemoveAssignFetch = async (caseResult: TestrayCaseResult) => {
		const data = {
			dueStatus: TEST_STATUS.Untested,
			r_userToCaseResults_userId: 0,
			startDate: null as any,
		};

		const response = await fetcher.put(
			`/caseresults/${caseResult.id}`,
			data
		);

		if (mutate) {
			mutate(response);
		}

		return response;
	};

	return {
		onAssignToFetch,
		onAssignToMeFetch,
		onRemoveAssignFetch,
	};
};

export default useAssignCaseResult;
