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
	type: 'user' | undefined;
	value?: string;
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
			label: Liferay.Language.get('has-the-role-of'),
			value: CONDITION_VALUES.role,
		},
		{
			label: Liferay.Language.get('belongs-to-segment'),
			value: CONDITION_VALUES.segment,
		},
	],
} as const;

const VALUE_SELECTOR_COMPONENTS: Record<
	(typeof CONDITION_VALUES)[keyof typeof CONDITION_VALUES],
	FC<SelectorProps> | null
> = {
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

	const completeConditon = !!condition.value;

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
					onSelectionChange={(selectedCondition) =>
						onConditionChange({
							...condition,
							condition: selectedCondition,
							value: undefined,
						})
					}
					selectedKey={condition.condition}
				/>
			) : null}

			{ValueSelectorComponent ? (
				<ValueSelectorComponent
					onValueChanged={(value) => {
						onConditionChange({
							...condition,
							value,
						});

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					value={condition.value}
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
