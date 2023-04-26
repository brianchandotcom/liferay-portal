import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import ClayLink from '@clayui/link';
import EventAnalysisListCard from '../hocs/EventAnalysisListCard';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import URLConstants from 'shared/util/url-constants';
import {Routes, toRoute} from 'shared/util/router';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useParams} from 'react-router-dom';
import {User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

interface IListProps extends React.HTMLAttributes<HTMLElement> {
	currentUser: User;
}

const List: React.FC<IListProps> = ({currentUser}) => {
	const {channelId, groupId} = useParams();

	const dataSourceStates = useDataSource();
	const {empty, error, loading} = dataSourceStates;

	const pageAction = [
		{
			button: true,
			disabled: empty || error || loading,
			displayType: 'primary',
			href: toRoute(Routes.EVENT_ANALYSIS_CREATE, {
				channelId,
				groupId
			}),
			label: Liferay.Language.get('create-analysis')
		}
	];

	const authorized = currentUser.isAdmin();

	return (
		<BasePage documentTitle={Liferay.Language.get('event-analysis')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: Liferay.Language.get('home')
					})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						title={Liferay.Language.get('event-analysis')}
					/>

					<BasePage.Header.Section>
						<BasePage.Header.PageActions actions={pageAction} />
					</BasePage.Header.Section>
				</BasePage.Row>
			</BasePage.Header>

			<BasePage.Body>
				<StatesRenderer {...dataSourceStates}>
					<StatesRenderer.Empty
						description={
							<>
								{Liferay.Language.get(
									'connect-a-data-source-to-get-started'
								)}

								<a
									className='d-block mb-3'
									href={URLConstants.DataSourceConnection}
									key='DOCUMENTATION'
									target='_blank'
								>
									{Liferay.Language.get(
										'access-our-documentation-to-learn-more'
									)}
								</a>

								{authorized && (
									<ClayLink
										button
										className='button-root'
										displayType='primary'
										href={toRoute(
											Routes.SETTINGS_ADD_DATA_SOURCE,
											{
												groupId
											}
										)}
									>
										{Liferay.Language.get(
											'connect-data-source'
										)}
									</ClayLink>
								)}
							</>
						}
						displayCard
						title={Liferay.Language.get(
							'no-data-sources-connected'
						)}
					/>

					<StatesRenderer.Success>
						<EventAnalysisListCard />
					</StatesRenderer.Success>
				</StatesRenderer>
			</BasePage.Body>
		</BasePage>
	);
};

export default withCurrentUser(List);
