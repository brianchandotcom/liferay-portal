/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addRule from '../../actions/addRule';
declare type Props = {
	action: ReturnType<typeof addRule>;
};
declare function undoAction({
	action,
}: Props): (
	dispatch: (
		action:
			| {
					readonly network: import('../../actions/updateNetwork').NetworkStatus;
					readonly type: 'UPDATE_NETWORK';
			  }
			| {
					readonly layoutData: import('../../../types/layout_data/LayoutData').LayoutData;
					readonly ruleId: string;
					readonly type: 'DELETE_RULE';
			  }
	) => void,
	getState: () => import('../../../types/State').State
) => Promise<void>;
declare function getDerivedStateForUndo({
	action,
}: Props): {
	ruleId: string;
};
export {undoAction, getDerivedStateForUndo};
