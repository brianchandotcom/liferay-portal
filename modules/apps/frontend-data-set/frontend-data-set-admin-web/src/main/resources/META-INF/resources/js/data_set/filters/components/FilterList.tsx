/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import OrderableTable from '../../../components/OrderableTable';
import {EFilterType, IFilter, IFilterTypeProps} from '../../../utils/types';

const FilterList = ({
	createFilter,
	deleteFilter,
	editFilter,
	filterTypes,
	filters,
	updateFiltersOrder,
}: {
	createFilter: (filterType: EFilterType) => void;
	deleteFilter: ({item}: {item: IFilter}) => void;
	editFilter: ({item}: {item: IFilter}) => void;
	filterTypes: Record<EFilterType, IFilterTypeProps>;
	filters: IFilter[];
	updateFiltersOrder: ({filtersOrder}: {filtersOrder: string}) => void;
}) => {
	return (
		<OrderableTable
			actions={[
				{
					icon: 'pencil',
					label: Liferay.Language.get('edit'),
					onClick: editFilter,
				},
				{
					icon: 'trash',
					label: Liferay.Language.get('delete'),
					onClick: deleteFilter,
				},
			]}
			className="fds-admin-filter-list"
			creationMenuItems={Object.keys(filterTypes).map((type) => ({
				label: filterTypes[type as EFilterType].label,
				onClick: () => createFilter(type as EFilterType),
			}))}
			creationMenuLabel={Liferay.Language.get('new-filter')}
			fields={[
				{
					label: Liferay.Language.get('name'),
					name: 'label',
				},
				{
					label: Liferay.Language.get('Field Name'),
					name: 'fieldName',
				},
				{
					label: Liferay.Language.get('type'),
					name: 'displayType',
				},
			]}
			items={filters}
			noItemsButtonLabel={Liferay.Language.get('new-filter')}
			noItemsDescription={Liferay.Language.get(
				'start-creating-a-filter-to-display-specific-data'
			)}
			noItemsTitle={Liferay.Language.get(
				'no-default-filters-were-created'
			)}
			onOrderChange={({order}: {order: string}) => {
				updateFiltersOrder({filtersOrder: order});
			}}
			title={Liferay.Language.get('filters')}
		/>
	);
};

export default FilterList;
