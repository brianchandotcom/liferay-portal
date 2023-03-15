import * as breadcrumbs from 'shared/util/breadcrumbs';
import autobind from 'autobind-decorator';
import BaseTabsPage, {
	tabIds
} from 'settings/components/data-source/BaseTabsPage';
import OAuthForm from 'settings/components/data-source/OAuthForm';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {connect} from 'react-redux';
import {
	createSalesforceDataSource,
	updateSalesforceDataSource
} from 'shared/actions/data-sources';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStatuses, DataSourceTypes} from 'shared/util/constants';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';

export class SalesforceAuthorization extends React.Component {
	static propTypes = {
		createSalesforceDataSource: PropTypes.func.isRequired,
		currentUser: PropTypes.instanceOf(User).isRequired,
		dataSource: PropTypes.instanceOf(DataSource),
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string,
		updateSalesforceDataSource: PropTypes.func.isRequired
	};

	@autobind
	handleUpdateSalesforce({dataSourceName, tempCredentials, url}) {
		const {
			createSalesforceDataSource,
			groupId,
			id,
			updateSalesforceDataSource
		} = this.props;

		const request = id
			? updateSalesforceDataSource
			: createSalesforceDataSource;

		return request({
			accountsConfiguration: {enableAllAccounts: true},
			contactsConfiguration: {
				enableAllContacts: true,
				enableAllLeads: true
			},
			credentials: tempCredentials,
			groupId,
			id,
			name: dataSourceName,
			status: DataSourceStatuses.Active,
			url
		}).then(response => {
			analytics.track('Created Salesforce Datasource');

			return response;
		});
	}

	render() {
		const {currentUser, dataSource, groupId, id} = this.props;

		const breadcrumbItems = id
			? [
					breadcrumbs.getDataSourceName({
						active: true,
						label: dataSource.name
					})
			  ]
			: [
					{
						href: toRoute(Routes.SETTINGS_ADD_DATA_SOURCE, {
							groupId
						}),
						label: Liferay.Language.get('add-data-source')
					},
					{
						active: true,
						label: Liferay.Language.get('new-salesforce')
					}
			  ];

		return (
			<BaseTabsPage
				activeTabId={tabIds.AUTHORIZATION}
				addRoute={Routes.SETTINGS_SALESFORCE_ADD}
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					...breadcrumbItems
				]}
				className={`salesforce-data-source-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
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
				pageTitle={Liferay.Language.get('configure-salesforce')}
				showDelete
			>
				<OAuthForm
					authorized={currentUser.isAdmin()}
					dataSource={dataSource}
					defaultUrl={URLConstants.SalesforceLogin}
					groupId={groupId}
					id={id}
					instruction={sub(
						Liferay.Language.get(
							'please-enter-the-url-of-the-target-instance-to-start-configuring-the-x-data-source.-you-will-need-to-enter-the-credentials-of-the-data-sources-administrator.-if-you-need-help-setting-this-up,-please-refer-to-the-{0}'
						),
						[
							Liferay.Language.get('salesforce'),
							<a
								href={URLConstants.SalesforceAddDocumentation}
								key='documentationLink'
							>
								{Liferay.Language.get('documentation-fragment')}
							</a>
						],
						false
					)}
					onSubmit={this.handleUpdateSalesforce}
					redirectRoute={
						Routes.SETTINGS_SALESFORCE_CONFIGURATION_STATUS
					}
					type={DataSourceTypes.Salesforce}
				/>
			</BaseTabsPage>
		);
	}
}

export default connect(null, {
	createSalesforceDataSource,
	updateSalesforceDataSource
})(SalesforceAuthorization);
