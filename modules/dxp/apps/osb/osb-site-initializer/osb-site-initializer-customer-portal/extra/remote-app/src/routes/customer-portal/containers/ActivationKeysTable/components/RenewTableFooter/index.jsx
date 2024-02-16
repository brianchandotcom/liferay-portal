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
import {isBulkRenewAvailable} from '../../utils/isBulkRenewAvailable';
import useGetAccountUserAccount from '../Header/hooks/useGetAccountUserAccount';

const RenewTableFooter = ({
	activationKeysChecked,
	isAdminUserAccount,
	isRenewTable,
	keysSelectedCount,
	productName,
	project,
	renewKeysFilterChecked,
}) => {
	const {
		userAccountsState: [userAccounts],
	} = useGetAccountUserAccount(project);

	const [isComplimentaryKey, setIsComplimentaryKey] = useState('');

	const currentUser = userAccounts?.find(
		({id}) => id === Number(Liferay.ThemeDisplay.getUserId())
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
				(activationKey) => activationKey.complimentary
			);

			const hasComplimentaryKey = handleComplimentaryKey.some(
				complimentaryKeyValidation
			);

			if (hasComplimentaryKey) {
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
							identifier="renew"
							isComplimentaryKey={isComplimentaryKey}
							isRenewTable={isRenewTable}
							keysSelectedCount={keysSelectedCount}
							project={project}
							renewKeysFilterChecked={renewKeysFilterChecked}
						>
							{keysSelectedCount === 1 ? i18n.sub('renew-x-key',
							 [keysSelectedCount]) : i18n.sub('renew-x-keys', 
							 [keysSelectedCount])}
						</RenewButton>
					)}
			</div>
		</div>
	);
};

export default RenewTableFooter;
