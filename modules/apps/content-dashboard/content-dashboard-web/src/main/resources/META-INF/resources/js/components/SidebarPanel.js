/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useReducer,
	useRef,
	useState,
} from 'react';

import {CLOSE_PANEL_VALUE} from '../utils/constants';
import Sidebar from './Sidebar';

const initialState = {
	data: null,
	error: null,
	loading: false,
	open: true,
};

const resetSessionPanelValues = () => {
	Liferay.Util.Session.set(
		'com.liferay.content.dashboard.web_panelState',
		CLOSE_PANEL_VALUE
	);

	Liferay.Util.Session.set(
		'com.liferay.content.dashboard.web_selectedItemRowId',
		''
	);
};

const dataReducer = (state, action) => {
	switch (action.type) {
		case 'CLOSE_SIDEBAR':
			resetSessionPanelValues();

			return {
				...state,
				isOpen: false,
			};

		case 'LOAD_DATA':
			return {
				...state,
				data: null,
				error: null,
				loading: true,
			};

		case 'OPEN_SIDEBAR':
			return {
				...state,
				isOpen: true,
				loading: true,
			};

		case 'SET_ERROR':
			return {
				...state,
				data: null,
				error: action.error,
				loading: false,
			};

		case 'SET_HTML':
			return {
				...state,
				data: {
					html: action.html,
				},
				error: null,
				loading: false,
			};

		case 'SET_JSON':
			return {
				...state,
				data: action.data,
				error: action.data?.error,
				loading: false,
			};

		default:
			return initialState;
	}
};

const SidebarPanel = React.forwardRef(
	(
		{
			fetchURL: initialFetchUrl,
			onClose,
			singlePageApplicationEnabled,
			viewComponent: View,
		},
		ref
	) => {
		const [fetchURL, setFetchURL] = useState(initialFetchUrl);
		const CurrentViewRef = useRef(View);

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

		const getData = useCallback(() => {
			safeDispatch({type: 'LOAD_DATA'});

			fetch(fetchURL, {
				method: 'GET',
			})
				.then((response) =>
					response.headers.get('content-type').includes('json')
						? response
								.json()
								.then((data) =>
									safeDispatch({data, type: 'SET_JSON'})
								)
						: response
								.text()
								.then((html) =>
									safeDispatch({html, type: 'SET_HTML'})
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
		}, [fetchURL, safeDispatch]);

		const onCloseHandle = () =>
			onClose ? onClose() : safeDispatch({type: 'CLOSE_SIDEBAR'});

		useEffect(() => {
			getData();
		}, [getData]);

		useEffect(() => {
			CurrentViewRef.current = View;
		}, [View]);

		useEffect(() => {
			if (!singlePageApplicationEnabled) {
				return;
			}

			const navigationEventHandler = Liferay.on(
				'startNavigate',
				({path, target}) => {
					const [, paramString] = target.currentURL.split('?');
					const params = new URLSearchParams(paramString);
					const currentPortletId = params.get('p_p_id');

					if (!path.includes(currentPortletId)) {
						resetSessionPanelValues();
					}
				}
			);

			return () => {
				navigationEventHandler.detach();
			};
		}, [singlePageApplicationEnabled]);

		useImperativeHandle(ref, () => ({
			close: () => safeDispatch({type: 'CLOSE_SIDEBAR'}),
			open: (fetchURL, View) => {
				CurrentViewRef.current = View;

				safeDispatch({type: 'OPEN_SIDEBAR'});

				setFetchURL(fetchURL);
			},
		}));

		return (
			<Sidebar
				fetchData={getData}
				onClose={onCloseHandle}
				open={state.isOpen}
			>
				{state?.loading ? (
					<div className="align-items-center d-flex loading-indicator-wrapper">
						<ClayLoadingIndicator small />
					</div>
				) : state?.error ? (
					<>
						<Sidebar.Header />

						<ClayAlert displayType="danger" variant="stripe">
							{state.error}
						</ClayAlert>
					</>
				) : (
					state?.data && (
						<CurrentViewRef.current
							{...state.data}
							singlePageApplicationEnabled={
								singlePageApplicationEnabled
							}
						/>
					)
				)}
			</Sidebar>
		);
	}
);

export default SidebarPanel;
