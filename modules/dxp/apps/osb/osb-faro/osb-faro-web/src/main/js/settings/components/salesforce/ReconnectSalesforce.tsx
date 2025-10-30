import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';

import {Alert} from 'shared/types';
import {ConnectSalesforceAuth} from './ConnectSalesforceAuth';
import {Routes} from 'shared/util/router';
import {Text} from '@clayui/core';

interface IReconnectSalesforceProps {
	addAlert: (alert: any) => void;
	setAccounts: (accounts: boolean) => void;
	setIndividuals: (individuals: boolean) => void;
	setIsDataSourceConnected: (isConnected: boolean) => void;
}

const ReconnectSalesforce: React.FC<IReconnectSalesforceProps> = ({
	addAlert,
	setAccounts,
	setIndividuals,
	setIsDataSourceConnected
}) => {
	const [inputs, setInputs] = useState({
		callbackURL: `${location.origin}${Routes.OAUTH_RECEIVE}`,
		clientId: {
			value: '',
			visible: false
		},
		clientSecret: {
			value: '',
			visible: false
		},
		salesForceDataSource: ''
	});

	return (
		<>
			<div className='mb-3'>
				<Text color='secondary' size={4}>
					{Liferay.Language.get(
						'to-reestablish-the-connection-between-salesforce-and-liferay-analytics-cloud,-generate-a-token-and-paste-the-code-on-the-input-below'
					)}
				</Text>

				<ClayLink
					className='ml-1'
					href=''
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('learn-more-about-data-sources')}
				</ClayLink>
			</div>

			<ClayForm
				onSubmit={event => {
					event.preventDefault();
				}}
			>
				<ConnectSalesforceAuth
					inputs={inputs}
					onInputsChange={setInputs}
				/>

				<ClayButton
					className='mt-3'
					onClick={() => {
						// TODO: Fire Reconnect Salesforce function

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'token-authenticated-successfully'
							)
						});

						setIsDataSourceConnected(true);
						setAccounts(true);
						setIndividuals(true);
					}}
					type='submit'
				>
					{Liferay.Language.get('connect')}
				</ClayButton>
			</ClayForm>
		</>
	);
};

export default ReconnectSalesforce;
