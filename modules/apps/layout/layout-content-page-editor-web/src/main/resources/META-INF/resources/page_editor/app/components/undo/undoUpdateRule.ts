/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Rule} from '../../../types/Rule';
import {State} from '../../../types/State';
import updateRuleAction from '../../actions/updateRule';
import updateRule from '../../thunks/updateRule';

function undoAction({action}: {action: Rule}) {
	const {actions, conditionType, conditions, id, name} = action;

	return updateRule({actions, conditionType, conditions, name, ruleId: id});
}

function getDerivedStateForUndo({
	action,
	state,
}: {
	action: ReturnType<typeof updateRuleAction>;
	state: State;
}) {
	const rule = state.layoutData.pageRules.find(
		(rule: Rule) => rule.id === action.ruleId
	);

	return rule;
}

export {undoAction, getDerivedStateForUndo};
