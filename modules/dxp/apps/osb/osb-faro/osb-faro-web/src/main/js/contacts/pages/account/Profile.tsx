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
		<div className='row'>
			<div className='col-6'>
				<LifecycleStatus className='h-100' />
			</div>
			<div className='col-6'>
				<AccountInfo account={account} className='h-100' />
			</div>
		</div>
		<AccountIndividuals />
	</>
);

export default Profile;
