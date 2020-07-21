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
import React, {useEffect, useImperativeHandle, useRef, useState} from 'react';

import Sidebar from './components/Sidebar';

const SidebarPanel = React.forwardRef(({View, fetchURL, onClose}, ref) => {
	const CurrentView = useRef(View);

	const [error, setError] = useState();
	const [isLoading, setIsLoading] = useState();
	const [isOpen, setIsOpen] = useState(true);
	const [resourceData, setResourceData] = useState();

	const getData = (fetchURL) => {
		setIsLoading(true);

		fetch(fetchURL, {
			method: 'GET',
		})
			.then((response) => response.json())
			.then((data) => {
				setError(data?.error);
				setResourceData(data);
				setIsLoading(false);
			})
			.catch(() => {
				setError(Liferay.Language.get('an-unexpected-error-occurred'));
				setResourceData(null);
				setIsLoading(false);
			});
	};

	useEffect(() => {
		getData(fetchURL);
	}, [fetchURL]);

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

	const onCloseHandle = () => (onClose ? onClose() : setIsOpen(false));

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
});

export default SidebarPanel;
