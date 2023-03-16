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

import {FDSCellRenderer} from '@liferay/js-api/data-set';
import React from 'react';

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

export type DataRendererType = 'clientExtension' | 'internal';

export interface DataRenderer {
	type: DataRendererType;
}

export interface InternalDataRenderer extends DataRenderer {
	Component: React.ComponentClass<any>;
	type: 'internal';
}

export interface ClientExtensionDataRenderer extends DataRenderer {
	renderer: FDSCellRenderer;
	type: 'clientExtension';
}

export type AnyDataRenderer =
	| ClientExtensionDataRenderer
	| InternalDataRenderer;

const fetchedDataRenderers: Array<{
	dataRenderer: AnyDataRenderer;
	url: string;
}> = [];

export async function getDataRendererByURL(
	url: string
): Promise<AnyDataRenderer> {
	const addedDataRenderer = fetchedDataRenderers.find(
		(contentRenderer) => contentRenderer.url === url
	);

	if (addedDataRenderer) {
		return addedDataRenderer.dataRenderer;
	}

	let dataRenderer: AnyDataRenderer;

	if (url.includes(' from ')) {
		const [moduleName, symbolName] = getModuleAndSymbolNames(url);

		// @ts-ignore
		const module = await import(/* webpackIgnore: true */ moduleName);

		dataRenderer = {
			renderer: module[symbolName],
			type: 'clientExtension',
		};
	}
	else {
		dataRenderer = {
			Component: await getJsModule(url),
			type: 'internal',
		};
	}

	fetchedDataRenderers.push({
		dataRenderer,
		url,
	});

	return dataRenderer;
}

function getModuleAndSymbolNames(url: string): [string, string] {
	const parts = url.split(' from ');

	const moduleName = parts[1].trim();
	let symbolName = parts[0].trim();

	if (
		symbolName !== 'default' &&
		(!symbolName.startsWith('{') || !symbolName.endsWith('}'))
	) {
		throw new Error(`Invalid data renderer URL: ${url}`);
	}

	if (symbolName.startsWith('{')) {
		symbolName = symbolName.substring(1, symbolName.length - 1).trim();
	}

	return [moduleName, symbolName];
}
