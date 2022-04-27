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

import {Security} from '../security';
import {
	TestrayActions,
	TestrayEntities,
	TestrayRoles,
} from '../security/RolePermission';

export type ActionMap<M extends {[index: string]: any}> = {
	[Key in keyof M]: M[Key] extends undefined
		? {
				type: Key;
		  }
		: {
				payload: M[Key];
				type: Key;
		  };
};

export enum DescriptionType {
	MARKDOWN = 'markdown',
	PLAINTEXT = 'plaintext',
}

export enum SortOption {
	ASC = 'asc',
	DESC = 'desc',
}

export type Action<T = any> = {
	action?: (item: T) => void;
	name: string;
	permission?: keyof typeof TestrayActions | boolean;
};

export type SecurityPermissions = {
	permissions: PermissionCheck;
	security: Security;
};

export type Actions = keyof typeof TestrayActions;
export type Entity = keyof TestrayEntities;
export type Roles = keyof typeof TestrayRoles;
export type PermissionCheck = Partial<
	{
		[key in Actions]: boolean;
	}
>;
