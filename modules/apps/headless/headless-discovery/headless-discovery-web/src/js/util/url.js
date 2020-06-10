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

export const getCategoryURL = (categories, categoryKey) => {
	return categories && categoryKey && categories
		? categories[categoryKey][0]
		: '';
};

export const getBaseURL = (categoryURL) => {
	return categoryURL.replace('/v1.0/openapi.json', '');
};

export const getSearchParams = (params = [], values) => {
	var searchParams = new URLSearchParams();

	params.forEach((param) => {
		const value = values[param.name];

		if (param.in === 'query' && value) {
			searchParams.set(param.name, value);
		}
	});

	const paramsString = searchParams.toString();

	return paramsString.length > 0 ? `?${searchParams.toString()}` : '';
};

export const getURL = ({baseURL, params, path, values}) => {
	return (
		baseURL +
		replaceParams(path, params, values) +
		getSearchParams(params, values)
	);
};

export const replaceParams = (path, params, values) => {
	if (params) {
		params.forEach((param) => {
			if (param.in === 'path') {
				path = path.replace(`{${param.name}}`, values[param.name]);
			}
		});
	}

	return path;
};
