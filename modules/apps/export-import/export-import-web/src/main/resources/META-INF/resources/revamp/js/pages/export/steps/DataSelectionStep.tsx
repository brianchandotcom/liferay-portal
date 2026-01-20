/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import BasePage from '../../../components/BasePage';
import Footer from '../../../components/Footer';
import {ESteps, IGenericStepProps} from '../../../components/Wizard';

const Step: React.FC<
	{children?: React.ReactNode | undefined} & IGenericStepProps
> = ({backURL, onChangeStep}) => (
	<>
		<BasePage
			description={Liferay.Language.get(
				'Select-and-filter-the-data-you-want-to-include-in-your-export.'
			)}
			title={Liferay.Language.get('data-selection')}
		>
			<ClayLayout.Sheet>
				{Liferay.Language.get('Filters')}
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('Portlets')}
			</ClayLayout.Sheet>
		</BasePage>

		<Footer
			backURL={backURL}
			nextStep={ESteps.Settings}
			onChangeStep={onChangeStep}
			previousStep={ESteps.Setup}
		/>
	</>
);

export default Step;
