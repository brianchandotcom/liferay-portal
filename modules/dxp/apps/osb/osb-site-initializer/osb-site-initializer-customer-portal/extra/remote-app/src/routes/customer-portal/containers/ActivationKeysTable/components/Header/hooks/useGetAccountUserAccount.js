/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useAppPropertiesContext} from '../../../../../../../common/contexts/AppPropertiesContext';
import {getAccountUserAccountsByExternalReferenceCode} from '../../../../../../../common/services/liferay/graphql/queries';

const MAX_PAGE_SIZE = 9999;

export default function useGetAccountUserAccount(project) {
	const [userAccounts, setUserAccounts] = useState([]);
	const [isLoadingUserAccounts, setIsLoadingUserAccounts] = useState(false);
	const [filterTerm, setFilterTerm] = useState('');
	const {client} = useAppPropertiesContext();

	useEffect(() => {
		setIsLoadingUserAccounts(true);
		const getAccountUserAccounts = async () => {
			const {data} = await client.query({
				query: getAccountUserAccountsByExternalReferenceCode,
				variables: {
					externalReferenceCode: project.accountKey,
					filter: filterTerm,
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
	}, [client, filterTerm, project.accountKey]);

	return {
		isLoadingUserAccounts,
		setFilterTerm,
		userAccountsState: [userAccounts, setUserAccounts],
	};
}
