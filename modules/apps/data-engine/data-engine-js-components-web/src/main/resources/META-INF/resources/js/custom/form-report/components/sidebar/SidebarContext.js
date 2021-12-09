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

import React, {createContext, useEffect, useState} from 'react';

import isClickOutside from '../../hooks/useOnClickOutside';

const SidebarContext = createContext({});

const SidebarContextProvider = ({
	children,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) => {
	const [sidebarState, setSidebarState] = useState({
		field: null,
		isOpen: false,
		totalEntries: 0,
		type: null,
	});

	useEffect(() => {
		const eventHandler = ({target}) => {
			if (
				isClickOutside(
					target,
					'#' + portletNamespace + 'sidebar-reports',
					'#' + portletNamespace + '-see-more'
				)
			) {
				setSidebarState(() => ({
					isOpen: false,
				}));
			}
		};

		window.addEventListener('click', eventHandler);

		return () => window.removeEventListener('click', eventHandler);
	});

	const toggleSidebar = (field, summary, totalEntries, type) => {
		const isOpen = field !== undefined;

		if (isOpen) {
			setSidebarState(() => ({
				field,
				isOpen,
				summary,
				totalEntries,
				type,
			}));
		}
		else {
			setSidebarState(() => ({
				...sidebarState,
				isOpen,
			}));
		}
	};

	return (
		<SidebarContext.Provider
			value={{
				...sidebarState,
				formReportRecordsFieldValuesURL,
				portletNamespace,
				toggleSidebar,
			}}
		>
			{children}
		</SidebarContext.Provider>
	);
};

export {SidebarContext, SidebarContextProvider};
