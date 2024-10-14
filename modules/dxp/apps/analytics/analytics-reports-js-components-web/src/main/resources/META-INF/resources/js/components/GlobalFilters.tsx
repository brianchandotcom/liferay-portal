/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AnalyticsReportsContext} from '../AnalyticsReportsContext';
import {Individuals, RangeSelectors} from '../types/global';
import {formatDate, getDateRange} from '../utils/date';
import Filter from './Filter';
import Title from './Title';

const individualFilterLang = {
	[Individuals.AllIndividuals]: Liferay.Language.get('all-individuals'),
	[Individuals.AnonymousIndividuals]: Liferay.Language.get(
		'anonymous-individuals'
	),
	[Individuals.KnownIndividuals]: Liferay.Language.get('known-individuals'),
};

function formatDateRange(rangeSelector: RangeSelectors) {
	const {endDate, startDate} = getDateRange(rangeSelector);

	return `${formatDate(endDate)} - ${formatDate(startDate)}`;
}

const GlobalFilters = () => {
	const {changeIndividualFilter, changeRangeSelectorFilter, filters} =
		useContext(AnalyticsReportsContext);

	let timeFilterItems = [
		{
			description: formatDateRange(RangeSelectors.Last7Days),
			label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [
				RangeSelectors.Last7Days,
			]),
			value: RangeSelectors.Last7Days,
		},
		{
			description: formatDateRange(RangeSelectors.Last28Days),
			label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [
				RangeSelectors.Last28Days,
			]),
			value: RangeSelectors.Last28Days,
		},
		{
			description: formatDateRange(RangeSelectors.Last30Days),
			label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [
				RangeSelectors.Last30Days,
			]),
			value: RangeSelectors.Last30Days,
		},
		{
			description: formatDateRange(RangeSelectors.Last90Days),
			label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [
				RangeSelectors.Last90Days,
			]),
			value: RangeSelectors.Last90Days,
		},
	];

	if (process.env.NODE_ENV === 'development') {
		timeFilterItems = [
			{
				description: formatDateRange(RangeSelectors.Last24Hours),
				label: Liferay.Util.sub(Liferay.Language.get('last-x-hours'), [
					24,
				]),
				value: RangeSelectors.Last24Hours,
			},
			...timeFilterItems,
		];
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

				<Filter
					active={filters.rangeSelector}
					filterByValue="rangeSelectors"
					icon="calendar"
					items={timeFilterItems}
					onSelectItem={(item) =>
						changeRangeSelectorFilter(item.value)
					}
					triggerLabel={
						timeFilterItems.find(
							({value}) => value === filters.rangeSelector
						)?.label ?? ''
					}
				/>
			</div>
		</div>
	);
};

export default GlobalFilters;
