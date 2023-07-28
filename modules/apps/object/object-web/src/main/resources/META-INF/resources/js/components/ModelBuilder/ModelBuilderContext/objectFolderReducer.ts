/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TAction, TState} from '../types';
import {TYPES} from './typesEnum';

export function objectFolderReducer(state: TState, action: TAction) {
	switch (action.type) {
		case TYPES.CREATE_MODEL_BUILDER_STRUCTURE: {
			const {objectFolders} = action.payload;

			return state;
		}
		default:
			return state;
	}
}
