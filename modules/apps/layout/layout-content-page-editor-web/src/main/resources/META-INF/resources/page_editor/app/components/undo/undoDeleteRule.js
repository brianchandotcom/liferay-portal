/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addRule from '../../actions/addRule';
import LayoutService from '../../services/LayoutService';
import {setIn} from '../../utils/setIn';

function undoAction({action, store}) {
	const {ruleId, rules} = action;
	const {layoutData} = store;

	const nextLayoutData = setIn(layoutData, ['pageRules'], rules);

	return (dispatch) => {
		return LayoutService.updateLayoutData({
			layoutData: nextLayoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId: store.segmentsExperienceId,
		}).then(() => {
			dispatch(addRule({layoutData: nextLayoutData, ruleId}));
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	return {ruleId: action.ruleId, rules: state.layoutData.pageRules};
}

export {undoAction, getDerivedStateForUndo};
