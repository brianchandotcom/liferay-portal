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

import {TestrayCaseResult} from '../graphql/queries';
import fetcher from '../services/fetcher';
import {Liferay} from '../services/liferay';
import {TEST_STATUS} from '../util/constants';

const useAssignCaseResult = () => {
	const onAssignToFetch = async (
		caseResult: TestrayCaseResult,
		userId: number | string | null
	) => {
		const url = `/caseresults/${caseResult.id}`;

		const data = {
			dueStatus: caseResult.dueStatus,
			r_userToCaseResults_userId: userId,
			startDate: caseResult.startDate,
		};

		return fetcher.put(url, data);
	};

	const onAssignToMeFetch = async (caseResult: TestrayCaseResult) => {
		const url = `/caseresults/${caseResult.id}`;

		const data = {
			dueStatus: TEST_STATUS['In Progress'],
			r_userToCaseResults_userId: Liferay.ThemeDisplay.getUserId(),
			startDate: caseResult.startDate,
		};

		return fetcher.put(url, data);
	};

	const onRemoveAssignFetch = async (caseResult: TestrayCaseResult) => {
		const url = `/caseresults/${caseResult.id}`;

		const data = {
			dueStatus: TEST_STATUS.Untested,
			r_userToCaseResults_userId: 0,
			startDate: null as any,
		};

		return fetcher.put(url, data);
	};

	return {
		onAssignToFetch,
		onAssignToMeFetch,
		onRemoveAssignFetch,
	};
};

export default useAssignCaseResult;
