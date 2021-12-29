import {useQuery} from '@apollo/client';
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
	getKoroneikiAccounts,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {
	PARAMS_KEYS,
	SearchParams,
} from '../../../common/services/liferay/search-params';
import {CUSTOM_EVENTS} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const AppContextProvider = ({assetsPath, children, page}) => {
	const {oktaSessionURL} = useApplicationProvider();
	const dispatchEvent = useCustomEvent(CUSTOM_EVENTS.USER_ACCOUNT);
	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		page,
		project: undefined,
		quickLinks: undefined,
		sessionId: 'sessionId',
		subscriptionGroups: [],
		userAccount: undefined,
	});

	const {data} = useQuery(getUserAccount, {
		variables: {id: LiferayTheme.getUserId()},
	});

	const userAccount = data?.userAccount;

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
		if (userAccount) {
			dispatch({
				payload: userAccount,
				type: actionTypes.UPDATE_USER_ACCOUNT,
			});

			dispatchEvent(userAccount);

			const getProject = async (projectExternalReferenceCode) => {
				const {data: projects} = await client.query({
					query: getKoroneikiAccounts,
					variables: {
						filter: `accountKey eq '${projectExternalReferenceCode}'`,
					},
				});

				if (projects) {
					dispatch({
						payload: projects.c.koroneikiAccounts.items[0],
						type: actionTypes.UPDATE_PROJECT,
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

			const projectExternalReferenceCode = SearchParams.get(
				PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
			);

			if (projectExternalReferenceCode) {
				getProject(projectExternalReferenceCode);
				getSessionId();
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [userAccount, oktaSessionURL]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
