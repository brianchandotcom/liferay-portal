import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ContextualInformation from '../components/ContextualInformation';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import ProfileCardCDP from '../hoc/ProfileCardCDP';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {connect} from 'react-redux';
import {Routes, toRoute} from 'shared/util/router';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDataSources} from 'shared/context/dataSources';
import {useRequest} from 'shared/hooks/useRequest';

const OverviewCDPEmptyState = ({
	authorized,
	dataSourceData,
	dataSourceLoading,
	groupId,
	hasConnectedDataSources,
	pageDisplay = true
}) => {
	if (dataSourceLoading) {
		return (
			<NoResultsDisplay>
				<Loading key='LOADING' />
			</NoResultsDisplay>
		);
	}

	const noSitesSelected = !dataSourceData?.items?.some(
		dataSource => dataSource.sitesSelected
	);

	if (noSitesSelected) {
		return (
			<Card pageDisplay={pageDisplay}>
				<Card.Body>
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
										<ClayIcon
											fontSize={8}
											symbol='shortcut'
										/>
									</span>
								</ClayLink>
							</>
						}
						primary
						title={Liferay.Language.get('no-site-data-synced')}
					>
						{authorized && !hasConnectedDataSources && (
							<ClayLink
								button
								className='button-root mt-1'
								displayType='primary'
								href={toRoute(
									Routes.SETTINGS_DATA_SOURCE_LIST,
									{
										groupId
									}
								)}
							>
								{Liferay.Language.get('connect-data-source')}
							</ClayLink>
						)}
					</NoResultsDisplay>
				</Card.Body>
			</Card>
		);
	}

	return null;
};

const Overview = ({channelId, groupId, individual, tabId, timeZoneId}) => {
	const currentUser = useCurrentUser();

	const authorized = currentUser.isAdmin();

	const dataSourceStates = useDataSources();

	const {data: dataSourceData, loading: dataSourceLoading} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			delta: 500,
			groupId
		}
	});

	const sitesSelected = dataSourceData?.items?.some(
		dataSource => dataSource.sitesSelected
	);

	return (
		<div className='overview-column-main'>
			<ContextualInformation
				contactId={individual.get('id')}
				contextData={individual.get('context')}
				email={individual.getIn(['properties', 'email'])}
				showEmptyState={!sitesSelected}
				userId={individual.getIn(['properties', 'userId'])}
				uuid={individual.getIn(['properties', 'uuid'])}
			>
				<OverviewCDPEmptyState
					authorized={authorized}
					dataSourceData={dataSourceData}
					dataSourceLoading={dataSourceLoading}
					groupId={groupId}
					hasConnectedDataSources={!dataSourceStates.empty}
					pageDisplay={false}
				/>
			</ContextualInformation>

			<ProfileCardCDP
				channelId={channelId}
				entity={individual}
				groupId={groupId}
				showEmptyState={!sitesSelected}
				tabId={tabId}
				timeZoneId={timeZoneId}
			>
				<OverviewCDPEmptyState
					authorized={authorized}
					dataSourceData={dataSourceData}
					dataSourceLoading={dataSourceLoading}
					groupId={groupId}
					hasConnectedDataSources={!dataSourceStates.empty}
				/>
			</ProfileCardCDP>
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
