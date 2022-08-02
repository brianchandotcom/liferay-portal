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
import {APIResponse, TestrayProductVersion} from './types';

type ProductVersion = typeof yupSchema.team.__outputType;

const adapter = ({
	name,
	projectId: r_projectToProductVersions_c_projectId,
}: ProductVersion) => ({
	name,
	r_projectToProductVersions_c_projectId,
});

const createProductVersion = (team: ProductVersion) =>
	fetcher.post('/productversions', adapter(team));

const updateProductVersion = (id: number, team: ProductVersion) =>
	fetcher.put(`/productversions/${id}`, adapter(team));

const nestedFieldsParam = 'nestedFields=project';

const productVersionsResource = `/productversions?${nestedFieldsParam}`;

const getProductVersionQuery = (teamId: number | string) =>
	`/productversions/${teamId}?${nestedFieldsParam}`;

const getProductVersionTransformData = (
	testrayProductVersion: TestrayProductVersion
): TestrayProductVersion => ({
	...testrayProductVersion,
	project: testrayProductVersion?.r_projectToProductVersions_c_project,
});

const getProductVersionsTransformData = (
	response: APIResponse<TestrayProductVersion>
) => ({
	...response,
	items: response?.items?.map(getProductVersionTransformData),
});

export {
	productVersionsResource,
	createProductVersion,
	updateProductVersion,
	getProductVersionQuery,
	getProductVersionTransformData,
	getProductVersionsTransformData,
};
