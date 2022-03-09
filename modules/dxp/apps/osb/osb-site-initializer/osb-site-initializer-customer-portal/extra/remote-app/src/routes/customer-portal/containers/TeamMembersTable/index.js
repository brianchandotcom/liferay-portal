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
import {getAccountUserAccountsByExternalReferenceCode} from '../../../../common/services/liferay/graphql/queries';
import {ROLE_TYPES} from '../../../../common/utils/constants';
import TeamMembersTableHeader from './components/Header';
import {
	NameColumnType,
	OptionsColumnType,
	RoleColumnType,
	StatusColumnType,
	SupportSeatColumnType,
} from './utils/constants/columns-definitions';
import {getColumnsByUserAccess} from './utils/getColumnsByUserAccess';

const MAX_PAGE_SIZE = 9999;

const TeamMembersTable = ({project, sessionId}) => {
	const [userAccounts, setUserAccounts] = useState([]);
	const [isLoadingUserAccounts, setIsLoadingUserAccounts] = useState(false);

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
				hasAdminAccess={hasAdminAccess}
				project={project}
				sessionId={sessionId}
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
					options: <OptionsColumnType />,
					role: <RoleColumnType roles={userAccount?.roles} />,
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
