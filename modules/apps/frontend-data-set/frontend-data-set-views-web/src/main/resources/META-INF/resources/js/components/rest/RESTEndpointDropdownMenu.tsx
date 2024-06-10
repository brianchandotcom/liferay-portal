/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import fuzzy from 'fuzzy';
import React, {useState} from 'react';

import {FUZZY_OPTIONS} from '../../utils/constants';

export default function RESTEndpointDropdownMenu({
	className,
	onItemClick,
	restEndpoints: initialRESTEndpoints,
}: {
	className?: string;
	onItemClick: Function;
	restEndpoints: Array<string>;
}) {
	const [restEndpoints, setRESTEndpoints] = useState<Array<string>>(
		initialRESTEndpoints || []
	);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setRESTEndpoints(
			query
				? initialRESTEndpoints.filter((restEndpoint) => {
						return restEndpoint.match(regexp);
					}) || []
				: initialRESTEndpoints
		);
	};

	return (
		<>
			<ClayDropDown.Search
				aria-label={Liferay.Language.get('search')}
				className={className}
				onChange={onSearch}
				value={query}
			/>

			<ClayDropDown.ItemList items={restEndpoints} role="listbox">
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
