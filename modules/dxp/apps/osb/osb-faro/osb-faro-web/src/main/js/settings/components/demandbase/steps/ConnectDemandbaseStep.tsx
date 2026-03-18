import ClayAlert from '@clayui/alert';
import ClayForm from '@clayui/form';
import ConnectDemandbaseAuth from '../ConnectDemandbaseAuth';
import React from 'react';
import {Alert} from 'shared/types';
import {DataSourceStatuses} from 'shared/util/constants';
import {disconnect} from 'shared/api/data-source';
import {modalTypes} from 'shared/actions/modals';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {updateSearchParams} from 'settings/components/base-page/utis';
import {useHistory} from 'react-router-dom';
import {useWizardPage} from '../../base-page/WizardPageContext';
import {WizardPageButtonGroup} from 'settings/components/base-page/WizardPageButtonGroup';

const ConnectDemandbaseStep = ({addAlert, close, groupId, onNext, open}) => {
	const {dataSource, refetchDataSource} = useWizardPage();
	const history = useHistory();

	if (!dataSource) {
		return (
			<ConnectDemandbaseAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				groupId={groupId}
				onCancel={() => {
					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
							groupId
						})
					);
				}}
				onSubmit={createdDataSource => {
					updateSearchParams(
						history,
						'dataSourceId',
						createdDataSource.id
					);
					onNext();
				}}
			/>
		);
	}

	if (dataSource.get('status') === DataSourceStatuses.Active) {
		return (
			<ClayForm
				onSubmit={event => {
					event.preventDefault();
					onNext();
				}}
			>
				<ClayAlert
					displayType='success'
					title={Liferay.Language.get('success')}
				>
					{Liferay.Language.get('token-authenticated-successfully')}
				</ClayAlert>

				<ConnectDemandbaseAuth
					addAlert={addAlert}
					buttonProps={{block: true}}
					dataSource={dataSource}
					disabled
					groupId={groupId}
					onSubmit={onNext}
				/>

				<WizardPageButtonGroup
					nextButtonLabel={Liferay.Language.get('continue')}
					onCancel={() => {
						open(modalTypes.CONFIRMATION_MODAL, {
							message: (
								<Text as='p' size={4}>
									{sub(
										Liferay.Language.get(
											'this-action-will-stop-syncing-data-from-your-x-instance-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
										),
										[Liferay.Language.get('demandbase')]
									)}
								</Text>
							),
							modalVariant: 'modal-warning',
							onClose: close,
							onSubmit: async () => {
								try {
									await disconnect({
										groupId,
										id: dataSource.id
									});

									updateSearchParams(
										history,
										'dataSourceId',
										''
									);

									refetchDataSource(dataSource.id);

									addAlert({
										alertType: Alert.Types.Success,
										message: Liferay.Language.get(
											'data-source-disconnected'
										)
									});

									close();
								} catch (error) {
									addAlert({
										alertType: Alert.Types.Error,
										message: Liferay.Language.get(
											'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
										)
									});
								}
							},
							submitButtonDisplay: 'warning',
							submitMessage: Liferay.Language.get('disconnect'),
							title: Liferay.Language.get(
								'disconnect-data-source'
							),
							titleIcon: 'warning-full'
						});
					}}
					prevButtonLabel={Liferay.Language.get(
						'disconnect-data-source'
					)}
				/>
			</ClayForm>
		);
	}

	return (
		<ConnectDemandbaseAuth
			addAlert={addAlert}
			buttonProps={{block: true}}
			dataSource={dataSource}
			groupId={groupId}
			onCancel={() => {
				history.push(
					toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
						groupId
					})
				);
			}}
			onSubmit={onNext}
		/>
	);
};

export {ConnectDemandbaseStep};
