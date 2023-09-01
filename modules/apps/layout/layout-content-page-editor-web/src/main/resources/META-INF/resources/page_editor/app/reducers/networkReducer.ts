/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_NETWORK} from '../actions/types';
import updateNetwork, {NetworkStatus} from '../actions/updateNetwork';

export const INITIAL_STATE: NetworkStatus = {
	status: null,
};

export default function networkReducer(
	networkStatus = INITIAL_STATE,
	action: ReturnType<typeof updateNetwork>
) {
	switch (action.type) {
		case UPDATE_NETWORK:
			return action.network;
		default:
			return networkStatus;
	}
}
