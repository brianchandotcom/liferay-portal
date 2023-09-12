/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/LayoutData';
import changeMasterLayout from '../actions/changeMasterLayout';
declare type State =
	| {
			masterLayoutData: null;
			masterLayoutPlid: '0';
	  }
	| {
			masterLayoutData: LayoutData;
			masterLayoutPlid: string;
	  };
export declare const INITIAL_STATE: State;
export default function languageReducer(
	masterLayout: State | undefined,
	action: ReturnType<typeof changeMasterLayout>
): {
	masterLayoutData: LayoutData | null;
	masterLayoutPlid: string;
};
export {};
