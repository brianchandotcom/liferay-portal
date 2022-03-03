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

import {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import {Table} from '../../../../common/components';
import {getAccountUserAccountsByExternalReferenceCode} from '../../../../common/services/liferay/graphql/queries';
import TeamMembersTableHeader from './components/Header';
import {COLUMNS} from './utils/constants';
import {
	NameColumnType,
	OptionsColumnType,
	RoleColumnType,
	StatusColumnType,
	SupportSeatColumnType,
} from './utils/constants/columns-definitions';

const TeamMembersTable = ({project}) => {
	const [userAccounts, setUserAccounts] = useState([]);
	const [isLoadingUserAccounts, setIsLoadingUserAccounts] = useState(false);

	useEffect(() => {
		setIsLoadingUserAccounts(true);
		const getAccountUserAccounts = async () => {
			const {data} = await client.query({
				query: getAccountUserAccountsByExternalReferenceCode,
				variables: {
					externalReferenceCode: project.accountKey,
				},
			});

			if (data) {
				const accountUserAccounts = data.accountUserAccountsByExternalReferenceCode.items.reduce(
					(userAccountsAccumulator, userAccount) => {
						const currentAccountBrief = userAccount.accountBriefs.find(
							(accountBrief) =>
								accountBrief.externalReferenceCode ===
								project.accountKey
						);
						if (currentAccountBrief) {
							userAccountsAccumulator.push({
								...userAccount,
								roles: currentAccountBrief.roleBriefs.map(
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

	return (
		<div className="pt-2">
			<TeamMembersTableHeader project={project} />

			<Table
				className="border-0 cp-team-members-table"
				columns={COLUMNS}
				isLoading={isLoadingUserAccounts}
				rows={userAccounts.map((userAccount) => ({
					email: (
						<p className="m-0 text-truncate">
							{userAccount.emailAddress}
						</p>
					),
					name: <NameColumnType userAccount={userAccount} />,
					options: <OptionsColumnType />,
					role: <RoleColumnType roles={userAccount.roles} />,
					status: (
						<StatusColumnType
							hasLoggedBefore={userAccount.lastLoginDate}
						/>
					),
					supportSeat: (
						<SupportSeatColumnType roles={userAccount.roles} />
					),
				}))}
			/>
		</div>
	);
};

export default TeamMembersTable;
