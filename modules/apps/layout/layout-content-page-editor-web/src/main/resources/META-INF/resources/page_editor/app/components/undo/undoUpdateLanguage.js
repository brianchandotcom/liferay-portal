/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateLanguageId} from '../../actions/index';

function undoAction({action}) {
	return (dispatch) => dispatch(updateLanguageId({...action}));
}

function getDerivedStateForUndo({action, state}) {
	return {languageId: state.languageId, nextLanguageId: action.languageId};
}

export {undoAction, getDerivedStateForUndo};
