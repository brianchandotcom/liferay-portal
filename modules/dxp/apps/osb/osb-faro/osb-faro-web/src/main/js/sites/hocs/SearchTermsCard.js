import CardWithRangeKey from 'shared/hoc/CardWithRangeKey';
import React from 'react';
import SearchTermsQuery from 'shared/queries/SearchTermsQuery';
import URLConstants from 'shared/util/url-constants';
import {compositionListColumns} from 'shared/util/table-columns';
import {CompositionTypes} from 'shared/util/constants';
import {
	getMapResultToProps,
	mapCardPropsToOptions
} from './mappers/composition-query';
import {graphql} from '@apollo/react-hoc';
import {useParams} from 'react-router-dom';
import {withTableData} from 'shared/hoc';

const withData = () =>
	graphql(SearchTermsQuery, {
		options: mapCardPropsToOptions,
		props: getMapResultToProps(CompositionTypes.SearchTerms)
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
				{Liferay.Language.get('learn-more-about-search-terms')}
			</a>
		</>
	),
	emptyTitle: Liferay.Language.get(
		'there-are-no-search-terms-on-the-selected-period'
	),
	getColumns: ({maxCount, totalCount}) => [
		compositionListColumns.getRelativeMetricBar({
			label: `${Liferay.Language.get(
				'search-query'
			)} | ${Liferay.Language.get('searches')}`,
			maxCount,
			showName: true,
			totalCount
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('searches'),
			totalCount
		})
	],
	rowIdentifier: 'name'
});

const SearchTermsCard = props => {
	const {channelId, id} = useParams();

	return (
		<CardWithRangeKey
			className='search-terms-card-root'
			label={Liferay.Language.get('search-terms')}
			legacyDropdownRangeKey={false}
		>
			{({rangeSelectors}) => (
				<TableWithData
					{...props}
					channelId={channelId}
					id={id}
					rangeSelectors={rangeSelectors}
					rowBordered={false}
				/>
			)}
		</CardWithRangeKey>
	);
};

export default SearchTermsCard;
