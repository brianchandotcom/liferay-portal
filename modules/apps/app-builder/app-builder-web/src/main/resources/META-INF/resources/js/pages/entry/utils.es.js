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

import React from 'react';
import {Link} from 'react-router-dom';

import {toQuery, toQueryString} from '../../hooks/useQuery.es';
import {FieldValuePreview} from './FieldPreview.es';

export function buildEntries({
	dataDefinition,
	fieldNames = [],
	permissions,
	scope,
}) {
	const query = toQuery(
		window.location.search,
		{
			keywords: '',
			page: 1,
			pageSize: 20,
			sort: '',
		},
		scope
	);

	return ({dataRecordValues = {}, ...entry}, index) => {
		const entryIndex = query.pageSize * (query.page - 1) + index + 1;

		const viewURL = `/entries/${entryIndex}?${toQueryString({
			...query,
			backURL: window.location.href,
		})}`;

		const displayedDataRecordValues = {};

		fieldNames.forEach((fieldName, columnIndex) => {
			let fieldValuePreview = (
				<FieldValuePreview
					dataDefinition={dataDefinition}
					dataRecordValues={dataRecordValues}
					displayType="list"
					fieldName={fieldName}
				/>
			);

			if (columnIndex === 0 && permissions.view) {
				fieldValuePreview = (
					<Link to={viewURL}>{fieldValuePreview}</Link>
				);
			}

			displayedDataRecordValues[
				'dataRecordValues/' + fieldName
			] = fieldValuePreview;
		});

		return {
			...displayedDataRecordValues,
			...entry,
			viewURL,
		};
	};
}

export function navigateToEditPage(basePortletURL, params = {}) {
	Liferay.Util.navigate(
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataRecordId: 0,
			mvcPath: '/edit_entry.jsp',
			...params,
		})
	);
}
