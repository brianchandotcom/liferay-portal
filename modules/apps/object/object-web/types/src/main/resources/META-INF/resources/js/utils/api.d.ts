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

export declare function fetchJSON<T>(
	input: RequestInfo,
	init?: RequestInit
): Promise<T>;
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
