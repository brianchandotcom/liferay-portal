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

import ClayLabel from '@clayui/label';
import {ClayResultsBar} from '@clayui/management-toolbar';
import React, {useContext} from 'react';

import {FILTER_NAMES} from '../../pages/apps/constants.es';
import {sub} from '../../utils/lang.es';
import {concatValues} from '../../utils/utils.es';
import Button from '../button/Button.es';
import SearchContext from './SearchContext.es';

const FilterItem = ({filterKey, name, value}) => {
	const [, dispatch] = useContext(SearchContext);

	return (
		<ClayResultsBar.Item>
			<ClayLabel
				className="tbar-label"
				closeButtonProps={{
					onClick: () => dispatch({filterKey, type: 'REMOVE_FILTER'}),
				}}
				displayType="unstyled"
			>
				<span className="label-section">
					{`${FILTER_NAMES[name][0]}: `}
					<span className="font-weight-normal">{value}</span>
				</span>
			</ClayLabel>
		</ClayResultsBar.Item>
	);
};

export const getSelectedFilters = (filters, appliedFilters) => {
	const selectedFilters = [];

	Object.keys(appliedFilters).forEach((filterKey) => {
		const filter = filters.find(({key}) => filterKey === key);
		const {items = [], key, name} = filter || {};

		const selectedItems = items.filter(({value}) => {
			return Array.isArray(appliedFilters[key])
				? appliedFilters[key].includes(value)
				: appliedFilters[key] === value;
		});

		const value = concatValues(selectedItems.map(({label}) => label));

		selectedFilters.push({
			filterKey,
			name,
			value,
		});
	});

	return selectedFilters;
};

export default ({filters = [], isLoading, totalCount}) => {
	const [{filters: appliedFilters = {}, keywords}, dispatch] = useContext(
		SearchContext
	);

	const selectedFilters = getSelectedFilters(filters, appliedFilters);

	return (
		<>
			{(keywords || selectedFilters.length > 0) && !isLoading && (
				<ClayResultsBar>
					<ClayResultsBar.Item>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{sub(Liferay.Language.get('x-results-for-x'), [
									totalCount,
									keywords,
								])}
							</span>
						</span>
					</ClayResultsBar.Item>

					{selectedFilters.map((filter, key) => (
						<FilterItem key={key} {...filter} />
					))}

					<ClayResultsBar.Item expand>
						<div className="tbar-section text-right">
							<Button
								className="component-link tbar-link"
								displayType="unstyled"
								onClick={() => dispatch({type: 'CLEAR'})}
							>
								{Liferay.Language.get('clear-all')}
							</Button>
						</div>
					</ClayResultsBar.Item>
				</ClayResultsBar>
			)}
		</>
	);
};
