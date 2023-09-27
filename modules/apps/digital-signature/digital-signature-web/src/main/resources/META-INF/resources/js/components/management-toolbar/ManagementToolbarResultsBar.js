/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {concatValues} from '../../utils/utils';
import SearchContext from './SearchContext';

const FilterItem = ({filterKey, name, value}) => {
	const [, dispatch] = useContext(SearchContext);

	return (
		<ManagementToolbar.ResultsBarItem>
			<ClayLabel
				className="tbar-label"
				closeButtonProps={{
					onClick: () => dispatch({filterKey, type: 'REMOVE_FILTER'}),
				}}
				displayType="unstyled"
			>
				<span className="label-section">
					{`${name}: `}

					<span className="font-weight-normal">{value}</span>
				</span>
			</ClayLabel>
		</ManagementToolbar.ResultsBarItem>
	);
};

export function getSelectedFilters(filters, appliedFilters) {
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
}

export default function ManagementToolbarResultsBar({
	filters = [],
	isLoading,
	totalCount,
}) {
	const [{filters: appliedFilters = {}, keywords}, dispatch] = useContext(
		SearchContext
	);

	const selectedFilters = getSelectedFilters(filters, appliedFilters);

	return (
		<>
			{(keywords || !!selectedFilters.length) && !isLoading && (
				<ManagementToolbar.ResultsBar>
					<ManagementToolbar.ResultsBarItem>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{sub(
									totalCount === 1
										? Liferay.Language.get('x-result-for-x')
										: Liferay.Language.get(
												'x-results-for-x'
										  ),
									totalCount,
									keywords
								)}
							</span>
						</span>
					</ManagementToolbar.ResultsBarItem>

					{selectedFilters.map((filter, key) => (
						<FilterItem key={key} {...filter} />
					))}

					<ManagementToolbar.ResultsBarItem expand>
						<div className="tbar-section text-right">
							<ClayButton
								className="component-link tbar-link"
								displayType="unstyled"
								onClick={() => dispatch({type: 'CLEAR'})}
							>
								{Liferay.Language.get('clear-all')}
							</ClayButton>
						</div>
					</ManagementToolbar.ResultsBarItem>
				</ManagementToolbar.ResultsBar>
			)}
		</>
	);
}
