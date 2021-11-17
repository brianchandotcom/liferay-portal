import { createContext, useEffect, useReducer } from 'react';
import { CUSTOM_EVENTS } from '../utils/constants';
import reducer, { actionTypes } from './reducer';

const initialApp = (assetsPath) => ({
  assetsPath,
  userAccount: undefined,
});

const AppContext = createContext();

const AppProvider = ({ assetsPath, children }) => {
  const [state, dispatch] = useReducer(reducer, initialApp(assetsPath));

  useEffect(() => {
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
