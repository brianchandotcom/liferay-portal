import {safeResultToProps} from 'shared/util/mappers';

const mapResultToProps: object = safeResultToProps(
	({
		cohort: {
			anonymousCohortHeatMapMetrics,
			knownCohortHeatMapMetrics,
			visitorsCohortHeatMapMetrics
		}
	}) => ({
		data: {
			anonymousVisitors: {
				items: anonymousCohortHeatMapMetrics
			},
			knownVisitors: {
				items: knownCohortHeatMapMetrics
			},
			visitors: {
				items: visitorsCohortHeatMapMetrics
			}
		},
		empty: [
			anonymousCohortHeatMapMetrics,
			knownCohortHeatMapMetrics,
			visitorsCohortHeatMapMetrics
		].some(metric => !metric.length)
	})
);

const mapPropsToOptions: object = ({channelId, interval}) => ({
	variables: {
		channelId,
		interval
	}
});

export {mapPropsToOptions, mapResultToProps};
