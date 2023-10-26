/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addRule from '../../actions/addRule';
import deleteRule from '../../thunks/deleteRule';

type Props = {
	action: ReturnType<typeof addRule>;
};

function undoAction({action}: Props) {
	const {ruleId} = action;

	return deleteRule({ruleId});
}

function getDerivedStateForUndo({action}: Props) {
	return {
		ruleId: action.ruleId,
	};
}

export {undoAction, getDerivedStateForUndo};
