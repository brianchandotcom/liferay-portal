import ChartTooltip, {
	Alignments,
	Weights
} from 'shared/components/chart-tooltip';
import React, {useMemo} from 'react';
import {CHART_DATA_PREVIOUS, METRIC_TOOLTIP_LABEL_MAP} from './MetricChart';
import {find, get} from 'lodash';
import {getActiveItem, getPreviousValueFromCompositeData} from './util';
import {getDateTitle} from 'shared/util/charts';
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
						weight: Weights.Normal,
						width: 55
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: currentPeriodTitle,
						weight: Weights.Semibold,
						width: 55
					}
				].filter(Boolean)
			}
		];

		const rows = [
			{
				columns: [
					{
						label: getDataRowName(dataOneItemData),
						weight: compareToPrevious
							? Weights.Semibold
							: Weights.Normal
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataOnePreviousValue)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue),
						weight: Weights.Semibold
					}
				].filter(Boolean)
			},
			compositeData && {
				columns: [
					{
						label: getDataRowName(dataTwoItemData),
						weight: compareToPrevious
							? Weights.Semibold
							: Weights.Normal
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataTwoPreviousValue)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataTwoValue),
						weight: Weights.Semibold
					}
				].filter(Boolean)
			},
			compositeData && {
				columns: [
					{
						label: Liferay.Language.get('total'),
						weight: compareToPrevious
							? Weights.Semibold
							: Weights.Normal
					},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(
							dataOnePreviousValue + dataTwoPreviousValue
						)
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue + dataTwoValue),
						weight: Weights.Semibold
					}
				].filter(Boolean)
			}
		].filter(Boolean);

		return [header, rows];
	}, [showCurrentPeriod, interval, payload, rangeSelectors]);

	return [header, rows];
};

const MetricTooltip = ({active, data, interval, payload, rangeSelectors}) => {
	if (!active || !payload || !payload.length) {
		return null;
	}

	const [header, rows] = useMetricTooltip({
		data,
		interval,
		payload,
		rangeSelectors
	});

	return (
		<div className='bb-tooltip-container' style={{position: 'static'}}>
			<ChartTooltip header={header} rows={rows} />
		</div>
	);
};

export default MetricTooltip;
