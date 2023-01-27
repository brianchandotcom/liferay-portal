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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

const ITEM_TYPES_SYMBOL = {
	article: 'document-text',
	folder: 'folder',
};

const SEARCH_DELTA = 2;

export default function SearchField({handleSearchChange, items}) {
	const initialState = {
		filteredItems: [],
		query: '',
	};

	const [state, setState] = useState(initialState);

	const [searchActive, setSearchActive] = useState(false);

	useEffect(() => {
		handleSearchChange({isSearchActive: searchActive});
	}, [handleSearchChange, searchActive]);

	const handleChange = (event) => {
		const newQuery = event.target.value;
		let results = [];

		if (newQuery.length > SEARCH_DELTA) {
			results = items.filter((item) =>
				item.name.toLowerCase().includes(newQuery.toLowerCase())
			);
		}

		setSearchActive(newQuery.length > SEARCH_DELTA);
		setState({
			filteredItems: results,
			query: newQuery,
		});
	};

	const handleClick = () => {
		if (state.query) {
			setState(initialState);
			setSearchActive(false);
		}
	};

	const iconTitle = state.query
		? Liferay.Language.get('clear')
		: Liferay.Language.get('search');

	return (
		<>
			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search-for')}
						className="form-control input-group-inset input-group-inset-after"
						onChange={handleChange}
						placeholder={Liferay.Language.get('search-for')}
						type="text"
						value={state.query}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayButtonWithIcon
							aria-label={iconTitle}
							displayType="unstyled"
							onClick={handleClick}
							small
							symbol={state.query ? 'times' : 'search'}
							title={iconTitle}
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<hr className="separator" />

			{searchActive && state.filteredItems && (
				<ul className="list-group">
					{state.filteredItems.map((item) => {
						return (
							<li className="list-group-item" key={item.id}>
								<ClayIcon
									className="mr-2"
									symbol={ITEM_TYPES_SYMBOL[item.type]}
								/>

								{item.name}
							</li>
						);
					})}
				</ul>
			)}
		</>
	);
}
