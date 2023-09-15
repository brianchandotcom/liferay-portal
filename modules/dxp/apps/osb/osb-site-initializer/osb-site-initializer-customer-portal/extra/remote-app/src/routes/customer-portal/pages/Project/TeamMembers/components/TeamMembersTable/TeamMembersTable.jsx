/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useState} from 'react';
import SearchBuilder from '~/common/core/SearchBuilder';
import {getHighPriorityContacts} from '~/common/services/liferay/api';
import {getRolesFiltered} from '~/common/utils/getProjectRoles';
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

const MAXIMUM_REQUESTORS_DEFAULT = -1;
const UNLIMITED_RESQUESTORS = 9999;

export const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncident: i18n.translate('critical-incident'),
	privacyBreach: i18n.translate('privacy-breach'),
	securityBreach: i18n.translate('security-breach'),
};

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

	const [currentUserEditing, setCurrentUserEditing] = useState();
	const [currentUserRemoving, setCurrentUserRemoving] = useState();
	const [selectedAccountRoleItem, setSelectedAccountRoleItem] = useState();
	const [highPriorityContactsNames, setHighPriorityContactsNames] = useState(
		[]
	);

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

	const [
		availableSupportSeatsCount,
		setAvailableSupportSeatsCount,
	] = useState(1);

	useEffect(() => {
		let remainingAdmins =
			koroneikiAccount?.maxRequestors - supportSeatsCount;
		remainingAdmins = remainingAdmins < 0 ? 0 : remainingAdmins;

		setAvailableSupportSeatsCount(
			koroneikiAccount?.maxRequestors === MAXIMUM_REQUESTORS_DEFAULT
				? UNLIMITED_RESQUESTORS
				: remainingAdmins
		);
	}, [koroneikiAccount, supportSeatsCount]);

	const userAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.items;

	const totalUserAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.totalCount;

	const {paginationConfig, teamMembersByStatusPaginated} = usePagination(
		userAccounts
	);

	const mapFilterToContactsCategory = (filter) => {
		const lowerCaseFirstLetter =
			filter.charAt(0).toLowerCase() + filter.slice(1);

		return {
			contactsCategory: {
				key: lowerCaseFirstLetter.replace(/\s/g, ''),
				name: filter,
			},
			filterRequest: new SearchBuilder()
				.eq('contactsCategory', lowerCaseFirstLetter.replace(/\s/g, ''))
				.and()
				.eq(
					'r_accountEntryToHighPriorityContacts_accountEntryERC',
					koroneikiAccount.accountKey
				)
				.build(),
		};
	};

	const getHighPriorityContactsByFilter = async (filter) => {
		try {
			const {filterRequest} = mapFilterToContactsCategory(filter);
			const response = await getHighPriorityContacts(filterRequest);
			const highPriorityContactsFiltered = response?.items;
			const mappedContacts = highPriorityContactsFiltered?.map(
				(contact, index) => {
					const {r_userToHighPriorityContacts_user} = contact;

					return {
						label: `${r_userToHighPriorityContacts_user?.givenName} ${r_userToHighPriorityContacts_user?.familyName}`,
						value: (index + 1).toString(),
					};
				}
			);

			return mappedContacts;
		} catch (error) {
			console.error(
				i18n.translate('error-high-priority-contacts'),
				error
			);
		}
	};

	useEffect(() => {
		const fetchHighPriorityContacts = async () => {
			try {
				const updatedFilteredContacts = {};

				for (const filter of Object.keys(
					HIGH_PRIORITY_CONTACT_CATEGORIES
				)) {
					updatedFilteredContacts[
						filter
					] = await getHighPriorityContactsByFilter(filter);
				}

				setHighPriorityContactsNames(
					Object.values(updatedFilteredContacts).flatMap((contacts) =>
						contacts.map((contact) => contact.label)
					)
				);
			} catch (error) {
				console.error(
					i18n.translate('error-fetching-high-priority-contacts'),
					error
				);
			}
		};

		fetchHighPriorityContacts();
	}, []);

	const {
		data: accountRolesData,
		loading: accountRolesLoading,
	} = useAccountRolesByAccountExternalReferenceCode(
		koroneikiAccount,
		koroneikiAccountLoading,
		!loggedUserAccount?.selectedAccountSummary.hasAdministratorRole
	);

	const availableAccountRoles = getRolesFiltered(
		accountRolesData?.accountAccountRolesByExternalReferenceCode.items,
		koroneikiAccount
	);

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
			setCurrentUserEditing();
			setSelectedAccountRoleItem();
		}
	}, [onOpenChange, updating]);

	useEffect(() => {
		if (currentUserEditing?.id) {
			setSelectedAccountRoleItem();
		}
	}, [currentUserEditing]);

	const getCurrentRoleBriefs = useCallback(
		(accountBrief) =>
			getFilteredRoleBriefsByName(accountBrief.roleBriefs, 'User'),
		[]
	);

	const handleEdit = () => {
		const currentAccountRoles =
			currentUserEditing.selectedAccountSummary.roleBriefs;

		update(
			currentUserEditing,
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
						<p>
							<b>Team Member:</b> {currentUserRemoving.name}
						</p>

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
								(userAccount) => ({
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
											edit={
												userAccount?.id ===
												currentUserEditing?.id
											}
											highPriorityContactsNames={
												highPriorityContactsNames
											}
											onCancel={() => {
												setCurrentUserEditing();
												setSelectedAccountRoleItem();
											}}
											onEdit={() =>
												setCurrentUserEditing(
													userAccount
												)
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
											userAccount={userAccount}
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
											edit={
												userAccount?.id ===
												currentUserEditing?.id
											}
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
