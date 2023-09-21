import * as d3 from 'd3';
import ImprovementTooltip from 'experiments/components/variant-card/ImprovementTooltip';
import moment from 'moment';
import PublishOtherVariantModal from 'experiments/components/modals/PublishOtherVariantModal';
import PublishVariantModal from 'experiments/components/modals/PublishVariantModal';
import React, {useEffect} from 'react';
import UpdateExperimentStatusModal from 'experiments/components/modals/UpdateExperimentStatusModal';
import {Alignments, Weights} from 'shared/components/chart-tooltip';
import {ButtonProps} from 'experiments/components/summary-base-card/types';
import {
	FormatHistogramKeyValue,
	FormatYAxisFn,
	GetFormattedDataTooltip,
	GetFormattedHistogramFn,
	GetFormattedMedianFn,
	GetFormattedVariantHistogramFn,
	GetLinkFn,
	GetMetricNameFn,
	GetMetricUnitFn,
	GetShortIntervals,
	GetStatusColorFn,
	GetStatusNameFn,
	GetStepFn,
	GetTicksFn,
	GetVariantLabels,
	MakeAllRefetchFn,
	MergedVariantsFn,
	ModalCompleteFn,
	ModalPublishOtherVariantFn,
	ModalPublishVariantFn,
	NormalizeHistogramFn,
	SortOrderExperiment,
	StepInputs,
	TooltipMetric
} from './types';
import {getDate as getDateUtil} from 'shared/util/date';
import {IActionProps} from 'shared/components/base-page/Header';
import {round} from 'lodash';
import {toRounded, toThousands, toThousandsBase} from 'shared/util/numbers';
import {useStateValue} from 'experiments/state';

const METRICS_NAMES = new Map([
	['BOUNCE_RATE', Liferay.Language.get('bounce-rate')],
	['CLICK_RATE', Liferay.Language.get('click-through-rate')],
	['MAX_SCROLL_DEPTH', Liferay.Language.get('max-scroll-depth')],
	['TIME_ON_PAGE', Liferay.Language.get('view-duration')]
]);

const METRICS_UNITS = new Map([
	['BOUNCE_RATE', '%'],
	['CLICK_RATE', '%'],
	['MAX_SCROLL_DEPTH', '%'],
	['TIME_ON_PAGE', 's']
]);

const STATUS_COLORS = new Map([
	['COMPLETED', 'success'],
	['DRAFT', 'secondary'],
	['FINISHED_NO_WINNER', 'secondary'],
	['FINISHED_WINNER', 'success'],
	['PAUSED', 'secondary'],
	['RUNNING', 'info'],
	['SCHEDULED', 'warning'],
	['TERMINATED', 'danger']
]);

const STATUS_NAMES = new Map([
	['COMPLETED', Liferay.Language.get('complete')],
	['DRAFT', Liferay.Language.get('draft')],
	['FINISHED_NO_WINNER', Liferay.Language.get('no-winner')],
	['FINISHED_WINNER', Liferay.Language.get('winner-declared')],
	['PAUSED', Liferay.Language.get('paused')],
	['RUNNING', Liferay.Language.get('running')],
	['SCHEDULED', Liferay.Language.get('scheduled')],
	['TERMINATED', Liferay.Language.get('terminated')]
]);

export const dateFormatter = d3.utcFormat('%Y-%m-%d');

export const TOOLTIP_METRICS: Array<TooltipMetric> = [
	{
		dataRenderer: data => {
			const {confidenceInterval, metricUnit} = data;

			return `${confidenceInterval[1]}${metricUnit}`;
		},
		name: 'high',
		title: Liferay.Language.get('high')
	},
	{
		dataRenderer: ({confidenceInterval, metricUnit}) =>
			`${confidenceInterval[0]}${metricUnit}`,
		name: 'low',
		title: Liferay.Language.get('low')
	},
	{
		dataRenderer: ({median, metricUnit}) => `${median}${metricUnit}`,
		name: 'median',
		title: Liferay.Language.get('median')
	},
	{
		dataRenderer: ({improvement}) => () => (
			<ImprovementTooltip improvement={improvement} />
		),
		name: 'lift',
		title: null
	}
];

export const formatHistogramKeyValue: FormatHistogramKeyValue = (
	variants,
	metricUnit
) =>
	variants.reduce((variants, variant, index) => {
		variants[`data${index + 1}`] = variant.variantsHistogram.reduce(
			(histogram, day) => {
				const date = dateFormatter(
					formatProcessedDate(day.processedDate)
				);

				histogram[date] = {
					...day,
					control: Boolean(variant.control),
					metricUnit,
					name: variant.dxpVariantName
				};

				return histogram;
			},
			{}
		);

		return variants;
	}, {});

export const formatProcessedDate = date =>
	moment.utc(date).startOf('day').toDate();

export const formatYAxis: FormatYAxisFn = metricUnit => value => {
	if (value % 1 === 0) {
		return `${value}${metricUnit}`;
	}

	return `${value.toFixed(1)}${metricUnit}`;
};

