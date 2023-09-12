/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_NETWORK} from './types';

import type {
	SERVICE_NETWORK_STATUS_TYPES,
	ServiceNetworkStatusType,
} from '../config/constants/serviceNetworkStatusTypes';

type ErrorStatusType = typeof SERVICE_NETWORK_STATUS_TYPES['error'];

export type NetworkStatus =
	| {error: string; status: ErrorStatusType}
	| {status: Omit<ServiceNetworkStatusType, ErrorStatusType> | null};

export default function updateNetwork(network: NetworkStatus) {
	return {
		network,
		type: UPDATE_NETWORK,
	} as const;
}
