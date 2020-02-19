/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';
import {
	CartesianGrid,
	Legend,
	Line,
	LineChart,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';

import {numberFormat} from '../utils/numberFormat';
import CustomTooltip from './CustomTooltip';

const {useEffect, useMemo, useReducer} = React;

const CHART_SIZE = {height: 220, width: 280};

function keyToTranslatedLabelValue(key) {
	if (key === 'analyticsReportsHistoricalViews') {
		return Liferay.Language.get('views-metric');
	}
	else if (key === 'analyticsReportsHistoricalReads') {
		return Liferay.Language.get('reads-metric');
	}
	else {
		return key;
	}
}

function keyToHexColor(key) {
	if (key === 'analyticsReportsHistoricalViews') {
		return '#4B9BFF';
	}
	else if (key === 'analyticsReportsHistoricalReads') {
		return '#50D2A0';
	}
	else {
		return '#666666';
	}
}

function mergeDataSets(newData, previousDataSet, key) {
	const resultDataSet = {};

	resultDataSet.keyList = [...previousDataSet.keyList, key];

	resultDataSet.totals = {
		...previousDataSet.totals,
		[key]: newData.value
	};

	const newFormattedHistogram = newData.histogram.map(h => ({
		[key]: h.value,
		label: h.key
	}));

	let start = 0;
	const mergeHistogram = [];

	while (start < newData.histogram.length) {
		if (!previousDataSet.histogram[start]) {
			mergeHistogram.push({
				...newFormattedHistogram[start]
			});
		}
		else if (
			newFormattedHistogram[start].label ===
			previousDataSet.histogram[start].label
		) {
			mergeHistogram.push({
				...newFormattedHistogram[start],
				...previousDataSet.histogram[start]
			});
		}

		start = start + 1;
	}

	resultDataSet.histogram = mergeHistogram;

	return resultDataSet;
}

function transformDataToDataSet(
	key,
	data,
	previousDataset = {histogram: [], keyList: [], totals: []}
) {
	const result = mergeDataSets(data, previousDataset, key);

	return result;
}

/*
 * If a number is bigger than 1000 it will transform it to kilos
 *
 * 4 => 4
 * 4000 => '4K'
 */
function thousandsToKilosFormater(value) {
	if (value > 1000) {
		return value / 1000 + 'K';
	}

	return value;
}

/*
 * It generates a set of functions used to produce
 * internationalized date related content.
 */
const generateDateFormatters = key => {
	/*
	 * Given 2 date objects it produces a user friendly date interval
	 *
	 * For 'en-US'
	 * [Date, Date] => '16 - Jun 21, 2020'
	 */
	function formatChartTitle([initialDate, finalDate]) {
		const dateFormatter = (
			date,
			options = {
				day: 'numeric',
				month: 'short',
				year: 'numeric'
			}
		) => Intl.DateTimeFormat([key], options).format(date);

		const equalMonth = initialDate.getMonth() === finalDate.getMonth();
		const equalYear = initialDate.getYear() === finalDate.getYear();

		const initialDateOptions = {
			day: 'numeric',
			month: equalMonth && equalYear ? undefined : 'short',
			year: equalYear ? undefined : 'numeric'
		};

		return `${dateFormatter(
			initialDate,
			initialDateOptions
		)} - ${dateFormatter(finalDate)}`;
	}

	/*
	 * Given a date like string it produces a internationalized long date
	 *
	 * For 'en-US'
	 * String => 'June 16, 2020'
	 */
	function formatLongDate(value) {
		return Intl.DateTimeFormat([key]).format(new Date(value));
	}

	/*
	 * Given a date like string produces the day of the month
	 *
	 * For 'en-US'
	 * String => '16'
	 */
	function formatNumericDay(value) {
		return Intl.DateTimeFormat([key], {
			day: 'numeric'
		}).format(new Date(value));
	}

	return {
		formatChartTitle,
		formatLongDate,
		formatNumericDay
	};
};

function legendFormatterGenerator(totals, languageTag) {
	return value => (
		<span>
			<span className="text-secondary">
				{keyToTranslatedLabelValue(value)}
			</span>
			<b>{' ' + numberFormat(languageTag, totals[value])}</b>
		</span>
	);
}

function reducer(state, action) {
	switch (action.type) {
		case 'add-data-key':
			return transformDataToDataSet(
				action.payload.key,
				action.payload.dataSet,
				state
			);
		default:
			return state;
	}
}

export default function Chart({languageTag, dataProviders = []}) {
	const [dataSet, setDataSet] = useReducer(reducer);
	const isMounted = useIsMounted();

	useEffect(() => {
		dataProviders.map(getter => {
			getter().then(data => {
				if (isMounted()) {
					Object.keys(data).map(key => {
						setDataSet({
							payload: {dataSet: data[key], key},
							type: 'add-data-key'
						});
					});
				}
			});
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataProviders]);

	const dateFormatters = useMemo(() => generateDateFormatters(languageTag), [
		languageTag
	]);

	const title = useMemo(() => {
		if (dataSet && dataSet.histogram) {
			const firstDateLabel = dataSet.histogram[0].label;
			const lastDateLabel =
				dataSet.histogram[dataSet.histogram.length - 1].label;

			return dateFormatters.formatChartTitle([
				new Date(firstDateLabel),
				new Date(lastDateLabel)
			]);
		}
	}, [dataSet, dateFormatters]);

	const legendFormatter =
		dataSet && legendFormatterGenerator(dataSet.totals, languageTag);

	return dataSet ? (
		<>
			{title && <h4>{title}</h4>}

			<div className="mt-3">
				<LineChart
					data={dataSet.histogram}
					height={CHART_SIZE.height}
					width={CHART_SIZE.width}
				>
					<Legend
						formatter={legendFormatter}
						iconType="circle"
						layout="vertical"
						verticalAlign="top"
						wrapperStyle={{left: 0, paddingBottom: '1rem'}}
					/>

					<CartesianGrid strokeDasharray="0 0" vertical={false} />

					<XAxis
						dataKey="label"
						tickFormatter={dateFormatters.formatNumericDay}
						tickLine={false}
					/>

					<YAxis
						allowDecimals={false}
						minTickGap={3}
						tickFormatter={thousandsToKilosFormater}
						tickLine={false}
						width={40}
					/>

					<Tooltip
						content={<CustomTooltip />}
						formatter={(value, name) => {
							return [
								numberFormat(languageTag, value),
								keyToTranslatedLabelValue(name)
							];
						}}
						labelFormatter={dateFormatters.formatLongDate}
						separator={': '}
					/>

					{dataSet.keyList.map(keyName => {
						const color = keyToHexColor(keyName);

						return (
							<Line
								activeDot={{r: 6, strokeWidth: 0}}
								dataKey={keyName}
								fill={color}
								key={keyName}
								stroke={color}
								strokeWidth={2}
								type="monotone"
							/>
						);
					})}
				</LineChart>
			</div>
		</>
	) : null;
}

Chart.propTypes = {
	dataProviders: PropTypes.arrayOf(PropTypes.func).isRequired,
	languageTag: PropTypes.string.isRequired
};
