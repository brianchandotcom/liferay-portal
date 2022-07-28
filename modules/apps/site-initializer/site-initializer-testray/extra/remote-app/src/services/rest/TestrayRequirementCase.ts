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

import {TestrayCase, TestrayRequirement} from '../../graphql/queries';
import fetcher from '../fetcher';

type TestrayRequirementCase = {
	case: TestrayCase;
	id: number;
	requirement: TestrayRequirement;
};

const nestedFieldsParam =
	'nestedFields=component,team.project&nestedFieldsDepth=2';

const requirementsCasesResource = `/requirementscaseses?${nestedFieldsParam}`;

const createRequirementCaseBatch = (
	requirements: {caseId: number; requirementId: number}[]
) => {
	if (requirements.length <= 20) {
		return Promise.allSettled(
			requirements.map(
				({
					caseId: r_caseToRequirementsCases_c_caseId,
					requirementId: r_requiremenToRequirementsCases_c_requirementId,
				}) =>
					fetcher.post('/requirementscaseses', {
						name:
							r_caseToRequirementsCases_c_caseId +
							r_requiremenToRequirementsCases_c_requirementId,
						r_caseToRequirementsCases_c_caseId,
						r_requiremenToRequirementsCases_c_requirementId,
					})
			)
		);
	}

	return fetcher.post(
		'/requirementscaseses/batch',
		requirements.map(
			({
				caseId: r_caseToRequirementsCases_c_caseId,
				requirementId: r_requiremenToRequirementsCases_c_requirementId,
			}: any) => ({
				name:
					r_caseToRequirementsCases_c_caseId +
					r_requiremenToRequirementsCases_c_requirementId,
				r_caseToRequirementsCases_c_caseId,
				r_requiremenToRequirementsCases_c_requirementId,
			})
		)
	);
};

export type {TestrayRequirementCase};

export {createRequirementCaseBatch, requirementsCasesResource};
