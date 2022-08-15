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
	shouldUpdate?: boolean;
	timeout?: number;
	title?: string;
	useDropdown?: Dropdown;
	useHeaderActions?: HeaderActions;
	useHeading?: HeaderTitle[];
	useIcon?: string;
	useTabs?: HeaderTabs[];
};

const DEFAULT_TIMEOUT = 0;

const useHeader = ({
	shouldUpdate = true,
	timeout = DEFAULT_TIMEOUT,
	useHeading,
	useHeaderActions,
	useIcon,
	useDropdown,
	useTabs = [],
}: UseHeader = {}) => {
	const [context, dispatch] = useContext(HeaderContext);

	const useDropdownRef = useRef(useDropdown);
	const useHeaderActionsRef = useRef(useHeaderActions);
	const useHeadingRef = useRef(useHeading);
	const useTabsRef = useRef(useTabs);

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
		if (shouldUpdate && useHeadingRef.current) {
			actTimeout(() => setHeading(useHeadingRef.current));
		}
	}, [actTimeout, setHeading, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && useIcon) {
			setDropdownIcon(useIcon);
		}
	}, [setDropdownIcon, shouldUpdate, useIcon]);

	useEffect(() => {
		if (shouldUpdate && useTabsRef.current) {
			setTabs(useTabsRef.current);
		}
	}, [setTabs, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && useHeaderActionsRef.current) {
			setHeaderActions(useHeaderActionsRef.current);
		}
	}, [setHeaderActions, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && useDropdownRef.current) {
			setDropdown(useDropdownRef.current);
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
