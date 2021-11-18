import {createContext, useEffect, useReducer} from 'react';
import useGraphQL from '~/common/hooks/useGraphQL';
import FormProvider from '~/common/providers/FormProvider';
import {LiferayTheme} from '~/common/services/liferay';
import {getUserAccountById} from '~/common/services/liferay/graphql/user-accounts';
import {
	PARAMS_KEYS,
	SearchParams,
} from '~/common/services/liferay/search-params';
import {
	getInitialDxpAdmin,
	getInitialInvite,
	roles,
	steps,
} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

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

const AppContextProvider = ({assetsPath, children}) => {
	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		dxp: {
			organization: 'SuperBank',
			version: '7.3',
		},
		project: {},
		step: steps.welcome,
		userAccount: {},
	});

	const {data} = useGraphQL([getUserAccountById(LiferayTheme.getUserId())]);

	useEffect(() => {
		const projectExternalReferenceCode = SearchParams.get(
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		);

		dispatch({
			payload: {
				externalReferenceCode: projectExternalReferenceCode,
			},
			type: actionTypes.UPDATE_ASSET_PATH,
		});
	}, []);

	useEffect(() => {
		if (data) {
			dispatch({
				payload: data.userAccount,
				type: actionTypes.UPDATE_USER_ACCOUNT,
			});
		}
	}, [data]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			<FormProvider initialValues={initialForm}>{children}</FormProvider>
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
