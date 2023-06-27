import Card from 'shared/components/Card';
import IndividualMetricsQuery from 'shared/queries/IndividualMetricsQuery';
import React from 'react';
import TypeTrend from '../components/TypeTrend';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {
	mapPropsToOptions,
	mapResultToProps
} from '../hocs/mappers/individual-metrics-query';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {useParams} from 'react-router-dom';
import {withError, withLoading} from 'shared/hoc';

const TypeTrendWithData = compose<any>(
	graphql(IndividualMetricsQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps
	}),
	withLoading(),
	withError({page: false})
)(TypeTrend);

const TypeTrendCard: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const {channelId} = useParams();

	return (
		<Card className='type-trend-card-root text-secondary'>
			<Card.Body>
				<TypeTrendWithData
					channelId={channelId}
					interval={INTERVAL_KEY_MAP.week}
					rangeSelectors={{rangeKey: RangeKeyTimeRanges.Last30Days}}
				/>
			</Card.Body>
		</Card>
	);
};

export default TypeTrendCard;
