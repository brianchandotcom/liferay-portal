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
import {APIResponse} from './types';

import type {TestrayBuild} from './types';

type Build = typeof yupSchema.build.__outputType & {projectId: number};

const adapter = ({
	description,
	gitHash,
	name,
	productVersionId: r_productVersionToBuilds_c_productVersionId,
	projectId: r_projectToBuilds_c_projectId,
	promoted,
	routineId: r_routineToBuilds_c_routineId,
	template,
}: Build) => ({
	description,
	gitHash,
	name,
	promoted,
	r_productVersionToBuilds_c_productVersionId,
	r_projectToBuilds_c_projectId,
	r_routineToBuilds_c_routineId,
	template,
});

const nestedFieldsParam = 'nestedFields=productVersion&nestedFieldsDepth=2';

const buildsResource = `/builds?${nestedFieldsParam}`;

const getBuildQuery = (buildId: number | string) =>
	`/builds/${buildId}?${nestedFieldsParam}`;

const getBuildTransformData = (testrayBuild: TestrayBuild): TestrayBuild => {
	return {
		...testrayBuild,
		creator: testrayBuild?.creator || {},
		productVersion: testrayBuild?.r_productVersionToBuilds_c_productVersion,
	};
};

const getBuildsTransformData = (response: APIResponse<TestrayBuild>) => ({
	...response,
	items: response?.items?.map((item) => getBuildTransformData(item)),
});

const createBuild = (build: Build) => fetcher.post('/builds', adapter(build));

const updateBuild = (id: number, build: Partial<Build>) =>
	fetcher.patch(`/builds/${id}`, adapter(build as Build));

export {
	buildsResource,
	createBuild,
	getBuildQuery,
	getBuildTransformData,
	getBuildsTransformData,
	updateBuild,
};
