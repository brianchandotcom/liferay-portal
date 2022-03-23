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
import useAccountUser from './hooks/useAccountUser';
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

const TeamMembersTable = ({licenseKeyDownloadURL, project, sessionId}) => {
	const {
		accountRoles,
		administratorsAvailable,
		isLoadingUserAccounts,
		setAdministratorsAvailable,
		setUserAccounts,
		userAccounts,
	} = useAccountUser(project);

	const [userAction, setUserAction] = useState();
	const [selectedRole, setSelectedRole] = useState();
	const [userActionStatus, setUserActionStatus] = useState();
	const [accountRolesOptions, setAccountRolesOptions] = useState([]);

	useEffect(() => {
		if (accountRoles.length) {
			const currentSelectedUser = userAccounts?.find(
				({id}) => id === userAction?.userId
			);

			if (currentSelectedUser) {
				const isSupportSeatRole = currentSelectedUser?.roles?.some(
					(role) =>
						role === ROLE_TYPES.admin.key ||
						role === ROLE_TYPES.requester.key
				);
				const filteredRoles = accountRoles.map((role) => {
					const isAdministratorOrRequestor =
						role.key === ROLE_TYPES.admin.key ||
						role.key === ROLE_TYPES.requester.key;

					return {
						...role,
						disabled:
							!isSupportSeatRole &&
							isAdministratorOrRequestor &&
							administratorsAvailable === 0,
					};
				});
				setAccountRolesOptions(filteredRoles);
			}
		}
	}, [
		accountRoles,
		administratorsAvailable,
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
				licenseKeyDownloadURL,
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
				licenseKeyDownloadURL,
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
				hasAdminAccess={hasAdminAccess}
				project={project}
				sessionId={sessionId}
				setAdministratorsAvailable={setAdministratorsAvailable}
				setUserAccounts={setUserAccounts}
				userAccounts={userAccounts}
			/>

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
