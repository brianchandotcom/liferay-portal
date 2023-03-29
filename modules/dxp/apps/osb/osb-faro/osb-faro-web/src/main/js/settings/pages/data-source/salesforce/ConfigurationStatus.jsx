import * as breadcrumbs from 'shared/util/breadcrumbs';
import BaseTabsPage, {
	tabIds
} from 'settings/components/data-source/BaseTabsPage';
import ConfigurationOverview from 'settings/components/salesforce/ConfigurationOverview';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {DataSource, User} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {withCurrentUser} from 'shared/hoc';

export class ConfigurationStatus extends React.Component {
	static propTypes = {
		currentUser: PropTypes.instanceOf(User).isRequired,
		dataSource: PropTypes.instanceOf(DataSource).isRequired,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string
	};

	render() {
		const {currentUser, dataSource, groupId, id} = this.props;

		return (
			<BaseTabsPage
				activeTabId={tabIds.CONFIGURE_DATA_SOURCE}
				addRoute={Routes.SETTINGS_SALESFORCE_ADD}
				breadcrumb={{
					label: Liferay.Language.get('data-sources'),
					url: toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
						groupId
					})
				}}
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					{label: dataSource.name, truncate: true},
					{
						active: true,
						label: Liferay.Language.get('configuration-status')
					}
				]}
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
				configurationRoute={
					Routes.SETTINGS_SALESFORCE_CONFIGURATION_STATUS
				}
				currentUser={currentUser}
				dataSource={dataSource}
				documentTitle={
					dataSource
						? dataSource.name
						: Liferay.Language.get('configure-salesforce')
				}
				groupId={groupId}
				id={id}
				key='configurationStatus'
				pageTitle={Liferay.Language.get('configure-salesforce')}
				showDelete
			>
				<Sheet.Body>
					<ConfigurationOverview
						dataSource={dataSource}
						disabled={!currentUser.isAdmin()}
						groupId={groupId}
						id={id}
					/>
				</Sheet.Body>
			</BaseTabsPage>
		);
	}
}

export default withCurrentUser(ConfigurationStatus);
