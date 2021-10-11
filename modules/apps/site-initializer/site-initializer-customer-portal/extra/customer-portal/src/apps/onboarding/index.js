import { AppProvider } from "./context";
import Pages from "./pages";
import OnboardingStyles from "~/apps/onboarding/styles/app.scss";

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
