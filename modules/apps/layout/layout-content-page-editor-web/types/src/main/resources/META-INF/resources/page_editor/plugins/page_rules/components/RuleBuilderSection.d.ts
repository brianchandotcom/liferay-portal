/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
import {FragmentEntryLink} from '../../../app/actions/addFragmentEntryLinks';
import {Action} from './Action';
import {Condition} from './Condition';
declare type RuleBuilderActionProps = {
	actions: Action[];
	fragmentEntryLinks: FragmentEntryLink[];
	layoutDataItems: {
		label: string;
		value: string;
	}[];
	setActions: Dispatch<SetStateAction<Action[]>>;
};
export declare function RuleBuilderActionSection({
	actions,
	layoutDataItems,
	setActions,
}: RuleBuilderActionProps): JSX.Element;
declare type ConditionType = 'all' | 'any';
declare type RuleBuilderConditionProps = {
	conditionType: ConditionType;
	conditions: Condition[];
	setConditionType: Dispatch<SetStateAction<ConditionType>>;
	setConditions: Dispatch<SetStateAction<Condition[]>>;
};
export declare function RuleBuilderConditionSection({
	conditionType,
	conditions,
	setConditionType,
	setConditions,
}: RuleBuilderConditionProps): JSX.Element;
export {};
