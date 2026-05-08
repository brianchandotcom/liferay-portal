import AccountIndividuals from './components/AccountIndividuals';
import AccountInfo, {IAccount} from './components/AccountInfo';
import React from 'react';

interface IProfileProps {
	account?: IAccount;
}

const Profile: React.FC<IProfileProps> = ({account}) => (
	<>
		<AccountInfo account={account} />
		<AccountIndividuals />
	</>
);

export default Profile;
