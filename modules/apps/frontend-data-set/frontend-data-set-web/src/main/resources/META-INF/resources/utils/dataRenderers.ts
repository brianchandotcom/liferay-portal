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

// @ts-ignore

import {dataRenderers, inputRenderers} from '../data_renderers/index';

// @ts-ignore

import {getJsModule} from './modules';

export function getInputRendererById(id: string): any {
	const inputRenderer = inputRenderers[id];

	if (!inputRenderer) {
		throw new Error(`No input renderer found with id "${id}"`);
	}

	return inputRenderer;
}

export function getDataRendererById(id: string): any {
	const dataRenderer = dataRenderers[id];

	if (!dataRenderer) {
		throw new Error(`No data renderer found with id "${id}"`);
	}

	return dataRenderer;
}

export const fetchedContentRenderers: Array<{
	component: React.ComponentClass<any>;
	url: string;
}> = [];

export async function getDataRendererByURL(url: string): Promise<any> {
	const addedDataRenderer = fetchedContentRenderers.find(
		(contentRenderer) => contentRenderer.url === url
	);
	if (addedDataRenderer) {
		return addedDataRenderer.component;
	}

	const fetchedComponent = await getJsModule(url);

	fetchedContentRenderers.push({
		component: fetchedComponent,
		url,
	});

	return fetchedComponent;
}
