/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../../plugins/page_rules/components/Action';
import {Condition} from '../../plugins/page_rules/components/Condition';
import {State} from '../../types/State';
import updateNetwork from '../actions/updateNetwork';
import updateRuleAction from '../actions/updateRule';
declare type Props = {
	actions: Action[];
	conditions: Condition[];
	name: string;
	ruleId: string;
};
export default function updateRule({
	actions,
	conditions,
	name,
	ruleId,
}: Props): (
	dispatch: (
		action: ReturnType<typeof updateNetwork | typeof updateRuleAction>
	) => void,
	getState: () => State
) => Promise<void>;
export {};
