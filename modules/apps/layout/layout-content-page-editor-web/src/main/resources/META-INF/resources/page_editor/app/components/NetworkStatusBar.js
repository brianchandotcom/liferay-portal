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
import {useEventListener} from '@liferay/frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const getContent = (isOnline, status) => {
	if (!isOnline) {
		return (
			<ClayLoadingIndicator
				aria-label={Liferay.Language.get('trying-to-reconnect')}
				className="my-0"
				size="sm"
				title={Liferay.Language.get('trying-to-reconnect')}
			/>
		);
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.draftSaved) {
		return (
			<ClayIcon
				aria-label={Liferay.Language.get('saved')}
				className="text-success"
				data-title={Liferay.Language.get('saved')}
				symbol="check-circle"
			/>
		);
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.savingDraft) {
		return (
			<ClayLoadingIndicator
				aria-label={Liferay.Language.get('saving')}
				className="my-0"
				size="sm"
				title={Liferay.Language.get('saving')}
			/>
		);
	}

	return null;
};

const NetworkStatusBar = ({error, status}) => {
	const [isOnline, setIsOnline] = useState(true);
	const autoSaveMessageRef = useRef(null);

	useEffect(() => {
		if (status === SERVICE_NETWORK_STATUS_TYPES.error) {
			openToast({
				message: error,
				type: 'danger',
			});
		}
	}, [error, status]);

	useEffect(() => {
		if (
			status === SERVICE_NETWORK_STATUS_TYPES.draftSaved &&
			!autoSaveMessageRef.current
		) {
			autoSaveMessageRef.current = Liferay.Language.get(
				'page-editor-autosaves-your-work'
			);
		}
	}, [status]);

	useEventListener('online', () => setIsOnline(true), true, window);

	useEventListener('offline', () => setIsOnline(false), true, window);

	const content = getContent(isOnline, status);

	return (
		<div className="page-editor__status-bar">
			<span className="sr-only" role="alert">
				{autoSaveMessageRef.current}
			</span>

			{content}
		</div>
	);
};

export default NetworkStatusBar;
