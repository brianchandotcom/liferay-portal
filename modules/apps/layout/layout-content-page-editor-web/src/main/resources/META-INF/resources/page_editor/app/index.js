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

import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import App from './components/App';
import {initializeConfig} from './config/index';

/**
 * Default application export.
 *
 * We should define contexts here instead of Container component, as Container
 * is re-rendered when hooks change.
 */
export default function (data) {
	initializeConfig(data.config);

	return (
		<DndProvider backend={HTML5Backend}>
			<App state={data.state} />
		</DndProvider>
	);
}
