/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache, {Fetcher} from '../../../app/utils/useCache';
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
	fetcher: Fetcher;
	onConditionChange: (condition: Condition) => void;
	onDeleteCondition: () => void;
}

const TYPE_VALUES = {
	user: 'user',
};

const TYPE_ITEMS = [
	{
		label: Liferay.Language.get('user'),
		value: TYPE_VALUES.user,
	},
];

const CONDITION_VALUES = {
	role: 'role',
	segment: 'segment',
	user: 'user',
};

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
};

const VALUE_SELECTOR_COMPONENTS = {
	[CONDITION_VALUES.user]: UserSelector,
};

export default function Condition({
	condition,
	fetcher,
	onConditionChange,
	onDeleteCondition,
}: ConditionProps) {
	const ValueSelectorComponent: any = condition.condition
		? VALUE_SELECTOR_COMPONENTS[condition.condition]
		: null;

	return (
		<RuleBuilderItem
			onDeleteButtonClick={onDeleteCondition}
			type="condition"
		>
			<RuleSelect
				items={TYPE_ITEMS}
				onSelectionChange={(type: any) =>
					onConditionChange({...condition, type})
				}
				selectedKey={condition.type}
			/>

			{condition.type && CONDITION_ITEMS[condition.type] ? (
				<RuleSelect
					items={CONDITION_ITEMS[condition.type]}
					onSelectionChange={(selectedCondition: any) =>
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
				<ValueSelectorComponent fetcher={fetcher} />
			) : null}
		</RuleBuilderItem>
	);
}

function UserSelector({
	fetcher,
	onValueChanged,
	value,
}: {
	fetcher: Fetcher;
	onValueChanged: (value: string) => void;
	value: string;
}) {
	const users: {screenName: string; userId: string}[] = useCache({
		fetcher,
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
