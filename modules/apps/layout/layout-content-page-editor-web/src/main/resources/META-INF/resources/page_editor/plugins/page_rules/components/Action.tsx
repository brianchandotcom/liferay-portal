/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import RuleBuilderItem from './RuleBuilderItem';
import RuleSelect from './RuleSelect';

export interface Action {
	action?: 'fragment';
	id: string;
	itemId?: string;
	type: 'show' | 'hide' | undefined;
}

interface ActionProps {
	action: Action;
	onActionChange: (action: Action) => void;
	onDeleteAction: () => void;
}

const TYPE_ITEMS = [
	{
		label: Liferay.Language.get('show'),
		value: 'show',
	},

	{
		label: Liferay.Language.get('hide'),
		value: 'hide',
	},
] as const;

const ACTION_ITEMS = [
	{
		label: Liferay.Language.get('fragment'),
		value: 'fragment',
	},
] as const;

export default function Condition({
	action,
	onActionChange,
	onDeleteAction,
}: ActionProps) {
	return (
		<RuleBuilderItem onDeleteButtonClick={onDeleteAction} type="action">
			<RuleSelect
				items={TYPE_ITEMS}
				onSelectionChange={(type) => onActionChange({...action, type})}
				selectedKey={action.type}
			/>

			{action.type ? (
				<RuleSelect
					items={ACTION_ITEMS}
					onSelectionChange={(selectedAction) =>
						onActionChange({
							...action,
							action: selectedAction,
							itemId: undefined,
						})
					}
					selectedKey={action.action}
				/>
			) : null}
		</RuleBuilderItem>
	);
}
