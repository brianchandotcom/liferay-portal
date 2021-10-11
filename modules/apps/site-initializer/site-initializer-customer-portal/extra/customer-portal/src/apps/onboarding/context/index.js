import { createContext, useReducer } from "react";
import FormProvider from "~/shared/providers/FormProvider";
import { getInitialInvite, getInitialDxpAdmin, roles, steps } from "../utils/constants";
import reducer from "./reducer";

const initialApp = {
  step: steps.welcome,
  dxp: {
    organization: "SuperBank",
    version: "7.3"
  },
};

const initialForm = {
  invites: [
    getInitialInvite(roles.creator.id),
    getInitialInvite(roles.watcher.id),
    getInitialInvite(roles.watcher.id)
  ],
  dxp: {
    projectId: "",
    dataCenterRegion: "",
    admins: [
      getInitialDxpAdmin()
    ],
  }
};

const AppContext = createContext();

const AppProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialApp);

  return (
    <AppContext.Provider value={[state, dispatch]}>
      <FormProvider initialValues={initialForm}>
        {children}
      </FormProvider>
    </AppContext.Provider>
  );
};

export { AppContext, AppProvider };
