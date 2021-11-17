import { createContext, useEffect, useReducer } from 'react';
import { PARAMS_KEYS, SearchParams } from '~/common/services/liferay/search-params';
import { CUSTOM_EVENTS } from '../utils/constants';
import reducer, { actionTypes } from './reducer';

const initialApp = (assetsPath) => ({
  assetsPath,
  project: undefined,
  userAccount: undefined
});

const AppContext = createContext();

const AppProvider = ({ assetsPath, children }) => {
  const [state, dispatch] = useReducer(reducer, initialApp(assetsPath));

  useEffect(() => {
    const koroneikiExternalReferenceCode = SearchParams.get(PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE);

    if (koroneikiExternalReferenceCode) {
      dispatch({
        payload: {
          externalReferenceCode: koroneikiExternalReferenceCode
        },
        type: actionTypes.UPDATE_PROJECT,
      });
    }

    const onUserAccountLoading = ({ detail: userAccountData }) => dispatch({
      payload: userAccountData,
      type: actionTypes.UPDATE_USER_ACCOUNT
    });
    window.addEventListener(CUSTOM_EVENTS.USER_ACCOUNT, onUserAccountLoading);

    return () => window.removeEventListener(CUSTOM_EVENTS.USER_ACCOUNT, onUserAccountLoading);
  }, []);

  return (
    <AppContext.Provider value={[state, dispatch]}>
      {children}
    </AppContext.Provider>
  );
};

export { AppContext, AppProvider };
