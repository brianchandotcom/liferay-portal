import {useContext} from 'react';
import BaseButton from '~/common/components/BaseButton';
import {
	onboardingPageRedirection,
	overviewPageRedirection,
	projectsPageRedirection,
	usePageGuard,
} from '~/common/hooks/usePageGuard';
import {AppContext} from '../context';
import {changeStep} from '../context/actions';
import {steps} from '../utils/constants';
import Layout from './layout';
import WelcomeSkeleton from './skeleton/welcome-skeleton';

const Welcome = ({externalReferenceCode}) => {
	const [state, dispatch] = useContext(AppContext);
	const {isLoading} = usePageGuard(
		externalReferenceCode,
		onboardingPageRedirection,
		[overviewPageRedirection, projectsPageRedirection]
	);

	if (isLoading) {
		return <WelcomeSkeleton />;
	}

	return (
		<Layout
			footerProps={{
				middleButton: (
					<BaseButton
						displayType="primary"
						onClick={() => dispatch(changeStep(steps.invites))}
					>
						Get Started
					</BaseButton>
				),
			}}
			headerProps={{
				greetings: 'Hello Sarah,',
				title: 'Welcome to Liferay’s Customer Portal',
			}}
			mainStyles="align-items-center d-flex flex-column pt-4 px-6"
		>
			<img
				alt="Costumer Service Intro"
				className="mb-4 pb-1"
				draggable={false}
				height={300}
				src={`${state.assetsPath}/assets/intro_onboarding.svg`}
				width={391.58}
			/>

			<p className="mb-0 px-1 text-center text-neutral-2">
				Let’s download your DXP activation keys, add any team members to
				your projects and give you a quick tour of the space.
			</p>
		</Layout>
	);
};

export default Welcome;
