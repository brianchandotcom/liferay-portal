import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import SalesforceAccountsAndIndividuals from 'settings/components/salesforce/SalesforceAccountsAndIndividuals';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {ButtonGroup} from './ButtonGroup';
import {fetch, updateSalesforce} from 'shared/api/data-source';
import {Text} from '@clayui/core';
import {useConnectSalesforce} from '../ConnectSalesforceContext';
import {useParams} from 'react-router-dom';

const SyncSalesforceDataStep = ({onNext, onPrev}) => {
	const [loading, setLoading] = useState(false);
	const {dataSource, setDataSource} = useConnectSalesforce();
	const {groupId} = useParams();
	const [enabledAccount, setEnabledAccount] = useState(false);
	const [enabledIndividual, setEnabledIndividual] = useState(false);

	useEffect(() => {
		if (dataSource) {
			const accounts = dataSource.provider?.getIn([
				'accountsConfiguration',
				'enableAllAccounts'
			]);

			const contactsConfiguration = dataSource.provider?.get(
				'contactsConfiguration'
			);

			const individuals =
				contactsConfiguration?.get('enableAllContacts') &&
				contactsConfiguration?.get('enableAllLeads');

			setEnabledAccount(accounts);
			setEnabledIndividual(individuals);
		}
	}, []);

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();

				try {
					setLoading(true);

					await updateSalesforce({
						accountsConfiguration: {
							enableAllAccounts: enabledAccount
						},
						contactsConfiguration: {
							enableAllContacts: enabledIndividual,
							enableAllLeads: enabledIndividual
						},
						groupId,
						id: dataSource.id
					} as any);

					const updatedDataSource = await fetch({
						groupId,
						id: dataSource.id
					});

					setDataSource(updatedDataSource);
				} catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						)
					});
				} finally {
					setLoading(false);

					onNext();
				}
			}}
		>
			<div className='mb-2'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			{dataSource && (
				<SalesforceAccountsAndIndividuals
					enabledAccount={enabledAccount}
					enabledIndividual={enabledIndividual}
					onAccountChange={() => setEnabledAccount(!enabledAccount)}
					onIndividualChange={() =>
						setEnabledIndividual(!enabledIndividual)
					}
					type='checkbox'
				/>
			)}

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				nextButtonLoading={loading}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {SyncSalesforceDataStep};
