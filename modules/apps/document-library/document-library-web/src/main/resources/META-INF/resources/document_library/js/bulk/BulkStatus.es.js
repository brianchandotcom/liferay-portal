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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useTimeout} from 'frontend-js-react-web';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useReducer} from 'react';

import reducer, {STATES} from './reducer.es';

/**
 * Shows the bulk actions status
 */
function BulkStatus({
	bulkComponentId,
	bulkInProgress,
	bulkStatusUrl = '/bulk/v1.0/status',
	intervalSpeed = 1000,
	pathModule,
	waitingTime = 1000
}) {
	const delay = useTimeout();

	const [state, dispatch] = useReducer(
		reducer,
		bulkInProgress ? {current: STATES.LONG_POLLING} : {current: STATES.IDLE}
	);

	const statusCallback = useCallback(() => {
		return delay(() => {
			fetch(
				`${Liferay.ThemeDisplay.getPortalURL()}${pathModule}${bulkStatusUrl}`
			)
				.then(response => response.json())
				.then(response => {
					if (response.actionInProgress) {
						dispatch({type: 'check'});
					} else {
						dispatch({type: 'success'});
					}
				})
				.catch(() => dispatch({type: 'error'}));
		}, intervalSpeed);
	}, [bulkStatusUrl, delay, pathModule, intervalSpeed]);

	useEffect(() => {
		let dispose;

		if (state.current === STATES.SHORT_POLLING) {
			statusCallback();

			dispose = delay(
				() => dispatch({type: 'initialDelayCompleted'}),
				waitingTime
			);
		} else if (state.current === STATES.LONG_POLLING) {
			statusCallback();
		} else if (state.current === STATES.NOTIFY) {
			openToast(state.toast);

			dispatch({type: 'notificationCompleted'});
		}

		return dispose;
	}, [delay, state, statusCallback, waitingTime]);

	if (!Liferay.component(bulkComponentId)) {
		Liferay.component(
			bulkComponentId,
			{
				startWatch: () => {
					dispatch({type: 'start'});
				}
			},
			{
				destroyOnNavigate: true
			}
		);
	}

	return (
		<div className="bulk-status-container">
			<div className={`bulk-status ${!state.current.show && 'closed'}`}>
				<div className="bulk-status-content">
					<ClayLoadingIndicator light small />

					<span>{Liferay.Language.get('processing-actions')}</span>
				</div>
			</div>
		</div>
	);
}

BulkStatus.propTypes = {
	bulkInProgress: PropTypes.bool,
	bulkStatusUrl: PropTypes.string,
	intervalSpeed: PropTypes.number,
	pathModule: PropTypes.string.isRequired,
	waitingTime: PropTypes.number
};

export default props => <BulkStatus {...props} />;
