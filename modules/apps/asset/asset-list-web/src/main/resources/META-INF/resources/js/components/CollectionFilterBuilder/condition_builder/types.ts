/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export type PropertyType =
	| 'asset-categories'
	| 'asset-tags'
	| 'boolean'
	| 'date'
	| 'date-time'
	| 'double'
	| 'integer'
	| 'picklist'
	| 'string';

export interface PropertyOption {
	label: string;
	value: string;
}

/**
 * A field or attribute that can be used in a condition row.
 * Consumers normalize their domain types (ObjectField, ClassTypeField,
 * fragment input, etc.) to this shape via an adapter.
 *
 * `classNameId` / `classTypeId` are populated for properties that originate
 * from a typed source (e.g. an Object definition), so the row stays
 * self-describing when the same field name appears on multiple types.
 */
export interface GenericProperty {
	classNameId?: number;
	classTypeId?: number;
	label: string;
	name: string;
	options?: PropertyOption[];
	type: PropertyType;
}

/**
 * A labeled group of properties, rendered as a section header in the
 * field Picker.
 */
export interface PropertyGroup {
	items: GenericProperty[];
	label: string;
}

export interface GenericOperator {
	label: string;
	value: string;
}

export interface FilterCondition {
	classNameId?: number;
	classTypeId?: number;
	id: string;
	operatorName?: string;
	propertyName?: string;
	value?: string | Array<string | object>;
}

export type ConditionType = 'all' | 'any';

/**
 * Render prop for the value input cell of a condition row.
 * Consumers provide this to handle domain-specific value inputs
 * (e.g. async picklist fetch for Objects, boolean toggle for Page Editor).
 * Use DefaultValueInput for standard type-based inputs.
 */
export type ValueInputRenderer = (
	property: GenericProperty,
	operator: string,
	value: string | Array<string | object> | undefined,
	onChange: (value: string | Array<string | object>) => void
) => React.ReactNode;

export interface ConditionBuilderProps {
	conditionType: ConditionType;
	conditions: FilterCondition[];

	/**
	 * Returns the operator set for a given property.
	 * Allows each consumer to restrict or extend the available operators
	 * per field type.
	 */
	getOperators: (property: GenericProperty) => GenericOperator[];

	onChange: (
		conditions: FilterCondition[],
		conditionType: ConditionType
	) => void;

	properties: Array<GenericProperty | PropertyGroup>;

	renderValueInput: ValueInputRenderer;

	/**
	 * When false the AND/ANY conjunction picker is hidden.
	 * Use this for consumers that only support AND logic (e.g. Objects View).
	 */
	showConjunctionPicker?: boolean;
}
