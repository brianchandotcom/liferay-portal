import {} from 'shared/util/router';
import BaseInterestDetails from '../BaseInterestDetails';
import PropTypes from 'prop-types';
import React from 'react';
import {Account} from 'shared/util/records';
import {ACCOUNTS, INDIVIDUALS, Routes} from 'shared/util/router';

export default class InterestDetails extends React.Component {
	static propTypes = {
		account: PropTypes.instanceOf(Account).isRequired
	};

	render() {
		const {account, tabId = INDIVIDUALS, ...otherProps} = this.props;

		return (
			<BaseInterestDetails
				{...otherProps}
				entity={account}
				interestDetailsRoute={Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS}
				tabId={tabId}
				type={ACCOUNTS}
			/>
		);
	}
}
