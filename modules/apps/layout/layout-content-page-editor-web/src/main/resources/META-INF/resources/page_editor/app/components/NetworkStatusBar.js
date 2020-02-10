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

import {useEventListener} from 'frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const getStatus = (isOnline, status, lastSaveDate) => {
	if (!isOnline) {
		return `${Liferay.Language.get('trying-to-reconnect')}...`;
	}
	else if (status === SERVICE_NETWORK_STATUS_TYPES.Fetching) {
		return Liferay.Language.get('saving-changes');
	}
	else if (lastSaveDate) {
		return lastSaveDate;
	}

	return null;
};

const NetworkStatusBar = ({error, lastFetch, status}) => {
	const [isOnline, setIsOnline] = useState(true);

	const lastSaveDate = useMemo(() => {
		if (!lastFetch) {
			return null;
		}

		const lastSaveDateText = Liferay.Language.get('draft-saved-at-x');

		return lastSaveDateText.replace(
			'{0}',
			lastFetch.toLocaleTimeString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)
		);
	}, [lastFetch]);

	useEffect(() => {
		if (status === SERVICE_NETWORK_STATUS_TYPES.Error) {
			openToast({
				message: error,
				title: Liferay.Language.get('error'),
				type: 'danger'
			});
		}
	}, [error, status]);

	useEventListener('online', () => setIsOnline(true), true, window);

	useEventListener('offline', () => setIsOnline(false), true, window);

	const statusText = getStatus(isOnline, status, lastSaveDate);

	if (!statusText) {
		return null;
	}

	return (
		<li className="d-inline nav-item text-truncate">
			<span className="lfr-portal-tooltip navbar-text">{statusText}</span>
		</li>
	);
};

export default NetworkStatusBar;
