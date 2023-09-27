/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import BasePage from '../../components/BasePage';
import People from '../../components/people/People';
import {ESteps, IGenericStepProps} from './WizardPage';

const Step: React.FC<IGenericStepProps> = ({onChangeStep}) => (
	<BasePage
		description={Liferay.Language.get('sync-people-description')}
		title={Liferay.Language.get('sync-people')}
	>
		<People />

		<BasePage.Footer>
			<ClayButton.Group spaced>
				<ClayButton
					displayType="secondary"
					onClick={() => onChangeStep(ESteps.Property)}
				>
					{Liferay.Language.get('previous')}
				</ClayButton>

				<ClayButton onClick={() => onChangeStep(ESteps.Attributes)}>
					{Liferay.Language.get('next')}
				</ClayButton>
			</ClayButton.Group>
		</BasePage.Footer>
	</BasePage>
);

export default Step;
