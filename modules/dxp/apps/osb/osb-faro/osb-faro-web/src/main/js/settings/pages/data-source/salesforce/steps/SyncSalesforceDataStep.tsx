import ClayForm from '@clayui/form';
import React, {useState} from 'react';
import SalesforceAccountsAndIndividuals from 'settings/components/salesforce/SalesforceAccountsAndIndividuals';
import {ButtonGroup} from './ButtonGroup';
import {Text} from '@clayui/core';

const SyncSalesforceDataStep = ({onNext, onPrev}) => {
	const [accounts, setAccounts] = useState(false);
	const [individuals, setIndividuals] = useState(false);

	return (
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onNext();
			}}
		>
			<div className='mb-2'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			<SalesforceAccountsAndIndividuals
				accounts={accounts}
				individuals={individuals}
				onChange={({accounts, individuals}) => {
					setAccounts(accounts);
					setIndividuals(individuals);
				}}
			/>

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {SyncSalesforceDataStep};
