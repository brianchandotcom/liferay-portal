import AssetsTouchpointQuery from 'shared/queries/AssetsTouchpointQuery';
import BaseCard from 'shared/components/base-card';
import ClayLink from '@clayui/link';
import React from 'react';
import TouchpointsListCard from 'assets/components/TouchpointsListCard';
import URLConstants from 'shared/util/url-constants';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {
	mapPropsToOptions,
	mapResultToProps
} from './mappers/touchpoint-list-query';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {withEmpty} from 'cerebro-shared/hocs/utils';
import {withError, withLoading} from 'shared/hoc';

const TouchpointListWithData = compose(
	graphql(AssetsTouchpointQuery, {
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

				<ClayLink
					href={URLConstants.AssetsDefinitionDocumentation}
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('learn-more-about-assets')}
				</ClayLink>
			</>
		),
		emptyTitle: Liferay.Language.get(
			'there-are-no-assets-on-the-selected-period'
		)
	})
)(TouchpointsListCard);

interface ITouchpointsListCardProps extends React.HTMLAttributes<HTMLElement> {
	assetType: string;
	label: string;
	legacyDropdownRangeKey: boolean;
}

const TouchpointListWithBaseCard: React.FC<ITouchpointsListCardProps> = ({
	assetType,
	label,
	legacyDropdownRangeKey
}) => (
	<BaseCard
		className='analytics-touchpoints-list-card'
		label={label}
		legacyDropdownRangeKey={legacyDropdownRangeKey}
		minHeight={536}
		reportContainer={ReportContainer.AssetAppearsOnCard}
	>
		{({filters, rangeSelectors, router}) => (
			<TouchpointListWithData
				assetType={assetType}
				filters={filters}
				rangeSelectors={rangeSelectors}
				router={router}
			/>
		)}
	</BaseCard>
);

export default TouchpointListWithBaseCard;
