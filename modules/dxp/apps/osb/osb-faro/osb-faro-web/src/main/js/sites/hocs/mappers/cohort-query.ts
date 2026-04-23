import {Interval} from 'shared/types';
import {safeResultToProps} from 'shared/util/mappers';

interface ICohortResult {
	cohort: {
		anonymousCohortHeatMapMetrics: unknown[];
		knownCohortHeatMapMetrics: unknown[];
		visitorsCohortHeatMapMetrics: unknown[];
	};
}

const mapResultToProps = safeResultToProps(
	({
		cohort: {
			anonymousCohortHeatMapMetrics,
			knownCohortHeatMapMetrics,
			visitorsCohortHeatMapMetrics
		}
	}: ICohortResult) => ({
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

const mapPropsToOptions = ({
	channelId,
	interval
}: {
	channelId: string;
	interval: Interval;
}) => ({
	variables: {
		channelId,
		interval
	}
});

export {mapPropsToOptions, mapResultToProps};
