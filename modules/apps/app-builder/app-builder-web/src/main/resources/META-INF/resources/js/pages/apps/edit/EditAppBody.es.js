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

import ClayLayout from '@clayui/layout';
import {SearchInput} from 'data-engine-taglib';
import React, {useState} from 'react';

import {useRequest} from '../../../hooks/index.es';
import {getLocalizedValue} from '../../../utils/lang.es';
import ListItems from './ListItems.es';

export default ({defaultLanguageId, endpoint, title, ...restProps}) => {
	const [searchText, setSearchText] = useState('');

	const {
		response: {items = []},
		isLoading,
	} = useRequest(endpoint);

	const filteredItems = items.filter((item) =>
		new RegExp(searchText, 'ig').test(
			getLocalizedValue(defaultLanguageId, item.name)
		)
	);

	return (
		<>
			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol expand>
					<h2>{title}</h2>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol expand>
					<SearchInput
						onChange={(searchText) => setSearchText(searchText)}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="pl-4 pr-4 scrollable-container">
				<ClayLayout.ContentCol expand>
					<ListItems
						defaultLanguageId={defaultLanguageId}
						isEmpty={filteredItems.length === 0}
						isLoading={isLoading}
						items={filteredItems}
						keywords={searchText}
						{...restProps}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</>
	);
};
