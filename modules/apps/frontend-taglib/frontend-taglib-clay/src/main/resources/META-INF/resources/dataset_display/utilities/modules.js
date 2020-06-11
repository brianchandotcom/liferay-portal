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

export function getLiferayJsModule(moduleUrl) {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			moduleUrl,
			(jsModule) => {
				return resolve(jsModule.default || jsModule);
			},
			(err) => {
				return reject(err);
			}
		);
	});
}

export function getFakeJsModule() {
	return new Promise((resolve) => {
		setTimeout(() => {
			resolve(() => {
				return <>fakely fetched component</>;
			});
		}, 500);
	});
}

const getJsModule = Liferay.Loader?.require
	? getLiferayJsModule
	: getFakeJsModule;

export default getJsModule;