export const getExperimentLink: GetLinkFn = ({action, id, pageURL}) => {
	const experimentLink = `${pageURL}?segmentsExperimentKey=${id}`;

	if (action) {
		return `${experimentLink}&segmentsExperimentAction=${action}`;
	}

	return experimentLink;
};

export const getFormattedHistogram: GetFormattedHistogramFn = histogram => ({
	key: histogram.map(({key}) => getDateUtil(key)),
	value: histogram.map(({value}) => parseInt(value) || 0)
});

export const getFormattedMedian: GetFormattedMedianFn = (median, metric) => {
	const precision = metric === 'CLICK_RATE' ? 3 : 2;

	return toRounded(median, precision);
};

export const getFormattedMedianLabel = metric =>
	metric === 'CLICK_RATE'
		? `${Liferay.Language.get('median')} ${getMetricName(metric)}`
		: `${getMetricName(metric)} ${Liferay.Language.get('median')}`;

export const getFormattedProbabilityToWin = value => {
	if (value < 0.1) {
		value = '< 0.1';
	} else if (value > 99.9) {
		value = '> 99.9';
	} else {
		value = toRounded(value);
	}

	return value;
};

export const getFormattedVariantHistogram: GetFormattedVariantHistogramFn = histogram => ({
	key: histogram.map(({processedDate}) => formatProcessedDate(processedDate)),
	value: histogram.map(({median}) => median || 0)
});

export const getMetricName: GetMetricNameFn = metric =>
	METRICS_NAMES.get(metric);

export const getMetricUnit: GetMetricUnitFn = metric =>
	METRICS_UNITS.get(metric);

export const getStatusColor: GetStatusColorFn = status =>
	STATUS_COLORS.get(status);

export const getStatusName: GetStatusNameFn = status =>
	STATUS_NAMES.get(status).toUpperCase();

export const getStep: GetStepFn = ({
	disabled,
	showIcon = true,
	tooltip,
	...otherProps
}: StepInputs) => {
	const buttonProps: ButtonProps = {
		...(disabled && {disabled}),
		...(showIcon && {
			symbol: 'dxp-logo'
		}),
		...(tooltip && {
			['data-tooltip']: true,
			title: tooltip
		})
	};

	return {
		buttonProps,
		...otherProps
	};
};

export const getVariantLink: GetLinkFn = ({id, pageURL}) =>
	`${pageURL}?segmentsExperienceKey=${id}`;

export const mergedVariants: MergedVariantsFn = (variants, variantMetrics) =>
	variants.map(variant => ({
		...variant,
		...variantMetrics.find(
			({dxpVariantId}) => variant.dxpVariantId === dxpVariantId
		)
	}));

export const modalComplete: ModalCompleteFn = (
	experimentId,
	publishedDXPVariantId
) => ({
	Component: UpdateExperimentStatusModal,
	props: {
		experimentId,
		modalBody: (
			<>
				<div className='mb-2 text-secondary'>
					{Liferay.Language.get(
						'are-you-sure-you-want-to-complete-this-test'
					)}
				</div>
				<strong>
					{Liferay.Language.get(
						'no-more-traffic-will-be-directed-to-the-test-variants-and-we-will-stop-collecting-test-data'
					)}{' '}
					{Liferay.Language.get(
						'you-will-still-have-access-to-the-data-that-has-already-been-collected'
					)}
				</strong>
			</>
		),
		nextStatus: 'COMPLETED',
		publishedDXPVariantId,
		submitMessage: Liferay.Language.get('complete-test'),
		title: Liferay.Language.get('complete-test')
	},
	title: Liferay.Language.get('complete-test')
});

export const modalPublishVariant: ModalPublishVariantFn = (
	dxpVariantId,
	dxpVariantName,
	experimentId,
	pageURL
) => ({
	Component: PublishVariantModal,
	props: {
		dxpVariantId,
		dxpVariantName,
		experimentId,
		pageURL,
		title: Liferay.Language.get('publish-winner')
	},
	title: Liferay.Language.get('publish-winner')
});

export const modalPublishOtherVariant: ModalPublishOtherVariantFn = (
	dxpVariants,
	experimentId,
	pageURL,
	title = Liferay.Language.get('publish-other-variant')
) => ({
	Component: PublishOtherVariantModal,
	props: {
		dxpVariants,
		experimentId,
		pageURL,
		title
	},
	title
});

export const normalizeHistogram: NormalizeHistogramFn = ({
	dxpVariants,
	goal: {metric},
	metricsHistogram
}) =>
	dxpVariants.map(variant => ({
		...variant,
		variantsHistogram: metricsHistogram.map(
			({processedDate, variantMetrics}) => {
				const variantMetric = variantMetrics.find(
					({dxpVariantId}) => dxpVariantId === variant.dxpVariantId
				);

				return {
					...variantMetric,
					confidenceInterval: variantMetric.confidenceInterval.map(
						value => getFormattedMedian(value, metric)
					),
					improvement: Number(
						toRounded(variantMetric.improvement, 2)
					),
					median: Number(
						getFormattedMedian(variantMetric.median, metric)
					),
					processedDate
				};
			}
		)
	}));

