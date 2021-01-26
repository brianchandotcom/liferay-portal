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

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEventListener} from 'frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const LoadingText = ({children}) => (
	<>
		<span className="m-0 navbar-text page-editor__status-bar text-info">
			{children}
		</span>
		<ClayLoadingIndicator className={'my-0'} small />
	</>
);

const SuccessText = ({children}) => (
	<>
		<span className="m-0 navbar-text page-editor__status-bar text-success">
			{children}
		</span>
		<ClayIcon className={'text-success'} symbol={'check-circle'} />
	</>
);

const getContent = (isOnline, status) => {
	if (!isOnline) {
		return (
			<LoadingText>
				{Liferay.Language.get('trying-to-reconnect')}
			</LoadingText>
		);
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.draftSaved) {
		return <SuccessText>{Liferay.Language.get('saved')}</SuccessText>;
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.savingDraft) {
		return <LoadingText>{Liferay.Language.get('saving')}</LoadingText>;
	}

	return null;
};

const NetworkStatusBar = ({error, status}) => {
	const [isOnline, setIsOnline] = useState(true);

	useEffect(() => {
		if (status === SERVICE_NETWORK_STATUS_TYPES.error) {
			openToast({
				message: error,
				type: 'danger',
			});
		}
	}, [error, status]);

	useEventListener('online', () => setIsOnline(true), true, window);

	useEventListener('offline', () => setIsOnline(false), true, window);

	const content = getContent(isOnline, status);

	return (
		<span className="align-items-center d-flex text-truncate">
			{content}
		</span>
	);
};

export default NetworkStatusBar;
