/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ScreenReaderAnnouncerContext,
	isNullOrUndefined,
} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {FC, useContext} from 'react';

import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import {
	ObjectField,
	ObjectFieldAttributes,
	ObjectFields,
} from '../../../app/contexts/ObjectDataContext';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache from '../../../app/utils/useCache';
import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import RuleDatePickerField from './RuleDatePickerField';
import RuleMultiSelectField from './RuleMultiSelectField';
import RuleSelect from './RuleSelect';
import RuleTextField from './RuleTextField';
import OPERATORS from './operators';

interface ConditionProps {
	condition: ConditionType;
	inputFragmentItems: {label: string; value: string}[];
	mappingFieldItems: {
		attributes?: ObjectFieldAttributes;
		label: string;
		type: string;
		value: string;
	}[];
	onConditionChange: (condition: ConditionType) => void;
}

export const TYPE_VALUES = {
	field: 'field',
	formFragment: 'form',
	user: 'user',
} as const;

export const CONDITION_TYPE_ITEMS = [
	{
		label: Liferay.Language.get('user'),
		value: TYPE_VALUES.user,
	},
	{
		label: Liferay.Language.get('form-fragment'),
		value: TYPE_VALUES.formFragment,
	},
] as const;

const CONDITION_VALUES = {
	not_role: 'not_role',
	not_segment: 'not_segment',
	not_user: 'not_user',
	role: 'role',
	segment: 'segment',
	user: 'user',
} as const;

export const USER_CONDITION_ITEMS = [
	{
		label: Liferay.Language.get('is-the-user'),
		value: CONDITION_VALUES.user,
	},
	{
		label: Liferay.Language.get('is-not-the-user'),
		value: CONDITION_VALUES.not_user,
	},
	{
		label: Liferay.Language.get('has-the-role-of'),
		value: CONDITION_VALUES.role,
	},
	{
		label: Liferay.Language.get('does-not-have-the-role-of'),
		value: CONDITION_VALUES.not_role,
	},
	{
		label: Liferay.Language.get('belongs-to-segment'),
		value: CONDITION_VALUES.segment,
	},
	{
		label: Liferay.Language.get('does-not-belong-to-segment'),
		value: CONDITION_VALUES.not_segment,
	},
];

const DEFAULT_OPERATORS = [
	OPERATORS.EQUAL,
	OPERATORS.NOT_EQUAL,
	OPERATORS.CONTAINS,
	OPERATORS.DOES_NOT_CONTAIN,
] as const;

export function hasValueInput(
	type: NonNullable<ConditionType['options']>['type'] | undefined
): boolean {
	return (
		!isNullOrUndefined(type) &&
		type !== 'is-empty' &&
		type !== 'is-not-empty'
	);
}