/**
 * Used to make all refetch that are passed by parameter
 * @param allRefetch
 */
export const makeAllRefetch: MakeAllRefetchFn = allRefetch =>
	allRefetch.map(refetch => refetch());

/**
 * Used to insert a new refetch in the makeAllRefetch Experiment's state
 * @param refetch
 */
export const useAddRefetch = (refetch: Function) => {
	const [, dispatch]: any = useStateValue();

	useEffect(() => {
		dispatch({
			newAction: refetch,
			type: 'addRefetch'
		});
	}, [refetch]);
};

export const getVariantLabels: GetVariantLabels = ({
	bestVariant,
	dxpVariantId,
	publishedDXPVariantId,
	status,
	winnerDXPVariantId
}) => {
	const labels = [];

	if (status === 'RUNNING' && bestVariant?.dxpVariantId === dxpVariantId) {
		labels.push({
			status: 'success',
			value: Liferay.Language.get('current-best')
		});
	}

	if (
		winnerDXPVariantId === dxpVariantId &&
		(status === 'COMPLETED' || status === 'FINISHED_WINNER')
	) {
		labels.push({
			status: 'success',
			value: Liferay.Language.get('winner')
		});
	}

	if (publishedDXPVariantId === dxpVariantId) {
		labels.push({
			status: 'info',
			value: Liferay.Language.get('published')
		});
	}

	return labels;
};

export const getTicks: GetTicksFn = maxValue => {
	const arr = [];
	let interval = 1;
	const step = Math.round(maxValue / 8);

	while (interval <= maxValue) {
		arr.push(interval);

		interval = interval + step;
	}

	return [...arr];
};

export const getShortIntervals: GetShortIntervals = intervals =>
	getTicks(intervals.length).map(tick => intervals[tick - 1]);

export const toThousandsABTesting = number => {
	if (number > 1e4) {
		return toThousandsBase(number, factor =>
			Math.trunc(round(number * factor, 2))
		);
	}

	return toThousands(number);
};

export const getFormattedDataHistogram = (histogram, index) =>
	histogram.map(({median, processedDate}) => ({
		id: `data${index + 1}`,
		key: processedDate,
		value: median
	}));

export const getFormattedDataTooltip: GetFormattedDataTooltip = dataPoint => {
	const header = [
		{
			columns: [
				{
					label: d3.utcFormat('%b %-d')(
						getDateUtil(dataPoint[0].payload.key)
					),
					weight: Weights.Semibold
				},
				{
					label: Liferay.Language.get('sessions'),
					weight: Weights.Semibold
				}
			]
		}
	];

	const rows = dataPoint.map(({color, name, payload}) => ({
		columns: [
			{
				color,
				label: name
			},
			{
				align: Alignments.Right,
				label: toThousandsABTesting(payload.value)
			}
		]
	}));

	return {header, rows};
};

export const sortOrderExperiment: SortOrderExperiment = (
	{control: experimentControlA},
	{control: experimentControlB}
) => Number(experimentControlB) - Number(experimentControlA);

export const getActions = (
	status: string,
	{id, onDelete, pageURL, publishable} = null
): IActionProps[] => {
	const deleteButton = {
		displayType: 'secondary',
		label: Liferay.Language.get('delete'),
		onClick: onDelete
	};

	switch (status) {
		case 'COMPLETED': {
			return [deleteButton];
		}
		case 'DRAFT': {
			return [
				{
					displayType: 'primary',
					label: Liferay.Language.get('review'),
					redirectURL: getExperimentLink({
						action: 'reviewAndRun',
						id,
						pageURL
					})
				},
				{
					displayType: 'secondary',
					label: Liferay.Language.get('delete'),
					redirectURL: getExperimentLink({
						action: 'delete',
						id,
						pageURL
					})
				}
			];
		}
		case 'FINISHED_NO_WINNER':
		case 'FINISHED_WINNER': {
			return [
				{
					displayType: 'primary',
					label: Liferay.Language.get('publish'),
					redirectURL: getExperimentLink({
						action: 'publish',
						id,
						pageURL
					})
				},
				{
					displayType: 'secondary',
					label: Liferay.Language.get('delete'),
					redirectURL: getExperimentLink({
						action: 'delete',
						id,
						pageURL
					})
				}
			];
		}
		case 'TERMINATED': {
			if (publishable) {
				return [
					{
						displayType: 'primary',
						label: Liferay.Language.get('publish'),
						redirectURL: getExperimentLink({
							action: 'publish',
							id,
							pageURL
						})
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('delete'),
						redirectURL: getExperimentLink({
							action: 'delete',
							id,
							pageURL
						})
					}
				];
			}

			return [deleteButton];
		}
		case 'RUNNING': {
			return [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('terminate'),
					redirectURL: getExperimentLink({
						action: 'terminate',
						id,
						pageURL
					})
				}
			];
		}
		default: {
			return [];
		}
	}
};
