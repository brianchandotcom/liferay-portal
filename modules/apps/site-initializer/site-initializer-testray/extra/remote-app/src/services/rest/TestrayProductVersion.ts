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

import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {SearchBuilder} from '../../util/search';
import fetcher from '../fetcher';
import {APIResponse, TestrayProductVersion} from './types';

type ProductVersion = typeof yupSchema.productVersion.__outputType;

const nestedFieldsParam = 'nestedFields=project';
const productVersionsResource = `/productversions?${nestedFieldsParam}`;

const adapter = ({
	name,
	projectId: r_projectToProductVersions_c_projectId,
}: ProductVersion) => ({
	name,
	r_projectToProductVersions_c_projectId,
});

const validateProductVersionName = async (
	productVersion: ProductVersion,
	productVersionId?: number
): Promise<void | Error> => {
	const filter = new SearchBuilder()
		.eq('name', productVersion.name)
		.and()
		.eq('projectId', productVersion.projectId as string)
		.build();

	const response = await fetcher<APIResponse<TestrayProductVersion>>(
		`/productversions?filter=${filter}`
	);

	const checkProductVersionId = productVersionId
		? response?.items[0]?.id !== productVersionId
		: true;

	if (response?.totalCount && checkProductVersionId) {
		throw new Error(
			i18n.sub('the-x-name-already-exists', 'product-version')
		);
	}
};

const createProductVersion = async (productVersion: ProductVersion) => {
	await validateProductVersionName(productVersion);

	return fetcher.post('/productversions', adapter(productVersion));
};

const updateProductVersion = async (
	id: number,
	productVersion: ProductVersion
) => {
	await validateProductVersionName(productVersion, id);

	return fetcher.put(`/productversions/${id}`, adapter(productVersion));
};

const getProductVersionQuery = (productVersionId: number | string) =>
	`/productversions/${productVersionId}?${nestedFieldsParam}`;

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
