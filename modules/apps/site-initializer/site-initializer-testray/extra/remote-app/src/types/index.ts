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

import {APIResponse} from '../services/rest';

export type ActionsHookParameter = {isHeaderActions?: boolean};

export type Action<T = any> = {
	action?: (item: T, mutate: KeyedMutator<APIResponse<T> | T>) => void;
	disabled?: boolean;
	icon?: string;
	name: ((item: T) => string) | string;
	permission?: keyof typeof TestrayActions | boolean;
};

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

export type Actions = keyof typeof TestrayActions;

export enum DescriptionType {
	MARKDOWN = 'markdown',
	PLAINTEXT = 'plaintext',
}

export enum SortOption {
	ASC = 'ASC',
	DESC = 'DESC',
}

export type SortDirection = keyof typeof SortOption;

export enum TestrayActions {
	'CREATE',
	'DELETE',
	'INDEX',
	'PERMISSIONS',
	'UPDATE',
}
