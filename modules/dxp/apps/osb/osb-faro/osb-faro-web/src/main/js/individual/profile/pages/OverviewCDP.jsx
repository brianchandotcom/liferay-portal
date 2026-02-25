import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ContextualInfo from '../components/ContextualInfo';
import IndividualProfileCard from '../hoc/ProfileCard';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {connect} from 'react-redux';
import {isNil} from 'lodash';
import {Routes, toRoute} from 'shared/util/router';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useRequest} from 'shared/hooks/useRequest';

const OverviewCDPEmptyState = ({
	authorized,
	dataSourceData,
	dataSourceLoading,
	groupId
}) => {
	if (dataSourceLoading) {
		return (
			<NoResultsDisplay>
				<Loading key='LOADING' />
			</NoResultsDisplay>
		);
	}

	const sitesSelected = dataSourceData?.items[0]?.sitesSelected;

	const noSitesSelected = isNil(sitesSelected) || !sitesSelected;

	if (noSitesSelected) {
		return (
			<Card pageDisplay>
				<NoResultsDisplay
					description={
						<>
							{authorized
								? Liferay.Language.get(
										'connect-a-data-source-containing-site-data'
								  )
								: Liferay.Language.get(
										'contact-an-administrator-to-connect-a-data-source-containing-site-data'
								  )}

							<ClayLink
								className='d-block mb-3'
								decoration='underline'
								href={URLConstants.DataSourceConnection}
								key='DOCUMENTATION'
								target='_blank'
							>
								{Liferay.Language.get(
									'learn-more-about-data-sources'
								)}

								<span className='inline-item inline-item-after'>
									<ClayIcon fontSize={8} symbol='shortcut' />
								</span>
							</ClayLink>
						</>
					}
					primary
					title={Liferay.Language.get('no-site-data-synced')}
				>
					{authorized && (
						<ClayLink
							button
							className='button-root mt-1'
							displayType='primary'
							href={toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
								groupId
							})}
						>
							{Liferay.Language.get('connect-data-source')}
						</ClayLink>
					)}
				</NoResultsDisplay>
			</Card>
		);
	}

	return null;
};

const Overview = ({channelId, groupId, individual, tabId, timeZoneId}) => {
	const currentUser = useCurrentUser();
	const authorized = currentUser.isAdmin();

	const {data: dataSourceData, loading: dataSourceLoading} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			delta: 1,
			groupId
		}
	});

	const sitesSelected = dataSourceData?.items[0]?.sitesSelected;

	const EMPTY_STATE_FRAGMENT = (
		<OverviewCDPEmptyState
			authorized={authorized}
			dataSourceData={dataSourceData}
			dataSourceLoading={dataSourceLoading}
			groupId={groupId}
		/>
	);

	return (
		<div className='overview-column-main'>
			<ContextualInfo showEmptyState={!sitesSelected}>
				{EMPTY_STATE_FRAGMENT}
			</ContextualInfo>
			<IndividualProfileCard
				channelId={channelId}
				entity={individual}
				groupId={groupId}
				showEmptyState={!sitesSelected}
				tabId={tabId}
				timeZoneId={timeZoneId}
			>
				{EMPTY_STATE_FRAGMENT}
			</IndividualProfileCard>
		</div>
	);
};

export default connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}))(Overview);
