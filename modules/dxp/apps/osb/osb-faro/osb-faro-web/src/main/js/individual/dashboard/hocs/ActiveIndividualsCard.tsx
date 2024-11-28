import ActiveIndividualsChart from '../components/ActiveIndividualsChart';
import BaseCard from 'shared/components/base-card';
import Card from 'shared/components/Card';
import IndividualSiteMetricsQuery from 'shared/queries/IndividualSiteMetricsQuery';
import React from 'react';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {
	mapPropsToOptions,
	mapResultToProps
} from '../hocs/mappers/site-metrics-query';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {useParams} from 'react-router-dom';
import {withError} from 'shared/hoc';

const ChartWithData = compose<any>(
	graphql(IndividualSiteMetricsQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps
	}),
	withError({page: false})
)(ActiveIndividualsChart);

const ActiveIndividualsCard = () => {
	const {channelId} = useParams();

	return (
		<BaseCard
			label={Liferay.Language.get('active-individuals')}
			legacyDropdownRangeKey={false}
			minHeight={536}
			reportContainer={ReportContainer.ActiveIndividualsCard}
			showInterval
		>
			{({interval, rangeSelectors}) => (
				<Card.Body className='justify-content-center'>
					<ChartWithData
						active
						channelId={channelId}
						interval={interval}
						rangeSelectors={rangeSelectors}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
};

export default ActiveIndividualsCard;
