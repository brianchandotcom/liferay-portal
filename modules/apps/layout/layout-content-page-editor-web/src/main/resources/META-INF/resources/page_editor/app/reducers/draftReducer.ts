/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_DRAFT} from '../actions/types';
import updateDraft from '../actions/updateDraft';

export default function draftReducer(
	draft: boolean = false,
	action: ReturnType<typeof updateDraft>
) {
	switch (action.type) {
		case UPDATE_DRAFT:
			return action.draft;

		default:
			return draft;
	}
}
