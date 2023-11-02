/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../plugins/page_rules/components/Action';
import {Condition} from '../plugins/page_rules/components/Condition';
import {ConditionType} from '../plugins/page_rules/components/RuleBuilderSection';

export type Rule = {
	actions: Action[];
	conditionType: ConditionType;
	conditions: Condition[];
	id: string;
	name: string;
};
