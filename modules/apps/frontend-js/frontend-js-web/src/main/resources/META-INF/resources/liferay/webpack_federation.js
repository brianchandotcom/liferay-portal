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

Liferay = window.Liferay || {};

Liferay.Webpack = {
	Container: {},
	SharedScope: {},
};

Liferay.require = (moduleName) =>
	new Promise((resolve, reject) => {
		const i = moduleName.indexOf('/');

		let containerId, path;

		if (i === -1) {
			containerId = moduleName;
			path = '.';
		}
		else {
			containerId = moduleName.substring(0, i);
			path = moduleName.substring(i + 1);
		}

		let container = Liferay.Webpack.Container[containerId];

		if (container) {
			Promise.resolve(container.get(path)).then((module) =>
				resolve(module())
			);
		}
		else {
			const script = document.createElement('script');

			script.src = '/o/' + containerId + '/__generated__/container.js';

			script.onload = () => {
				container = Liferay.Webpack.Container[containerId];

				if (container) {
					Promise.resolve(container.init(Liferay.Webpack.SharedScope))
						.then(() => container.get(path))
						.then((module) => resolve(module()));
				}
				else {
					const message =
						'Container ' +
						containerId +
						' was fetched but ' +
						'did not define itself in Liferay.Webpack.Container';

					console.warn(message);

					reject(new Error(message));
				}
			};

			document.body.appendChild(script);
		}
	});
