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
import {APIResponse, TestrayFactorOptions} from './types';

type FactorOption = typeof yupSchema.factorOption.__outputType;

const adapter = ({
	factorCategoryId: r_factorCategoryToOptions_c_factorCategory,
	name,
}: FactorOption) => ({
	name,
	r_factorCategoryToOptions_c_factorCategory,
});

const createFactorOption = (factorOption: FactorOption) =>
	fetcher.post(`/factoroptions`, adapter(factorOption));

const updateFactorOption = (id: number, factorOption: FactorOption) =>
	fetcher.put(`/factoroptions/${id}`, adapter(factorOption));

const nestedFieldsParam = 'nestedFields=factorCategory';

const factorOptionResource = `/factoroptions?${nestedFieldsParam}`;

const getFactorOptionQuery = (factorCategoryId: number | string) =>
	`/factoroptions/${factorCategoryId}?${nestedFieldsParam}`;

const getFactorOptionTransformData = (
	testrayFactorOption: TestrayFactorOptions
): TestrayFactorOptions => ({
	...testrayFactorOption,
	factorCategory: testrayFactorOption?.r_factorCategoryToOptions_c_factorCategory
		? {...testrayFactorOption.r_factorCategoryToOptions_c_factorCategory}
		: undefined,
});

const getFactorOptionsTransformData = (
	response: APIResponse<TestrayFactorOptions>
) => ({
	...response,
	items: response?.items?.map(getFactorOptionTransformData),
});

export {
	factorOptionResource,
	createFactorOption,
	updateFactorOption,
	getFactorOptionQuery,
	getFactorOptionTransformData,
	getFactorOptionsTransformData,
};

export type {TestrayFactorOptions};
