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

import {useEffect, useMemo, useState} from 'react';
import client from '../../../../apolloClient';
import {Table} from '../../../../common/components';
import {Liferay} from '../../../../common/services/liferay';
import {
	associateUserAccountWithAccountAndAccountRole,
	getAccountUserAccountsByExternalReferenceCode,
} from '../../../../common/services/liferay/graphql/queries';
import {ROLE_TYPES} from '../../../../common/utils/constants';
import TeamMembersTableHeader from './components/Header';
import useAccountUser from './hooks/useAccountUser';
import {TEAM_MEMBERS_ACTION_TYPES} from './utils/constants';
import {
	NameColumnType,
	OptionsColumnType,
	RoleColumnType,
	StatusColumnType,
	SupportSeatColumnType,
} from './utils/constants/columns-definitions';
import {deleteAllPreviousUserRoles} from './utils/constants/deleteAllPreviousUserRoles';
import {getColumnsByUserAccess} from './utils/getColumnsByUserAccess';

const MAX_PAGE_SIZE = 9999;

const TeamMembersTable = ({project, sessionId}) => {
	const {
		accountRoles,
		administratorsAvailable,
		setAdministratorsAvailable,
	} = useAccountUser(project);

	const [userAccounts, setUserAccounts] = useState([]);
	const [isLoadingUserAccounts, setIsLoadingUserAccounts] = useState(false);
	const [userAction, setUserAction] = useState();
	const [selectedRole, setSelectedRole] = useState();

	useEffect(() => {
		setIsLoadingUserAccounts(true);
		const getAccountUserAccounts = async () => {
			const {data} = await client.query({
				query: getAccountUserAccountsByExternalReferenceCode,
				variables: {
					externalReferenceCode: project.accountKey,
					pageSize: MAX_PAGE_SIZE,
				},
			});

			if (data) {
				const accountUserAccounts = data.accountUserAccountsByExternalReferenceCode?.items?.reduce(
					(userAccountsAccumulator, userAccount) => {
						const currentAccountBrief = userAccount.accountBriefs?.find(
							(accountBrief) =>
								accountBrief.externalReferenceCode ===
								project?.accountKey
						);
						if (currentAccountBrief) {
							userAccountsAccumulator.push({
								...userAccount,
								roles: currentAccountBrief.roleBriefs?.map(
									({name}) => name
								),
							});
						}

						return userAccountsAccumulator;
					},
					[]
				);

				setUserAccounts(accountUserAccounts);
			}

			setIsLoadingUserAccounts(false);
		};
		getAccountUserAccounts();
	}, [project.accountKey]);

	const handleChangeUserRole = async (userAccount) => {
		if (selectedRole) {
			const currentRole = accountRoles?.find(
				(role) => role?.name === selectedRole
			);

			deleteAllPreviousUserRoles(
				project.accountKey,
				userAccount,
				accountRoles
			);

			client.mutate({
				mutation: associateUserAccountWithAccountAndAccountRole,
				variables: {
					accountKey: project.accountKey,
					accountRoleId: currentRole.id,
					emailAddress: userAccount?.emailAddress,
				},
			});

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
			setSelectedRole();
		}
		setUserAction(TEAM_MEMBERS_ACTION_TYPES.close);
	};

	const hasAdminAccess = useMemo(() => {
		const currentUser = userAccounts?.find(
			({id}) => id === +Liferay.ThemeDisplay.getUserId()
		);

		if (currentUser) {
			const hasAdminRoles = currentUser?.roles?.some(
				(role) =>
					role === ROLE_TYPES.admin.key ||
					role === ROLE_TYPES.requester.key
			);

			return hasAdminRoles;
		}
	}, [userAccounts]);

	const columnsByUserAccess = getColumnsByUserAccess(hasAdminAccess);

	return (
		<div className="pt-2">
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
							accountRoles={accountRoles}
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
		</div>
	);
};

export default TeamMembersTable;
