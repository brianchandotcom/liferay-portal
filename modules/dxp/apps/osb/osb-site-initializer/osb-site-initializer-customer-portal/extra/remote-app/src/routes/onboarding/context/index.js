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
	addAccountFlag,
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {searchParams} from '../../../common/services/liferay/searchParams';
import {getCurrentSession} from '../../../common/services/okta/rest/sessions';
import {
	ROLE_TYPES,
	ROUTE_TYPES,
	SEARCH_PARAMS_KEYS,
} from '../../../common/utils/constants';
import {isValidPage} from '../../../common/utils/page.validation';
import {PRODUCT_TYPES} from '../../customer-portal/utils/constants';
import {ONBOARDING_STEP_TYPES} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const AppContextProvider = ({assetsPath, children}) => {
	const {oktaSessionURL} = useApplicationProvider();
	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		koroneikiAccount: {},
		project: undefined,
		sessionId: '',
		step: ONBOARDING_STEP_TYPES.welcome,
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

				const userAccount = {
					...data.userAccount,
					isAdmin: isAccountAdministrator,
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

		const getSessionId = async () => {
			const session = await getCurrentSession(oktaSessionURL);

			if (session) {
				dispatch({
					payload: session.id,
					type: actionTypes.UPDATE_SESSION_ID,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey) => {
			const {data} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `(accountKey eq '${accountKey}') and (name eq '${PRODUCT_TYPES.dxpCloud}')`,
				},
			});

			if (data) {
				const items = data.c?.accountSubscriptionGroups?.items;
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = searchParams.get(
				SEARCH_PARAMS_KEYS.accountKey
			);

			const user = await getUser(projectExternalReferenceCode);

			if (!user) {
				return;
			}

			const isValid = await isValidPage(
				user,
				projectExternalReferenceCode,
				ROUTE_TYPES.onboarding
			);

			if (user && isValid) {
				const accountBrief = user.accountBriefs?.find(
					(accountBrief) =>
						accountBrief.externalReferenceCode ===
						projectExternalReferenceCode
				);

				if (accountBrief) {
					getProject(projectExternalReferenceCode, accountBrief);
					getSubscriptionGroups(projectExternalReferenceCode);
					getSessionId();

					client.mutate({
						mutation: addAccountFlag,
						variables: {
							accountFlag: {
								accountKey: projectExternalReferenceCode,
								finished: true,
								name: ROUTE_TYPES.onboarding,
							},
						},
					});
				}
			}
		};

		fetchData();
	}, [oktaSessionURL]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useOnboarding = () => useContext(AppContext);

export {AppContext, AppContextProvider, useOnboarding};
