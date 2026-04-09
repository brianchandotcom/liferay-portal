import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';
import {CopyInputValue} from '../CopyInputValue';
import {createDemandbase, updateDemandbase} from 'shared/api/data-source';
import {CredentialTypes, DataSourceStatuses} from 'shared/util/constants';
import {DataSource} from 'shared/util/records';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';

interface IConnectDemandbaseAuthProps {
	addAlert: any;
	buttonProps?: {[key: string]: any};
	dataSource?: DataSource;
	disabled?: boolean;
	groupId: string;
	onCancel?: () => void;
	onSubmit: (dataSource: any) => void;
}

const ConnectDemandbaseAuth: React.FC<IConnectDemandbaseAuthProps> = ({
	addAlert,
	buttonProps,
	dataSource,
	disabled = false,
	groupId,
	onCancel,
	onSubmit
}) => {
	const endpointURL = `${window.location.origin}/api/account`;

	const [token, setToken] = useState('');
	const [isSubmitting, setIsSubmitting] = useState(false);

	useEffect(() => {
		const fetchDemandbaseToken = async () => {
			try {
				const data = await API.apiTokens.generateDemandbaseToken({
					groupId
				});

				if (data?.token) setToken(data.token);
			} catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: error.message,
					timeout: false
				});
			}
		};

		fetchDemandbaseToken();
	}, [groupId]);

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();
				setIsSubmitting(true);

				try {
					if (dataSource) {
						const updatedDataSource = await updateDemandbase({
							credentials: {
								privateKey: token,
								publicKey: '',
								type: CredentialTypes.Token
							},
							groupId,
							id: dataSource.id,
							name: dataSource.name,
							status: DataSourceStatuses.Active
						} as any);

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'data-source-credentials-saved'
							)
						});

						onSubmit(updatedDataSource);
					} else {
						const newDataSource = await createDemandbase({
							credentials: {
								privateKey: token,
								publicKey: '',
								type: CredentialTypes.Token
							},
							groupId,
							name: Liferay.Language.get('demandbase'),
							status: DataSourceStatuses.Active
						} as any);

						onSubmit(newDataSource);
					}
				} catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						)
					});
				} finally {
					setIsSubmitting(false);
				}
			}}
		>
			<label htmlFor='endpoint'>
				<Text weight='semi-bold'>
					{Liferay.Language.get(
						'copy-this-endpoint-url-to-your-demandbase-instance'
					)}
				</Text>
			</label>

			<Text as='p' color='secondary' size={3}>
				{sub(
					Liferay.Language.get(
						'this-is-analytics-cloud-url-x-will-redirect-to-after-a-user-authorizes-the-connection'
					),
					[Liferay.Language.get('demandbase')]
				)}
			</Text>

			<CopyInputValue
				addAlert={addAlert}
				disabled={disabled}
				value={endpointURL}
			/>

			<label htmlFor='token'>
				<Text weight='semi-bold'>
					{sub(
						Liferay.Language.get(
							'copy-this-token-to-your-x-instance'
						),
						[Liferay.Language.get('demandbase')]
					)}
				</Text>
			</label>

			<CopyInputValue
				addAlert={addAlert}
				disabled={disabled}
				value={token}
			/>

			{!disabled && (
				<div className='mt-4'>
					<ClayButton
						{...buttonProps}
						disabled={isSubmitting || !token}
						loading={isSubmitting}
						type='submit'
					>
						{Liferay.Language.get('connect')}
					</ClayButton>

					{onCancel && (
						<ClayButton
							block
							borderless
							displayType='secondary'
							onClick={onCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					)}
				</div>
			)}
		</ClayForm>
	);
};

export default ConnectDemandbaseAuth;
