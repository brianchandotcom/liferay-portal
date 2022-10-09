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
	addAccountFlag,
	getAccountSubscriptionGroups,
	getAccountUserAccountsByExternalReferenceCode,
	getAnalyticsCloudWorkspace,
	getDXPCloudEnvironment,
	getKoroneikiAccounts,
	getLiferayExperienceCloudEnvironments,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {getCurrentSession} from '../../../common/services/okta/rest/getCurrentSession';
import {ROLE_TYPES, ROUTE_TYPES} from '../../../common/utils/constants';
import {getAccountKey} from '../../../common/utils/getAccountKey';
import {isValidPage} from '../../../common/utils/page.validation';
import {ONBOARDING_STEP_TYPES} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const MAX_PAGE_SIZE = 9999;

const AppContextProvider = ({children}) => {
	const {client, oktaSessionAPI} = useAppPropertiesContext();
	const [state, dispatch] = useReducer(reducer, {
		analyticsCloudActivationSubmittedStatus: undefined,
		dxpCloudActivationSubmittedStatus: undefined,
		koroneikiAccount: {},
		liferayExperienceCloudActivationSubmittedStatus: undefined,
		project: undefined,
		sessionId: '',
		step: ONBOARDING_STEP_TYPES.welcome,
		subscriptionGroups: undefined,
		totalAdministratorAccounts: 0,
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

		const getTotalAdministratorAccounts = async (
			projectExternalReferenceCode
		) => {
			const {data} = await client.query({
				query: getAccountUserAccountsByExternalReferenceCode,
				variables: {
					externalReferenceCode: projectExternalReferenceCode,
					pageSize: MAX_PAGE_SIZE,
				},
			});

			if (data) {
				const totalAdministratorAccounts = data.accountUserAccountsByExternalReferenceCode?.items?.reduce(
					(totalAdministrators, userAccount) => {
						const currentAccountBrief = userAccount.accountBriefs?.find(
							(accountBrief) =>
								accountBrief.externalReferenceCode ===
								projectExternalReferenceCode
						);
						if (currentAccountBrief) {
							const isAdmin = currentAccountBrief?.roleBriefs?.some(
								(role) => role.name === ROLE_TYPES.admin.key
							);
							const isRequester = currentAccountBrief?.roleBriefs?.some(
								(role) => role.name === ROLE_TYPES.requester.key
							);

							if (isAdmin || isRequester) {
								return ++totalAdministrators;
							}
						}

						return totalAdministrators;
					},
					0
				);

				dispatch({
					payload: totalAdministratorAccounts,
					type: actionTypes.UPDATE_CURRENT_TOTAL_ADMINISTRATORS,
				});
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
			const session = await getCurrentSession(oktaSessionAPI);

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
					filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
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

		const getDXPCloudActivationStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = !!data.c?.dXPCloudEnvironments?.items?.length;

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const getAnalyticsCloudActivationStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = !!data.c?.analyticsCloudWorkspaces?.items
					?.length;

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const getLiferayExperienceCloudActivationStatus = async (
			accountKey
		) => {
			const {data} = await client.query({
				query: getLiferayExperienceCloudEnvironments,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (data) {
				const status = !!data.c?.liferayExperienceCloudEnvironments
					?.items?.length;

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = getAccountKey();

			const user = await getUser(projectExternalReferenceCode);

			if (!user) {
				return;
			}

			const isValid = await isValidPage(
				client,
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
					getDXPCloudActivationStatus(projectExternalReferenceCode);
					getAnalyticsCloudActivationStatus(
						projectExternalReferenceCode
					);
					getLiferayExperienceCloudActivationStatus(
						projectExternalReferenceCode
					);
					getSessionId();
					getTotalAdministratorAccounts(projectExternalReferenceCode);

					client.mutate({
						context: {
							displaySuccess: false,
						},
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
	}, [client, oktaSessionAPI]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useOnboarding = () => useContext(AppContext);

export {AppContext, AppContextProvider, useOnboarding};
