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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import './ManagementToolbarSearch.scss';

interface IProps {
	query: string;
	setQuery: (value: string) => void;
}

export function ManagementToolbarSearch({query, setQuery}: IProps) {
	return (
		<ClayManagementToolbar.Search
			onSubmit={(event) => event.preventDefault()}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label="Search"
						className="form-control input-group-inset input-group-inset-after"
						onChange={({target}) => {
							setQuery(target.value);
						}}
						placeholder="Search"
						type="text"
						value={query}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayIcon
							className="lfr-object__object-view-management-toolbar-search-icon"
							symbol="search"
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayManagementToolbar.Search>
	);
}
