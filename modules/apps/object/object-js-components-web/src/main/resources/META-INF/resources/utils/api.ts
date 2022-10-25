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

import {fetch} from 'frontend-js-web';

import {ERRORS} from './errors';
import {stringToURLParameterFormat} from './string';

interface ErrorDetails extends Error {
	detail?: string;
}

interface NotificationTemplate {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	objectDefinitionId: number | null;
	recipientType: RecipientType;
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: NotificationTemplateType;
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

type RecipientType = 'role' | 'term' | 'user';

type NotificationTemplateType = 'email' | 'userNotification';

interface ObjectRelationship {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	objectRelationshipId: number;
	parameterObjectFieldId?: number;
	reverse?: boolean;
	type: ObjectRelationshipType;
}
interface PickListItem {
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface PickList {
	actions: Actions;
	externalReferenceCode?: string;
	id: number;
	listTypeEntries: PickListItem[];
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

interface HTTPMethod {
	href: string;
	method: string;
}

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

async function deleteItem(url: string) {
	const response = await fetch(url, {headers, method: 'DELETE'});

	if (response.status === 401) {
		window.location.reload();
	}
	else if (!response.ok) {
		const errorMessage = Liferay.Language.get('an-error-occurred');

		throw new Error(errorMessage);
	}
}

export function deleteObjectDefinitions(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-definitions/${id}`);
}

export function deleteObjectRelationships(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-relationships/${id}`);
}

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function getList<T>(url: string) {
	const {items} = await fetchJSON<{items: T[]}>(url);

	return items;
}

export async function getNotificationTemplate(notificationTemplateId: number) {
	return await fetchJSON<NotificationTemplate>(
		`/o/notification/v1.0/notification-templates/${notificationTemplateId}`
	);
}

export async function getNotificationTemplates() {
	return await getList<NotificationTemplate>(
		'/o/notification/v1.0/notification-templates'
	);
}

export async function getObjectDefinition(objectDefinitionId: number) {
	return await fetchJSON<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`
	);
}

export async function getAllObjectDefinitions() {
	return await getList<ObjectDefinition>(
		'/o/object-admin/v1.0/object-definitions?page=-1'
	);
}

export async function getObjectDefinitions(parameters?: string) {
	if (!parameters) {
		return await getList<ObjectDefinition>(
			'/o/object-admin/v1.0/object-definitions'
		);
	}

	return await getList<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions?${stringToURLParameterFormat(
			parameters
		)}`
	);
}

export async function getObjectFields(objectDefinitionId: number) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields?pageSize=-1`
	);
}

export async function getObjectRelationships(objectDefinitionId: number) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`
	);
}

export async function getPickList(pickListId: number): Promise<PickList> {
	return await fetchJSON<PickList>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}`
	);
}

export async function getPickLists() {
	return await getList<PickList>(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
	);
}

export async function getPickListItems(pickListId: number) {
	return await getList<PickListItem>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries`
	);
}

export async function save(
	url: string,
	item: unknown,
	method: 'PUT' | 'POST' = 'PUT'
) {
	const isFormData = item instanceof FormData;

	const response = await fetch(url, {
		body: isFormData ? item : JSON.stringify(item),
		...(!isFormData && {headers}),
		method,
	});

	if (response.status === 401) {
		window.location.reload();
	}
	else if (!response.ok) {
		const {
			detail,
			title,
			type,
		}: {
			detail?: string;
			title?: string;
			type?: string;
		} = await response.json();

		const errorMessage =
			(type && ERRORS[type]) ??
			title ??
			Liferay.Language.get('an-error-occurred');

		const ErrorDetails = () => {
			return {
				detail,
				message: errorMessage,
				name: '',
			} as ErrorDetails;
		};
		throw ErrorDetails();
	}
}

export async function updateRelationship({
	objectRelationshipId,
	...others
}: ObjectRelationship) {
	return await save(
		`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`,
		others
	);
}

export async function getRelationship<T>(objectRelationshipId: number) {
	return fetchJSON<T>(
		`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`
	);
}

export async function updatePickList({
	externalReferenceCode,
	id,
	name_i18n,
}: Partial<PickList>) {
	return await save(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${id}`,
		{externalReferenceCode, name_i18n},
		'PUT'
	);
}

export async function deletePickList(pickListId: number) {
	return await deleteItem(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}`
	);
}

export async function addPickListItem({
	id,
	key,
	name_i18n,
}: Partial<PickListItem>) {
	return await save(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${id}/list-type-entries`,
		{key, name_i18n},
		'POST'
	);
}

export async function deletePickListItem(id: number) {
	return await deleteItem(
		`/o/headless-admin-list-type/v1.0/list-type-entries/${id}`
	);
}

export async function updatePickListItem({
	id,
	name_i18n,
}: Partial<PickListItem>) {
	return await save(
		`/o/headless-admin-list-type/v1.0/list-type-entries/${id}`,
		{name_i18n},
		'PUT'
	);
}
