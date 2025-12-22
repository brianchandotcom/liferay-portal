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
	type: 'show' | 'hide' | 'enable' | 'disable' | undefined;
}

export interface Condition {
	error?: RuleError | null;
	field?: 'user' | 'role' | 'segment' | string;
	id: string;
	options?: {
		type: 'equal' | 'not-equal';
		value?: string;
	};
	type: 'user' | 'form' | undefined;
}

export type BasicRule = {
	actions: Action[];
	conditionType?: ConditionType;
	conditions?: Condition[];
	id: string;
	name: string;
	script?: never;
};

export type AdvancedRule = {
	actions: Action[];
	conditionType?: never;
	conditions?: never;
	id: string;
	name: string;
	script: string;
};

export type Rule = BasicRule | AdvancedRule;

export type RuleError = {
	element: HTMLButtonElement | HTMLInputElement | HTMLElement;
	message: string;
};
