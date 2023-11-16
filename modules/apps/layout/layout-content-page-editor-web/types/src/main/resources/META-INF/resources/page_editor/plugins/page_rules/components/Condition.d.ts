/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps} from 'react';
import RuleBuilderItem from './RuleBuilderItem';
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
export declare const CONDITION_TYPE_ITEMS: readonly [
	{
		readonly label: string;
		readonly value: 'user';
	}
];
export declare const CONDITION_ITEMS: {
	readonly user: readonly [
		{
			readonly label: string;
			readonly value: 'user';
		},
		{
			readonly label: string;
			readonly value: 'role';
		},
		{
			readonly label: string;
			readonly value: 'segment';
		}
	];
};
export default function Condition({
	condition,
	onConditionChange,
	onDeleteCondition,
	showDeleteButton,
	wrapperRef,
}: ConditionProps): JSX.Element;
export {};
