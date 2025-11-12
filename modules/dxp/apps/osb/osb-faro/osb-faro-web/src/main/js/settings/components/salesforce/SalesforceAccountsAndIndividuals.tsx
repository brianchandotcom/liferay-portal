import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';
import {ClayToggle} from '@clayui/form';
import {DataSource} from 'shared/util/records';
import {sub} from 'shared/util/lang';
import {updateSalesforce} from 'shared/api/data-source';

interface ISalesforceAccountsAndIndividualsProps {
	addAlert: Alert.AddAlert;
	groupId: string;
	accountsSyncedCount?: number;
	dataSource: DataSource;
	disabled?: boolean;
	individualsSyncedCount?: number;
	onChange: () => void;
}

const SalesforceAccountsAndIndividuals: React.FC<ISalesforceAccountsAndIndividualsProps> = ({
	accountsSyncedCount,
	addAlert,
	dataSource,
	disabled = false,
	groupId,
	individualsSyncedCount,
	onChange
}) => {
	const [loading, setLoading] = useState(false);
	const [enabledAccount, setEnabledAccount] = useState(false);
	const [enabledIndividual, setEnabledIndividual] = useState(false);

	const handleChange = async (dataSource: any) => {
		setLoading(true);

		try {
			await updateSalesforce(dataSource);

			onChange();
		} catch (error) {
			addAlert({
				alertType: Alert.Types.Error,
				message: Liferay.Language.get(
					'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
				)
			});
		} finally {
			setLoading(false);
		}
	};

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
		<div className='pt-1'>
			<ClayList className='mb-0'>
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='briefcase'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('accounts')}
						</ClayList.ItemTitle>

						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-fields-from-the-account-object-within-salesforce'
							)}
						</ClayList.ItemText>

						{accountsSyncedCount >= 0 && (
							<ClayList.ItemText>
								{sub(Liferay.Language.get('x-items-synced'), [
									accountsSyncedCount
								])}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayList.ItemField
						style={{
							display: 'flex',
							justifyContent: 'center',
							width: '120px'
						}}
					>
						<ClayToggle
							disabled={disabled || loading}
							id='accounts'
							label={
								enabledAccount
									? Liferay.Language.get('connected')
									: Liferay.Language.get('disconnected')
							}
							onToggle={async accounts => {
								await handleChange({
									accountsConfiguration: {
										enableAllAccounts: accounts
									},
									groupId,
									id: dataSource.id
								});

								setEnabledAccount(accounts);
							}}
							sizing='sm'
							toggled={enabledAccount && !disabled}
						/>
					</ClayList.ItemField>
				</ClayList.Item>

				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='users'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField
						className='d-flex justify-content-center'
						expand
					>
						<ClayList.ItemTitle>
							{Liferay.Language.get('individuals')}
						</ClayList.ItemTitle>

						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-fields-from-the-contact-or-lead-object-within-salesforce'
							)}
						</ClayList.ItemText>

						{individualsSyncedCount >= 0 && (
							<ClayList.ItemText>
								{sub(Liferay.Language.get('x-items-synced'), [
									individualsSyncedCount
								])}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayList.ItemField
						style={{
							display: 'flex',
							justifyContent: 'center',
							width: '120px'
						}}
					>
						<ClayToggle
							disabled={disabled || loading}
							id='individuals'
							label={
								enabledIndividual
									? Liferay.Language.get('connected')
									: Liferay.Language.get('disconnected')
							}
							name={Liferay.Language.get('individuals')}
							onToggle={async individuals => {
								await handleChange({
									contactsConfiguration: {
										enableAllContacts: individuals,
										enableAllLeads: individuals
									},
									groupId,
									id: dataSource.id
								});

								setEnabledIndividual(individuals);
							}}
							sizing='sm'
							toggled={enabledIndividual && !disabled}
						/>
					</ClayList.ItemField>
				</ClayList.Item>
			</ClayList>
		</div>
	);
};

export default SalesforceAccountsAndIndividuals;
