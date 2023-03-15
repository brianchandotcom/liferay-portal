import * as API from 'shared/api';
import BaseConfigurationOverview from 'settings/components/data-source/BaseConfigurationOverview';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {compose, withHistory, withPolling} from 'shared/hoc';
import {connect} from 'react-redux';
import {DataSource} from 'shared/util/records';
import {
	DataSourceProgressStatuses,
	DataSourceStatuses
} from 'shared/util/constants';
import {get} from 'lodash';
import {getServiceAlertConfig} from 'shared/util/data-sources';
import {getServiceError} from 'shared/util/request';
import {hasChanges} from 'shared/util/react';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

const stopPollingCondition = ({accounts, individuals}, {dataSource}) =>
	DataSourceStatuses.Active !== dataSource.status ||
	[accounts, individuals].every(entity =>
		[
			DataSourceProgressStatuses.Completed,
			DataSourceProgressStatuses.Failed
		].includes(get(entity, 'status'))
	);

export class ConfigurationOverview extends React.Component {
	static propTypes = {
		addAlert: PropTypes.func.isRequired,
		dataSource: PropTypes.instanceOf(DataSource).isRequired,
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired,
		id: PropTypes.string.isRequired,
		pollingError: PropTypes.instanceOf(Error),
		progress: PropTypes.object
	};

	componentDidUpdate(prevProps) {
		if (hasChanges(prevProps, this.props, 'pollingError')) {
			this.handleServicePermissionError();
		}
	}

	buildConfigurationItems() {
		const {
			dataSource: {provider},
			groupId,
			id,
			progress
		} = this.props;

		const buttonParams = {
			display: 'secondary',
			label: Liferay.Language.get('view')
		};

		return [
			{
				buttonParams,
				configuration: provider.get('accountsConfiguration'),
				description: Liferay.Language.get(
					'represents-an-organization-or-person-involved-with-your-business'
				),
				href: toRoute(
					Routes.SETTINGS_SALESFORCE_FIELD_MAPPING_ACCOUNTS,
					{
						groupId,
						id
					}
				),
				label: Liferay.Language.get('accounts'),
				progress: get(progress, 'accounts'),
				title: Liferay.Language.get('accounts')
			},
			{
				buttonParams,
				configuration: provider.get('contactsConfiguration'),
				description: Liferay.Language.get(
					'represented-by-a-contact-or-lead-within-salesforce'
				),
				href: toRoute(
					Routes.SETTINGS_SALESFORCE_FIELD_MAPPING_INDIVIDUALS,
					{
						groupId,
						id
					}
				),
				label: Liferay.Language.get('individuals'),
				progress: get(progress, 'individuals'),
				title: Liferay.Language.get('individuals')
			}
		];
	}

	handleServicePermissionError() {
		const {addAlert, groupId, history, id, pollingError} = this.props;

		const serviceError = getServiceError(pollingError);

		if (serviceError) {
			addAlert(getServiceAlertConfig(serviceError.status));

			history.push(
				toRoute(Routes.SETTINGS_DATA_SOURCE, {
					groupId,
					id
				})
			);
		}
	}

	render() {
		const {
			dataSource: {status},
			...otherProps
		} = this.props;

		return (
			<BaseConfigurationOverview
				{...omitDefinedProps(
					otherProps,
					ConfigurationOverview.propTypes
				)}
				configurationItems={this.buildConfigurationItems()}
				status={status}
			/>
		);
	}
}

export default compose(
	withHistory,
	connect(null, {addAlert}),
	withPolling(API.dataSource.fetchProgress, stopPollingCondition, {
		propName: 'progress',
		requestProps: ['id']
	})
)(ConfigurationOverview);
