/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_LANGUAGE_ID} from '../actions/types';
import updateLanguageId from '../actions/updateLanguageId';

export default function languageReducer(
	languageId: string = 'en-US',
	action: ReturnType<typeof updateLanguageId>
) {
	switch (action.type) {
		case UPDATE_LANGUAGE_ID:
			return action.languageId;

		default:
			return languageId;
	}
}
