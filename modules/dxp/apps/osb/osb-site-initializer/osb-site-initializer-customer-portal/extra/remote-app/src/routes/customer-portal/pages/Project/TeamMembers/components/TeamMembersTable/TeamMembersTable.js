/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useCallback, useState} from 'react';
import StatusTag from '../../../../../../../common/components/StatusTag';
import Table from '../../../../../../../common/components/Table';
import {useAppPropertiesContext} from '../../../../../../../common/contexts/AppPropertiesContext';
import {STATUS_TAG_TYPES} from '../../../../../utils/constants/statusTag';
import RemoveUserModal from './components/RemoveUserModal/RemoveUserModal';
import NameColumn from './components/columns/NameColumn';
import OptionsColumn from './components/columns/OptionsColumn';
import RolesColumn from './components/columns/RolesColumn/RolesColumn';
import useAccountRolesByAccountExternalReferenceCode from './hooks/useAccountRolesByAccountExternalReferenceCode';
import useMyUserAccountByAccountExternalReferenceCode from './hooks/useMyUserAccountByAccountExternalReferenceCode';
import useUserAccountsByAccountExternalReferenceCode from './hooks/useUserAccountsByAccountExternalReferenceCode';
import {getColumns} from './utils/getColumns';

const TeamMembersTable = ({
	koroneikiAccount,
	loading: koroneikiAccountLoading,
}) => {
	const {articleAccountSupportURL, gravatarAPI} = useAppPropertiesContext();
	const {observer, onOpenChange, open} = useModal();

	const [currentIndexEditing, setCurrentIndexEditing] = useState();
	const [currentIndexRemoving, setCurrentIndexRemoving] = useState();

	const [
		hasAccountAdministrator,
		{loading: myUserAccountLoading},
	] = useMyUserAccountByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		koroneikiAccountLoading
	);

	const [
		supportSeatsCount,
		{data: userAccountsData, loading: userAccountsLoading},
	] = useUserAccountsByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		koroneikiAccountLoading
	);

	const {
		data: accountRolesData,
		loading: accountRolesLoading,
	} = useAccountRolesByAccountExternalReferenceCode(
		koroneikiAccount,
		koroneikiAccountLoading
	);

	const userAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.items;

	const availableAccountRoles =
		accountRolesData?.accountAccountRolesByExternalReferenceCode.items;

	const availableSupportSeatsCount =
		koroneikiAccount?.maxRequestors - supportSeatsCount;

	const loading =
		myUserAccountLoading || userAccountsLoading || accountRolesLoading;

	const getCurrentRoleBriefName = useCallback(
		(accountBrief) =>
			accountBrief.roleBriefs.filter(
				(roleBrief) => roleBrief.name !== 'User'
			)[0]?.name || 'User',
		[]
	);

	return (
		<>
			{open && currentIndexRemoving !== undefined && (
				<RemoveUserModal
					observer={observer}
					onClose={() => onOpenChange(false)}
					onRemove={() => {}}
				/>
			)}

			<div className="cp-team-members-table-wrapper overflow-auto">
				<Table
					className="border-0 cp-team-members-table"
					columns={getColumns(
						hasAccountAdministrator,
						articleAccountSupportURL
					)}
					isLoading={loading}
					rows={userAccounts?.map((userAccount, index) => ({
						email: (
							<p className="m-0 text-truncate">
								{userAccount.emailAddress}
							</p>
						),
						name: (
							<NameColumn
								gravatarAPI={gravatarAPI}
								userAccount={userAccount}
							/>
						),
						options: (
							<OptionsColumn
								edit={index === currentIndexEditing}
								onCancel={() => setCurrentIndexEditing()}
								onEdit={() => setCurrentIndexEditing(index)}
								onRemove={() => {
									setCurrentIndexRemoving(index);
									onOpenChange(true);
								}}
								onSave={() => {}}
							/>
						),
						role: (
							<RolesColumn
								accountRoles={availableAccountRoles}
								availableSupportSeatsCount={
									availableSupportSeatsCount
								}
								currentRoleBriefName={getCurrentRoleBriefName(
									userAccount.selectedAccountSummary
								)}
								edit={index === currentIndexEditing}
								hasAccountSupportSeatRole={
									userAccount.selectedAccountSummary
										.hasSupportSeatRole
								}
								onClick={(selectedAccountRoleName) =>
									selectedAccountRoleName
								}
								supportSeatsCount={supportSeatsCount}
							/>
						),
						status: (
							<StatusTag
								currentStatus={
									userAccount?.lastLoginDate
										? STATUS_TAG_TYPES.active
										: STATUS_TAG_TYPES.invited
								}
							/>
						),
						supportSeat: userAccount.selectedAccountSummary
							.hasSupportSeatRole && (
							<ClayIcon
								className="cp-team-members-support-seat-icon"
								symbol="check-circle-full"
							/>
						),
					}))}
				/>
			</div>
		</>
	);
};

export default TeamMembersTable;
