/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {Context} from '../../Context';
import {Individuals} from '../../types/global';
import Filter from '../Filter';
import {
	RangeSelectors,
	RangeSelectorsDropdown,
} from '../RangeSelectorsDropdown';
import Title from '../Title';

const individualFilterLang = {
	[Individuals.AllIndividuals]: Liferay.Language.get('all-individuals'),
	[Individuals.AnonymousIndividuals]: Liferay.Language.get(
		'anonymous-individuals'
	),
	[Individuals.KnownIndividuals]: Liferay.Language.get('known-individuals'),
};

const GlobalFilters = () => {
	const {changeIndividualFilter, changeRangeSelectorFilter, filters} =
		useContext(Context);

	const rangeSelectors = [
		RangeSelectors.Last7Days,
		RangeSelectors.Last28Days,
		RangeSelectors.Last30Days,
		RangeSelectors.Last90Days,
	];

	if (process.env.NODE_ENV === 'development') {
		rangeSelectors.push(RangeSelectors.Last24Hours);
	}

	return (
		<div className="d-flex global-filters justify-content-between mb-3">
			<Title value={Liferay.Language.get('overview')} />

			<div className="d-flex">
				<Filter
					active={filters.individual}
					className="mr-3"
					filterByValue="individuals"
					icon="users"
					items={[
						{
							label: Liferay.Language.get('all-individuals'),
							value: Individuals.AllIndividuals,
						},
						{
							label: Liferay.Language.get(
								'anonymous-individuals'
							),
							value: Individuals.AnonymousIndividuals,
						},
						{
							label: Liferay.Language.get('known-individuals'),
							value: Individuals.KnownIndividuals,
						},
					]}
					onSelectItem={(item) => changeIndividualFilter(item.value)}
					triggerLabel={individualFilterLang[filters.individual]}
				/>

				<RangeSelectorsDropdown
					activeRangeSelector={filters.rangeSelector}
					availableRangeKeys={rangeSelectors}
					onChange={changeRangeSelectorFilter}
					size="xs"
				/>
			</div>
		</div>
	);
};

export default GlobalFilters;
