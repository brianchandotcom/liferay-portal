/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TRenderer} from './types';

const fetchedRenderers: Array<TRenderer> = [];

async function getRenderer({
	type,
	url,
}: {
	type: 'clientExtension' | 'internal';
	url: string;
}): Promise<TRenderer> {
	const fetchedRenderer = fetchedRenderers.find(
		(renderer) => renderer.url === url
	);

	if (fetchedRenderer) {
		return fetchedRenderer;
	}

	let renderer: TRenderer;

	if (url.includes(' from ')) {
		const [moduleName, symbolName] = getModuleAndSymbolNames(url);

		// @ts-ignore
		const module = await import(/* webpackIgnore: true */ moduleName);

		if (type === 'clientExtension') {
			renderer = {
				htmlElementBuilder: module[symbolName],
				type,
			};
		}
		else {
			renderer = {
				component: module[symbolName],
				type,
			};
		}
	}
	else {
		renderer = {
			component: (await getRendererComponent(url)) as React.ComponentType<
				any
			>,
			type: 'internal',
		};
	}

	renderer.url = url;

	fetchedRenderers.push(renderer);

	return renderer;
}

function getRendererComponent(url: string): Promise<any> {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			url,
			(jsModule: any) => resolve(jsModule.default || jsModule),
			(error: string) => reject(error)
		);
	});
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

export default getRenderer;
