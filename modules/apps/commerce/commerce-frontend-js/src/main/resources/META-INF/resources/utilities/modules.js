/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {loadModule} from 'frontend-js-web';
import React from 'react';

export function getFakeJsModule() {
	return new Promise((resolve) => {
		setTimeout(
			() =>
				resolve(() => (
					<div className="custom-component">
						fakely fetched component
					</div>
				)),
			3000
		);
	});
}

export const getJsModule = Liferay.Loader?.require
	? loadModule
	: getFakeJsModule;

export const fetchedJsModules = [];

export function getComponentByModuleUrl(url) {
	return new Promise((resolve, reject) => {
		const foundModule = fetchedJsModules.find((cr) => cr.url === url);
		if (foundModule) {
			resolve(foundModule.component);
		}

		return loadModule(url)
			.then((fetchedComponent) => {
				fetchedJsModules.push({
					component: fetchedComponent,
					url,
				});

				return resolve(fetchedComponent);
			})
			.catch(reject);
	});
}
