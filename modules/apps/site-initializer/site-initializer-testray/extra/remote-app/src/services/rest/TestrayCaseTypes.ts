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

type CaseType = typeof yupSchema.caseType.__outputType;

const adapter = ({name}: CaseType) => ({
	name,
});

const createCaseTypes = (casetype: CaseType) =>
	fetcher.post('/casetypes', adapter(casetype));

const updateCaseTypes = (id: number, casetype: CaseType) =>
	fetcher.put(`/casetypes/${id}`, adapter(casetype));

export {createCaseTypes, updateCaseTypes};
