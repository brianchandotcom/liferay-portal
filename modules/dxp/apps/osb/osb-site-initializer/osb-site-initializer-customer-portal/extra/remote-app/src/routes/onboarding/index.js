import OnboardingStyles from "~/routes/onboarding/styles/app.scss";
import { AppProvider } from "./context";
import Pages from "./pages";

const Onboarding = () => {
  return (
    <>
      <style>{OnboardingStyles}</style>
      <AppProvider>
        <Pages />
      </AppProvider>
    </>
  );
};

export default Onboarding;
