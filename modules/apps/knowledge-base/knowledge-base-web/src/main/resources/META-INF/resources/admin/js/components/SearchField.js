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
import React, {useState} from 'react';

const ITEM_TYPES_SYMBOL = {
	article: 'document-text',
	folder: 'folder',
};

export default function SearchField({items}) {
	const [state, setState] = useState({
		filteredItems: [],
		query: '',
	});

	const handleChange = (event) => {
		const newQuery = event.target.value;

		let results;

		if (newQuery === '') {
			results = items;
		}
		else {
			results = items.filter((item) =>
				item.name.toLowerCase().includes(newQuery.toLowerCase())
			);
		}

		setState({
			filteredItems: results,
			query: newQuery,
		});
	};

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
							aria-label={Liferay.Language.get(
								state.query ? 'times' : 'search'
							)}
							displayType="unstyled"
							small
							symbol={state.query ? 'times' : 'search'}
							title={Liferay.Language.get('clear')}
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<hr className="separator" />

			{state.filteredItems && (
				<ul className="list-group">
					{state.filteredItems.map((item) => {
						return (
							<li className="list-group-item" key={item.id}>
								<ClayIcon
									className='mr-2'
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
