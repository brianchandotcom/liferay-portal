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

import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';
import {APIResponse, TestrayComponent} from './types';

type Component = typeof yupSchema.component.__outputType;

const adapter = ({
	name,
	projectId: r_projectToComponents_c_projectId,
	teamId: r_teamToComponents_c_teamId,
}: Component) => ({
	name,
	r_projectToComponents_c_projectId,
	r_teamToComponents_c_teamId,
});

const createComponent = (component: Component) =>
	fetcher.post('/components', adapter(component));

const updateComponent = (id: number, component: Component) =>
	fetcher.put(`/components/${id}`, adapter(component));

const nestedFieldsParam = 'nestedFields=project,team';

const componentsResource = `/components?${nestedFieldsParam}`;

const getComponentQuery = (componentId: number | string) =>
	`/components/${componentId}?${nestedFieldsParam}`;

const getComponentTransformData = (
	testrayComponent: TestrayComponent
): TestrayComponent => ({
	...testrayComponent,
	project: testrayComponent?.r_projectToComponents_c_project,
	team: testrayComponent?.r_teamToComponents_c_team,
});

const getComponentsTransformData = (
	response: APIResponse<TestrayComponent>
) => ({
	...response,
	items: response?.items?.map(getComponentTransformData),
});

export {
	componentsResource,
	createComponent,
	updateComponent,
	getComponentQuery,
	getComponentTransformData,
	getComponentsTransformData,
};
