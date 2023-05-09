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
import {ComponentType} from 'react';

// @ts-ignore

import InputCheckboxRenderer from '../cell_renderers/InputCheckboxRenderer';

// @ts-ignore

import InputDateTimeRenderer from '../cell_renderers/InputDateTimeRenderer';

// @ts-ignore

import InputTextRenderer from '../cell_renderers/InputTextRenderer';
import {InternalCellRenderer} from '../cell_renderers/InternalCellRenderer';

// @ts-ignore

import {getJsModule} from './modules';

const INPUT_RENDERERS: {[key: string]: ComponentType} = {
	checkbox: InputCheckboxRenderer,
	dateTime: InputDateTimeRenderer,
	text: InputTextRenderer,
};

export function getInputRendererById(id: string): ComponentType {
	const inputRenderer = INPUT_RENDERERS[id];

	if (!inputRenderer) {
		throw new Error(`No input renderer found with id "${id}"`);
	}

	return inputRenderer;
}

export interface Renderer {
	type: 'clientExtension' | 'internal';
}

export interface ClientExtensionCellRenderer extends Renderer {
	renderer: FDSCellRenderer;
	type: 'clientExtension';
}

export type CellRenderer = ClientExtensionCellRenderer | InternalCellRenderer;

const fetchedDataRenderers: Array<{
	cellRenderer: CellRenderer;
	url: string;
}> = [];

export async function getCellRendererByURL(
	url: string,
	type: 'clientExtension' | 'internal'
): Promise<CellRenderer> {
	const addedCellRenderer = fetchedDataRenderers.find(
		(contentRenderer) => contentRenderer.url === url
	);

	if (addedCellRenderer) {
		return addedCellRenderer.cellRenderer;
	}

	let cellRenderer: CellRenderer;

	if (url.includes(' from ')) {
		const [moduleName, symbolName] = getModuleAndSymbolNames(url);

		// @ts-ignore
		const module = await import(/* webpackIgnore: true */ moduleName);

		if (type === 'clientExtension') {
			cellRenderer = {
				renderer: module[symbolName],
				type,
			};
		}
		else {
			cellRenderer = {
				component: module[symbolName],
				type,
			};
		}
	}
	else {
		cellRenderer = {
			component: await getJsModule(url),
			type: 'internal',
		};
	}

	fetchedDataRenderers.push({
		cellRenderer,
		url,
	});

	return cellRenderer;
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
