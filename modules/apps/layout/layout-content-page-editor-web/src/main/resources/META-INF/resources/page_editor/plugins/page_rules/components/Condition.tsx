/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncerContext} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {ComponentProps, FC, useContext, useRef} from 'react';

import {config} from '../../../app/config/index';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache from '../../../app/utils/useCache';
import useConditionValues from '../../../app/utils/useConditionValues';
import RuleBuilderItem from './RuleBuilderItem';
import RuleSelect from './RuleSelect';

export interface Condition {
	field?: 'user' | 'role' | 'segment' | string;
	id: string;
	options?: {
		type: 'equal' | 'not-equal';
		value?: string;
	};
	type: 'user' | 'formFragment' | undefined;
}

interface ConditionProps {
	condition: Condition;
	inputFragmentItems: {label: string; value: string}[];
	onConditionChange: (condition: Condition) => void;
	onDeleteCondition: () => void;
	showDeleteButton: boolean;
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
}

const TYPE_VALUES = {
	formFragment: 'formFragment',
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

	const [{description}] = useConditionValues({conditions: [condition], items: inputFragmentItems});

	const selectRef = useRef<HTMLButtonElement | undefined>();

	const completeConditon = !!condition.options?.value;

	return (
		<RuleBuilderItem
			aria-label={
				completeConditon
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
				items={CONDITION_TYPE_ITEMS}
				onSelectionChange={(type) =>
					onConditionChange({...condition, type})
				}
				selectedKey={condition.type}
				triggerRef={selectRef}
			/>

			{condition.type === TYPE_VALUES.user ? (
				<UserTypeSelectors
					condition={condition}
					onConditionChange={onConditionChange}
					sendMessage={sendMessage}
				/>
			) : null}

			{condition.type === TYPE_VALUES.formFragment ? (
				<FormFragmentTypeSelectors
					condition={condition}
					inputFragmentItems={inputFragmentItems}
					onConditionChange={onConditionChange}
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
	sendMessage,
}: {
	condition: Condition;
	inputFragmentItems: {label: string; value: string}[];
	onConditionChange: (condition: Condition) => void;
	sendMessage: (message: string) => void;
}) {
	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x'),
					Liferay.Language.get('fragment')
				)}
				items={inputFragmentItems}
				onSelectionChange={(selectedFragment) => {
					onConditionChange({
						...condition,
						field: selectedFragment,
					});
				}}
				selectedKey={condition.field}
			/>

			{condition.field ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('type')
					)}
					items={[
						{
							label: Liferay.Language.get('is-equal-to'),
							value: 'equal',
						},
						{
							label: Liferay.Language.get('is-not-equal-to'),
							value: 'not-equal',
						},
					]}
					onSelectionChange={(type) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
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

function UserTypeSelectors({
	condition,
	onConditionChange,
	sendMessage,
}: {
	condition: Condition;
	onConditionChange: (condition: Condition) => void;
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
	onValueChanged: (value: string) => void;
	value: string | undefined;
}

function RolesSelector({onValueChanged, value}: SelectorProps) {
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
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function UserSelector({onValueChanged, value}: SelectorProps) {
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
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function SegmentsSelector({onValueChanged, value}: SelectorProps) {
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
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function convertConditionValueToOptions(
	field: keyof typeof CONDITION_VALUES
): Partial<Condition> {
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
	condition: Condition
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
