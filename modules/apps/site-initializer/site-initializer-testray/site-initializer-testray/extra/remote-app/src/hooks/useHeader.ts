/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useAtom} from 'jotai';
import {useCallback, useEffect, useRef} from 'react';
import {
	Dropdown,
	HeaderActions,
	HeaderTabs,
	HeaderTitle,
	headerAtom,
} from '~/atoms';

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
	const dropdownRef = useRef(dropdown);
	const headerActionsRef = useRef(headerActions);
	const headingRef = useRef(heading);
	const tabsRef = useRef(tabs);

	const [, setDropdownAtom] = useAtom(headerAtom.dropdown);
	const [, setHeaderActionsAtom] = useAtom(headerAtom.headerActions);
	const [, setHeadingAtom] = useAtom(headerAtom.heading);
	const [, setSymbolAtom] = useAtom(headerAtom.symbol);
	const [, setTabsAtom] = useAtom(headerAtom.tabs);

	const actTimeout = useCallback(
		(fn: () => void) => {
			if (shouldUpdate) {
				setTimeout(() => fn(), timeout);
			}
		},
		[shouldUpdate, timeout]
	);

	const setHeaderActions = useCallback(
		(newActions: HeaderActions) => {
			actTimeout(() => setHeaderActionsAtom(newActions));
		},
		[actTimeout, setHeaderActionsAtom]
	);

	const setHeading = useCallback(
		(newHeading: HeaderTitle[] = [], append = false) => {
			actTimeout(() => {
				setHeadingAtom((prevHeading) =>
					append ? [...prevHeading, ...newHeading] : newHeading
				);
			});
		},
		[actTimeout, setHeadingAtom]
	);

	const setTabs = useCallback(
		(newTabs: HeaderTabs[] = []) => actTimeout(() => setTabsAtom(newTabs)),
		[actTimeout, setTabsAtom]
	);

	useEffect(() => {
		if (shouldUpdate && headingRef.current) {
			actTimeout(() => setHeading(headingRef.current));
		}
	}, [actTimeout, setHeading, shouldUpdate]);

	useEffect(() => {
		if (shouldUpdate && icon) {
			setSymbolAtom(icon);
		}
	}, [setSymbolAtom, shouldUpdate, icon]);

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
			setDropdownAtom(dropdownRef.current);
		}
	}, [setDropdownAtom, shouldUpdate]);

	return {
		setDropdown: setDropdownAtom,
		setDropdownIcon: setSymbolAtom,
		setHeaderActions,
		setHeading,
		setTabs,
	};
};

export {useHeader};

export default useHeader;
