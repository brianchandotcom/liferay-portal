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

import ClayAlert from '@clayui/alert';
import {useEffect, useMemo, useState} from 'react';
import client from '../../../../apolloClient';
import {Table} from '../../../../common/components';
import {Liferay} from '../../../../common/services/liferay';
import {
	associateUserAccountWithAccountAndAccountRole,
	deleteAccountUserAccount,
} from '../../../../common/services/liferay/graphql/queries';
import {
	associateContactRoleNameByEmailByProject,
	deleteContactRoleNameByEmailByProject,
} from '../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {ROLE_TYPES} from '../../../../common/utils/constants';
import TeamMembersTableHeader from './components/Header';
import RemoveUserModal from './components/RemoveUserModal';
import useAccountRoles from './hooks/useAccountRoles';
import useFilters from './hooks/useFilters';
import useGetAccountUserAccount from './hooks/useGetAccountUserAccounts';
import {
	STATUS_ACTION_TYPES,
	STATUS_NAME_TYPES,
	TEAM_MEMBERS_ACTION_TYPES,
} from './utils/constants';
import {
	NameColumnType,
	OptionsColumnType,
	RoleColumnType,
	StatusColumnType,
	SupportSeatColumnType,
} from './utils/constants/columns-definitions';
import {deleteAllPreviousUserRoles} from './utils/deleteAllPreviousUserRoles';
import {getColumnsByUserAccess} from './utils/getColumnsByUserAccess';

const ROLE_FILTER_NAME = 'contactRoleNames';
const ALERT_TIMEOUT = 3000;