export function getOperators(type: string | undefined): ReadonlyArray<{
	label: string;
	value: NonNullable<ConditionType['options']>['type'];
}> {
	switch (type) {
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

const VALUE_SELECTOR_COMPONENTS: Record<
	(typeof CONDITION_VALUES)[keyof typeof CONDITION_VALUES],
	FC<SelectorProps> | null
> = {
	[CONDITION_VALUES.not_user]: UserSelector,
	[CONDITION_VALUES.not_role]: RolesSelector,
	[CONDITION_VALUES.not_segment]: SegmentsSelector,
	[CONDITION_VALUES.user]: UserSelector,
	[CONDITION_VALUES.role]: RolesSelector,
	[CONDITION_VALUES.segment]: SegmentsSelector,
};

export default function Condition({
	condition,
	inputFragmentItems,
	mappingFieldItems,
	onConditionChange,
}: ConditionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const onErrorChange = (error: RuleError | null) => {
		if (condition.error?.element.id !== error?.element.id) {
			onConditionChange({...condition, error});
		}
	};

	const conditionTypeItems =
		config.layoutType === LAYOUT_TYPES.display &&
		config.selectedMappingTypes?.formEnabled
			? [
					...CONDITION_TYPE_ITEMS,
					{
						label: sub(
							Liferay.Language.get('x-field'),
							config.selectedMappingTypes?.type.label
						),
						value: TYPE_VALUES.field,
					},
				]
			: CONDITION_TYPE_ITEMS;

	return (
		<>
			<RuleSelect
				aria-label={Liferay.Language.get(
					'select-item-for-the-condition'
				)}
				items={conditionTypeItems}
				onErrorChange={onErrorChange}
				onSelectionChange={(type) =>
					onConditionChange({...condition, type})
				}
				selectedKey={condition.type}
			/>

			{condition.type === TYPE_VALUES.field ? (
				<FieldFragmentTypeSelectors
					condition={condition}
					items={mappingFieldItems}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}

			{condition.type === TYPE_VALUES.formFragment ? (
				<FormFragmentTypeSelectors
					condition={condition}
					inputFragmentItems={inputFragmentItems}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}

			{condition.type === TYPE_VALUES.user ? (
				<UserTypeSelectors
					condition={condition}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}
		</>
	);
}

function FormFragmentTypeSelectors({
	condition,
	inputFragmentItems,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	inputFragmentItems: {label: string; value: string}[];
	onConditionChange: (condition: ConditionType) => void;
	onErrorChange: (error: RuleError | null) => void;
	sendMessage: (message: string) => void;
}) {
	const selectedKey = inputFragmentItems.some(
		(item) => item.value === condition.field
	)
		? condition.field
		: undefined;

	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x-for-the-condition'),
					Liferay.Language.get('fragment')
				)}
				items={inputFragmentItems}
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
					items={DEFAULT_OPERATORS}
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

			{condition.options?.type ? (
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

			{condition.options?.type ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('value')
					)}
					items={[
						{label: Liferay.Language.get('true'), value: 'true'},
						{
							label: Liferay.Language.get('false'),
							value: 'false',
						},
					]}
					onErrorChange={onErrorChange}
					onSelectionChange={(value) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value,
							},
						} as ConditionType);

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					selectedKey={
						Array.isArray(condition.options?.value)
							? undefined
							: condition.options?.value
					}
				/>
			) : null}
		</>
	);
}

function FieldFragmentTypeSelectors({
	condition,
	items,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	items: {
		attributes?: ObjectFieldAttributes;
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

function ConditionValueInput({
	attributes,
	fieldType,
	onBlur,
	onChange,
	onErrorChange,
	value,
}: {
	attributes?: ObjectFieldAttributes;
	fieldType: string | undefined;
	onBlur: () => void;
	onChange: (value: string | string[]) => void;
	onErrorChange: (error: RuleError | null) => void;
	value: string | string[] | undefined;
}) {
	const stringValue = typeof value === 'string' ? value : '';

	if (fieldType === 'multiselect' && attributes?.options) {
		return (
			<RuleMultiSelectField
				onBlur={onBlur}
				onChange={onChange}
				onErrorChange={onErrorChange}
				options={attributes.options}
				value={Array.isArray(value) ? value : []}
			/>
		);
	}

	if (fieldType === 'select' && attributes?.options) {
		return (
			<RuleSelect
				aria-label={Liferay.Language.get('select-a-value')}
				items={attributes.options}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedValue) => {
					onChange(selectedValue);

					onBlur();
				}}
				selectedKey={stringValue || undefined}
			/>
		);
	}

	if (fieldType === 'date' || fieldType === 'date-time') {
		return (
			<RuleDatePickerField
				isDateTime={fieldType === 'date-time'}
				onBlur={onBlur}
				onChange={onChange}
				onErrorChange={onErrorChange}
				value={stringValue}
			/>
		);
	}

	return (
		<RuleTextField
			isNumber={fieldType === 'number'}
			onBlur={onBlur}
			onChange={onChange}
			onErrorChange={onErrorChange}
			value={stringValue}
		/>
	);
}

function UserTypeSelectors({
	condition,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	onConditionChange: (condition: ConditionType) => void;
	onErrorChange: (error: RuleError | null) => void;
	sendMessage: (message: string) => void;
}) {
	const ValueSelectorComponent: FC<SelectorProps> | null =
		VALUE_SELECTOR_COMPONENTS[
			condition.field as keyof typeof CONDITION_VALUES
		];

	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x'),
					Liferay.Language.get('condition')
				)}
				items={USER_CONDITION_ITEMS}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedCondition) => {
					onConditionChange({
						...condition,
						...convertConditionValueToOptions(selectedCondition),
					} as ConditionType);
				}}
				selectedKey={convertOptionsToConditionValue(condition)}
			/>

			{ValueSelectorComponent ? (
				<ValueSelectorComponent
					onErrorChange={onErrorChange}
					onValueChanged={(value) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value,
							},
						} as ConditionType);

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					value={
						typeof condition.options?.value === 'string'
							? condition.options.value
							: undefined
					}
				/>
			) : null}
		</>
	);
}

