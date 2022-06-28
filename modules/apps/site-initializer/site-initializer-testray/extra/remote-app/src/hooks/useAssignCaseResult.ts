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

import client from '../graphql/apolloClient';
import {UpdateCaseResult} from '../graphql/mutations';
import {TestrayCaseResult} from '../graphql/queries';
import {Liferay} from '../services/liferay';
import {TEST_STATUS} from '../util/constants';

const useAssignCaseResult = () => {
	const onAssignTo = (
		caseResult: TestrayCaseResult,
		userId: number | string | null
	) =>
		client.mutate({
			mutation: UpdateCaseResult,
			variables: {
				CaseResult: {
					dueStatus: caseResult.dueStatus,
					r_userToCaseResults_userId: userId,
					startDate: caseResult.startDate,
				},
				caseResultId: caseResult.id,
			},
		});

	const onAssignToMe = (caseResult: TestrayCaseResult) =>
		onAssignTo(
			{
				...caseResult,
				dueStatus: TEST_STATUS['In Progress'],
				startDate: new Date() as any,
			},
			Liferay.ThemeDisplay.getUserId()
		);

	const onRemoveAssign = (caseResult: TestrayCaseResult) =>
		onAssignTo(
			{
				...caseResult,
				dueStatus: TEST_STATUS.Untested,
				startDate: null as any,
			},
			0
		);

	return {onAssignTo, onAssignToMe, onRemoveAssign};
};

export default useAssignCaseResult;
