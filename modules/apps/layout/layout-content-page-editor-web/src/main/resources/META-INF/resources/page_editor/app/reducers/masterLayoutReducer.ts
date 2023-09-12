/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/LayoutData';
import changeMasterLayout from '../actions/changeMasterLayout';
import {CHANGE_MASTER_LAYOUT} from '../actions/types';

type State =
	| {masterLayoutData: null; masterLayoutPlid: '0'}
	| {masterLayoutData: LayoutData; masterLayoutPlid: string};

export const INITIAL_STATE: State = {
	masterLayoutData: null,
	masterLayoutPlid: '0',
};

export default function languageReducer(
	masterLayout = INITIAL_STATE,
	action: ReturnType<typeof changeMasterLayout>
) {
	switch (action.type) {
		case CHANGE_MASTER_LAYOUT: {
			return {
				masterLayoutData: action.masterLayoutData,
				masterLayoutPlid: action.masterLayoutPlid,
			};
		}

		default:
			return masterLayout;
	}
}
