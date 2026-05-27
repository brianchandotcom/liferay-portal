/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {MappingFieldAttributes} from '../../../types/MappingField';
import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import ConditionValueInput from './ConditionValueInput';
import RuleSelect from './RuleSelect';

export default function FieldFragmentTypeSelector({
	condition,
	items,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	items: {
		attributes?: MappingFieldAttributes;
		label: string;
		type: string;
		value: string;
	}[];
	onConditionChange: (condition: ConditionType) => void;
	onErrorChange: (error: RuleError | null) => void;
	sendMessage: (message: string) => void;
}) {
	const selectedItem = items.find((item) => item.value === condition.field);

	const selectedKey = selectedItem ? condition.field : undefined;

	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x-for-the-condition'),
					Liferay.Language.get('fragment')
				)}
				items={items}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedFragment) => {
					onConditionChange({
						...condition,
						field: selectedFragment,
						options: undefined,
					});
				}}
				selectedKey={selectedKey}
			/>

			{condition.field ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('type')
					)}
					items={getOperators(selectedItem?.type)}
					onErrorChange={onErrorChange}
					onSelectionChange={(type) => {
						onConditionChange({
							...condition,
							options: {
								type,
							},
						} as ConditionType);
					}}
					selectedKey={condition.options?.type}
				/>
			) : null}

			{hasValueInput(condition.options?.type) ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('type')
					)}
					items={[
						{
							label: Liferay.Language.get('value'),
							value: 'value',
						},
					]}
					onErrorChange={onErrorChange}
					onSelectionChange={() => {}}
					selectedKey="value"
				/>
			) : null}

			{hasValueInput(condition.options?.type) ? (
				<ConditionValueInput
					attributes={selectedItem?.attributes}
					fieldType={selectedItem?.type}
					onBlur={() => {
						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					onChange={(value) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value,
							},
						} as ConditionType);
					}}
					onErrorChange={onErrorChange}
					value={condition.options?.value}
				/>
			) : null}
		</>
	);
}

export const OPERATORS = {
	CONTAINS: {
		label: Liferay.Language.get('contains'),
		value: 'contains',
	},
	DOES_NOT_CONTAIN: {
		label: Liferay.Language.get('does-not-contain'),
		value: 'does-not-contain',
	},
	EQUAL: {
		label: Liferay.Language.get('is-equal-to'),
		value: 'equal',
	},
	GREATER_THAN: {
		label: Liferay.Language.get('is-greater-than'),
		value: 'greater-than',
	},
	GREATER_THAN_OR_EQUALS: {
		label: Liferay.Language.get('is-greater-than-or-equal-to'),
		value: 'greater-than-or-equals',
	},
	IS_EMPTY: {
		label: Liferay.Language.get('is-empty'),
		value: 'is-empty',
	},
	IS_NOT_EMPTY: {
		label: Liferay.Language.get('is-not-empty'),
		value: 'is-not-empty',
	},
	LESS_THAN: {
		label: Liferay.Language.get('is-less-than'),
		value: 'less-than',
	},
	LESS_THAN_OR_EQUALS: {
		label: Liferay.Language.get('is-less-than-or-equal-to'),
		value: 'less-than-or-equals',
	},
	NOT_EQUAL: {
		label: Liferay.Language.get('is-not-equal-to'),
		value: 'not-equal',
	},
} as const;

export const DEFAULT_OPERATORS = [
	OPERATORS.EQUAL,
	OPERATORS.NOT_EQUAL,
	OPERATORS.CONTAINS,
	OPERATORS.DOES_NOT_CONTAIN,
] as const;

function getOperators(type: string | undefined): ReadonlyArray<{
	label: string;
	value: NonNullable<ConditionType['options']>['type'];
}> {
	switch (type) {
		case 'boolean':
			return [OPERATORS.EQUAL, OPERATORS.NOT_EQUAL];

		case 'date':
		case 'date-time':
		case 'number':
			return [
				OPERATORS.EQUAL,
				OPERATORS.NOT_EQUAL,
				OPERATORS.GREATER_THAN,
				OPERATORS.GREATER_THAN_OR_EQUALS,
				OPERATORS.LESS_THAN,
				OPERATORS.LESS_THAN_OR_EQUALS,
			];

		case 'file':
			return [OPERATORS.IS_EMPTY, OPERATORS.IS_NOT_EMPTY];

		case 'multiselect':
			return [
				OPERATORS.EQUAL,
				OPERATORS.NOT_EQUAL,
				OPERATORS.CONTAINS,
				OPERATORS.DOES_NOT_CONTAIN,
				OPERATORS.IS_EMPTY,
				OPERATORS.IS_NOT_EMPTY,
			];

		case 'select':
			return [
				OPERATORS.EQUAL,
				OPERATORS.NOT_EQUAL,
				OPERATORS.IS_EMPTY,
				OPERATORS.IS_NOT_EMPTY,
			];

		case 'text':
			return [
				OPERATORS.EQUAL,
				OPERATORS.NOT_EQUAL,
				OPERATORS.CONTAINS,
				OPERATORS.DOES_NOT_CONTAIN,
				OPERATORS.IS_EMPTY,
				OPERATORS.IS_NOT_EMPTY,
			];

		default:
			return DEFAULT_OPERATORS;
	}
}

function hasValueInput(
	type: NonNullable<ConditionType['options']>['type'] | undefined
): boolean {
	return (
		!isNullOrUndefined(type) &&
		type !== 'is-empty' &&
		type !== 'is-not-empty'
	);
}
