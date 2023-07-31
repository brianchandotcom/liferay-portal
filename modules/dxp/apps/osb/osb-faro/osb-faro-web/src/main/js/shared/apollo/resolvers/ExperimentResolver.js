import {isEqual} from 'lodash';
import {mergedVariants} from 'experiments/util/experiments';

export default {
	bestVariant: ({dxpVariants, goal: {metric}, metrics: {variantMetrics}}) => {
		if (isEqual(variantMetrics.map(({median}) => median))) {
			return null;
		}

		if (metric === 'BOUNCE_RATE') {
			return mergedVariants(
				dxpVariants,
				variantMetrics
			).reduce((prev, current) =>
				prev.median < current.median ? prev : current
			);
		}

		return mergedVariants(
			dxpVariants,
			variantMetrics
		).reduce((prev, current) =>
			prev.median > current.median ? prev : current
		);
	}
};
