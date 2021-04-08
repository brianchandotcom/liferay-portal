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

const omit = (obj, props) => {
	const result = {...obj};

	props.forEach((prop) => {
		delete result[prop];
	});

	return result;
};

/**
 * The implementation of parse data tries to omit some data that we already received
 * via props at application startup that are comparable with the data via request,
 * some data is assembled from the props via data representation for the
 * Data Engine structure.
 */
const parseData = ({dataDefinition, dataLayout}) => {
	if (!dataDefinition || !dataLayout) {
		return null;
	}

	const {
		availableLanguageIds,
		description,
		name,
		...dataDefinitions
	} = omit(dataDefinition, ['defaultLanguageId', 'defaultDataLayout']);

	const {paginationMode, ...dataLayouts} = omit(dataLayout, [
		'dataRules',
		'dataLayoutPages',
		'description',
		'name',
	]);

	return {
		availableLanguageIds,
		dataDefinition: dataDefinitions,
		dataLayout: dataLayouts,
		description,
		name,
		paginationMode,
	};
};

export const useData = ({dataDefinitionId, dataLayoutId}) => {
	const {resource: dataDefinition} = useResource({
		fetch,
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`,
	});

	const {resource: dataLayout} = useResource({
		fetch,
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}/o/data-engine/v2.0/data-layouts/${dataLayoutId}`,
	});

	return parseData({dataDefinition, dataLayout});
};