interface SelectorProps {
	onErrorChange: (error: RuleError | null) => void;
	onValueChanged: (value: string) => void;
	value: string | undefined;
}

function RolesSelector({onErrorChange, onValueChanged, value}: SelectorProps) {
	const roles = useCache({
		fetcher: () => RulesService.getRoles(),
		key: [CACHE_KEYS.roles],
	});

	if (!roles) {
		return null;
	}

	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('role')
			)}
			items={roles.map((role) => ({
				label: role.name,
				value: role.roleId,
			}))}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function UserSelector({onErrorChange, onValueChanged, value}: SelectorProps) {
	const users = useCache({
		fetcher: () => RulesService.getUsers(),
		key: [CACHE_KEYS.users],
	});

	if (!users) {
		return null;
	}

	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('user')
			)}
			items={users.map((user) => ({
				label: user.screenName,
				value: user.userId,
			}))}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function SegmentsSelector({
	onErrorChange,
	onValueChanged,
	value,
}: SelectorProps) {
	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('segment')
			)}
			items={Object.values(config.availableSegmentsEntries).map(
				(segmentsEntry) => ({
					label: segmentsEntry.name,
					value: segmentsEntry.segmentsEntryId,
				})
			)}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function convertConditionValueToOptions(
	field: keyof typeof CONDITION_VALUES
): Partial<ConditionType> {
	if (field === CONDITION_VALUES.not_user) {
		return {
			field: CONDITION_VALUES.user,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (field === CONDITION_VALUES.not_role) {
		return {
			field: CONDITION_VALUES.role,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (field === CONDITION_VALUES.not_segment) {
		return {
			field: CONDITION_VALUES.segment,
			options: {
				type: 'not-equal',
			},
		};
	}

	return {
		field,
		options: {
			type: 'equal',
		},
	};
}

export function convertOptionsToConditionValue(
	condition: ConditionType
): keyof typeof CONDITION_VALUES | undefined {
	if (condition.field === CONDITION_VALUES.user) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.user;
		}
		else {
			return CONDITION_VALUES.not_user;
		}
	}
	else if (condition.field === CONDITION_VALUES.role) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.role;
		}
		else {
			return CONDITION_VALUES.not_role;
		}
	}
	else if (condition.field === CONDITION_VALUES.segment) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.segment;
		}
		else {
			return CONDITION_VALUES.not_segment;
		}
	}

	return undefined;
}

export function filterAndConvertMappingFields(
	mappingFields: ObjectFields | null
): {
	attributes?: ObjectFieldAttributes;
	label: string;
	type: string;
	value: string;
}[] {
	if (!mappingFields || !config.selectedMappingTypes?.type) {
		return [];
	}

	return mappingFields
		.flatMap((field) => ('fields' in field ? field.fields : [field]))
		.map((field) => {
			const objectField = field as ObjectField;

			return {
				attributes: objectField.attributes,
				label: objectField.label,
				type: objectField.type,
				value: objectField.key,
			};
		});
}
