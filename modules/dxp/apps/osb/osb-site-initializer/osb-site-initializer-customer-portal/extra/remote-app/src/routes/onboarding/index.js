import OnboardingStyles from "~/routes/onboarding/styles/app.scss";
import { AppProvider } from "./context";
import Pages from "./pages";

const Onboarding = (props) => {
  return (
    <>
      <style>{OnboardingStyles}</style>
      <AppProvider {...props}>
        <Pages />
      </AppProvider>
    </>
  );
};

export default Onboarding;
