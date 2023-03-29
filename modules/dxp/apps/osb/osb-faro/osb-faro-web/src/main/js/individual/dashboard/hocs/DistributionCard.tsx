import DistributionCard from 'contacts/components/distribution-card';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {connect, ConnectedProps} from 'react-redux';
import {fetchIndividualsDistribution} from 'shared/actions/distributions';
import {Routes, toRoute} from 'shared/util/router';
import {useParams} from 'react-router-dom';

const connector = connect(null, {
	fetchDistribution: fetchIndividualsDistribution
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IIndividualsDistributionCardProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	id?: string;
	showAddDataSource?: boolean;
}

const IndividualsDistributionCard: React.FC<IIndividualsDistributionCardProps> = ({
	fetchDistribution,
	id,
	...otherProps
}) => {
	const {channelId, groupId} = useParams();

	return (
		<DistributionCard
			channelId={channelId}
			distributionKey='individualsDashboard'
			fetchDistribution={fetchDistribution}
			groupId={groupId}
			id={id}
			noResultsRenderer={() => (
				<NoResultsDisplay
					description={
						<>
							{Liferay.Language.get(
								'try-choosing-a-different-breakdown'
							)}

							<a
								className='d-block'
								href={
									URLConstants.IndividualsDashboardBreakdownDocumentation
								}
								key='DOCUMENTATION'
								target='_blank'
							>
								{Liferay.Language.get(
									'learn-more-about-distribution'
								)}
							</a>
						</>
					}
					title={Liferay.Language.get('there-are-no-results-found')}
				/>
			)}
			viewAllLink={toRoute(Routes.CONTACTS_INDIVIDUALS_DISTRIBUTION, {
				channelId,
				groupId
			})}
			{...otherProps}
		/>
	);
};

export default connector(IndividualsDistributionCard);
