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
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
}
declare type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';
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
}
interface PickList {
	id: number;
	listTypeEntries: PickListItem[];
	name: string;
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
export declare function getObjectDefinitions(): Promise<ObjectDefinition[]>;
export declare function getObjectFields(
	objectDefinitionId: number
): Promise<ObjectField[]>;
export declare function getObjectRelationships(
	objectDefinitionId: number
): Promise<ObjectRelationship[]>;
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
export {};
