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

interface APIURLPaths {
	applications: string;
	endpoints: string;
	filters: string;
	properties: string;
	schemas: string;
	sorts: string;
}

interface HTTPMethod {
	href: string;
	method: string;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

type voidReturn = () => void;

interface FDSItem<T> {
	action: {id: string};
	id: number;
	itemData: T;
	loadData: voidReturn;
	value: string;
}

interface BaseItem {
	actions: Actions;
	createDate: string;
	creator: string;
	dateCreated: string;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	keywords: string[];
	modifiedDate: string;
	scopeKey: string;
	status: string;
}

interface ApplicationStatus {
	key: 'published' | 'unpublished';
	name?: 'Published' | 'Unpublished';
}
interface APIApplicationItem extends BaseItem {
	applicationStatus: ApplicationStatus;
	baseURL: string;
	title: string;
	version: string;
}

interface APIApplicationEndpointItem extends BaseItem {
	name: string;
	path: string;
}

interface APIApplicationSchemaItem extends BaseItem {
	mainObjectDefinitionERC: string;
	name: string;
}
