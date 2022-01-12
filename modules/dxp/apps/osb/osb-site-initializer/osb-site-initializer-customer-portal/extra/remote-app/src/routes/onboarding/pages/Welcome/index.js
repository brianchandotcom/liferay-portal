/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import BaseButton from '../../../../common/components/BaseButton';
import Layout from '../../components/Layout';
import {useOnboarding} from '../../context';
import {actionTypes} from '../../context/reducer';
import {steps} from '../../utils/constants';
import WelcomeSkeleton from './Skeleton';

const Welcome = () => {
	const [{assetsPath}, dispatch] = useOnboarding();

	return (
		<Layout
			className="align-items-center d-flex flex-column pt-6 px-6"
			footerProps={{
				middleButton: (
					<BaseButton
						displayType="primary"
						onClick={() =>
							dispatch({
								payload: steps.invites,
								type: actionTypes.CHANGE_STEP,
							})
						}
					>
						Start Project Setup
					</BaseButton>
				),
			}}
			headerProps={{
				greetings: `Ready, set, go!`,
				title: 'Let’s set up your project',
			}}
		>
			<img
				alt="Costumer Service Intro"
				className="mb-4 pb-1"
				draggable={false}
				height={237}
				src={`${assetsPath}/assets/intro_onboarding.svg`}
				width={331}
			/>

			<p className="mb-0 px-1 text-center text-neutral-8">
				We&apos;ll start by adding any team members to your project and
				complete your product activation.
			</p>
		</Layout>
	);
};

Welcome.Skeleton = WelcomeSkeleton;

export default Welcome;
