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
import {fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useImperativeHandle,
	useRef,
	useState,
} from 'react';

import Sidebar from './components/Sidebar';

const SidebarPanel = React.forwardRef(
	({fetchURL, onClose, viewComponent: View}, ref) => {
		const CurrentView = useRef(View);

		const [error, setError] = useState();
		const [isLoading, setIsLoading] = useState();
		const [isOpen, setIsOpen] = useState(true);
		const [resourceData, setResourceData] = useState();

		const getData = useCallback((fetchURL) => {
			setIsLoading(true);
			setResourceData(null);

			fetch(fetchURL, {
				method: 'GET',
			})
				.then((response) =>
					response.headers.get('content-type').includes('json')
						? response
								.json()
								.then((data) => setData(data, data?.error))
						: response.text().then((html) => setData({html}))
				)
				.catch(() => {
					setData(
						null,
						Liferay.Language.get('an-unexpected-error-occurred')
					);
				});
		}, []);

		const onCloseHandle = () => (onClose ? onClose() : setIsOpen(false));

		const setData = (data, error) => {

			// Force 300 ms of waiting to render the response so loading
			// looks more natural.

			setTimeout(() => {
				setIsLoading(false);
				setError(error);
				setResourceData(data);
			}, 300);
		};

		useEffect(() => {
			getData(fetchURL);
		}, [fetchURL, getData]);

		useEffect(() => {
			CurrentView.current = View;
		}, [View]);

		useImperativeHandle(ref, () => ({
			close: () => setIsOpen(false),
			open: (fetchURL, View) => {
				CurrentView.current = View;

				getData(fetchURL);

				setIsOpen(true);
			},
		}));

		return (
			<Sidebar onClose={onCloseHandle} open={isOpen}>
				{isLoading ? (
					<Sidebar.Body>
						<ClayLoadingIndicator />
					</Sidebar.Body>
				) : error ? (
					<>
						<Sidebar.Header />

						<ClayAlert displayType="danger" variant="stripe">
							{error}
						</ClayAlert>
					</>
				) : (
					resourceData && <CurrentView.current {...resourceData} />
				)}
			</Sidebar>
		);
	}
);

export default SidebarPanel;
