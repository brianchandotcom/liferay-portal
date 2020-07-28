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
import {useIsMounted, useTimeout} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useImperativeHandle, useReducer, useRef} from 'react';

import Sidebar from './components/Sidebar';

const initialState = {
	data: null,
	error: null,
	loading: false,
	open: true,
};

const SidebarPanel = React.forwardRef(
	({fetchURL, onClose, viewComponent: View}, ref) => {
		const CurrentView = useRef(View);

		const delay = useTimeout();

		const isMounted = useIsMounted();

		const dataReducer = (state, action) => {
			if (isMounted()) {
				switch (action.type) {
					case 'CLOSE_SIDEBAR':
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
			}
		};

		const [state, dispatch] = useReducer(dataReducer, initialState);

		// Force 300 ms of waiting to render the response so loading
		// looks more natural.

		const dispatchWithDelay = (action) =>
			delay(() => dispatch(action), 300);

		const getData = (fetchURL) => {
			dispatch({type: 'LOAD_DATA'});

			fetch(fetchURL, {
				method: 'GET',
			})
				.then((response) =>
					response.headers.get('content-type').includes('json')
						? response
								.json()
								.then((data) =>
									dispatchWithDelay({data, type: 'SET_JSON'})
								)
						: response
								.text()
								.then((html) =>
									dispatchWithDelay({html, type: 'SET_HTML'})
								)
				)
				.catch(() => {
					dispatchWithDelay({
						error: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'SET_ERROR',
					});
				});
		};

		const onCloseHandle = () =>
			onClose ? onClose() : dispatch({type: 'CLOSE_SIDEBAR'});

		useEffect(() => {
			getData(fetchURL);
			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, [fetchURL]);

		useEffect(() => {
			CurrentView.current = View;
		}, [View]);

		useImperativeHandle(ref, () => ({
			close: () => dispatch({type: 'CLOSE_SIDEBAR'}),
			open: (fetchURL, View) => {
				CurrentView.current = View;

				getData(fetchURL);

				dispatch({type: 'OPEN_SIDEBAR'});
			},
		}));

		return (
			<Sidebar onClose={onCloseHandle} open={state.isOpen}>
				{state?.loading ? (
					<Sidebar.Body>
						<ClayLoadingIndicator />
					</Sidebar.Body>
				) : state?.error ? (
					<>
						<Sidebar.Header />

						<ClayAlert displayType="danger" variant="stripe">
							{state.error}
						</ClayAlert>
					</>
				) : (
					state?.data && <CurrentView.current {...state.data} />
				)}
			</Sidebar>
		);
	}
);

export default SidebarPanel;
