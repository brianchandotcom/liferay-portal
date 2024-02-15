/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import {useDispatch} from '../../contexts/StoreContext';
import switchSidebarPanel from '../../thunks/switchSidebarPanel';

export default function useDetectSmallResolution() {
	const dispatch = useDispatch();

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPD-10988']) {
			return;
		}

		const onChange = (event) => {
			document.body.classList.toggle(
				'page-editor__small-resolution',
				event.matches
			);

			dispatch(
				switchSidebarPanel({
					itemConfigurationOpen: !event.matches,
					sidebarOpen: !event.matches,
				})
			);
		};

		const mediaQuery = window.matchMedia('(max-width: 768px)');

		if (mediaQuery.matches) {
			document.body.classList.toggle(
				'page-editor__small-resolution',
				true
			);

			dispatch(
				switchSidebarPanel({
					itemConfigurationOpen: false,
					sidebarOpen: false,
				})
			);
		}

		mediaQuery.addEventListener('change', onChange);

		return () => mediaQuery.removeEventListener('change', onChange);
	}, [dispatch]);
}
