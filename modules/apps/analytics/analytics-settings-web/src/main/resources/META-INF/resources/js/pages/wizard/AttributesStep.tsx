/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import BasePage from '../../components/BasePage';
import Attributes from '../../components/attributes/Attributes';
import {ESteps, IGenericStepProps} from './WizardPage';

interface IStepProps extends IGenericStepProps {}

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
	return (
		<BasePage
			description={Liferay.Language.get('attributes-step-description')}
			title={Liferay.Language.get('attributes')}
		>
			<Attributes />

			<BasePage.Footer>
				<ClayButton
					onClick={() => onChangeStep(ESteps.Recommendations)}
				>
					{Liferay.Language.get('next')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => onChangeStep(ESteps.People)}
				>
					{Liferay.Language.get('previous')}
				</ClayButton>
			</BasePage.Footer>
		</BasePage>
	);
};

export default Step;
