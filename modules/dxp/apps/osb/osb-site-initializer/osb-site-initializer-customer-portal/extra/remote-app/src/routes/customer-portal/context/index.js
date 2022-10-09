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

import {createContext, useContext, useEffect, useReducer} from 'react';
import {useAppPropertiesContext} from '../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../common/services/liferay';
import {
	getAccountByExternalReferenceCode,
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {getCurrentSession} from '../../../common/services/okta/rest/getCurrentSession';
import {ROLE_TYPES, ROUTE_TYPES} from '../../../common/utils/constants';
import {getAccountKey} from '../../../common/utils/getAccountKey';
import {isValidPage} from '../../../common/utils/page.validation';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const AppContextProvider = ({children}) => {
	const {client, oktaSessionAPI} = useAppPropertiesContext();
	const [state, dispatch] = useReducer(reducer, {
		isQuickLinksExpanded: true,
		project: undefined,
		quickLinks: undefined,
		sessionId: '',
		structuredContents: undefined,
		subscriptionGroups: undefined,
		userAccount: undefined,
	});

	useEffect(() => {
		const getUser = async (projectExternalReferenceCode) => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				},
			});

			if (data) {
				const isAccountAdministrator = !!data.userAccount?.accountBriefs
					?.find(
						({externalReferenceCode}) =>
							externalReferenceCode ===
							projectExternalReferenceCode
					)
					?.roleBriefs?.find(
						({name}) => name === ROLE_TYPES.admin.key
					);

				const isAccountProvisioning = !!data.userAccount?.accountBriefs
					?.find(
						({externalReferenceCode}) =>
							externalReferenceCode ===
							projectExternalReferenceCode
					)
					?.roleBriefs?.find(({name}) => name === 'Provisioning');

				const isStaff = data.userAccount?.organizationBriefs?.some(
					(organization) => organization.name === 'Liferay Staff'
				);

				const userAccount = {
					...data.userAccount,
					isAdmin: isAccountAdministrator,
					isProvisioning: isAccountProvisioning,
					isStaff,
				};

				dispatch({
					payload: userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT,
				});

				return userAccount;
			}
		};

		const getProject = async (externalReferenceCode, accountBrief) => {
			const {data: projects} = await client.query({
				fetchPolicy: 'network-only',
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${externalReferenceCode}'`,
				},
			});

			if (projects) {
				const currentProject = {
					...projects.c.koroneikiAccounts.items[0],
					id: accountBrief.id,
					name: accountBrief.name,
				};

				dispatch({
					payload: currentProject,
					type: actionTypes.UPDATE_PROJECT,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey) => {
			const {data: dataSubscriptionGroups} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (dataSubscriptionGroups) {
				const items =
					dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
				});
			}
		};

		const getSessionId = async () => {
			const session = await getCurrentSession(oktaSessionAPI);

			if (session) {
				dispatch({
					payload: session.id,
					type: actionTypes.UPDATE_SESSION_ID,
				});
			}
		};

		const getStructuredContents = async () => {
			const {data} = await client.query({
				query: getStructuredContentFolders,
				variables: {
					filter: `name eq 'actions'`,
					siteKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				dispatch({
					payload:
						data.structuredContentFolders?.items[0]
							?.structuredContents?.items,
					type: actionTypes.UPDATE_STRUCTURED_CONTENTS,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = getAccountKey();

			const user = await getUser(projectExternalReferenceCode);

			if (user) {
				const isValid = await isValidPage(
					client,
					user,
					projectExternalReferenceCode,
					ROUTE_TYPES.project
				);

				if (isValid) {
					let accountBrief = user.accountBriefs?.find(
						(accountBrief) =>
							accountBrief.externalReferenceCode ===
							projectExternalReferenceCode
					);

					if (!accountBrief) {
						const {data: dataAccount} = await client.query({
							query: getAccountByExternalReferenceCode,
							variables: {
								externalReferenceCode: projectExternalReferenceCode,
							},
						});

						if (dataAccount) {
							accountBrief =
								dataAccount?.accountByExternalReferenceCode;
						}
					}

					getProject(projectExternalReferenceCode, accountBrief);
					getSubscriptionGroups(projectExternalReferenceCode);
					getStructuredContents();
					getSessionId();
				}
			}
		};

		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [oktaSessionAPI]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
