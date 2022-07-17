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

type TestraySuite = {
	caseParameters: string;
	creator: {
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	description: string;
	id: number;
	name: string;
	type: string;
};

type Suite = typeof yupSchema.suite.__outputType & {projectId: number};

const adapter = ({
	autoanalyze,
	caseParameters,
	description,
	id,
	name,
	projectId: r_projectToSuites_c_projectId,
	smartSuite,
}: Suite) => ({
	autoanalyze,
	caseParameters,
	description,
	id,
	name,
	r_projectToSuites_c_projectId,
	smartSuite,
});

const createSuite = (suite: Suite) => fetcher.post('/suites', adapter(suite));

const updateSuite = (id: number, suite: Suite) =>
	fetcher.put(`/suites/${id}`, adapter(suite));

export type {TestraySuite};

export {createSuite, updateSuite};
