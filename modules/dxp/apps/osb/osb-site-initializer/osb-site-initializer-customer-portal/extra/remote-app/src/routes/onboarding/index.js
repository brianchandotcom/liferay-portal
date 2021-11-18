import OnboardingStyles from '~/routes/onboarding/styles/app.scss';
import {AppContextProvider} from './context';
import Pages from './pages';

const Onboarding = (props) => {
	return (
		<>
			<style>{OnboardingStyles}</style>
			<AppContextProvider {...props}>
				<Pages />
			</AppContextProvider>
		</>
	);
};

export default Onboarding;
