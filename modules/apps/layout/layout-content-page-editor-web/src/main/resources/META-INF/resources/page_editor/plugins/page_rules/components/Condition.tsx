/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {ScreenReaderAnnouncerContext} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {ComponentProps, FC, useContext, useMemo, useRef} from 'react';

import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import {
	ObjectField,
	ObjectFields,
} from '../../../app/contexts/ObjectDataContext';
import InfoItemService from '../../../app/services/InfoItemService';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache from '../../../app/utils/useCache';
import useConditionValues from '../../../app/utils/useConditionValues';
import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import RuleBuilderItem from './RuleBuilderItem';
import RuleSelect from './RuleSelect';

interface ConditionProps {
	condition: ConditionType;
	inputFragmentItems: {label: string; value: string}[];
	onConditionChange: (condition: ConditionType) => void;
	onDeleteCondition: () => void;
	showDeleteButton: boolean;
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
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

export const FORM_FRAGMENT_CONDITION_ITEMS = [
	{
		label: Liferay.Language.get('is-equal-to'),
		value: 'equal',
	},
	{
		label: Liferay.Language.get('is-not-equal-to'),
		value: 'not-equal',
	},
] as const;

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
	onConditionChange,
	onDeleteCondition,
	showDeleteButton,
	wrapperRef,
}: ConditionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const [{description}] = useConditionValues({
		conditions: [condition],
		items: inputFragmentItems,
	});

	const selectRef = useRef<HTMLButtonElement | undefined>();

	const completeCondition = !!condition.options?.value;

	const onErrorChange = (error: RuleError | null) => {
		if (condition.error?.element.id !== error?.element.id) {
			onConditionChange({...condition, error});
		}
	};

	const conditionTypeItems =
		config.layoutType === LAYOUT_TYPES.display
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
		<RuleBuilderItem
			aria-label={
				completeCondition
					? description
					: Liferay.Language.get('incomplete-condition')
			}
			description={description}
			onDeleteButtonClick={onDeleteCondition}
			onItemSelected={() => {
				selectRef.current?.focus();
			}}
			showDeleteButton={showDeleteButton}
			type="condition"
			wrapperRef={wrapperRef}
		>
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
				triggerRef={selectRef}
			/>

			{condition.type === TYPE_VALUES.field ? (
				<FieldFragmentTypeSelectors
					condition={condition}
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
		</RuleBuilderItem>
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
					items={FORM_FRAGMENT_CONDITION_ITEMS}
					onErrorChange={onErrorChange}
					onSelectionChange={(type) => {
						onConditionChange({
							...condition,
							options: {
								type,
							},
						});
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
						});

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					selectedKey={condition.options?.value}
				/>
			) : null}
		</>
	);
}

function FieldFragmentTypeSelectors({
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
	const {subtype, type} = config.selectedMappingTypes!;

	const mappingFields = useCache({
		fetcher: () =>
			InfoItemService.getAvailableStructureMappingFields({
				classNameId: type.id,
				classTypeId: subtype ? subtype.id : '',
			}),
		key: subtype
			? [CACHE_KEYS.mappingFields, type.id, subtype.id]
			: [CACHE_KEYS.mappingFields, type.id],
	});

	const items = useMemo(
		() => filterAndConvertMappingFields(mappingFields),
		[mappingFields]
	);

	if (!mappingFields) {
		return null;
	}

	const selectedKey = items.some((item) => item.value === condition.field)
		? condition.field
		: undefined;

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
					items={FORM_FRAGMENT_CONDITION_ITEMS}
					onErrorChange={onErrorChange}
					onSelectionChange={(type) => {
						onConditionChange({
							...condition,
							options: {
								type,
							},
						});
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
				<ClayInput
					aria-label={Liferay.Language.get('value')}
					className="w-auto"
					onBlur={() => {
						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					onChange={(event) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value: event.target.value,
							},
						});
					}}
					onKeyDown={(event) => {
						if (event.key === 'Enter') {
							sendMessage(
								Liferay.Language.get('condition-completed')
							);
						}
					}}
					sizing="sm"
					value={condition.options?.value}
				/>
			) : null}
		</>
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
					});
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
						});

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					value={condition.options?.value}
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
): {label: string; value: string}[] {
	if (!config.selectedMappingTypes?.type) {
		return [];
	}

	return mappingFields
		? mappingFields
				.filter(
					(field) =>
						field.label === config.selectedMappingTypes?.type.label
				)
				.flatMap((field) =>
					'fields' in field ? field.fields : [field]
				)
				.map((field) => {
					return {
						label: field.label,
						value: (field as ObjectField).key,
					};
				})
		: [];
}
