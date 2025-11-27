/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STATUS} from './constants';

export interface ElementDefinitionBase {
	category?: string;
	configuration?: {[key: string]: any};
	icon?: string;
	uiConfiguration?: {[key: string]: any}[];
}

export type SxpElement = {
	createDate?: string;
	description: string;
	description_i18n?: {[key: string]: string};
	elementDefinition: ElementDefinitionBase & {[key: string]: any};
	externalReferenceCode: string;
	id: number;
	modifiedDate?: string;
	readOnly?: boolean;
	schemaVersion?: string;
	title: string;
	title_i18n?: {[key: string]: string};
	type?: number;
	userName?: string;
	version?: string;
};

export type ElementInstances = {
	configurationEntry?: {[key: string]: any};
	id?: number;
	sxpElement: SxpElement;
	uiConfigurationValues: {[key: string]: any};
};

export type IndexFields = {
	languageIdPosition: number;
	name: string;
	type: string;
};

export type Scope = {
	externalReferenceCode: string;
	name: string;
	status: Status;
	type: string;
};

export type SearchableTypes = {
	className: string;
	displayName: string;
	hasSubtype: boolean;
};

export type Sorting = {
	column: React.Key;
	direction: 'ascending' | 'descending';
};

export type Status = STATUS.ACTIVE | STATUS.INACTIVE;
