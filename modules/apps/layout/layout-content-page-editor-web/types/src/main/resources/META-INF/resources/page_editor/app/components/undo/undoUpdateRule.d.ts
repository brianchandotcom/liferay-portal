/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Rule} from '../../../types/Rule';
import {State} from '../../../types/State';
import updateRuleAction from '../../actions/updateRule';
declare function undoAction({
	action,
}: {
	action: Rule;
}): (
	dispatch: (
		action:
			| {
					readonly network: import('../../actions/updateNetwork').NetworkStatus;
					readonly type: 'UPDATE_NETWORK';
			  }
			| {
					readonly layoutData: import('../../../types/layout_data/LayoutData').LayoutData;
					readonly ruleId: string;
					readonly type: 'UPDATE_RULE';
			  }
	) => void,
	getState: () => State
) => Promise<void>;
declare function getDerivedStateForUndo({
	action,
	state,
}: {
	action: ReturnType<typeof updateRuleAction>;
	state: State;
}): Rule | undefined;
export {undoAction, getDerivedStateForUndo};
