/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
interface ListTypeDefinition {
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: ListTypeEntry[];
	name: string;
	name_i18n: LocalizedValue<string>;
	system: boolean;
}
interface ListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeDefinitionId: number;
	name: string;
	name_i18n: LocalizedValue<string>;
}
declare type NotificationTemplateType = 'email' | 'userNotification';
declare type RecipientType = 'role' | 'term' | 'user';
declare type Recipient = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};
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
	recipients: Recipient[];
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: NotificationTemplateType;
}
interface ObjectFolderItem {
	linkedObjectDefinition: boolean;
	objectDefinitionExternalReferenceCode: string;
	positionX: number;
	positionY: number;
}
interface ObjectFolder {
	actions: Actions;
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectFolderItems: ObjectFolderItem[];
}
declare type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';
interface ObjectRelationship {
	deletionType: string;
	edge: boolean;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	parameterObjectFieldId?: number;
	reverse: boolean;
	type: ObjectRelationshipType;
}
interface saveProps {
	item: unknown;
	method?: 'PATCH' | 'POST' | 'PUT';
	returnValue?: boolean;
	url: string;
}
export declare function deleteObjectDefinition(
	objectDefinitionId: number
): Promise<void>;
export declare function deleteObjectField(objectFieldId: number): Promise<void>;
export declare function deleteObjectFolder(
	objectFolderId: number
): Promise<void>;
export declare function deleteObjectRelationship(
	objectRelationshipId: number
): Promise<void>;
export declare function fetchJSON<T>(
	input: RequestInfo,
	init?: RequestInit
): Promise<T>;
export declare function getAllObjectDefinitions(): Promise<ObjectDefinition[]>;
export declare function getAllObjectFolders(): Promise<ObjectFolder[]>;
export declare function getList<T>(url: string): Promise<T[]>;
export declare function getListTypeDefinition(
	listTypeDefinitionId: number
): Promise<ListTypeDefinition>;
export declare function getListTypeDefinitionListTypeEntries(
	listTypeDefinitionId: number
): Promise<ListTypeEntry[]>;
export declare function getListTypeDefinitions(): Promise<ListTypeDefinition[]>;
export declare function getNotificationTemplateByExternalReferenceCode(
	externalReferenceCode: string
): Promise<NotificationTemplate>;
export declare function getNotificationTemplateById(
	notificationTemplateId: number
): Promise<NotificationTemplate>;
export declare function getNotificationTemplates(): Promise<
	NotificationTemplate[]
>;
export declare function getObjectDefinitionByExternalReferenceCode(
	externalReferenceCode: string
): Promise<ObjectDefinition>;
export declare function getObjectDefinitionByExternalReferenceCodeObjectFields(
	externalReferenceCode: string
): Promise<ObjectField[]>;
export declare function getObjectDefinitionByExternalReferenceCodeObjectRelationships(
	externalReferenceCode: string
): Promise<ObjectRelationship[]>;
export declare function getObjectDefinitionById(
	objectDefinitionId: number
): Promise<ObjectDefinition>;
export declare function getObjectDefinitionObjectFields(
	objectDefinitionId: number
): Promise<ObjectField[]>;
export declare function getObjectDefinitions(
	parameters?: string
): Promise<ObjectDefinition[]>;
export declare function getObjectField(
	objectFieldId: number
): Promise<ObjectField>;
export declare function getObjectFolderByExternalReferenceCode(
	externalReferenceCode: string
): Promise<ObjectFolder>;
export declare function getObjectRelationship<T>(
	objectRelationshipId: number
): Promise<T>;
export declare function getObjectValidationRuleById<T>(
	objectValidationRuleId: number
): Promise<T>;
export declare function postListTypeEntry({
	key,
	listTypeDefinitionId,
	name_i18n,
}: Partial<ListTypeEntry>): Promise<any>;
export declare function postObjectDefinition(
	objectDefinition: Partial<ObjectDefinition>
): Promise<any>;
export declare function postObjectDefinitionPublish(
	objectDefinitionId: number
): Promise<Response>;
export declare function putObjectDefinitionByExternalReferenceCode(
	objectDefinition: Partial<ObjectDefinition>
): Promise<Response>;
export declare function putObjectFolderByExternalReferenceCode(
	objectFolder: Partial<ObjectFolder>
): Promise<Response>;
export declare function putListTypeDefinition({
	externalReferenceCode,
	id,
	listTypeEntries,
	name_i18n,
}: Partial<ListTypeDefinition>): Promise<any>;
export declare function putListTypeEntry({
	externalReferenceCode,
	id,
	name_i18n,
}: Partial<ListTypeEntry>): Promise<any>;
export declare function putObjectRelationship({
	id,
	...others
}: ObjectRelationship): Promise<any>;
export declare function save({
	item,
	method,
	returnValue,
	url,
}: saveProps): Promise<any>;
export {};
