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
import {useApplicationProvider} from '../../../common/context/AppPropertiesProvider';
import {Liferay} from '../../../common/services/liferay';
import {
	getAccountByExternalReferenceCode,
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {getCurrentSession} from '../../../common/services/okta/rest/sessions';
import {ROLE_TYPES, ROUTE_TYPES} from '../../../common/utils/constants';
import {getAccountKey} from '../../../common/utils/getAccountKey';
import {isValidPage} from '../../../common/utils/page.validation';
import usePaginatedKoroneikiAccounts from '../hooks/usePaginatedKoroneikiAccounts';
import {CUSTOM_EVENT_TYPES} from '../utils/constants';
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

const eventUserAccount = Liferay.publish(
	CUSTOM_EVENT_TYPES.userAccount,
	EVENT_OPTION
);

const eventProject = Liferay.publish(CUSTOM_EVENT_TYPES.project, EVENT_OPTION);

const eventKoroneikiAccounts = Liferay.publish(
	CUSTOM_EVENT_TYPES.koroneikiAccounts,
	{
		async: true,
	}
);

const AppContextProvider = ({assetsPath, children, page}) => {
	const {oktaSessionURL} = useApplicationProvider();
	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		isQuickLinksExpanded: true,
		page,
		project: undefined,
		quickLinks: undefined,
		sessionId: '',
		structuredContents: undefined,
		subscriptionGroups: undefined,
		userAccount: undefined,
	});

	const [
		{initialTotalCount, items, totalCount},
		{fetchMore, search},
	] = usePaginatedKoroneikiAccounts(state.userAccount);

	useEffect(() => {
		if (items) {
			eventKoroneikiAccounts.fire({
				detail: {
					initialTotalCount,
					koroneikiAccounts: items,
					totalCount,
				},
			});
		}
	}, [initialTotalCount, items, totalCount]);

	useEffect(() => {
		Liferay.on(CUSTOM_EVENT_TYPES.fetchMoreKoroneikiAccounts, () =>
			fetchMore()
		);

		return () =>
			Liferay.detach(CUSTOM_EVENT_TYPES.fetchMoreKoroneikiAccounts);
	}, [fetchMore]);

	useEffect(() => {
		Liferay.on(CUSTOM_EVENT_TYPES.searchKoroneikiAccounts, ({detail}) =>
			search(detail)
		);

		return () => Liferay.detach(CUSTOM_EVENT_TYPES.searchKoroneikiAccounts);
	}, [search]);

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

				const isStaff = data.userAccount?.roleBriefs?.some(
					(role) => role.name === 'Administrator'
				);

				const userAccount = {
					...data.userAccount,
					isAdmin: isAccountAdministrator,
					isStaff,
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
				const currentProject = {
					...projects.c.koroneikiAccounts.items[0],
					id: accountBrief.id,
					name: accountBrief.name,
				};

				dispatch({
					payload: currentProject,
					type: actionTypes.UPDATE_PROJECT,
				});

				eventProject.fire({
					detail: currentProject,
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
			}
		};

		const getSessionId = async () => {
			const session = await getCurrentSession(oktaSessionURL);

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

			if (user && getCurrentPageName() === ROUTE_TYPES.project) {
				const isValid = await isValidPage(
					user,
					projectExternalReferenceCode,
					ROUTE_TYPES.project
				);

				if (isValid) {
					const hasRoleBriefAdministrator = user?.roleBriefs?.some(
						(role) => role.name === 'Administrator'
					);

					let accountBrief;

					if (hasRoleBriefAdministrator) {
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
					else {
						accountBrief = user.accountBriefs?.find(
							(accountBrief) =>
								accountBrief.externalReferenceCode ===
								projectExternalReferenceCode
						);
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
	}, [oktaSessionURL]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
