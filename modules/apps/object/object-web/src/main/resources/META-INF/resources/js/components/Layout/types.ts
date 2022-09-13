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

export type BoxType = 'regular' | 'categorization';

export type TName = LocalizedValue<string>;

export type TObjectLayout = {
	defaultObjectLayout: boolean;
	name: TName;
	objectDefinitionId: number;
	objectLayoutTabs: TObjectLayoutTab[];
};

export type TObjectLayoutTab = {
	name: LocalizedValue<string>;
	objectLayoutBoxes: TObjectLayoutBox[];
	objectRelationshipId: number;
	priority: number;
};

export type TObjectLayoutBox = {
	collapsable: boolean;
	name: TName;
	objectLayoutRows: TObjectLayoutRow[];
	priority: number;
	type: BoxType;
};

export type TObjectLayoutRow = {
	objectLayoutColumns: TObjectLayoutColumn[];
	priority: number;
};

export type TObjectLayoutColumn = {
	objectFieldName: string;
	priority: number;
	size: number;
};

export interface TObjectField extends ObjectField {
	inLayout?: boolean;
}

export interface TObjectRelationship extends ObjectRelationship {
	inLayout?: boolean;
}
