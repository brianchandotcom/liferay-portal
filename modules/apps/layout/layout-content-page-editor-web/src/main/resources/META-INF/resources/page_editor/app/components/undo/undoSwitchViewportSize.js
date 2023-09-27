/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {switchViewportSize} from '../../actions/index';

function undoAction({action}) {
	return (dispatch) => dispatch(switchViewportSize({size: action.size}));
}

function getDerivedStateForUndo({action, state}) {
	return {
		nextSize: action.size,
		size: state.selectedViewportSize,
	};
}

export {undoAction, getDerivedStateForUndo};
