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

import {useResource} from '@clayui/data-provider';
import {fetch} from 'frontend-js-web';

import {useStorage} from './useStorage.es';

const ENDPOINT_FIELD_TYPES = `${
	window.location.origin
}${themeDisplay.getPathContext()}/o/dynamic-data-mapping-form-field-types`;

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

export const useFieldTypesResource = () => {
	const storage = useStorage();

	return useResource({
		fetch: (url, options) => fetch(url, options).then((res) => res.json()),
		fetchOptions: {
			headers: HEADERS,
		},
		fetchPolicy: 'cache-first',
		link: ENDPOINT_FIELD_TYPES,
		storage,
		variables: {
			p_auth: Liferay.authToken,
		},
	});
};
