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
import client from '../../../apolloClient';
import {useApplicationProvider} from '../../../common/context/ApplicationPropertiesProvider';
import {Liferay} from '../../../common/services/liferay';
import {fetchSession} from '../../../common/services/liferay/api';
import {
	getAccountRoles,
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {
	PARAMS_KEYS,
	SearchParams,
} from '../../../common/services/liferay/search-params';
import {ROLES_PERMISSIONS} from '../../../common/utils/constants';
import {isValidPage} from '../../../common/utils/page.validation';
import {CUSTOM_EVENTS} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const getCurrentPageName = () => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	return pathSplit.length > 2 ? pathSplit[2] : '';
};

const EVENT_OPTION = {
	async: true,
	fireOnce: true,
};

const AppContextProvider = ({assetsPath, children, page}) => {
	const {oktaSessionURL} = useApplicationProvider();
	const eventUserAccount = Liferay.publish(
		CUSTOM_EVENTS.USER_ACCOUNT,
		EVENT_OPTION
	);
	const eventSubscriptionGroups = Liferay.publish(
		CUSTOM_EVENTS.SUBSCRIPTION_GROUPS,
		EVENT_OPTION
	);

	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		page,
		project: undefined,
		quickLinks: undefined,
		sessionId: '',
		structuredContents: undefined,
		subscriptionGroups: undefined,
		userAccount: undefined,
	});

	useEffect(() => {
		const handler = ({detail}) =>
			dispatch({
				payload: detail,
				type: actionTypes.UPDATE_PAGE,
			});

		Liferay.on(CUSTOM_EVENTS.MENU_PAGE, handler);

		return () => Liferay.detach(CUSTOM_EVENTS.MENU_PAGE, handler);
	}, []);

	useEffect(() => {
		const getUser = async () => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				},
			});

			if (data) {
				const {data: accountRolesData} = await client.query({
					query: getAccountRoles,
					variables: {
						accountId: data.userAccount.id,
					},
				});

				const isAccountAdministrator = !!accountRolesData.accountAccountRoles?.items?.find(
					({name}) => name === ROLES_PERMISSIONS.ACCOUNT_ADMINISTRATOR
				);

				const userAccount = {
					...data.userAccount,
					isAdmin: isAccountAdministrator,
				};

				dispatch({
					payload: userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT,
				});

				eventUserAccount.fire({
					detail: data.userAccount,
				});

				return userAccount;
			}
		};

		const getProject = async (externalReferenceCode, accountBrief) => {
			const {data: projects} = await client.query({
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${externalReferenceCode}'`,
				},
			});

			if (projects) {
				dispatch({
					payload: {
						...projects.c.koroneikiAccounts.items[0],
						id: accountBrief.id,
						name: accountBrief.name,
					},
					type: actionTypes.UPDATE_PROJECT,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey) => {
			const {data: dataSubscriptionGroups} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
				},
			});

			if (dataSubscriptionGroups) {
				const items =
					dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
				});

				eventSubscriptionGroups.fire({
					detail: items,
				});
			}
		};

		const getSessionId = async () => {
			const session = await fetchSession(oktaSessionURL);

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
			const user = await getUser();
			const projectExternalReferenceCode = SearchParams.get(
				PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
			);

			if (user && getCurrentPageName() === 'overview') {
				const isValid = await isValidPage(
					user,
					projectExternalReferenceCode,
					'overview'
				);

				if (isValid) {
					const accountBrief = user.accountBriefs?.find(
						(accountBrief) =>
							accountBrief.externalReferenceCode ===
							projectExternalReferenceCode
					);

					if (accountBrief) {
						getProject(projectExternalReferenceCode, accountBrief);
						getSubscriptionGroups(projectExternalReferenceCode);
						getStructuredContents();
						getSessionId();
					}
				}
			}
		};

		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [oktaSessionURL]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
