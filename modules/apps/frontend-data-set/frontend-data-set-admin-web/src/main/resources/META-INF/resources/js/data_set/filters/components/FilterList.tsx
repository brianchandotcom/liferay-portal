/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import OrderableTable from '../../../components/OrderableTable';
import Toggle from '../../../components/Toggle';
import {FDS_ORDER_BY_ERC_FEATURE_FLAG_KEY} from '../../../utils/constants';
import {
	EFilterType,
	ESelectionFilterSourceType,
	IFilter,
	IFilterTypeProps,
} from '../../../utils/types';

const isVisible = ({item}: {item: any}): boolean => {
	return item?.sourceType !== ESelectionFilterSourceType.ITEM_PROXY;
};

const FilterList = ({
	createFilter,
	deleteFilter,
	editFilter,
	filterTypes,
	filters,
	toogleActiveDisabled,
	updateActive,
	updateFiltersOrder,
}: {
	createFilter: (filterType: EFilterType) => void;
	deleteFilter: ({item}: {item: IFilter}) => void;
	editFilter: ({item}: {item: IFilter}) => void;
	filterTypes: Record<EFilterType, IFilterTypeProps>;
	filters: IFilter[];
	toogleActiveDisabled: boolean;
	updateActive: (item: IFilter) => Promise<void>;
	updateFiltersOrder: ({filtersOrder}: {filtersOrder: string}) => void;
}) => {
	return (
		<OrderableTable
			actions={[
				{
					icon: 'pencil',
					isVisible,
					label: Liferay.Language.get('edit'),
					onClick: editFilter,
				},
				{
					icon: 'trash',
					isVisible,
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
				{
					contentRenderer: {
						component: ({item}: any) =>
							Toggle({
								disabled: toogleActiveDisabled,
								item,
								toggleChange: updateActive,
							}),
					},
					label: Liferay.Language.get('status'),
					name: 'active',
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
			orderByERC={
				!!Liferay.FeatureFlags?.[FDS_ORDER_BY_ERC_FEATURE_FLAG_KEY]
			}
			title={Liferay.Language.get('filters')}
		/>
	);
};

export default FilterList;
