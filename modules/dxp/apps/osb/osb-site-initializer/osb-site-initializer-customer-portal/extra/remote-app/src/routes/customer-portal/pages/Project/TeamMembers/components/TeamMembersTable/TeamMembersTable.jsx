/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useState} from 'react';
import i18n from '../../../../../../../common/I18n';
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
import usePagination from './hooks/usePaginationTeamMembers';
import useUserAccountsByAccountExternalReferenceCode from './hooks/useUserAccountsByAccountExternalReferenceCode';
import {getColumns} from './utils/getColumns';
import getFilteredRoleBriefsByName from './utils/getFilteredRoleBriefsByName';

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
	const [currentUserRemoving, setCurrentUserRemoving] = useState();
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

	const {paginationConfig, teamMembersByStatusPaginated} = usePagination(
		userAccounts
	);

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

			setCurrentUserRemoving();
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

	const getCurrentRoleBriefs = useCallback(
		(accountBrief) =>
			getFilteredRoleBriefsByName(accountBrief.roleBriefs, 'User'),
		[]
	);

	const handleEdit = () => {
		const currentAccountRoles =
			userAccounts[currentIndexEditing].selectedAccountSummary.roleBriefs;

		update(
			userAccounts[currentIndexEditing],
			currentAccountRoles,
			selectedAccountRoleItem
		);
	};

	return (
		<>
			{open && currentUserRemoving !== undefined && (
				<RemoveUserModal
					modalTitle={i18n.translate('remove-user')}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onRemove={() => remove(currentUserRemoving)}
					removing={updating}
				>
					<p className="my-0 text-neutral-10">
						<p><b>Team Member:</b> {currentUserRemoving.name}</p>

						{i18n.translate(
							'are-you-sure-you-want-to-remove-this-team-member-from-the-project'
						)}
					</p>
				</RemoveUserModal>
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

			<div className="cp-team-members-table-wrapper">
				{!totalUserAccounts && !(loading || searching) && (
					<div className="d-flex justify-content-center pt-4">
						{i18n.translate('no-team-members-were-found')}
					</div>
				)}

				{!!teamMembersByStatusPaginated &&
					(totalUserAccounts || loading || searching) && (
						<Table
							className="border-0"
							columns={getColumns(
								loggedUserAccount?.selectedAccountSummary
									.hasAdministratorRole,
								articleAccountSupportURL
							)}
							hasPagination
							isLoading={loading || searching}
							paginationConfig={paginationConfig}
							rows={teamMembersByStatusPaginated?.map(
								(userAccount, index) => ({
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
											onEdit={() =>
												setCurrentIndexEditing(index)
											}
											onRemove={() => {
												setCurrentUserRemoving(
													userAccount
												);
												onOpenChange(true);
											}}
											onSave={() => handleEdit()}
											saveDisabled={
												!selectedAccountRoleItem ||
												updating
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
												getCurrentRoleBriefs(
													userAccount.selectedAccountSummary
												)?.[0]?.name || 'User'
											}
											edit={index === currentIndexEditing}
											hasAccountSupportSeatRole={
												userAccount
													.selectedAccountSummary
													.hasSupportSeatRole
											}
											onClick={(
												selectedAccountRoleItem
											) =>
												setSelectedAccountRoleItem(
													selectedAccountRoleItem
												)
											}
											supportSeatsCount={
												supportSeatsCount
											}
										/>
									),
									status: (
										<StatusTag
											currentStatus={
												userAccount.lastLoginDate ||
												userAccount.dateCreated <=
													importDate
													? STATUS_TAG_TYPES.active
													: STATUS_TAG_TYPES.invited
											}
										/>
									),
									supportSeat: userAccount
										.selectedAccountSummary
										.hasSupportSeatRole &&
										!userAccount.isLiferayStaff && (
											<ClayIcon
												className="text-brand-primary-darken-2"
												symbol="check-circle-full"
											/>
										),
								})
							)}
						/>
					)}
			</div>
		</>
	);
};

export default TeamMembersTable;
