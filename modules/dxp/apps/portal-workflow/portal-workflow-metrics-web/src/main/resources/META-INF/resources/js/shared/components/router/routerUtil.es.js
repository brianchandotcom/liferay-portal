/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import pathToRegexp from 'path-to-regexp';
import React from 'react';

export const withParams = (...components) => ({
	history,
	location: {search},
	match: {params}
}) => {
	return components.map((Component, index) => {
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
	});
};

export function getPathname(params, path) {
	return pathToRegexp.compile(path)(params);
}
