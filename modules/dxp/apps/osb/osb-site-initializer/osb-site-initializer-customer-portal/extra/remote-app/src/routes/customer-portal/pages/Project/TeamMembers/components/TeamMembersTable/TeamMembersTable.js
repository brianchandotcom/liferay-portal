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
import {useCallback, useEffect, useState} from 'react';
import StatusTag from '../../../../../../../common/components/StatusTag';
import Table from '../../../../../../../common/components/Table';
import {useAppPropertiesContext} from '../../../../../../../common/contexts/AppPropertiesContext';
import {useCustomerPortal} from '../../../../../context';
import {STATUS_TAG_TYPES} from '../../../../../utils/constants/statusTag';
import RemoveUserModal from './components/RemoveUserModal/RemoveUserModal';
import TeamMembersTableHeader from './components/TeamMembersTableHeader/TeamMembersTableHeader';
import NameColumn from './components/columns/NameColumn';
import OptionsColumn from './components/columns/OptionsColumn';
import RolesColumn from './components/columns/RolesColumn/RolesColumn';
import useAccountRolesByAccountExternalReferenceCode from './hooks/useAccountRolesByAccountExternalReferenceCode';
import useMyUserAccountByAccountExternalReferenceCode from './hooks/useMyUserAccountByAccountExternalReferenceCode';
import useUserAccountsByAccountExternalReferenceCode from './hooks/useUserAccountsByAccountExternalReferenceCode';
import {getColumns} from './utils/getColumns';
import getFilteredRoleBriefByName from './utils/getFilteredRoleBriefByName';

const TeamMembersTable = ({
	koroneikiAccount,
	loading: koroneikiAccountLoading,
}) => {
	const {
		articleAccountSupportURL,
		gravatarAPI,
		importDate,
	} = useAppPropertiesContext();

	const [{sessionId}] = useCustomerPortal();

	const {observer, onOpenChange, open} = useModal();

	const [currentIndexEditing, setCurrentIndexEditing] = useState();
	const [currentIndexRemoving, setCurrentIndexRemoving] = useState();
	const [selectedAccountRoleItem, setSelectedAccountRoleItem] = useState();

	const {
		data: myUserAccountData,
		loading: myUserAccountLoading,
	} = useMyUserAccountByAccountExternalReferenceCode(
		koroneikiAccountLoading,
		koroneikiAccount?.accountKey
	);

	const loggedUserAccount = myUserAccountData?.myUserAccount;

	const [
		supportSeatsCount,
		{
			data: userAccountsData,
			loading: userAccountsLoading,
			remove,
			search,
			searching,
			update,
			updating,
		},
	] = useUserAccountsByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		koroneikiAccountLoading
	);

	let availableSupportSeatsCount =
		koroneikiAccount?.maxRequestors - supportSeatsCount;
	availableSupportSeatsCount =
		availableSupportSeatsCount < 0 ? 0 : availableSupportSeatsCount;

	const userAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.items;

	const totalUserAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.totalCount;

	const {
		data: accountRolesData,
		loading: accountRolesLoading,
	} = useAccountRolesByAccountExternalReferenceCode(
		koroneikiAccount,
		koroneikiAccountLoading,
		!loggedUserAccount?.selectedAccountSummary.hasAdministratorRole
	);

	const availableAccountRoles =
		accountRolesData?.accountAccountRolesByExternalReferenceCode.items;

	const loading =
		myUserAccountLoading || userAccountsLoading || accountRolesLoading;

	useEffect(() => {
		if (!updating) {
			onOpenChange(false);

			setCurrentIndexRemoving();
		}
	}, [onOpenChange, updating]);

	useEffect(() => {
		if (!updating) {
			setCurrentIndexEditing();
			setSelectedAccountRoleItem();
		}
	}, [onOpenChange, updating]);

	useEffect(() => {
		if (currentIndexEditing) {
			setSelectedAccountRoleItem();
		}
	}, [currentIndexEditing]);

	const getCurrentRoleBrief = useCallback(
		(accountBrief) =>
			getFilteredRoleBriefByName(accountBrief.roleBriefs, 'User'),
		[]
	);

	const handleEdit = () => {
		const currentAccountRole = getCurrentRoleBrief(
			userAccounts[currentIndexEditing].selectedAccountSummary
		);

		update(
			userAccounts[currentIndexEditing],
			currentAccountRole,
			selectedAccountRoleItem
		);
	};

	return (
		<>
			{open && currentIndexRemoving !== undefined && (
				<RemoveUserModal
					observer={observer}
					onClose={() => onOpenChange(false)}
					onRemove={() => remove(userAccounts[currentIndexRemoving])}
					removing={updating}
				/>
			)}

			<TeamMembersTableHeader
				articleAccountSupportURL={articleAccountSupportURL}
				availableSupportSeatsCount={availableSupportSeatsCount}
				count={totalUserAccounts}
				hasAdministratorRole={
					loggedUserAccount?.selectedAccountSummary
						.hasAdministratorRole
				}
				koroneikiAccount={koroneikiAccount}
				loading={loading}
				onSearch={(term) => search(term)}
				searching={searching}
				sessionId={sessionId}
			/>

			<div className="cp-team-members-table-wrapper overflow-auto">
				{!totalUserAccounts && !(loading || searching) && (
					<div className="d-flex justify-content-center pt-4">
						No team members were found.
					</div>
				)}

				{(totalUserAccounts || loading || searching) && (
					<Table
						className="border-0"
						columns={getColumns(
							loggedUserAccount?.selectedAccountSummary
								.hasAdministratorRole,
							articleAccountSupportURL
						)}
						isLoading={loading || searching}
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
									onCancel={() => {
										setCurrentIndexEditing();
										setSelectedAccountRoleItem();
									}}
									onEdit={() => setCurrentIndexEditing(index)}
									onRemove={() => {
										setCurrentIndexRemoving(index);
										onOpenChange(true);
									}}
									onSave={() => handleEdit()}
									saveDisabled={
										!selectedAccountRoleItem || updating
									}
								/>
							),
							role: (
								<RolesColumn
									accountRoles={availableAccountRoles}
									availableSupportSeatsCount={
										availableSupportSeatsCount
									}
									currentRoleBriefName={
										getCurrentRoleBrief(
											userAccount.selectedAccountSummary
										)?.name || 'User'
									}
									edit={index === currentIndexEditing}
									hasAccountSupportSeatRole={
										userAccount.selectedAccountSummary
											.hasSupportSeatRole
									}
									onClick={(selectedAccountRoleItem) =>
										setSelectedAccountRoleItem(
											selectedAccountRoleItem
										)
									}
									supportSeatsCount={supportSeatsCount}
								/>
							),
							status: (
								<StatusTag
									currentStatus={
										userAccount.lastLoginDate ||
										userAccount.dateCreated <= importDate
											? STATUS_TAG_TYPES.active
											: STATUS_TAG_TYPES.invited
									}
								/>
							),
							supportSeat: userAccount.selectedAccountSummary
								.hasSupportSeatRole && (
								<ClayIcon
									className="text-brand-primary-darken-2"
									symbol="check-circle-full"
								/>
							),
						}))}
					/>
				)}
			</div>
		</>
	);
};

export default TeamMembersTable;
