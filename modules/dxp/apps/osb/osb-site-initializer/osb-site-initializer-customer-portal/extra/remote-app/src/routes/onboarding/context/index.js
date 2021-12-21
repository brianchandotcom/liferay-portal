import {useQuery} from '@apollo/client';
import {createContext, useEffect, useReducer} from 'react';
import client from '../../../apolloClient';
import FormProvider from '../../../common/providers/FormProvider';
import {LiferayTheme} from '../../../common/services/liferay';
import {
	getKoroneikiAccounts,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {
	PARAMS_KEYS,
	SearchParams,
} from '../../../common/services/liferay/search-params';
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
		dataCenterRegion: {},
		disasterDataCenterRegion: {},
		projectId: '',
	},
	invites: [
		getInitialInvite(),
		getInitialInvite(roles.MEMBER),
		getInitialInvite(roles.MEMBER),
	],
};

const AppContext = createContext();

const AppContextProvider = ({assetsPath, children}) => {
	const [state, dispatch] = useReducer(reducer, {
		assetsPath,
		koroneikiAccount: {},
		project: undefined,
		step: steps.welcome,
		userAccount: undefined,
	});

	const {data} = useQuery(getUserAccount, {
		variables: {id: LiferayTheme.getUserId()},
	});

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

	useEffect(() => {
		const projectExternalReferenceCode = SearchParams.get(
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		);

		if (projectExternalReferenceCode) {
			getProject(projectExternalReferenceCode);
		}
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
