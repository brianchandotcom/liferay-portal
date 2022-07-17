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

import {KeyedMutator} from 'swr';

import {Security} from '../security';

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

export enum TestrayActions {
	'CREATE',
	'DELETE',
	'INDEX',
	'PERMISSIONS',
	'UPDATE',
}

export enum SortOption {
	ASC = 'ASC',
	DESC = 'DESC',
}

export type SortDirection = keyof typeof SortOption;

export type Action<T = any> = {
	action?: (item: T, mutate: KeyedMutator<any>) => void;
	disabled?: boolean;
	name: string;
	permission?: keyof typeof TestrayActions | boolean;
};

export type ActionList<T = any> = (item: {
	actions: any;
}) => Action<T>[] | Action<T>[];

export type SecurityPermissions = {
	permissions: PermissionCheck;
	security: Security;
};

export type Actions = keyof typeof TestrayActions;
export type PermissionCheck = Partial<
	{
		[key in Actions]: boolean;
	}
>;
