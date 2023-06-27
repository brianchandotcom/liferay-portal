import AssetsListCard from '../components/AssetsListCard';
import AssetsQuery from 'shared/queries/AssetsQuery';
import BaseCard from 'shared/components/base-card';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {HOC_CARD_PROPTYPES} from 'shared/util/proptypes';
import {
	mapPropsToOptions,
	mapResultToProps
} from './mappers/touchpoint-assets-list-query';
import {withEmpty} from 'cerebro-shared/hocs/utils';
import {withError, withLoading} from 'shared/hoc';

const AssetsListWithData = compose(
	graphql(AssetsQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps
	}),
	withLoading(),
	withError({page: false}),
	withEmpty({
		emptyDescription: (
			<>
				<span className='mr-1'>
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
					)}
				</span>

				<a
					href={URLConstants.SitesDashboardPagesAssets}
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('learn-more-about-display-assets')}
				</a>
			</>
		),
		emptyTitle: Liferay.Language.get(
			'there-are-no-assets-on-the-selected-period'
		)
	})
)(AssetsListCard);

AssetsListWithData.propTypes = HOC_CARD_PROPTYPES;

const defaultProps = {
	className: 'analytics-assets-list-card'
};

const AssetsListBaseCard = ({className, label}) => (
	<BaseCard
		className={className}
		label={label}
		legacyDropdownRangeKey={false}
		minHeight={536}
	>
		{({filters, rangeSelectors, router}) => (
			<AssetsListWithData
				filters={filters}
				rangeSelectors={rangeSelectors}
				router={router}
			/>
		)}
	</BaseCard>
);

AssetsListBaseCard.defaultProps = defaultProps;

export default AssetsListBaseCard;
