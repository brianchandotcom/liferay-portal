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
	condition?: 'user' | 'role' | 'segment';
	id: string;
	options?: {
		type: 'equal' | 'not-equal';
		value?: string;
	};
	type: 'user' | undefined;
}

interface ConditionProps {
	condition: Condition;
	onConditionChange: (condition: Condition) => void;
	onDeleteCondition: () => void;
	showDeleteButton: boolean;
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
}

const TYPE_VALUES = {
	user: 'user',
} as const;

export const CONDITION_TYPE_ITEMS = [
	{
		label: Liferay.Language.get('user'),
		value: TYPE_VALUES.user,
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

export const CONDITION_ITEMS = {
	[TYPE_VALUES.user]: [
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
			label: Liferay.Language.get('has-not-the-role-of'),
			value: CONDITION_VALUES.not_role,
		},
		{
			label: Liferay.Language.get('belongs-to-segment'),
			value: CONDITION_VALUES.segment,
		},
		{
			label: Liferay.Language.get('not-belongs-to-segment'),
			value: CONDITION_VALUES.not_segment,
		},
	],
} as const;

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
	onConditionChange,
	onDeleteCondition,
	showDeleteButton,
	wrapperRef,
}: ConditionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const ValueSelectorComponent: FC<SelectorProps> | null = condition.condition
		? VALUE_SELECTOR_COMPONENTS[condition.condition]
		: null;

	const [{description}] = useConditionValues({conditions: [condition]});

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

			{condition.type && CONDITION_ITEMS[condition.type] ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('condition')
					)}
					items={CONDITION_ITEMS[condition.type]}
					onSelectionChange={(selectedCondition) => {
						onConditionChange({
							...condition,
							...convertConditionValueToOptions(
								selectedCondition
							),
						});
					}}
					selectedKey={convertOptionsToConditionValue(condition)}
				/>
			) : null}

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
		</RuleBuilderItem>
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
	condition: keyof typeof CONDITION_VALUES
): Partial<Condition> {
	if (condition === CONDITION_VALUES.not_user) {
		return {
			condition: CONDITION_VALUES.user,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (condition === CONDITION_VALUES.not_role) {
		return {
			condition: CONDITION_VALUES.role,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (condition === CONDITION_VALUES.not_segment) {
		return {
			condition: CONDITION_VALUES.segment,
			options: {
				type: 'not-equal',
			},
		};
	}

	return {
		condition,
		options: {
			type: 'equal',
		},
	};
}

export function convertOptionsToConditionValue(
	condition: Condition
): keyof typeof CONDITION_VALUES | undefined {
	if (condition.condition === CONDITION_VALUES.user) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.user;
		}
		else {
			return CONDITION_VALUES.not_user;
		}
	}
	else if (condition.condition === CONDITION_VALUES.role) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.role;
		}
		else {
			return CONDITION_VALUES.not_role;
		}
	}
	else if (condition.condition === CONDITION_VALUES.segment) {
		if (condition.options?.type === 'equal') {
			return CONDITION_VALUES.segment;
		}
		else {
			return CONDITION_VALUES.not_segment;
		}
	}

	return undefined;
}
