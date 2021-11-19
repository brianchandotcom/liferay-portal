import {createContext, useEffect, useReducer} from 'react';
import useGraphQL from '~/common/hooks/useGraphQL';
import {LiferayTheme} from '~/common/services/liferay';
import {getUserAccountById} from '~/common/services/liferay/graphql/user-accounts';
import {
	PARAMS_KEYS,
	SearchParams,
} from '~/common/services/liferay/search-params';
import {CUSTOM_EVENTS} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const initialApp = (assetsPath, page) => ({
	assetsPath,
	page,
	project: undefined,
	userAccount: undefined,
});

const AppContext = createContext();

const AppProvider = ({assetsPath, children, page}) => {
	const [state, dispatch] = useReducer(reducer, initialApp(assetsPath, page));
	const {data} = useGraphQL([getUserAccountById(LiferayTheme.getUserId())]);

	useEffect(() => {
		const projectExternalReferenceCode = SearchParams.get(
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		);

		dispatch({
			payload: {
				accountKey: projectExternalReferenceCode,
			},
			type: actionTypes.UPDATE_PROJECT,
		});
	}, []);

	useEffect(() => {
		if (data) {
			dispatch({
				payload: data.userAccount,
				type: actionTypes.UPDATE_USER_ACCOUNT,
			});

			window.dispatchEvent(
				new CustomEvent(CUSTOM_EVENTS.USER_ACCOUNT, {
					bubbles: true,
					composed: true,
					detail: {
						...data.userAccount,
					},
				})
			);
		}
	}, [data]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

export {AppContext, AppProvider};
