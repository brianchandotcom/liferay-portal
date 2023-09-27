/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import pathToRegexp from 'path-to-regexp';
import React from 'react';

import {FilterContextProvider} from '../filter/FilterContext.es';

export function withParams(...components) {
	return ({history, location: {search}, match: {params}}) => {
		return (
			<FilterContextProvider>
				{components.map((Component, index) => {
					if (params.sort) {
						params.sort = decodeURIComponent(params.sort);
					}

					return (
						<Component
							{...params}
							history={history}
							key={index}
							query={search}
							routeParams={params}
						/>
					);
				})}
			</FilterContextProvider>
		);
	};
}

export function getPathname(params, path) {
	return pathToRegexp.compile(path)(params);
}
