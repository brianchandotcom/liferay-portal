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

import dataRenderers, {inputRenderers} from '../data_renderers/index';
import {getJsModule} from './modules';

export function getInputRendererById(id) {
	const inputRenderer = inputRenderers[id];

	if (!inputRenderer) {
		throw new Error(`No input renderer found with id "${id}"`);
	}

	return inputRenderer;
}

export function getDataRendererById(id) {
	const dataRenderer = dataRenderers[id];

	if (!dataRenderer) {
		throw new Error(`No data renderer found with id "${id}"`);
	}

	return dataRenderer;
}

export const fetchedContentRenderers = [];

export function getDataRendererByURL(url) {
	return new Promise((resolve, reject) => {
		const addedDataRenderer = fetchedContentRenderers.find(
			(contentRenderer) => contentRenderer.url === url
		);
		if (addedDataRenderer) {
			resolve(addedDataRenderer.component);
		}

		return getJsModule(url)
			.then((fetchedComponent) => {
				fetchedContentRenderers.push({
					component: fetchedComponent,
					url,
				});

				return resolve(fetchedComponent);
			})
			.catch(reject);
	});
}
