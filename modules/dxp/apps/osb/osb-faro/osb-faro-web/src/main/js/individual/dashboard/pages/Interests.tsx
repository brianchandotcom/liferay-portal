import BasePage from 'shared/components/base-page';
import ClayLink from '@clayui/link';
import Interests from '../hocs/Interests';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import URLConstants from 'shared/util/url-constants';
import {Routes, toRoute} from 'shared/util/router';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useParams} from 'react-router-dom';
import {User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

interface IInterestsPageProps extends React.HTMLAttributes<HTMLElement> {
	currentUser: User;
}

const InterestsPage: React.FC<IInterestsPageProps> = ({currentUser}) => {
	const {groupId} = useParams();
	const authorized = currentUser.isAdmin();

	const dataSourceStates = useDataSource();

	return (
		<BasePage.Body pageContainer>
			<StatesRenderer {...dataSourceStates}>
				<StatesRenderer.Empty
					description={
						<>
							{Liferay.Language.get(
								'connect-a-data-source-with-sites-data'
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
						'no-sites-synced-from-data-sources'
					)}
				/>

				<StatesRenderer.Success>
					<div className='individuals-dashboard-interests-root'>
						<Interests />
					</div>
				</StatesRenderer.Success>
			</StatesRenderer>
		</BasePage.Body>
	);
};

export default withCurrentUser(InterestsPage);
