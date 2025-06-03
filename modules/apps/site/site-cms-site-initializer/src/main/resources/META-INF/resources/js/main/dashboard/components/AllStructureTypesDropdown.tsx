/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

import ApiHelper from '../../../services/ApiHelper';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {buildQueryString} from '../utils/buildQueryString';
import {FilterDropdown, Item} from './FilterDropdown';
import {IAllFiltersDropdown, initialStructure} from './InventoryAnalysisCard';

const AllStructureTypesDropdown: React.FC<IAllFiltersDropdown> = ({
	className,
	item,
	onSelectItem,
}) => {
	const {constants} = useContext(ViewDashboardContext);

	const [structures, setStructures] = useState<Item[]>([initialStructure]);
	const [loading, setLoading] = useState(false);
	const [dropdownActive, setDropdownActive] = useState(false);

	const fetchStructures = async (search: string = '') => {
		const queryParams = buildQueryString({
			filter: `(objectFolderExternalReferenceCode eq '${constants.ercContentStructures}' or objectFolderExternalReferenceCode eq '${constants.ercFileTypes}')`,
			search,
		});

		const endpoint = `/o/object-admin/v1.0/object-definitions${queryParams}`;

		const {data, error} = await ApiHelper.get<{
			items: {id: string; name: string}[];
		}>(endpoint);

		if (data) {
			return data.items.map(({id, name}) => ({
				label: name,
				value: String(id),
			}));
		}

		if (error) {
			console.error(error);
		}

		return [];
	};

	return (
		<FilterDropdown
			active={dropdownActive}
			className={className}
			filterByValue="structures"
			icon="edit-layout"
			items={structures}
			loading={loading}
			onActiveChange={() => setDropdownActive(!dropdownActive)}
			onSearch={async (value) => {
				setLoading(true);

				const structures = await fetchStructures(value);

				setStructures(
					value ? structures : [initialStructure, ...structures]
				);

				setLoading(false);
			}}
			onSelectItem={(item) => {
				onSelectItem(item);

				setDropdownActive(false);
			}}
			onTrigger={async () => {
				setLoading(true);

				const structures = await fetchStructures();

				setStructures([initialStructure, ...structures]);

				setLoading(false);
			}}
			selectedItem={item}
			title={Liferay.Language.get('filter-by-structure-type')}
		/>
	);
};

export {AllStructureTypesDropdown};
