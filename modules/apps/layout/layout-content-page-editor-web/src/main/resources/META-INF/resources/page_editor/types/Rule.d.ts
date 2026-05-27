/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../plugins/page_rules/components/Action';
import {Condition} from '../plugins/page_rules/components/Condition';
import {ConditionType} from '../plugins/page_rules/components/RuleBuilderSection';

export interface Action {
	error?: RuleError | null;
	id: string;
	itemId?: string;
	readOnly?: boolean;
	type: 'disable' | 'enable' | 'hide' | 'show' | undefined;
}

export type AdvancedRule = {
	actions: Action[];
	conditionType?: never;
	conditions?: never;
	id: string;
	name: string;
	script: string;
};

export type BasicRule = {
	actions: Action[];
	conditionType?: ConditionType;
	conditions?: Condition[];
	id: string;
	name: string;
	script?: never;
};

type EqualityOperator = 'equal' | 'not-equal';

type ComparisonOperator =
	| 'greater-than'
	| 'greater-than-or-equals'
	| 'less-than'
	| 'less-than-or-equals';

type ContainmentOperator = 'contains' | 'does-not-contain';

type EmptinessOperator = 'is-empty' | 'is-not-empty';

type ConditionBase = {
	error?: RuleError | null;
	field?: string;
	id: string;
	type: 'field' | 'form' | 'user' | undefined;
};

export type Condition = ConditionBase &
	(
		| {
				fieldType?: undefined;
				options?: {
					type:
						| EqualityOperator
						| ComparisonOperator
						| ContainmentOperator
						| EmptinessOperator;
					value?: string;
				};
		  }
		| {
				fieldType: 'date' | 'date-time' | 'number';
				options?: {
					type: EqualityOperator | ComparisonOperator;
					value?: string;
				};
		  }
		| {
				fieldType: 'text';
				options?: {
					type:
						| EqualityOperator
						| ContainmentOperator
						| EmptinessOperator;
					value?: string;
				};
		  }
		| {
				fieldType: 'select';
				options?: {
					type: EqualityOperator | EmptinessOperator;
					value?: string;
				};
		  }
		| {
				fieldType: 'multiselect';
				options?: {
					type:
						| ContainmentOperator
						| EmptinessOperator
						| EqualityOperator;
					value?: string[];
				};
		  }
	);

export type Rule = AdvancedRule | BasicRule;

export type RuleError = {
	element: HTMLButtonElement | HTMLElement | HTMLInputElement;
	message: string;
};
