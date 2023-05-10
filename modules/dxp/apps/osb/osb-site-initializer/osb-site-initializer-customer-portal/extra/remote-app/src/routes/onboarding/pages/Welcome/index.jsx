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

import i18n from '../../../../common/I18n';
import {Button} from '../../../../common/components';
import Layout from '../../../../common/containers/setup-forms/Layout';
import {IntroOnboarding} from '../../../../common/icons';
import {useOnboarding} from '../../context';
import {actionTypes} from '../../context/reducer';
import {ONBOARDING_STEP_TYPES} from '../../utils/constants';
import WelcomeSkeleton from './Skeleton';

const Welcome = () => {
	const [, dispatch] = useOnboarding();

	return (
		<Layout
			className="align-items-center d-flex flex-column pt-6 px-6"
			footerProps={{
				middleButton: (
					<Button
						displayType="primary"
						onClick={() =>
							dispatch({
								payload: ONBOARDING_STEP_TYPES.invites,
								type: actionTypes.CHANGE_STEP,
							})
						}
					>
						{i18n.translate('start-project-setup')}
					</Button>
				),
			}}
			headerProps={{
				greetings: i18n.translate('ready-set-go'),
				title: i18n.translate('let-s-set-up-your-project'),
			}}
		>
			<IntroOnboarding
				className="mb-4 pb-1"
				draggable={false}
				height={237}
				width={331}
			/>

			<p className="mb-0 px-1 text-center text-neutral-8">
				{i18n.translate(
					'we-ll-start-by-adding-any-team-members-to-your-project-and-complete-your-product-activation'
				)}
			</p>
		</Layout>
	);
};

Welcome.Skeleton = WelcomeSkeleton;

export default Welcome;
