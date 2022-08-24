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

import {useEffect, useRef} from 'react';

import {switchSidebarPanel} from '../../actions/index';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectSidebarIsOpened from '../../selectors/selectSidebarIsOpened';
import hideProductMenuIfPresent from '../../utils/hideProductMenuIfPresent';

export default function useProductMenuHandler() {
	const dispatch = useDispatch();
	const lastSidebarStateRef = useRef(true);

	const sidebarOpen = useSelector(selectSidebarIsOpened);
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);

	useEffect(() => {
		if (Liferay.FeatureFlags['LPS-153452']) {
			hideProductMenuIfPresent({
				onHide: () => {
					dispatch(switchSidebarPanel({sidebarOpen: true}));
				},
			});
		}
		else {
			const sideNavigation = Liferay.SideNavigation?.instance(
				document.querySelector('.product-menu-toggle')
			);

			if (!sideNavigation?.visible()) {
				dispatch(switchSidebarPanel({sidebarOpen: true}));
			}
		}
	}, [dispatch]);

	useEffect(() => {
		const sideNavigation = Liferay.SideNavigation?.instance(
			document.querySelector('.product-menu-toggle')
		);

		const onProductMenuChange = ({open}) => {
			if (sidebarHidden) {
				return;
			}

			if (open) {
				lastSidebarStateRef.current = sidebarOpen;

				dispatch(
					switchSidebarPanel({
						itemConfigurationOpen: false,
						sidebarOpen: false,
					})
				);
			}
			else {
				dispatch(
					switchSidebarPanel({
						itemConfigurationOpen: true,
						sidebarOpen: lastSidebarStateRef.current,
					})
				);
			}
		};

		const closeSideNavigationListener = sideNavigation?.on(
			'closed.lexicon.sidenav',
			() => onProductMenuChange({open: false})
		);
		const openSideNavigationListener = sideNavigation?.on(
			'openStart.lexicon.sidenav',
			() => onProductMenuChange({open: true})
		);

		return () => {
			closeSideNavigationListener?.removeListener();
			openSideNavigationListener?.removeListener();
		};
	}, [dispatch, sidebarOpen, sidebarHidden]);
}
