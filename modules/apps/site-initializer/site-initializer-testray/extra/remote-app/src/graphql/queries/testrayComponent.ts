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

import {testrayComponentFragment} from '../fragments';
import {TestrayTeam} from './testrayTeam';

export type TestrayComponent = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	originationKey: string;
	status: string;
	team?: TestrayTeam;
};

export const getComponent = gql`
	${testrayComponentFragment}

	query getComponent($componentId: Long!) {
		c {
			Component(componentId: $componentId) {
				...ComponentFragment
			}
		}
	}
`;

export const getComponents = gql`
	${testrayComponentFragment}

	query getComponents($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			components(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...ComponentFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
