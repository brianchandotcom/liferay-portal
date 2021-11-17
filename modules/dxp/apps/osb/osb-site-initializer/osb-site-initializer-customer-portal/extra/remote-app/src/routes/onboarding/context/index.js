import { createContext, useEffect, useReducer } from 'react';
import useGraphQL from '~/common/hooks/useGraphQL';
import FormProvider from '~/common/providers/FormProvider';
import { LiferayTheme } from '~/common/services/liferay';
import { getUserAccountById } from '~/common/services/liferay/graphql/user-accounts';
import { PARAMS_KEYS, SearchParams } from '~/common/services/liferay/search-params';
import {
	getInitialDxpAdmin,
	getInitialInvite,
	roles,
	steps,
} from '../utils/constants';
import reducer, { actionTypes } from './reducer';

const initialApp = (assetsPath) => ({
	assetsPath,
	dxp: {
		organization: 'SuperBank',
		version: '7.3',
	},
	project: undefined,
	step: steps.welcome,
	userAccount: undefined
});

const initialForm = {
	dxp: {
		admins: [getInitialDxpAdmin()],
		dataCenterRegion: '',
		projectId: '',
	},
	invites: [
		getInitialInvite(roles.creator.id),
		getInitialInvite(roles.watcher.id),
		getInitialInvite(roles.watcher.id),
	],
};

const AppContext = createContext();

const AppProvider = ({ assetsPath, children }) => {
	const [state, dispatch] = useReducer(reducer, initialApp(assetsPath));
	const { data: userAccount, isLoading } = useGraphQL(getUserAccountById(LiferayTheme.getUserId()));

	useEffect(() => {
		const projectExternalReferenceCode = SearchParams.get(PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE);

		dispatch({
			payload: {
				externalReferenceCode: projectExternalReferenceCode
			},
			type: actionTypes.UPDATE_ASSET_PATH
		});
	}, []);

	if (isLoading) {
		dispatch({
			payload: userAccount,
			type: actionTypes.UPDATE_USER_ACCOUNT
		});
	}

	return (
		<AppContext.Provider value={[state, dispatch]}>
			<FormProvider initialValues={initialForm}>{children}</FormProvider>
		</AppContext.Provider>
	);
};

export { AppContext, AppProvider };
