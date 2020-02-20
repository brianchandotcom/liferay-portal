/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {UPDATE_NETWORK} from '../actions/types';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

export const INITIAL_STATE = {
	error: null,
	lastFetch: null,
	status: SERVICE_NETWORK_STATUS_TYPES.draftSaved
};

export default function networkReducer(networkStatus = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_NETWORK:
			return {
				...networkStatus,
				...action.network
			};
		default:
			return networkStatus;
	}
}
