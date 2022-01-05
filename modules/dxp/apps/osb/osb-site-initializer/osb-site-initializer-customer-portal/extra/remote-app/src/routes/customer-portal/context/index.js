import {
	createContext,
	useCallback,
	useContext,
	useEffect,
	useReducer,
} from 'react';
import client from '../../../apolloClient';
import {useApplicationProvider} from '../../../common/context/ApplicationPropertiesProvider';
import {useCustomEvent} from '../../../common/hooks/useCustomEvent';
import {LiferayTheme} from '../../../common/services/liferay';
import {fetchSession} from '../../../common/services/liferay/api';
import {
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {
	PARAMS_KEYS,
	SearchParams,
} from '../../../common/services/liferay/search-params';
import {isValidPage} from '../../../common/utils/page.validation';
import {CUSTOM_EVENTS} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const getCurrentPageName = () => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	return pathSplit.length > 2 ? pathSplit[2] : '';
};

const AppContextProvider = ({assetsPath, children, page}) => {
	const {oktaSessionURL} = useApplicationProvider();
	const dispatchEventUserAccount = useCustomEvent(CUSTOM_EVENTS.USER_ACCOUNT);
	const dispatchEventSubscriptionGroups = useCustomEvent(
		CUSTOM_EVENTS.SUBSCRIPTION_GROUPS
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

	const onPageMenuChange = useCallback(({detail}) => {
		dispatch({
			payload: detail,
			type: actionTypes.UPDATE_PAGE,
		});
	}, []);

	useEffect(() => {
		window.addEventListener(CUSTOM_EVENTS.MENU_PAGE, onPageMenuChange);

		return () => {
			window.removeEventListener(
				CUSTOM_EVENTS.MENU_PAGE,
				onPageMenuChange
			);
		};
	}, [onPageMenuChange]);

	useEffect(() => {
		const getUser = async () => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: LiferayTheme.getUserId(),
				},
			});

			if (data) {
				dispatch({
					payload: data.userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT,
				});

				dispatchEventUserAccount(data.userAccount);

				return data.userAccount;
			}
		};

		const getProject = async (projectExternalReferenceCode) => {
			const {data: projects} = await client.query({
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${projectExternalReferenceCode}'`,
				},
			});

			if (projects) {
				const project = projects?.c?.koroneikiAccounts?.items[0];
				dispatch({
					payload: project,
					type: actionTypes.UPDATE_PROJECT,
				});

				return project;
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

				dispatchEventSubscriptionGroups(items);
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
					siteKey: LiferayTheme.getScopeGroupId(),
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
					getProject(projectExternalReferenceCode);
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
