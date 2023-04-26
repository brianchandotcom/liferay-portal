import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import Form, {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import InputWithEditToggle from 'shared/components/InputWithEditToggle';
import Label from 'shared/components/Label';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {addAlert} from '../../../shared/actions/alerts';
import {Alert} from 'shared/types';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStates} from 'shared/util/constants';
import {
	fetchDataSource,
	updateLiferayDataSource
} from 'shared/actions/data-sources';
import {noop} from 'lodash';
import {sequence} from 'shared/util/promise';
import {validateUniqueName} from 'shared/util/data-sources';

const getStatusLabel = (configured: boolean): React.ReactNode => (
	<Label display={configured ? 'success' : 'secondary'} size='lg' uppercase>
		{configured
			? Liferay.Language.get('configured')
			: Liferay.Language.get('unconfigured')}
	</Label>
);

type ConfigurationItem = {
	description: string;
	getConfigurationStatus: (dataSource: DataSource) => boolean;
	title: string;
};

const configurationItems: ConfigurationItem[] = [
	{
		description: Liferay.Language.get(
			'to-configure-sites,-go-to-the-analytics-cloud-configuration-in-your-dxp-instance-settings,-then-select-the-sites-you-would-like-to-start-tracking'
		),
		getConfigurationStatus: dataSource => dataSource.sitesSelected,
		title: Liferay.Language.get('synced-sites')
	},
	{
		description: Liferay.Language.get(
			'to-configure-contacts,-go-to-the-analytics-cloud-configuration-in-your-dxp-instance-settings,-then-select-which-contacts-you-would-like-to-sync'
		),
		getConfigurationStatus: dataSource => dataSource.contactsSelected,
		title: Liferay.Language.get('synced-contacts')
	}
];

const connector = connect(null, {
	addAlert,
	close,
	fetchDataSource,
	open,
	updateLiferayDataSource
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ILiferayOverviewProps extends PropsFromRedux {
	currentUser: User;
	dataSource: DataSource;
	groupId: string;
	id: string;
}

class LiferayOverview extends React.Component<ILiferayOverviewProps> {
	private _cachedNameValues = new Map();

	@autobind
	handleDisconnectClick() {
		const {
			addAlert,
			close,
			fetchDataSource,
			groupId,
			id,
			open
		} = this.props;

		open(modalTypes.CONFIRMATION_MODAL, {
			message: (
				<>
					<p className='text-secondary'>
						{Liferay.Language.get(
							'are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-the-dxp-instance'
						)}
					</p>

					<b>
						{Liferay.Language.get(
							'this-will-stop-any-syncing-of-analytics-or-contact-data-to-your-analytics-cloud-workspace'
						)}
					</b>
				</>
			),
			modalVariant: 'modal-warning',
			onClose: close,
			onSubmit: () =>
				API.dataSource
					.disconnect({
						groupId,
						id
					})
					.then(() => {
						fetchDataSource({
							groupId,
							id
						});

						close();
					})
					.catch(error => {
						addAlert({
							alertType: Alert.Types.Error,
							message: error.message,
							timeout: false
						});

						fetchDataSource({
							groupId,
							id
						});
					}),
			submitButtonDisplay: 'warning',
			submitMessage: Liferay.Language.get('disconnect'),
			title: Liferay.Language.get('disconnecting-data-source'),
			titleIcon: 'warning-full'
		});
	}

	@autobind
	handleReconnectClick() {
		const {close, fetchDataSource, groupId, id, open} = this.props;

		open(modalTypes.CONNECT_DXP_MODAL, {
			groupId,
			id,
			onClose: () => {
				fetchDataSource({groupId, id});

				close();
			}
		});
	}

	@autobind
	handleUpdateName(name) {
		const {groupId, id, updateLiferayDataSource} = this.props;

		return updateLiferayDataSource({groupId, id, name});
	}
	@autobind
	handleValidate(value) {
		const {dataSource, groupId} = this.props;

		let error = '';

		if (value !== dataSource.name) {
			if (this._cachedNameValues.has(value)) {
				error = this._cachedNameValues.get(value);
			} else {
				error = validateUniqueName({groupId, value});

				this._cachedNameValues.set(value, error);
			}
		}

		return toPromise(error);
	}

	render() {
		const {currentUser, dataSource} = this.props;

		const disconnected = dataSource.state === DataSourceStates.Disconnected;

		return (
			<Sheet className='liferay-data-source-root'>
				<Sheet.Header className='mb-1'>
					<Sheet.Title>
						{Liferay.Language.get('data-source-information')}
					</Sheet.Title>
				</Sheet.Header>

				<Sheet.Body>
					<Sheet.Section>
						<InputWithEditToggle
							editable={currentUser.isAdmin()}
							inputWidth={100}
							label={Liferay.Language.get('name')}
							name='dataSourceName'
							onSubmit={this.handleUpdateName}
							required
							validate={sequence([
								validateRequired,
								validateMaxLength(255),
								this.handleValidate
							])}
							value={dataSource.name || ''}
						/>
					</Sheet.Section>

					<Sheet.Section>
						<Form
							initialValues={{
								dataSourceName: dataSource.name,
								dxpInstanceId: dataSource.id
							}}
							onSubmit={noop}
						>
							{() => (
								<Form.Form className='dxp-instance-id-root'>
									<Form.Group>
										<Form.GroupItem className='d-flex mb-3'>
											<Form.Input
												label={Liferay.Language.get(
													'dxp-instance-id'
												)}
												name='dxpInstanceId'
												readOnly
												showSuccess={false}
												width={100}
											/>
										</Form.GroupItem>

										<Form.GroupItem>
											<ClayButton
												className='button-root'
												data-testid='disconnect-button'
												disabled={
													!currentUser.isAdmin()
												}
												displayType='secondary'
												onClick={
													disconnected
														? this
																.handleReconnectClick
														: this
																.handleDisconnectClick
												}
											>
												{disconnected
													? Liferay.Language.get(
															'reconnect'
													  )
													: Liferay.Language.get(
															'disconnect'
													  )}
											</ClayButton>
										</Form.GroupItem>
									</Form.Group>
								</Form.Form>
							)}
						</Form>
					</Sheet.Section>

					{configurationItems.map(
						({
							description,
							getConfigurationStatus,
							title
						}: ConfigurationItem) => (
							<Sheet.Section key={title} lastChildMargin>
								<div className='mb-1 d-flex align-items-center'>
									<h4 className='mb-0 mr-2'>{title}</h4>

									{getStatusLabel(
										getConfigurationStatus(dataSource)
									)}
								</div>

								<p className='description-secondary'>
									{description}
								</p>
							</Sheet.Section>
						)
					)}
				</Sheet.Body>
			</Sheet>
		);
	}
}

export default connector(LiferayOverview);
