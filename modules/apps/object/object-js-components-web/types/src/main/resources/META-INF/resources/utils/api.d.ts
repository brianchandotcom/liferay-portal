/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
declare type NotificationTemplateType = 'email' | 'userNotification';
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
declare type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';
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
declare type RecipientType = 'role' | 'term' | 'user';
declare type Recipients = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};
export declare function deleteObjectDefinitions(id: number): Promise<void>;
export declare function deleteFolder(id: number): Promise<void>;
export declare function deleteObjectField(id: number): Promise<void>;
export declare function deleteObjectRelationships(id: number): Promise<void>;
export declare function deletePickList(pickListId: number): Promise<void>;
export declare function deletePickListItem(id: number): Promise<void>;
export declare function fetchJSON<T>(
	input: RequestInfo,
	init?: RequestInit
): Promise<T>;
export declare function getAllObjectDefinitions(): Promise<ObjectDefinition[]>;
export declare function getList<T>(url: string): Promise<T[]>;
export declare function getNotificationTemplateByExternalReferenceCode(
	notificationTemplateExternalReferenceCode: string
): Promise<NotificationTemplate>;
export declare function getNotificationTemplateById(
	notificationTemplateId: number
): Promise<NotificationTemplate>;
export declare function getNotificationTemplates(): Promise<
	NotificationTemplate[]
>;
export declare function getObjectDefinitionByExternalReferenceCode(
	objectDefinitionExternalReferenceCode: string
): Promise<ObjectDefinition>;
export declare function getObjectDefinitionById(
	objectDefinitionId: number
): Promise<ObjectDefinition>;
export declare function getObjectDefinitions(
	parameters?: string
): Promise<ObjectDefinition[]>;
export declare function getObjectField(
	objectFieldId: number
): Promise<ObjectField>;
export declare function getObjectFieldsByExternalReferenceCode(
	externalReferenceCode: string
): Promise<ObjectField[]>;
export declare function getObjectFieldsById(
	objectDefinitionId: number
): Promise<ObjectField[]>;
export declare function getObjectRelationshipsByExternalReferenceCode(
	externalReferenceCode: string
): Promise<ObjectRelationship[]>;
export declare function getObjectRelationshipsById(
	objectDefinitionId: number
): Promise<ObjectRelationship[]>;
export declare function getPickList(pickListId: number): Promise<PickList>;
export declare function getPickListItems(
	pickListId: number
): Promise<PickListItem[]>;
export declare function getPickLists(): Promise<PickList[]>;
export declare function getRelationship<T>(
	objectRelationshipId: number
): Promise<T>;
export declare function getObjectValidationRuleById<T>(
	objectValidationRuleId: number
): Promise<T>;
export declare function publishObjectDefinitionById(
	objectDefinitionId: number
): Promise<Response>;
export declare function putObjectDefinitionByExternalReferenceCode(
	values: Partial<ObjectDefinition>
): Promise<Response>;
export declare function save(
	url: string,
	item: unknown,
	method?: 'PATCH' | 'POST' | 'PUT'
): Promise<void>;
export declare function addPickListItem({
	id,
	key,
	name_i18n,
}: Partial<PickListItem>): Promise<void>;
export declare function updatePickList({
	externalReferenceCode,
	id,
	listTypeEntries,
	name_i18n,
}: Partial<PickList>): Promise<void>;
export declare function updatePickListItem({
	externalReferenceCode,
	id,
	name_i18n,
}: Partial<PickListItem>): Promise<void>;
export declare function updateRelationship({
	objectRelationshipId,
	...others
}: ObjectRelationship): Promise<void>;
export {};
