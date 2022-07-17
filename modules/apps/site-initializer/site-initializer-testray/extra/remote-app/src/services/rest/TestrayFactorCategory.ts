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

type TestrayFactorCategory = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

type FactorCategory = typeof yupSchema.factorCategory.__outputType;

const adapter = ({id, name}: FactorCategory) => ({
	id,
	name,
});

const createFactorCategory = (factorcategory: FactorCategory) =>
	fetcher.post('/factorcategories', adapter(factorcategory));

const updateFactorCategory = (id: number, factorcategory: FactorCategory) =>
	fetcher.put(`/factorcategories/${id}`, adapter(factorcategory));

export type {TestrayFactorCategory};

export {createFactorCategory, updateFactorCategory};
