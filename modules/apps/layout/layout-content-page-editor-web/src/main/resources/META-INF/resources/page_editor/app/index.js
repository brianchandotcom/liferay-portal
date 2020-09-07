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

import React, {useEffect} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import App from './components/App';
import {initializeConfig} from './config/index';

let removeChild;

/**
 * LPS-120418 remove child function that doesn't throw a NotFoundError exception.
 * This is done by default in all browser except ie11
 *
 * When mounting the dropzones this error is thrown making the fragment fail,
 * swallowing seems harmless and makes the dropzones work in ie11
 */
function safeRemoveChild() {
	try {
		return removeChild.apply(this, arguments);
	}
	catch (error) {
		if (!!error && !!error.message && error.message === 'NotFoundError') {
			if (process.env.NODE_ENV === 'development') {
				console.warn('IE NotFoundError handled');
			}
		}
		else {
			throw error;
		}
	}
}

/**
 * Default application export.
 *
 * We should define contexts here instead of Container component, as Container
 * is re-rendered when hooks change.
 */
export default function (data) {
	initializeConfig(data.config);

	if (Liferay?.Browser?.isIe()) {
		removeChild = window.HTMLElement.prototype.removeChild;

		window.HTMLElement.prototype.removeChild = safeRemoveChild;
	}

	useEffect(() => {
		return () => {
			if (removeChild) {
				window.HTMLElement.prototype.removeChild = removeChild;
				removeChild = undefined;
			}
		};
	}, []);

	return (
		<DndProvider backend={HTML5Backend}>
			<App state={data.state} />
		</DndProvider>
	);
}
