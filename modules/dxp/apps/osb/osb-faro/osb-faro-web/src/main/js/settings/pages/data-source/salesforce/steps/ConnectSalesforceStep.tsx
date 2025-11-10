import ClayAlert from '@clayui/alert';
import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';
import {ButtonGroup} from './ButtonGroup';
import {ConnectSalesforceAuth} from 'settings/components/salesforce/ConnectSalesforceAuth';
import {DataSource} from 'shared/util/records';
import {disconnect, fetch} from 'shared/api/data-source';
import {modalTypes} from 'shared/actions/modals';
import {Routes, toRoute} from 'shared/util/router';
import {Text} from '@clayui/core';
import {updateSearchParams} from '../utis';
import {useHistory} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

const ConnectSalesforceStep = ({addAlert, close, groupId, onNext, open}) => {
	const history = useHistory();
	const {id} = useQueryParams();
	const [dataSource, setDataSource] = useState();

	useEffect(() => {
		async function fetchFn() {
			try {
				const dataSource = await fetch({
					groupId,
					id
				});

				setDataSource(dataSource);
			} catch (error) {
				throw new Error(error);
			}
		}

		if (id) {
			fetchFn();
		}
	}, [id]);

	if (!id) {
		return (
			<ConnectSalesforceAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				onCancel={() => {
					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
							groupId
						})
					);
				}}
				onSubmit={({id}) => {
					updateSearchParams(history, 'id', id);

					onNext();
				}}
			/>
		);
	}

	if (!dataSource) {
		return null;
	}

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
				{Liferay.Language.get('connection-established-successfully')}
			</ClayAlert>

			<ConnectSalesforceAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				dataSource={new DataSource(dataSource)}
				disabled
				onSubmit={onNext}
			/>

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={() => {
					open(modalTypes.CONFIRMATION_MODAL, {
						message: (
							<Text as='p' size={4}>
								{Liferay.Language.get(
									'this-action-will-stop-syncing-data-from-salesforce-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
								)}
							</Text>
						),
						modalVariant: 'modal-warning',
						onClose: close,
						onSubmit: () =>
							disconnect({
								groupId,
								id
							})
								.then(() => {
									addAlert({
										alertType: Alert.Types.Success,
										message: Liferay.Language.get(
											'data-source-disconnected'
										)
									});

									history.push(
										toRoute(
											Routes.SETTINGS_SALESFORCE_ADD,
											{
												groupId
											}
										)
									);

									close();
								})
								.catch(() => {
									addAlert({
										alertType: Alert.Types.Error,
										message: Liferay.Language.get(
											'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
										),
										timeout: false
									});
								}),
						submitButtonDisplay: 'warning',
						submitMessage: Liferay.Language.get('disconnect'),
						title: Liferay.Language.get('disconnect-data-source'),
						titleIcon: 'warning-full'
					});
				}}
				prevButtonLabel={Liferay.Language.get('disconnect-data-source')}
			/>
		</ClayForm>
	);
};

export {ConnectSalesforceStep};
