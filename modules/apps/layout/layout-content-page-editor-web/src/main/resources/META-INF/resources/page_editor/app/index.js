/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import App from './components/App';
import {initializeConfig} from './config/index';
import {disposeCache, initializeCache} from './utils/cache';

/**
 * Default application export.
 *
 * We should define contexts here instead of Container component, as Container
 * is re-rendered when hooks change.
 */
export default function (data) {
	initializeConfig(data.config);

	initializeCache();

	useEffect(() => {
		return () => {
			disposeCache();
		};
	}, []);

	return (
		<DndProvider backend={HTML5Backend}>
			<App state={data.state} />
		</DndProvider>
	);
}
