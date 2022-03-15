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

export type CType<ObjectKey extends string, Query = any> = {
	c: {
		[key in ObjectKey]: Query;
	};
};

export type APIResponse<Query = any> = {
	items: Query[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type TypePagination<ObjectKey extends string, Query = any> = {
	[key in ObjectKey]: APIResponse<Query>;
};

export type CTypePagination<ObjectKey extends string, Query = any> = {
	c: TypePagination<ObjectKey, Query>;
};

export * from './testrayBuild';
export * from './testrayCase';
export * from './testrayCaseResult';
export * from './testrayCaseType';
export * from './testrayComponent';
export * from './testrayFactorCategory';
export * from './testrayFactorOptions';
export * from './testrayProject';
export * from './testrayRequirement';
export * from './testrayRoutine';
export * from './testrayShared';
export * from './testraySuite';
export * from './testrayTask';
