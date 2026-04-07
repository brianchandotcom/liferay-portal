import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import React, {useContext} from 'react';
import {ChannelContext} from 'shared/context/channel';
import {useParams} from 'react-router-dom';

const BaseLifecycle = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId} = useParams();

	return (
		<BasePage documentTitle={Liferay.Language.get('lifecycles')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel.name
					})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						className='mb-3'
						title={Liferay.Language.get('lifecycles')}
					/>
				</BasePage.Row>
			</BasePage.Header>
			<BasePage.SubHeader>
				<div className='d-flex justify-content-between w-100'></div>
			</BasePage.SubHeader>
		</BasePage>
	);
};

export default BaseLifecycle;
