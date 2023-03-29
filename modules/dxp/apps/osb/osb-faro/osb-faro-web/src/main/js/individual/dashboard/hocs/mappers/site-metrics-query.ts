import moment from 'moment';
import {getSafeRangeSelectors} from 'shared/util/util';
import {Map} from 'immutable';
import {safeResultToProps} from 'shared/util/mappers';

export const mapPropsToOptions = ({channelId, interval, rangeSelectors}) => ({
	variables: {
		channelId,
		interval,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

export const mapResultToProps = safeResultToProps(
	({
		site: {anonymousVisitorsMetric, knownVisitorsMetric, visitorsMetric}
	}) => ({
		data: anonymousVisitorsMetric.histogram.metrics.reduce(
			(acc, {key, value}, i) => [
				...acc,
				{
					anonymousVisitors: value,
					intervalInitDate: moment.utc(key).valueOf(),
					knownVisitors:
						knownVisitorsMetric.histogram.metrics[i].value,
					visitors: visitorsMetric.histogram.metrics[i].value
				}
			],
			[]
		),
		dateKeysIMap: Map(
			visitorsMetric.histogram.metrics.map(({key, valueKey}) => [
				moment.utc(key).valueOf(),
				valueKey
					.split('/')
					.map(valueKeyHalf => moment.utc(valueKeyHalf).valueOf())
			])
		)
	})
);
