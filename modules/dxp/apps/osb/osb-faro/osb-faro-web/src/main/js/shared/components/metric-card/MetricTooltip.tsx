import ChartTooltip, {
	Alignments,
	Weights
} from 'shared/components/chart-tooltip';
import React, {useMemo} from 'react';
import {CHART_DATA_PREVIOUS, METRIC_TOOLTIP_LABEL_MAP} from './MetricChart';
import {find, get} from 'lodash';
import {getActiveItem, getPreviousValueFromCompositeData} from './util';
import {getDateTitle} from 'shared/util/charts';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {useData} from './MetricBaseCard';

const CHART_DATA_ID_1 = 'data_1';
const CHART_DATA_ID_2 = 'data_2';

const useMetricTooltip = ({data, interval, payload, rangeSelectors}) => {
	const {compareToPrevious} = useData();

	const {
		asymmetricComparison,
		chartData,
		compositeData,
		content: {name, title},
		dateKeysIMap,
		format,
		prevDateKeysIMap
	} = useMemo(() => getActiveItem(data, compareToPrevious), [
		compareToPrevious,
		data
	]);

	const showCurrentPeriod =
		compareToPrevious && asymmetricComparison ? payload.length > 1 : true;

	const [header, rows] = useMemo(() => {
		const dateKey = payload[0].payload.date;

		const dataOneItemData = find(
			chartData,
			({id}) => id === CHART_DATA_ID_1
		);
		const dataOneValue = payload[0].value;

		const dataTwoItemData = find(
			chartData,
			({id}) => id === CHART_DATA_ID_2
		);
		const dataTwoValue = payload[1] && payload[1].value;

		const dataPreviousPoint = find(
			payload,
			({dataKey}) => dataKey === CHART_DATA_PREVIOUS
		);

		const dataOnePreviousValue = compositeData
			? getPreviousValueFromCompositeData(
					compositeData,
					get(dataOneItemData, 'dataName'),
					dateKey
			  )
			: get(dataPreviousPoint, 'value');
		const dataTwoPreviousValue = getPreviousValueFromCompositeData(
			compositeData,
			get(dataTwoItemData, 'dataName'),
			dateKey
		);

		const currentPeriodTitle = getDateTitle(
			dateKeysIMap.get(dateKey),
			rangeSelectors.rangeKey,
			interval
		);
		const previousPeriodTitle = getDateTitle(
			prevDateKeysIMap.get(dateKey),
			rangeSelectors.rangeKey,
			interval
		);

		const getDataRowName = itemData =>
			get(itemData, 'tooltipTitle') ||
			METRIC_TOOLTIP_LABEL_MAP[name] ||
			get(itemData, 'name');

		const header = [
			{
				columns: [
					{label: title, weight: Weights.Semibold, width: 116},
					compareToPrevious && {
						align: Alignments.Right,
						label: previousPeriodTitle,
						width: 55
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: currentPeriodTitle,
						width: 55
					}
				].filter(Boolean)
			}
		];

		const rows = [
			{
				columns: [
					{
						label: getDataRowName(dataOneItemData)
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataOnePreviousValue)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue)
					}
				].filter(Boolean)
			},
			compositeData && {
				columns: [
					{
						label: getDataRowName(dataTwoItemData)
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataTwoPreviousValue)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataTwoValue)
					}
				].filter(Boolean)
			},
			compositeData && {
				columns: [
					{
						label: Liferay.Language.get('total')
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(
							dataOnePreviousValue + dataTwoPreviousValue
						)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue + dataTwoValue)
					}
				].filter(Boolean)
			}
		].filter(Boolean);

		return [header, rows];
	}, [showCurrentPeriod, interval, payload, rangeSelectors]);

	return [header, rows];
};

const MetricTooltip = ({
	active,
	compareToPrevious,
	data,
	interval,
	payload,
	rangeSelectors,
	retentionPeriod
}) => {
	if (active && payload?.length) {
		const [header, rows] = useMetricTooltip({
			data,
			interval,
			payload,
			rangeSelectors
		});

		let description = '';

		if (
			(compareToPrevious &&
				rangeSelectors.rangeKey === RangeKeyTimeRanges.Last180Days) ||
			(retentionPeriod === 13 &&
				rangeSelectors.rangeKey === RangeKeyTimeRanges.LastYear)
		) {
			description = sub(
				Liferay.Language.get(
					'there-is-no-data-available-for-dates-prior-to-x-months-due-to-your-workspaces-data-retention-period'
				),
				[retentionPeriod]
			) as string;
		}

		return (
			<div
				className='bb-tooltip-container'
				style={{maxWidth: 400, position: 'static'}}
			>
				<ChartTooltip
					description={description}
					header={header}
					rows={rows}
				/>
			</div>
		);
	}

	return null;
};

export default MetricTooltip;
