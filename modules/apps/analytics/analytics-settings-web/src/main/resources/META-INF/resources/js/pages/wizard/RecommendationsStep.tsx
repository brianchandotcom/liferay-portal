/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';

import BasePage from '../../components/BasePage';
import Recommendations from '../../components/recommendations/Recommendations';
import {EPageView, Events, useDispatch} from '../../index';
import {sync} from '../../utils/api';
import {ESteps, IGenericStepProps} from './WizardPage';

const Step: React.FC<IGenericStepProps> = ({onChangeStep}) => {
	const dispatch = useDispatch();

	return (
		<BasePage
			description={
				<>
					{Liferay.Language.get(
						'content-recommendations-personalize-user-experiences-by-suggesting-relevant-items-based-on-user-behavior-and-preferences'
					)}

					<ClayLink
						className="ml-1"
						href="https://learn.liferay.com/w/analytics-cloud/getting-started/connecting-liferay-dxp-to-analytics-cloud"
						target="_blank"
					>
						{Liferay.Language.get(
							'learn-more-about-recommendations'
						)}
					</ClayLink>
				</>
			}
			title={Liferay.Language.get('recommendations')}
		>
			<Recommendations />

			<BasePage.Footer>
				<ClayButton
					onClick={() => {
						sync();

						dispatch({
							payload: EPageView.Default,
							type: Events.ChangePageView,
						});

						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'dxp-has-successfully-connected-to-analytics-cloud.-you-will-begin-to-see-data-as-activities-occur-on-your-sites'
							),
						});
					}}
				>
					{Liferay.Language.get('finish')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => onChangeStep(ESteps.Attributes)}
				>
					{Liferay.Language.get('previous')}
				</ClayButton>
			</BasePage.Footer>
		</BasePage>
	);
};

export default Step;
