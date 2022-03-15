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

import {gql} from '@apollo/client';

import {TestrayCaseType} from './testrayCaseType';
import {TestrayComponent} from './testrayComponent';
import {TestrayRequirement} from './testrayRequirement';
import {TestrayTeam} from './testrayTeam';

export type TestraySelectCasesParameter = {
	caseTypes: {
		items: TestrayCaseType[];
	};
	components: {
		items: TestrayComponent[];
	};
	requirements: {
		items: TestrayRequirement[];
	};
	teams: {
		items: TestrayTeam[];
	};
};

export type SelectCasesParameters = {
	c: TestraySelectCasesParameter;
};

export const getSelectCasesParameters = gql`
	query getSelectCasesParameters($pageSize: Int = 20) {
		c {
			caseTypes(pageSize: $pageSize) {
				items {
					name
					id: caseTypeId
				}
			}

			components(pageSize: $pageSize) {
				items {
					name
					id: componentId
				}
			}

			requirements(pageSize: $pageSize) {
				items {
					key
					summary
					id: requirementId
				}
			}

			teams(pageSize: $pageSize) {
				items {
					name
					id: teamId
				}
			}
		}
	}
`;
