import { createContext, useReducer } from 'react';
import reducer from './reducer';

const initialApp = (assetsPath) => ({
  assetsPath
});

const AppContext = createContext();

const AppProvider = ({ assetsPath, children }) => {
  const [state, dispatch] = useReducer(reducer, initialApp(assetsPath));

  return (
    <AppContext.Provider value={[state, dispatch]}>
      {children}
    </AppContext.Provider>
  );
};

export { AppContext, AppProvider };
