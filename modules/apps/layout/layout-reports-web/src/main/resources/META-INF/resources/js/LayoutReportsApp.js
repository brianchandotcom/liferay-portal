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
import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useLayoutEffect,
	useReducer,
} from 'react';

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

	const safeDispatch = (action) => {
		if (isMounted()) {
			dispatch(action);
		}
	};

	const getData = (fetchURL) => {
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
					error: Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'SET_ERROR',
				});
			});
	};

	const handlePageAuditData = useCallback(() => {
		getData(layoutReportsDataURL);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [layoutReportsDataURL]);

	const handlePanelClose = useCallback(() => {
		Liferay.Util.Session.set(
			'com.liferay.layout.reports.web_layoutReportsPanelState',
			'closed'
		);
	}, []);

	useEffect(() => {
		var layoutReportsPanelToggle = document.getElementById(
			`${portletNamespace}layoutReportsPanelToggleId`
		);

		layoutReportsPanelToggle.addEventListener(
			'mouseenter',
			handlePageAuditData,
			{once: true}
		);
		layoutReportsPanelToggle.addEventListener(
			'focus',
			handlePageAuditData,
			{once: true}
		);

		var sidenavInstance = Liferay.SideNavigation.initialize(
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
	}, [handlePageAuditData, portletNamespace]);

	useLayoutEffect(() => {
		if (isPanelStateOpen) {
			getData(layoutReportsDataURL);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [layoutReportsDataURL]);

	return (
		<>
			<div className="sidebar-header">
				<ClayLayout.ContentRow>
					<ClayLayout.ContentCol expand>
						<h1 className="sr-only">
							{Liferay.Language.get('page-audit')}
						</h1>
						<span>{Liferay.Language.get('page-audit')}</span>
					</ClayLayout.ContentCol>
					<ClayLayout.ContentCol>
						<ClayButtonWithIcon
							className="sidenav-close"
							displayType="unstyled"
							monospaced
							onClick={handlePanelClose}
							symbol="times"
							type="button"
						/>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</div>
			<div className="p-3 sidebar-body">
				{state.loading ? (
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
				)}
			</div>
		</>
	);
}