const TeamMembersTable = ({project, provisioningServerAPI, sessionId}) => {
	const {accountRoles} = useAccountRoles(project);

	const {
		isLoadingUserAccounts,
		setFilterTerm,
		userAccountsState: [userAccounts, setUserAccounts],
	} = useGetAccountUserAccount(project);

	const [administratorsAvailable, setAdministratorsAvailable] = useState();
	const [filters, setFilters] = useFilters(setFilterTerm);

	const [userAction, setUserAction] = useState();
	const [selectedRole, setSelectedRole] = useState();
	const [userActionStatus, setUserActionStatus] = useState();
	const [accountRolesOptions, setAccountRolesOptions] = useState([]);

	const hasOnlyOneAdminOrPartnerManager = useMemo(() => {
		return (
			userAccounts.filter(
				(user) =>
					user?.roles[0] === ROLE_TYPES.admin.key ||
					user?.roles[0] === ROLE_TYPES.partnerManager.key ||
					user?.roles[0] === ROLE_TYPES.requester.key
			).length === 1
		);
	}, [userAccounts]);

	useEffect(() => {
		if (accountRoles.length) {
			const currentSelectedUser = userAccounts?.find(
				({id}) => id === userAction?.userId
			);

			if (currentSelectedUser) {
				const isSupportSeatRole = currentSelectedUser?.roles?.some(
					(role) =>
						role === ROLE_TYPES.admin.key ||
						role === ROLE_TYPES.requester.key ||
						role === ROLE_TYPES.partnerManager.key
				);
				const filteredRoles = accountRoles.map((role) => {
					const isAdministratorOrRequestor =
						role.key === ROLE_TYPES.admin.key ||
						role.key === ROLE_TYPES.requester.key;

					return {
						...role,
						disabled:
							(!isSupportSeatRole &&
								isAdministratorOrRequestor &&
								administratorsAvailable === 0) ||
							(hasOnlyOneAdminOrPartnerManager &&
								isSupportSeatRole),
					};
				});
				setAccountRolesOptions(filteredRoles);
			}
		}
	}, [
		accountRoles,
		administratorsAvailable,
		hasOnlyOneAdminOrPartnerManager,
		userAccounts,
		userAction?.userId,
	]);

	const handleChangeUserRole = async (userAccount) => {
		if (selectedRole) {
			const currentRole = accountRolesOptions?.find(
				(role) => role?.name === selectedRole
			);

			deleteAllPreviousUserRoles(
				project.accountKey,
				userAccount,
				accountRolesOptions
			);

			client.mutate({
				mutation: associateUserAccountWithAccountAndAccountRole,
				variables: {
					accountKey: project.accountKey,
					accountRoleId: currentRole.id,
					emailAddress: userAccount?.emailAddress,
				},
			});

			associateContactRoleNameByEmailByProject(
				project.accountKey,
				provisioningServerAPI,
				sessionId,
				encodeURI(userAccount?.emailAddress),
				currentRole?.raysourceName
			);

			setUserAccounts((previousUserAccounts) => {
				const newUserAcconts = [...previousUserAccounts];
				const accountIndexToUpdate = newUserAcconts.findIndex(
					(userType) => userType?.id === userAccount?.id
				);

				if (accountIndexToUpdate !== -1) {
					newUserAcconts[accountIndexToUpdate] = {
						...newUserAcconts[accountIndexToUpdate],
						roles: [currentRole?.key],
					};
				}

				return newUserAcconts;
			});
			setUserActionStatus(STATUS_NAME_TYPES.onEditSuccess);
			setSelectedRole();
		}
		setUserAction(TEAM_MEMBERS_ACTION_TYPES.close);
	};

	const handleRemoveUser = async () => {
		const userToBeRemoved = userAccounts.find(
			(userAccount) => userAccount.id === userAction?.userId
		);

		if (userToBeRemoved) {
			await client.mutate({
				mutation: deleteAccountUserAccount,
				variables: {
					accountKey: project.accountKey,
					emailAddress: userToBeRemoved?.emailAddress,
				},
			});

			const rolesToBeRemoved = userToBeRemoved.roles.reduce(
				(rolesAccumulator, role, index) => {
					const raysourceRole = accountRolesOptions.find(
						(roleType) => roleType.name === role
					);

					return `${rolesAccumulator}${
						index > 0
							? `&${ROLE_FILTER_NAME}=${raysourceRole?.raysourceName}`
							: `${ROLE_FILTER_NAME}=${raysourceRole?.raysourceName}`
					}`;
				},
				''
			);

			deleteContactRoleNameByEmailByProject(
				project.accountKey,
				provisioningServerAPI,
				sessionId,
				encodeURI(userToBeRemoved?.emailAddress),
				rolesToBeRemoved
			);

			setUserAccounts((previousUserAccounts) =>
				previousUserAccounts.filter(
					(userAccount) => userAccount.id !== userAction.userId
				)
			);

			setUserActionStatus(STATUS_NAME_TYPES.onRemoveSuccess);
		}
	};

	const hasAdminAccess = useMemo(() => {
		const currentUser = userAccounts?.find(
			({id}) => id === +Liferay.ThemeDisplay.getUserId()
		);

		if (currentUser) {
			const hasAdminRoles = currentUser?.roles?.some(
				(role) =>
					role === ROLE_TYPES.admin.key ||
					role === ROLE_TYPES.partnerManager.key
			);

			return hasAdminRoles;
		}
	}, [userAccounts]);

	const columnsByUserAccess = getColumnsByUserAccess(hasAdminAccess);

	return (
		<div className="pt-2">
			<RemoveUserModal
				onRemoveTeamMember={handleRemoveUser}
				setUserAction={setUserAction}
				userAction={userAction}
			/>

			<TeamMembersTableHeader
				administratorsAvailable={administratorsAvailable}
				filterState={[filters, setFilters]}
				hasAdminAccess={hasAdminAccess}
				loading={isLoadingUserAccounts}
				project={project}
				sessionId={sessionId}
				setAdministratorsAvailable={setAdministratorsAvailable}
				setUserAccounts={setUserAccounts}
				userAccounts={userAccounts}
			/>

			{!!userAccounts.length && (
				<Table
					className="border-0 cp-team-members-table"
					columns={columnsByUserAccess}
					isLoading={isLoadingUserAccounts}
					rows={userAccounts?.map((userAccount) => ({
						email: (
							<p className="m-0 text-truncate">
								{userAccount?.emailAddress}
							</p>
						),
						name: <NameColumnType userAccount={userAccount} />,
						options: (
							<OptionsColumnType
								confirmChanges={handleChangeUserRole}
								setSelectedRole={setSelectedRole}
								setUserAction={setUserAction}
								userAccount={userAccount}
								userAction={userAction}
							/>
						),
						role: (
							<RoleColumnType
								accountRoles={accountRolesOptions}
								selectedRole={selectedRole}
								setSelectedRole={setSelectedRole}
								userAccount={userAccount}
								userAction={userAction}
							/>
						),
						status: (
							<StatusColumnType
								hasLoggedBefore={userAccount?.lastLoginDate}
							/>
						),
						supportSeat: (
							<SupportSeatColumnType roles={userAccount?.roles} />
						),
					}))}
				/>
			)}

			{!userAccounts.length &&
				(filters.searchTerm || filters.hasValue) && (
					<div className="d-flex justify-content-center py-4">
						No team members found with this search criteria.
					</div>
				)}

			{userActionStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={ALERT_TIMEOUT}
						className="cp-activation-key-download-alert px-4 py-3 text-paragraph"
						displayType={
							STATUS_ACTION_TYPES[userActionStatus]?.type
						}
						onClose={() => setUserActionStatus('')}
					>
						{STATUS_ACTION_TYPES[userActionStatus]?.message}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</div>
	);
};

export default TeamMembersTable;
