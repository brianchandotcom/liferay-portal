/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

import RESTApplicationDropdownItem from './RESTApplicationDropdownItem';

export default function RESTApplicationDropdownMenu({
	className,
	onItemClick,
	restApplications: initialRESTApplications,
}: {
	className?: string;
	onItemClick: Function;
	restApplications: Array<string>;
}) {
	const [restApplications, setRESTApplications] = useState<Array<string>>(
		initialRESTApplications || []
	);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setRESTApplications(
			query
				? initialRESTApplications.filter((restApplication) =>
						restApplication.match(regexp)
					) || []
				: initialRESTApplications
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

			<ClayDropDown.ItemList items={restApplications} role="listbox">
				{(item: string) => (
					<ClayDropDown.Item
						key={item}
						onClick={() => onItemClick(item)}
						roleItem="option"
					>
						<RESTApplicationDropdownItem
							query={query}
							restApplication={item}
						/>
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</>
	);
}
