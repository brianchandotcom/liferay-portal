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
import {useEventListener, useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useReducer} from 'react';

import BasicInformation from './components/BasicInformation';
import EmptyLayoutReports from './components/EmptyLayoutReports';

const initialState = {
	data: null,
	error: null,
	loading: false,
};

const dataReducer = (state, action) => {
	switch (action.type) {
		case 'LOAD_DATA':
			return {
				...state,
				loading: true,
			};

		case 'SET_ERROR':
			return {
				...state,
				error: action.error,
				loading: false,
			};

		case 'SET_DATA':
			return {
				data: {
					...action.data,
				},
				error: action.data?.error,
				loading: false,
			};

		default:
			return initialState;
	}
};

export default function ({
	isPanelStateOpen,
	layoutReportsDataURL,
	portletNamespace,
}) {
	const isMounted = useIsMounted();

	const [state, dispatch] = useReducer(dataReducer, initialState);

	const safeDispatch = useCallback(
		(action) => {
			if (isMounted()) {
				dispatch(action);
			}
		},
		[isMounted]
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

	const handlePageAuditData = useCallback(() => {
		getData(layoutReportsDataURL);
	}, [layoutReportsDataURL, getData]);

	const layoutReportsPanelToggle = document.getElementById(
		`${portletNamespace}layoutReportsPanelToggleId`
	);

	useEffect(() => {
		const sidenavInstance = Liferay.SideNavigation.initialize(
			layoutReportsPanelToggle
		);

		sidenavInstance.on('open.lexicon.sidenav', () => {
			Liferay.Util.Session.set(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'open'
			);
		});

		sidenavInstance.on('closed.lexicon.sidenav', () => {
			Liferay.Util.Session.set(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'closed'
			);
		});

		Liferay.once('screenLoad', () => {
			Liferay.SideNavigation.destroy(layoutReportsPanelToggle);
		});
	}, [handlePageAuditData, layoutReportsPanelToggle, portletNamespace]);

	useEffect(() => {
		if (isPanelStateOpen) {
			getData(layoutReportsDataURL);
		}
	}, [isPanelStateOpen, layoutReportsDataURL, getData]);

	useEventListener(
		'mouseenter',
		handlePageAuditData,
		{once: true},
		layoutReportsPanelToggle
	);

	useEventListener(
		'focus',
		handlePageAuditData,
		{once: true},
		layoutReportsPanelToggle
	);

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
