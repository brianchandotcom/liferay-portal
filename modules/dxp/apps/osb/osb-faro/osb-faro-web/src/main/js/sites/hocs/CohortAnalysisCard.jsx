import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import CohortAnalysis from 'sites/components/cohort-analysis';
import CohortQuery from 'shared/queries/CohortQuery';
import Form from 'shared/components/form';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useContext, useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {ClaySelectWithOption} from '@clayui/select';
import {compose} from 'shared/hoc';
import {
	DAY,
	INTERVAL_OPTIONS,
	VISITORS,
	VISITORS_TYPE_OPTIONS
} from 'sites/components/cohort-analysis/utils';
import {graphql} from '@apollo/react-hoc';
import {mapPropsToOptions, mapResultToProps} from './mappers/cohort-query';
import {withError, withLoading} from 'shared/hoc/util';

const withEmpty =
	Component =>
	({empty, ...otherProps}) => {
		if (empty) {
			return (
				<NoResultsDisplay
					description={
						<>
							<span className='mr-1'>
								{Liferay.Language.get(
									'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
								)}
							</span>

							<a
								href={URLConstants.SitesDashboardCohortAnalysis}
								key='DOCUMENTATION'
								target='_blank'
							>
								{Liferay.Language.get(
									'learn-more-about-cohort-analysis'
								)}
							</a>
						</>
					}
					title={Liferay.Language.get(
						'there-are-no-sessions-on-the-selected-period'
					)}
				/>
			);
		}

		return <Component {...otherProps} />;
	};

const CohortAnalysisWithData = compose(
	graphql(CohortQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps
	}),
	withError({page: false}),
	withEmpty,
	withLoading({alignCenter: true, page: false})
)(CohortAnalysis);

const CohortAnalysisCard = () => {
	const {router} = useContext(BasePage.Context);

	const [interval, setInterval] = useState(DAY);
	const [visitorsType, setVisitorsType] = useState(VISITORS);

	const handleIntervalSelect = event => {
		const {value} = event.target;

		setInterval(value);
	};

	const handleVisitorsTypeSelect = event => {
		const {value} = event.target;

		setVisitorsType(value);
	};

	const {
		params: {channelId}
	} = router;

	return (
		<Card className='cohort-analysis-card-root'>
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('cohort-analysis')}
				</Card.Title>
			</Card.Header>

			<Card.Body>
				<Form.Group autoFit>
					<Form.GroupItem shrink>
						<ClaySelectWithOption
							className='visitors-type-select'
							onChange={handleVisitorsTypeSelect}
							options={VISITORS_TYPE_OPTIONS}
							value={visitorsType}
						/>
					</Form.GroupItem>

					<Form.GroupItem label shrink>
						{Liferay.Language.get('broken-down-by')}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<ClaySelectWithOption
							className='interval-select'
							onChange={handleIntervalSelect}
							options={INTERVAL_OPTIONS}
							value={interval}
						/>
					</Form.GroupItem>
				</Form.Group>

				<CohortAnalysisWithData
					channelId={channelId}
					interval={interval}
					visitorsType={visitorsType}
				/>
			</Card.Body>
		</Card>
	);
};

export default CohortAnalysisCard;
