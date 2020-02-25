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

import {SearchInput} from 'data-engine-taglib';
import React, {useState} from 'react';

import {useRequest} from '../../hooks/index.es';
import ListItems from './ListItems.es';

export default ({endpoint, title, ...restProps}) => {
	const [searchText, setSearchText] = useState('');

	const {
		response: {items = []},
		isLoading,
	} = useRequest(endpoint);

	const filteredItems = items.filter(item =>
		new RegExp(searchText, 'ig').test(item.name.en_US)
	);

	return (
		<>
			<div className="autofit-row mb-4 pl-4 pr-4">
				<div className="autofit-col-expand">
					<h2>{title}</h2>
				</div>
			</div>

			<div className="autofit-row mb-4 pl-4 pr-4">
				<div className="autofit-col-expand">
					<SearchInput
						onChange={searchText => setSearchText(searchText)}
					/>
				</div>
			</div>

			<div className="autofit-row pl-4 pr-4 scrollable-container">
				<div className="autofit-col-expand">
					<ListItems
						isEmpty={filteredItems.length === 0}
						isLoading={isLoading}
						items={filteredItems}
						keywords={searchText}
						{...restProps}
					/>
				</div>
			</div>
		</>
	);
};
