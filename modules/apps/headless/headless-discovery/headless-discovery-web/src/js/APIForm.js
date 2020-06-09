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

import React, {useMemo} from 'react';

import APIFormBase from './APIFormBase';
import {useAppState} from './hooks/appState';
import {getBaseURL, getCategoryURL} from './util/url';
import {getSchemaType} from './util/util';

const getContentType = (requestBody) =>
	requestBody
		? requestBody.content['multipart/form-data']
			? 'multipart/form-data'
			: requestBody.content['application/json']
			? 'application/json'
			: null
		: null;

const APIForm = (_) => {
	const [state, dispatch] = useAppState();

	const {
		categories,
		categoryKey,
		headers,
		method,
		path,
		paths,
		schemas,
	} = state;

	const methodData = paths[path][method];

	const {operationId, requestBody} = methodData;

	const schema = schemas[getSchemaType(requestBody)];

	const baseURL = getBaseURL(getCategoryURL(categories, categoryKey));

	const contentType = useMemo(() => getContentType(requestBody), [
		requestBody,
	]);

	return (
		<APIFormBase
			baseURL={baseURL}
			contentType={contentType}
			headers={headers}
			key={operationId}
			method={method}
			methodData={methodData}
			onResponse={({apiURL, data, response}) => {
				dispatch({
					apiURL,
					contentType,
					data,
					response,
					type: 'LOAD_API_RESPONSE',
				});
			}}
			path={path}
			schema={schema}
		/>
	);
};

export default APIForm;
