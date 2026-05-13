import AccountIndividuals from './components/AccountIndividuals';
import AccountInfo, {IAccount} from './components/AccountInfo';
import LifecycleStatus from './components/LifecycleStatus';
import React from 'react';
import {SectionHeader} from 'shared/components/SectionHeader';

interface IProfileProps {
	account?: IAccount;
}

const Profile: React.FC<IProfileProps> = ({account}) => (
	<>
		<SectionHeader
			icon='plus-squares'
			title={Liferay.Language.get('account-details')}
		/>
		<div className='account-profile-cards'>
			<LifecycleStatus className='h-100' />
			<AccountInfo account={account} className='h-100' />
		</div>
		<AccountIndividuals />
	</>
);

export default Profile;
