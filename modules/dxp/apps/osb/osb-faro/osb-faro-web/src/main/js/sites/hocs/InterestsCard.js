import Button from 'shared/components/Button';
import Card from 'shared/components/Card';
import CardWithRangeKey from 'shared/hoc/CardWithRangeKey';
import InterestsQuery from 'shared/queries/InterestsQuery';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {compositionListColumns} from 'shared/util/table-columns';
import {CompositionTypes} from 'shared/util/constants';
import {
	getMapResultToProps,
	mapCardPropsToOptions
} from './mappers/composition-query';
import {graphql} from '@apollo/react-hoc';
import {Routes, setUriQueryValues, toRoute} from 'shared/util/router';
import {useParams} from 'react-router-dom';
import {withTableData} from 'shared/hoc';

const withData = () =>
	graphql(InterestsQuery, {
		options: mapCardPropsToOptions,
		props: getMapResultToProps(CompositionTypes.SiteInterests)
	});

const TableWithData = withTableData(withData, {
	emptyDescription: (
		<>
			<span className='mr-1'>
				{Liferay.Language.get(
					'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
				)}
			</span>

			<a
				href={URLConstants.SitesDashboardSearchTermsAndInterests}
				key='DOCUMENTATION'
				target='_blank'
			>
				{Liferay.Language.get('learn-more-about-interests')}
			</a>
		</>
	),
	emptyTitle: Liferay.Language.get(
		'there-are-no-interests-on-the-selected-period'
	),
	getColumns: ({maxCount, totalCount}) => [
		compositionListColumns.getRelativeMetricBar({
			label: `${Liferay.Language.get(
				'interest-topics'
			)} | ${Liferay.Language.get('sessions')}`,
			maxCount,
			showName: true,
			totalCount
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('sessions'),
			totalCount
		})
	],
	rowIdentifier: 'name'
});

const InterestsCard = () => {
	const {channelId, groupId} = useParams();

	return (
		<CardWithRangeKey
			className='interests-card-root'
			label={Liferay.Language.get('interests')}
			legacyDropdownRangeKey={false}
		>
			{({rangeSelectors}) => (
				<>
					<TableWithData
						channelId={channelId}
						rangeSelectors={rangeSelectors}
						rowBordered={false}
					/>

					<Card.Footer>
						<Button
							display='link'
							href={setUriQueryValues(
								rangeSelectors,
								toRoute(Routes.SITES_INTERESTS, {
									channelId,
									groupId
								})
							)}
							icon='angle-right'
							iconAlignment='right'
							size='sm'
						>
							{Liferay.Language.get('all-interests')}
						</Button>
					</Card.Footer>
				</>
			)}
		</CardWithRangeKey>
	);
};

export default InterestsCard;
