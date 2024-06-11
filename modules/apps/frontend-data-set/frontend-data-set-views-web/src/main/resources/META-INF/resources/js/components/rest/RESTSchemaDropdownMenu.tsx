/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import fuzzy from 'fuzzy';
import React, {useState} from 'react';

import {FUZZY_OPTIONS} from '../../utils/constants';

export default function RESTSchemaDropdownMenu({
	onItemClick,
	restSchemas: initialRESTSchemas,
}: {
	onItemClick: Function;
	restSchemas: Array<string>;
}) {
	const [restSchemas, setRESTSchemas] =
		useState<Array<string>>(initialRESTSchemas);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setRESTSchemas(
			query
				? initialRESTSchemas.filter((restSchema) => {
						return restSchema.match(regexp);
					}) || []
				: initialRESTSchemas
		);
	};

	return (
		<>
			<ClayDropDown.Search
				aria-label={Liferay.Language.get('search')}
				onChange={onSearch}
				value={query}
			/>

			<ClayDropDown.ItemList items={restSchemas} role="listbox">
				{(item: string) => {
					const fuzzymatch = fuzzy.match(query, item, FUZZY_OPTIONS);

					return (
						<ClayDropDown.Item
							key={item}
							onClick={() => onItemClick(item)}
							roleItem="option"
						>
							{fuzzymatch ? (
								<span
									dangerouslySetInnerHTML={{
										__html: fuzzymatch.rendered,
									}}
								/>
							) : (
								item
							)}
						</ClayDropDown.Item>
					);
				}}
			</ClayDropDown.ItemList>
		</>
	);
}
