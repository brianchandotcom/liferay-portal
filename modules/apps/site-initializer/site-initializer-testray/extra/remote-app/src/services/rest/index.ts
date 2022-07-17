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

import fetcher from '../fetcher';

const deleteResource = (resource: RequestInfo) => {
	return fetcher.delete(resource);
};

export {deleteResource};

export * from './TestrayBuild';
export * from './TestrayCase';
export * from './testrayFactorCategory';
export * from './TestrayProject';
export * from './TestrayRequirement';
export * from './TestrayRoutine';
export * from './TestraySuite';
export * from './TestraySuiteCases';
