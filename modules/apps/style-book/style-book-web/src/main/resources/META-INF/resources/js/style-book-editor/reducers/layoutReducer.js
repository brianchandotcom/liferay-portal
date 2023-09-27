/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	LOADING,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
} from '../constants/actionTypes';

export default function layoutReducer(state, action) {
	switch (action.type) {
		case LOADING: {
			const {value} = action;

			return {...state, loading: value};
		}

		case SET_PREVIEW_LAYOUT: {
			const {layout} = action;

			return {...state, previewLayout: layout};
		}

		case SET_PREVIEW_LAYOUT_TYPE: {
			const {layoutType} = action;

			return {...state, previewLayoutType: layoutType};
		}

		default:
			return state;
	}
}
