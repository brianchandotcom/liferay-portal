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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import BasicInformation from './BasicInformation';
import EmptyLayoutReports from './EmptyLayoutReports';

export default function LayoutReports({
	eventTriggered,
	isPanelStateOpen,
	layoutReportsDataURL,
}) {
	const isMounted = useIsMounted();

	const {data, error, loading} = useContext(StoreStateContext);

	const dispatch = useContext(StoreDispatchContext);

	const safeDispatch = useCallback(
		(action) => {
			if (isMounted()) {
				dispatch(action);
			}
		},
		[dispatch, isMounted]
	);

	const getData = useCallback(
		(fetchURL) => {
			safeDispatch({type: 'LOAD_DATA'});

			fetch(fetchURL, {
				method: 'GET',
			})
				.then((response) =>
					response.json().then((data) =>
						safeDispatch({
							data,
							type: 'SET_DATA',
						})
					)
				)
				.catch(() => {
					safeDispatch({
						error: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'SET_ERROR',
					});
				});
		},
		[safeDispatch]
	);

	useEffect(() => {
		if (isPanelStateOpen) {
			getData(layoutReportsDataURL);
		}
	}, [isPanelStateOpen, layoutReportsDataURL, getData]);

	useEffect(() => {
		if (eventTriggered) {
			getData(layoutReportsDataURL);
		}
	}, [eventTriggered, layoutReportsDataURL, getData]);

	return state.loading ? (
		<ClayLoadingIndicator small />
	) : state.error ? (
		<ClayAlert displayType="danger" variant="stripe">
			{state.error}
		</ClayAlert>
	) : (
		state.data && (
			<>
				<BasicInformation
					canonicalURLs={state.data.canonicalURLs}
					defaultLanguageId={state.data.defaultLanguageId}
				/>

				{state.data.validConnection || (
					<EmptyLayoutReports
						assetsPath={state.data.assetsPath}
						configureGooglePageSpeedURL={
							state.data.configureGooglePageSpeedURL
						}
					/>
				)}
			</>
		)
	);
}

LayoutReports.propTypes = {
	eventTriggered: PropTypes.bool.isRequired,
	isPanelStateOpen: PropTypes.bool.isRequired,
	layoutReportsDataURL: PropTypes.string.isRequired,
};
