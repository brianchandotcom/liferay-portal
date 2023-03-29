import {CHART_COLORS} from 'shared/components/Chart';
import {CONTROL_COLOR} from 'experiments/components/variant-card/constants';
import {getMetricUnit, mergedVariants} from 'experiments/util/experiments';

type ExperimentVariant = any;

const getLegendData = dxpVariants => {
	const COLORS = [...CHART_COLORS];

	return dxpVariants.map(({control, dxpVariantId, dxpVariantName}) => ({
		color: control ? CONTROL_COLOR : COLORS.shift(),
		id: dxpVariantId,
		name: dxpVariantName
	}));
};

const getMedianGraphData = ({dxpVariants, metricUnit}) => {
	const COLORS = [...CHART_COLORS];

	const type = metricUnit === '%' ? 'percentage' : 'number';

	const formatter =
		metricUnit === '%' ? value => value : value => `${value}s`;

	const items = dxpVariants.map(({confidenceInterval, control, median}) => ({
		intervals: [
			{
				end: confidenceInterval[1],
				start: confidenceInterval[0]
			}
		],
		progress: [
			{
				color: control ? CONTROL_COLOR : COLORS.shift(),
				value: median
			}
		]
	}));

	const maxValue = Math.max(
		...dxpVariants.map(({confidenceInterval}) => confidenceInterval[1])
	);

	return {
		disableScroll: true,
		empty: maxValue === 0,
		formatSpacement: false,
		grid: {
			formatter,
			maxValue,
			minValue: 0,
			precision: 2,
			show: true,
			type
		},
		items
	};
};

export default ({
	experiment: {
		dxpVariants,
		goal,
		metrics: {variantMetrics}
	}
}): ExperimentVariant => {
	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metric = goal ? goal.metric : null;
	const metricUnit = getMetricUnit(metric);
	const mediansData = getMedianGraphData({
		dxpVariants: variants,
		metricUnit
	});

	if (mediansData.items && !mediansData.items.length) {
		return {
			empty: true
		};
	}

	return {
		legend: getLegendData(variants),
		mediansData,
		metricUnit
	};
};
