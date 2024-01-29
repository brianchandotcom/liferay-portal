/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useState} from 'react';
import {useGetMyUserAccount} from '~/common/services/liferay/graphql/user-accounts';
import RenewTableFooter from '~/routes/customer-portal/containers/ActivationKeysTable/components/RenewTableFooter';
import {hasAdminUserAccount} from '~/routes/customer-portal/containers/ActivationKeysTable/utils/hasAdminUserAccount';
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import {useCustomerPortal} from '../../../context';

const RenewTable = ({hasKeyComplimentary, isDXPTable, isRenewTable}) => {
	const productName = isDXPTable ? 'DXP' : 'Portal';

	const [{project, sessionId}] = useCustomerPortal();
	const {data: myAccount} = useGetMyUserAccount();

	const isAdminUserAccount = hasAdminUserAccount(myAccount);

	const [keysSelected, setKeysSelected] = useState('');
	const [activationKeysChecked, setActivationKeysChecked] = useState('');
	const [filterCheckedRenewKeys, setFilterCheckedRenewKeys] = useState('');

	const initialFilter = isDXPTable
		? "(startswith(productName,'DXP') or startswith(productName,'Digital'))"
		: "startswith(productName,'Portal')";

	return (
		<div className="container renew-table">
			<ActivationKeysTable
				hasKeyComplimentary={hasKeyComplimentary}
				initialFilter={initialFilter}
				isRenewTable={isRenewTable}
				productName={productName}
				project={project}
				sessionId={sessionId}
				setActivationKeysChecked={setActivationKeysChecked}
				setFilterCheckedRenewKeys={setFilterCheckedRenewKeys}
				setKeysSelected={setKeysSelected}
			/>

			<RenewTableFooter
				activationKeysChecked={activationKeysChecked}
				filterCheckedRenewKeys={filterCheckedRenewKeys}
				isAdminUserAccount={isAdminUserAccount}
				isRenewTable={isRenewTable}
				keysSelected={keysSelected}
				productName={productName}
				project={project}
			/>
		</div>
	);
};

export default RenewTable;
