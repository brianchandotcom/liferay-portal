/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItem from '../actions/duplicateItem';
import {InitAction} from '../actions/init';
export declare const INITIAL_STATE: Set<string>;
export default function restrictedItemIdsReducer(
	restrictedItemIds: Set<string> | undefined,
	action: InitAction | ReturnType<typeof duplicateItem>
): Set<string>;
