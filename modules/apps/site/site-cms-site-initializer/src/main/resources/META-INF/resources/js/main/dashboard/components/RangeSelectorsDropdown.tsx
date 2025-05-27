/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {FilterDropdown, Item} from './FilterDropdown';

export enum RangeSelectors {
	Last24Hours = '0',
	Last28Days = '28',
	Last30Days = '30',
	Last7Days = '7',
	Last90Days = '90',
	Yesterday = '1',
}

export interface IRangeSelectorsDropdown {
	activeRangeSelector: RangeSelectors;
	className?: string;
	onChange: (rangeSelector: RangeSelectors) => void;
}

const LAST_24_HOURS = {
	description: `${formatUTCDate(new Date(getDateByRangeSelector(RangeSelectors.Yesterday)).toISOString(), true)} - ${formatUTCDate(
		new Date().toISOString(),
		true
	)}`,
	label: Liferay.Util.sub(Liferay.Language.get('last-x-hours'), [24]),
	value: RangeSelectors.Last24Hours,
};

const LAST_7_DAYS = {
	description: formatDateRange(RangeSelectors.Last7Days),
	label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [7]),
	value: RangeSelectors.Last7Days,
};

const LAST_28_DAYS = {
	description: formatDateRange(RangeSelectors.Last28Days),
	label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [28]),
	value: RangeSelectors.Last28Days,
};

const LAST_30_DAYS = {
	description: formatDateRange(RangeSelectors.Last30Days),
	label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [30]),
	value: RangeSelectors.Last30Days,
};

const LAST_90_DAYS = {
	description: formatDateRange(RangeSelectors.Last90Days),
	label: Liferay.Util.sub(Liferay.Language.get('last-x-days'), [90]),
	value: RangeSelectors.Last90Days,
};

const RangeSelectorsDropdown: React.FC<IRangeSelectorsDropdown> = ({
	activeRangeSelector,
	className,
	onChange,
}) => {
	const rangeSelectors: Item[] = [
		LAST_24_HOURS,
		LAST_7_DAYS,
		LAST_28_DAYS,
		LAST_30_DAYS,
		LAST_90_DAYS,
	];

	return (
		<FilterDropdown
			active={activeRangeSelector}
			className={className}
			filterByValue="rangeSelectors"
			items={rangeSelectors}
			onSelectItem={(item) => onChange(item.value as RangeSelectors)}
			triggerLabel={
				rangeSelectors.find(({value}) => value === activeRangeSelector)
					?.label ?? ''
			}
		/>
	);
};

function formatUTCDate(dateString: string, displayTime: boolean = false) {
	const date = new Date(dateString);

	const months = [
		'JAN',
		'FEB',
		'MAR',
		'APR',
		'MAY',
		'JUN',
		'JUL',
		'AUG',
		'SEP',
		'OCT',
		'NOV',
		'DEC',
	];

	const day = String(date.getUTCDate()).padStart(2, '0');
	const month = months[date.getUTCMonth()];

	let hour = date.getUTCHours();

	const suffix = hour >= 12 ? 'P.M.' : 'A.M.';

	hour = hour % 12;

	if (hour === 0) {
		hour = 12;
	}

	if (displayTime) {
		return `${month} ${day}, ${hour.toString().padStart(2, '0')} ${suffix}`;
	}

	return `${month} ${day}`;
}

function getDateByRangeSelector(rangeSelector: RangeSelectors) {
	return new Date().setDate(new Date().getDate() - Number(rangeSelector));
}

function formatDateRange(
	startRangeSelector: RangeSelectors,
	endRangeSelector: RangeSelectors = RangeSelectors.Yesterday
) {
	const formatDate = (date: Date, endOfDay = false) =>
		new Date(
			Date.UTC(
				date.getUTCFullYear(),
				date.getUTCMonth(),
				date.getUTCDate(),
				endOfDay ? 23 : 0,
				endOfDay ? 59 : 0,
				endOfDay ? 59 : 0
			)
		).toISOString();

	const startDate = formatDate(
		new Date(getDateByRangeSelector(startRangeSelector))
	);
	const endDate = formatDate(
		new Date(getDateByRangeSelector(endRangeSelector)),
		true
	);

	return `${formatUTCDate(startDate)} - ${formatUTCDate(endDate)}`;
}

export {RangeSelectorsDropdown};
