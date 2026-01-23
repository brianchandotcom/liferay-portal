/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AssigneeValue} from '@liferay/object-dynamic-data-mapping-form-field-type';

import {DISPLAY_TYPES} from './constants';

export type IDisplayType = (typeof DISPLAY_TYPES)[number];

export interface ITaskSchema {
	description: string;
	image?: string;
	labels?: any[];
	link?: string;
	sticker?: string;
	symbol: string;
	title: string;
}

export interface IColumn {
	displayType: IDisplayType;
	icon: {
		color: string;
		name: string;
	};
	key: string;
	name: string;
	tasks: ITask[];
}

export interface IKanbanState {
	'blocked': IColumn;
	'completed': IColumn;
	'in-progress': IColumn;
	'not-started': IColumn;
}

interface ICreator {
	additionalName: string;
	contentType: string;
	externalReferenceCode: string;
	familyName: string;
	givenName: string;
	id: number;
	name: string;
}

export interface IProjectObjectEntry {
	creator: ICreator;
	dateCreated: string;
	dateModified: string;
	defaultLanguageId: string;
	description: string;
	externalReferenceCode: string;
	friendlyUrlPath: string;
	id: number;
	keywords: string[];
	objectEntryFolderExternalReferenceCode: string;
	objectEntryFolderId: number;
	scopeId: number;
	scopeKey: string;
	title: string;
}

export interface IItemsActions {
	data: {
		id: string;
	};
	disable: boolean;
	href: string;
	icon?: string;
	label: string;
	name: string;
	type: string;
}

export interface ITaskObjectEntry {
	assignTo: AssigneeValue;
	cmpProjectToCMPTasks: IProjectObjectEntry;
	creator: ICreator;
	dateCreated: string;
	dateModified: string;
	defaultLanguageId: string;
	description: string;
	dueDate: string;
	externalReferenceCode: string;
	friendlyUrlPath: string;
	id: number;
	keywords: string[];
	objectEntryFolderExternalReferenceCode: string;
	objectEntryFolderId: number;
	r_cmpProjectToCMPTasks_c_cmpProject: any;
	r_cmpProjectToCMPTasks_c_cmpProjectERC: string;
	r_cmpProjectToCMPTasks_c_cmpProjectId: number;
	scopeId: number;
	scopeKey: string;
	state: {
		key: string;
		name: string;
	};
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	systemProperties: {
		scope: {
			externalReferenceCode: string;
			type: string;
		};
		version: {
			number: number;
		};
	};
	title: string;
}

export interface ITask {
	actions: {
		[action: string]: {
			href: string;
			method: string | 'DELETE' | 'GET' | 'PATCH' | 'POST' | 'PUT';
		};
	};
	dateCreated: string;
	dateModified: string;
	embedded: ITaskObjectEntry;
	entryClassName: string;
	score: number;
}
