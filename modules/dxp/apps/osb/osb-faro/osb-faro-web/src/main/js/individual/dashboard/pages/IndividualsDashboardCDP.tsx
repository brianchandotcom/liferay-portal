import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import IndividualsOverviewCDP from './IndividualsOverviewCDP';
import React from 'react';
import {useChannelContext} from 'shared/context/channel';
import {useParams} from 'react-router-dom';

const IndividualsDashboardCDP = () => {
	const {channelId, groupId} = useParams();
	const {selectedChannel} = useChannelContext();

	return (
		<BasePage
			className='individuals-dashboard-root'
			documentTitle={Liferay.Language.get('individuals')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name
					}),
					breadcrumbs.getEntityName({
						label: Liferay.Language.get('individuals')
					})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('individuals')}
				/>
			</BasePage.Header>

			<IndividualsOverviewCDP />
		</BasePage>
	);
};

export default IndividualsDashboardCDP;
