/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {EPageView, Events, useDispatch} from '../../App';
import BasePage from '../../components/BasePage';
import Attributes from '../../components/attributes/Attributes';
import {IGenericStepProps} from './WizardPage';

interface IStepProps extends IGenericStepProps {}

const Step: React.FC<IStepProps> = () => {
	const dispatch = useDispatch();

	return (
		<BasePage
			description={Liferay.Language.get('attributes-step-description')}
			title={Liferay.Language.get('attributes')}
		>
			<Attributes />

			<BasePage.Footer>
				<ClayButton
					onClick={() => {
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
