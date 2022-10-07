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
declare type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';
declare type RecipientType = 'role' | 'term' | 'user';
declare type NotificationTemplateType = 'email' | 'userNotification';
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
export declare function deleteObjectDefinitions(id: number): Promise<void>;
export declare function deleteObjectRelationships(id: number): Promise<void>;
export declare function fetchJSON<T>(
	input: RequestInfo,
	init?: RequestInit
): Promise<T>;
export declare function getList<T>(url: string): Promise<T[]>;
export declare function getNotificationTemplate(
	notificationTemplateId: number
): Promise<NotificationTemplate>;
export declare function getNotificationTemplates(): Promise<
	NotificationTemplate[]
>;
export declare function getObjectDefinition(
	objectDefinitionId: number
): Promise<ObjectDefinition>;
export declare function getAllObjectDefinitions(): Promise<ObjectDefinition[]>;
export declare function getObjectDefinitions(
	parameters?: string
): Promise<ObjectDefinition[]>;
export declare function getObjectFields(
	objectDefinitionId: number
): Promise<ObjectField[]>;
export declare function getObjectRelationships(
	objectDefinitionId: number
): Promise<ObjectRelationship[]>;
export declare function getPickList(pickListId: number): Promise<PickList>;
export declare function getPickLists(): Promise<PickList[]>;
export declare function getPickListItems(
	pickListId: number
): Promise<PickListItem[]>;
export declare function save(
	url: string,
	item: unknown,
	method?: 'PUT' | 'POST'
): Promise<void>;
export declare function updateRelationship({
	objectRelationshipId,
	...others
}: ObjectRelationship): Promise<void>;
export declare function getRelationship<T>(
	objectRelationshipId: number
): Promise<T>;
export declare function updatePickList({
	externalReferenceCode,
	id,
	name_i18n,
}: Partial<PickList>): Promise<void>;
export declare function deletePickList(pickListId: number): Promise<void>;
export declare function addPickListItem({
	id,
	key,
	name_i18n,
}: Partial<PickListItem>): Promise<void>;
export declare function deletePickListItem(id: number): Promise<void>;
export declare function updatePickListItem({
	id,
	name_i18n,
}: Partial<PickListItem>): Promise<void>;
export {};
