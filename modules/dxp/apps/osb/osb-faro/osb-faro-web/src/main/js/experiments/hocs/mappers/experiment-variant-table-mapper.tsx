import {getMetricUnit, mergedVariants} from 'experiments/util/experiments';

type ExperimentVariant = any;

export default ({
	experiment: {
		bestVariant,
		dxpVariants,
		goal,
		metrics: {variantMetrics},
		status,
		winnerDXPVariantId
	}
}): ExperimentVariant => {
	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metric = goal ? goal.metric : null;
	const metricUnit = getMetricUnit(metric);

	return {
		bestVariant,
		data: variants,
		metric,
		metricUnit,
		status,
		winnerDXPVariantId
	};
};
