/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STATUS} from './constants';

export interface IAssetSubtype {
	assetSubtypeExternalReferenceCode: string;
	assetSubtypeLocalizedName: string;
	entryClassName: string;
	groupExternalReferenceCode: string;
	groupLocalizedName: string;
	label: string;
	value: string;
}

export interface ISelectedSubtype {
	label: string;
	value: string;
}

export interface ISelectedItem {
	subtypes: ISelectedSubtype[];
	type: string;
}

export interface IElementDefinition {
	category?: string;
	configuration?: {[key: string]: any};
	icon?: string;
	uiConfiguration?: {[key: string]: any}[];
}

export interface IElementInstances {
	configurationEntry?: {[key: string]: any};
	id?: number;
	sxpElement: SxpElement;
	uiConfigurationValues: {[key: string]: any};
}

export interface IFrameworkConfiguration {
	clauseContributorsExcludes?: string[];
	clauseContributorsIncludes?: string[];
	collectionProvider?: boolean;
	collectionProviderType?: string;
	scope?: IScope[];
	searchableAssetTypes?: string[];
}

export interface IIndexFields {
	languageIdPosition: number;
	name: string;
	type: string;
}

export interface IScope {
	externalReferenceCode: string;
	name: string;
	status: (typeof STATUS)[keyof typeof STATUS];
	type: string;
}

export interface ISearchableTypes {
	className: string;
	displayName: string;
	hasSubtype: boolean;
}

export interface ISorting {
	column: React.Key;
	direction: 'ascending' | 'descending';
}

export type SxpElement = {
	createDate?: string;
	description: string;
	description_i18n?: {[key: string]: string};
	elementDefinition: IElementDefinition & {[key: string]: any};
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
