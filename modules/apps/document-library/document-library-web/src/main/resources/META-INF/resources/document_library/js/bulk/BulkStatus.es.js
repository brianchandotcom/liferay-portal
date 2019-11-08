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

import {useResource} from '@clayui/data-provider';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState, useCallback} from 'react';

function _delay(fn, duration) {
	return setTimeout(() => {
		fn();
	}, duration);
}

/**
 * Shows a toast notification.
 *
 * @param {boolean} error Flag indicating if is an error or not
 * @protected
 */
function _showNotification(hasError) {
	let message;

	if (hasError) {
		message = Liferay.Language.get('an-unexpected-error-occurred');
	} else {
		message = Liferay.Language.get('changes-saved');
	}

	const openToastParams = {
		message
	};

	if (hasError) {
		openToastParams.title = Liferay.Language.get('error');
		openToastParams.type = 'danger';
	}

	openToast(openToastParams);
}

/**
 * Shows the bulk actions status
 */
function BulkStatus({
	bulkInProgress = false,
	bulkStatusUrl = '/bulk/v1.0/status',
	intervalSpeed = 1000,
	pathModule,
	portletNamespace,
	waitingTime = 1000
}) {
	const [isBulkInProgress, setIsBulkInProgress] = useState(bulkInProgress);
	const [
		isBulkLoadingIndicatorVisible,
		setIsBulkLoadingIndicatorVisible
	] = useState(false);
	const isBulkHappened = useRef(false);
	const timerIdRef = useRef(null);

	useEffect(() => {
		if (waitingTime) {
			if (isBulkInProgress && !isBulkLoadingIndicatorVisible) {
				timerIdRef.current = _delay(() => {
					setIsBulkLoadingIndicatorVisible(true);
				}, waitingTime);
			}
		}

		return () => {
			if (timerIdRef.current) {
				clearTimeout(timerIdRef.current);
			}
		};
	}, [waitingTime, isBulkInProgress, isBulkLoadingIndicatorVisible]);

	const [networkState, setNetworkState] = useState({
		error: false,
		loading: false,
		networkStatus: 4,
		unused: false
	});

	const refetchSafe = useCallback(() => refetch(), [refetch]);

	useEffect(() => {
		if (!Liferay.component(`${portletNamespace}BulkStatus`)) {
			Liferay.component(
				`${portletNamespace}BulkStatus`,
				{
					startWatch: () => {
						if (!isBulkInProgress) {
							setIsBulkInProgress(true);
							refetchSafe();
						}
					}
				},
				{
					destroyOnNavigate: true
				}
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {refetch, resource} = useResource({
		link: () => {
			return fetch(
				Liferay.ThemeDisplay.getPortalURL() + pathModule + bulkStatusUrl
			).then(response => response.json());
		},
		onNetworkStatusChange: status =>
			setNetworkState({
				error: status === 5,
				loading: status < 4,
				networkStatus: status,
				unused: status === 4
			})
	});

	useEffect(() => {
		if (networkState.error) {
			_showNotification(true);
		}
	}, [networkState.error]);

	useEffect(() => {
		if (
			networkState.unused &&
			!isBulkInProgress &&
			isBulkHappened.current
		) {
			_showNotification(false);
		}
	}, [isBulkHappened, isBulkInProgress, networkState.unused]);

	useEffect(() => {
		if (resource) {
			if (resource.actionInProgress && !networkState.error) {
				setIsBulkInProgress(true);
				setTimeout(() => {
					refetchSafe();
				}, intervalSpeed);
				if (isBulkHappened.current === false) {
					isBulkHappened.current = true;
				}
			}

			if (!resource.actionInProgress || networkState.error) {
				setIsBulkInProgress(false);
				setIsBulkLoadingIndicatorVisible(false);
			}
		}
	}, [
		intervalSpeed,
		isBulkHappened,
		networkState.error,
		refetchSafe,
		resource
	]);

	return (
		<div className="bulk-status-container">
			<div
				className={`bulk-status ${!isBulkLoadingIndicatorVisible &&
					'closed'}`}
			>
				<div className="bulk-status-content">
					<ClayLoadingIndicator light small />
					<span>{Liferay.Language.get('processing-actions')}</span>
				</div>
			</div>
		</div>
	);
}

BulkStatus.propTypes = {
	/**
	 * Wether to show the component or not
	 * @type {Boolean}
	 */
	bulkInProgress: PropTypes.bool,

	/**
	 * Uri to send the bulk status fetch request.
	 * @instance
	 * @memberof BulkStatus
	 * @type {String}
	 */
	bulkStatusUrl: PropTypes.string,

	/**
	 * The interval (in milliseconds) on how often
	 * we will check if there are bulk actions in progress.
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @type {Number}
	 */
	intervalSpeed: PropTypes.number,

	/**
	 * PathModule
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	pathModule: PropTypes.string.isRequired,

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @review
	 * @type {string}
	 */
	portletNamespace: PropTypes.string.isRequired,

	/**
	 * The time (in milliseconds) we have to wait to
	 * show the component.
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @type {Number}
	 */
	waitingTime: PropTypes.number
};

export default props => <BulkStatus {...props} />;
