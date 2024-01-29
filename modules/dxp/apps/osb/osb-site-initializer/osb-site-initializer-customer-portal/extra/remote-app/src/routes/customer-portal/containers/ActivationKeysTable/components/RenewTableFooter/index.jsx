/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import i18n from '~/common/I18n';
import {Button} from '~/common/components';
import RenewButton from '~/routes/customer-portal/containers/ActivationKeysTable/components/Renew';
import {hasAdminOrPartnerManager} from '../../utils/hasAdminOrPartnerManager';
import {hasComplimentaryKey} from '../../utils/hasComplimentaryKey';
import {isBulkRenewAvailable} from '../../utils/isBulkRenewAvaible';
import useGetAccountUserAccount from '../Header/hooks/useGetAccountUserAccount';

const RenewTableFooter = ({
	activationKeysChecked,
	filterCheckedRenewKeys,
	isAdminUserAccount,
	isRenewTable,
	keysSelected,
	productName,
	project,
}) => {
	const {
		userAccountsState: [userAccounts],
	} = useGetAccountUserAccount(project);

	const [isComplimentaryKey, setIsComplimentaryKey] = useState('');

	const currentUser = userAccounts?.find(
		({id}) => id === +Liferay.ThemeDisplay.getUserId()
	);
	const allowSelfProvisioning = project.allowSelfProvisioning;
	const isAdminOrPartnerManager = hasAdminOrPartnerManager(
		project,
		currentUser
	);

	const urlPreviousPage = `/${
		project?.accountKey
	}/activation/${productName.toLowerCase()}`;

	const bulkRenewAvailable = isBulkRenewAvailable(activationKeysChecked);

	useEffect(() => {
		if (activationKeysChecked) {
			const complimentaryKeyValidation = (activationKey) => activationKey;

			const handleComplimentaryKey = activationKeysChecked?.map(
				(activationKey) => hasComplimentaryKey(activationKey)
			);

			const isComplimentaryKey = handleComplimentaryKey.some(
				complimentaryKeyValidation
			);

			if (isComplimentaryKey) {
				return setIsComplimentaryKey(true);
			}

			return setIsComplimentaryKey(false);
		}
	}, [activationKeysChecked]);

	return (
		<div>
			<hr></hr>

			<div className="d-flex justify-content-between">
				<Link to={urlPreviousPage}>
					<Button
						className="btn btn-borderless btn-style-neutral"
						displayType="secondary"
					>
						{i18n.translate('cancel')}
					</Button>
				</Link>

				{(isAdminUserAccount || isAdminOrPartnerManager) &&
					allowSelfProvisioning && (
						<RenewButton
							activationKeysChecked={activationKeysChecked}
							bulkRenewAvailable={bulkRenewAvailable}
							displayType="primary"
							filterCheckedRenewKeys={filterCheckedRenewKeys}
							identifier="renew"
							isComplimentaryKey={isComplimentaryKey}
							isRenewTable={isRenewTable}
							keysSelected={keysSelected}
							project={project}
						>
							{i18n.sub('renew-x-key', [keysSelected])}
						</RenewButton>
					)}
			</div>
		</div>
	);
};

export default RenewTableFooter;
