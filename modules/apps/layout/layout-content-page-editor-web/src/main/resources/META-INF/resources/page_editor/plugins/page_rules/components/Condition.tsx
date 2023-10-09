/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

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

const TYPE_ITEMS = [
	{
		label: Liferay.Language.get('user'),
		value: 'user',
	},
];

const CONDITION_ITEMS = [
	{
		label: Liferay.Language.get('is-the-user'),
		value: 'user',
	},

	{
		label: Liferay.Language.get('has-the-role-of'),
		value: 'role',
	},
	{
		label: Liferay.Language.get('belongs-to-segment'),
		value: 'segment',
	},
];

export default function Condition({
	condition,
	onConditionChange,
	onDeleteCondition,
}: ConditionProps) {
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

			{condition.type ? (
				<RuleSelect
					items={CONDITION_ITEMS}
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
		</RuleBuilderItem>
	);
}
