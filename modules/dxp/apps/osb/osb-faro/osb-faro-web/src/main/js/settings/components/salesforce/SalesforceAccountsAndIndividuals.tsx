import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import React from 'react';
import {ClayToggle} from '@clayui/form';
import {sub} from 'shared/util/lang';

interface ISalesforceAccountsAndIndividualsProps {
	accounts: boolean;
	accountsSyncedCount?: number;
	individualsSyncedCount?: number;
	individuals: boolean;
	disabled?: boolean;
	onChange: (values: {accounts: boolean; individuals: boolean}) => void;
}

const SalesforceAccountsAndIndividuals: React.FC<ISalesforceAccountsAndIndividualsProps> = ({
	accounts,
	accountsSyncedCount,
	disabled = false,
	individuals,
	individualsSyncedCount,
	onChange
}) => (
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
						disabled={disabled}
						id='accounts'
						label={
							accounts
								? Liferay.Language.get('connected')
								: Liferay.Language.get('disconnected')
						}
						onToggle={value => {
							onChange({accounts: value, individuals});

							// TODO: fire SyncAccounts function when endpoint is ready
						}}
						sizing='sm'
						toggled={accounts && !disabled}
					/>
				</ClayList.ItemField>
			</ClayList.Item>

			<ClayList.Item flex>
				<ClayList.ItemField>
					<ClaySticker displayType='unstyled'>
						<ClayIcon className='text-secondary' symbol='users' />
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
						disabled={disabled}
						id='individuals'
						label={
							individuals
								? Liferay.Language.get('connected')
								: Liferay.Language.get('disconnected')
						}
						name={Liferay.Language.get('individuals')}
						onToggle={value => {
							onChange({accounts, individuals: value});

							// TODO: fire SyncIndividuals function when endpoint is ready
						}}
						sizing='sm'
						toggled={individuals && !disabled}
					/>
				</ClayList.ItemField>
			</ClayList.Item>
		</ClayList>
	</div>
);

export default SalesforceAccountsAndIndividuals;
