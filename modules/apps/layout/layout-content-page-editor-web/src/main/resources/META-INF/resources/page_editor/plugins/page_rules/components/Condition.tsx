/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {FC} from 'react';

import {config} from '../../../app/config/index';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache from '../../../app/utils/useCache';
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
}

const TYPE_VALUES = {
	user: 'user',
} as const;

const TYPE_ITEMS = [
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

const CONDITION_ITEMS = {
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
	typeof CONDITION_VALUES[keyof typeof CONDITION_VALUES],
	FC<SelectorProps> | null
> = {
	[CONDITION_VALUES.user]: UserSelector,
	[CONDITION_VALUES.role]: null,
	[CONDITION_VALUES.segment]: SegmentsSelector,
};

export default function Condition({
	condition,
	onConditionChange,
	onDeleteCondition,
}: ConditionProps) {
	const ValueSelectorComponent: FC<SelectorProps> | null = condition.condition
		? VALUE_SELECTOR_COMPONENTS[condition.condition]
		: null;

	return (
		<RuleBuilderItem
			onDeleteButtonClick={onDeleteCondition}
			type="condition"
		>
			<RuleSelect
				items={TYPE_ITEMS}
				onSelectionChange={(type) =>
					onConditionChange({...condition, type})
				}
				selectedKey={condition.type}
			/>

			{condition.type && CONDITION_ITEMS[condition.type] ? (
				<RuleSelect
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
					onValueChanged={(value) =>
						onConditionChange({
							...condition,
							value,
						})
					}
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
