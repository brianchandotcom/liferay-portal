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

import {useCallback, useContext, useEffect, useRef} from 'react';

import {
	Dropdown,
	HeaderActions,
	HeaderContext,
	HeaderTabs,
	HeaderTitle,
	HeaderTypes,
} from '../context/HeaderContext';

type UseHeader = {
	dropdown?: Dropdown;
	headerActions?: HeaderActions;
	heading?: HeaderTitle[];
	icon?: string;
	shouldUpdate?: boolean;
	tabs?: HeaderTabs[];
	timeout?: number;
	title?: string;
};

const DEFAULT_TIMEOUT = 0;

const useHeader = ({
	shouldUpdate = true,
	timeout = DEFAULT_TIMEOUT,
	heading,
	headerActions,
	icon,
	dropdown,
	tabs = [],
}: UseHeader = {}) => {
	const [context, dispatch] = useContext(HeaderContext);

	const dropdownRef = useRef(dropdown);
	const headerActionsRef = useRef(headerActions);
	const headingRef = useRef(heading);
	const tabsRef = useRef(tabs);

	const actTimeout = useCallback(
		(fn: () => void) => {
			if (shouldUpdate) {
				setTimeout(() => fn(), timeout);
			}
		},
		[shouldUpdate, timeout]
	);

	const setDropdown = useCallback(
		(newDropdown: Dropdown) => {
			dispatch({payload: newDropdown, type: HeaderTypes.SET_DROPDOWN});
		},
		[dispatch]
	);

	const setDropdownIcon = useCallback(
		(newSymbol: string) => {
			dispatch({
				payload: newSymbol,
				type: HeaderTypes.SET_SYMBOL,
			});
		},
		[dispatch]
	);

	const setHeaderActions = useCallback(
		(newActions: HeaderActions) => {
			actTimeout(() =>
				dispatch({
					payload: newActions,
					type: HeaderTypes.SET_HEADER_ACTIONS,
				})
			);
		},
		[actTimeout, dispatch]
	);

	const setHeading = useCallback(
		(newHeading: HeaderTitle[] = [], append?: boolean) => {
			actTimeout(() =>
				dispatch({
					payload: {append, heading: newHeading},
					type: HeaderTypes.SET_HEADING,
				})
			);
		},
		[actTimeout, dispatch]
	);

	const setTabs = useCallback(
		(newTabs: HeaderTabs[] = []) =>
			actTimeout(() =>
				dispatch({payload: newTabs, type: HeaderTypes.SET_TABS})
			),
		[actTimeout, dispatch]
	);

	useEffect(() => {
		if (shouldUpdate && headingRef.current) {
			actTimeout(() => setHeading(headingRef.current));
		}
	}, [actTimeout, setHeading, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && icon) {
			setDropdownIcon(icon);
		}
	}, [setDropdownIcon, shouldUpdate, icon]);

	useEffect(() => {
		if (shouldUpdate && tabsRef.current) {
			setTabs(tabsRef.current);
		}
	}, [setTabs, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && headerActionsRef.current) {
			setHeaderActions(headerActionsRef.current);
		}
	}, [setHeaderActions, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && dropdownRef.current) {
			setDropdown(dropdownRef.current);
		}
	}, [setDropdown, shouldUpdate]);

	return {
		context,
		dispatch,
		setDropdown,
		setDropdownIcon,
		setHeaderActions,
		setHeading,
		setTabs,
	};
};

export {useHeader};

export default useHeader;
