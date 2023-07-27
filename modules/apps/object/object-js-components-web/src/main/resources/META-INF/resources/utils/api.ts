/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ERRORS} from './errors';
import {stringToURLParameterFormat} from './string';

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

interface ErrorDetails extends Error {
	detail?: string;
}

interface PickListItem {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface PickList {
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: PickListItem[];
	name: string;
	name_i18n: LocalizedValue<string>;
}

type NotificationTemplateType = 'email' | 'userNotification';

export interface NotificationTemplate {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	editorType: 'freemarker' | 'richText';
	externalReferenceCode: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number | null;
	recipientType: RecipientType;
	recipients: Recipients[];
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: NotificationTemplateType;
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

interface ObjectRelationship {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	objectRelationshipId: number;
	parameterObjectFieldId?: number;
	reverse: boolean;
	type: ObjectRelationshipType;
}

type RecipientType = 'role' | 'term' | 'user';

type Recipients = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};

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
		const {
			title,
		}: {
			title?: string;
		} = await response.json();

		const errorMessage = title || Liferay.Language.get('an-error-occurred');

		throw new Error(errorMessage);
	}
}

export function deleteObjectDefinitions(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-definitions/${id}`);
}

export function deleteFolder(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-folders/${id}`);
}

export function deleteObjectField(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-fields/${id}`);
}

export function deleteObjectRelationships(id: number) {
	return deleteItem(`/o/object-admin/v1.0/object-relationships/${id}`);
}

export async function deletePickList(pickListId: number) {
	return await deleteItem(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}`
	);
}

export async function deletePickListItem(id: number) {
	return await deleteItem(
		`/o/headless-admin-list-type/v1.0/list-type-entries/${id}`
	);
}

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function getAllObjectDefinitions() {
	return await getList<ObjectDefinition>(
		'/o/object-admin/v1.0/object-definitions?page=-1'
	);
}

export async function getList<T>(url: string) {
	const {items} = await fetchJSON<{items: T[]}>(url);

	return items;
}

export async function getNotificationTemplateByExternalReferenceCode(
	notificationTemplateExternalReferenceCode: string
) {
	return await fetchJSON<NotificationTemplate>(
		`/o/notification/v1.0/notification-templates/by-external-reference-code/${notificationTemplateExternalReferenceCode}`
	);
}

export async function getNotificationTemplateById(
	notificationTemplateId: number
) {
	return await fetchJSON<NotificationTemplate>(
		`/o/notification/v1.0/notification-templates/${notificationTemplateId}`
	);
}

export async function getNotificationTemplates() {
	return await getList<NotificationTemplate>(
		'/o/notification/v1.0/notification-templates?pageSize=-1'
	);
}

export async function getObjectDefinitionByExternalReferenceCode(
	objectDefinitionExternalReferenceCode: string
) {
	return await fetchJSON<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${objectDefinitionExternalReferenceCode}`
	);
}

export async function getObjectDefinitionById(objectDefinitionId: number) {
	return await fetchJSON<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`
	);
}

export async function getObjectDefinitions(parameters?: string) {
	if (!parameters) {
		return await getList<ObjectDefinition>(
			'/o/object-admin/v1.0/object-definitions?pageSize=-1'
		);
	}

	return await getList<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions?pageSize=-1&${stringToURLParameterFormat(
			parameters
		)}`
	);
}

export async function getObjectField(objectFieldId: number) {
	return await fetchJSON<ObjectField>(
		`/o/object-admin/v1.0/object-fields/${objectFieldId}`
	);
}

export async function getObjectFieldsByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}/object-fields?pageSize=-1`
	);
}

export async function getObjectFieldsById(objectDefinitionId: number) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields?pageSize=-1`
	);
}

export async function getObjectRelationshipsByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}/object-relationships`
	);
}

export async function getObjectRelationshipsById(objectDefinitionId: number) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`
	);
}

export async function getPickList(pickListId: number): Promise<PickList> {
	return await fetchJSON<PickList>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}`
	);
}

export async function getPickListItems(pickListId: number) {
	return await getList<PickListItem>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries?pageSize=-1`
	);
}

export async function getPickLists() {
	return await getList<PickList>(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
	);
}

export async function getRelationship<T>(objectRelationshipId: number) {
	return fetchJSON<T>(
		`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`
	);
}

export async function getObjectValidationRuleById<T>(
	objectValidationRuleId: number
) {
	return await fetchJSON<T>(
		`/o/object-admin/v1.0/object-validation-rules/${objectValidationRuleId}`
	);
}

export async function publishObjectDefinitionById(objectDefinitionId: number) {
	return await fetch(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/publish`,
		{
			method: 'POST',
		}
	);
}

export async function putObjectDefinitionByExternalReferenceCode(
	values: Partial<ObjectDefinition>
) {
	return await fetch(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${values.externalReferenceCode}`,
		{
			body: JSON.stringify(values),
			headers,
			method: 'PUT',
		}
	);
}

export async function save(
	url: string,
	item: unknown,
	method: 'PATCH' | 'POST' | 'PUT' = 'PUT'
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

export async function updatePickList({
	externalReferenceCode,
	id,
	listTypeEntries,
	name_i18n,
}: Partial<PickList>) {
	return await save(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${id}`,
		{externalReferenceCode, listTypeEntries, name_i18n},
		'PUT'
	);
}

export async function updatePickListItem({
	externalReferenceCode,
	id,
	name_i18n,
}: Partial<PickListItem>) {
	return await save(
		`/o/headless-admin-list-type/v1.0/list-type-entries/${id}`,
		{externalReferenceCode, name_i18n},
		'PUT'
	);
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
