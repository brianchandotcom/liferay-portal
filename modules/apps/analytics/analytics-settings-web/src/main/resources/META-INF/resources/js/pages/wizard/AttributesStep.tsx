/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {EPageView, Events, useDispatch} from '../../App';
import BasePage from '../../components/BasePage';
import Attributes from '../../components/attributes/Attributes';
import {sync} from '../../utils/api';
import {ESteps, IGenericStepProps} from './WizardPage';

interface IStepProps extends IGenericStepProps {}

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
	const dispatch = useDispatch();

	return (
		<BasePage
			description={Liferay.Language.get('attributes-step-description')}
			title={Liferay.Language.get('attributes')}
		>
			<Attributes />

			<BasePage.Footer>
				<ClayButton
					displayType="secondary"
					onClick={() => onChangeStep(ESteps.People)}
				>
					{Liferay.Language.get('previous')}
				</ClayButton>

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
			</BasePage.Footer>
		</BasePage>
	);
};

export default Step;
