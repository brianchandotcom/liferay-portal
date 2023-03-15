import {isEqual} from 'lodash';
import {mergedVariants} from 'experiments/util/experiments';

export default {
	bestVariant: ({dxpVariants, metrics: {variantMetrics}}) => {
		if (isEqual(variantMetrics.map(({median}) => median))) {
			return null;
		}

		return mergedVariants(
			dxpVariants,
			variantMetrics
		).reduce((prev, current) =>
			prev.median > current.median ? prev : current
		);
	}
};
