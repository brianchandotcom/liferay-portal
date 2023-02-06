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

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';
interface IFDSViewsProps {
	addFDSViewURL: string;
	namespace: string;
}

const FDSViews = ({addFDSViewURL, namespace}: IFDSViewsProps) => {
	const creationMenu = {
		primaryItems: [
			{
				href: addFDSViewURL,
				label: Liferay.Language.get('new-dataset'),
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			label: Liferay.Language.get('table'),
			name: 'viewsTable',
			schema: {
				fields: [
					{fieldName: 'name', label: Liferay.Language.get('name')},
					{
						fieldName: 'provider',
						label: Liferay.Language.get('provider'),
					},
					{
						fieldName: 'modifiedDate',
						label: Liferay.Language.get('modified-date'),
					},
				],
			},
			thumbnail: 'table',
		},
	];

	return (
		<FrontendDataSet
			creationMenu={creationMenu}
			id={`${namespace}FDSViews`}
			views={views}
		/>
	);
};

export default FDSViews;
