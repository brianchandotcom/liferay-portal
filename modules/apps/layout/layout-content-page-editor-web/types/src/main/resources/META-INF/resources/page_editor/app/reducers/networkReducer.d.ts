/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateNetwork, {NetworkStatus} from '../actions/updateNetwork';
export declare const INITIAL_STATE: NetworkStatus;
export default function networkReducer(
	networkStatus: NetworkStatus | undefined,
	action: ReturnType<typeof updateNetwork>
): NetworkStatus;
