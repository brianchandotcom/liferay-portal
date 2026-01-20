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
				'Name-your-export-and-make-an-initial-data-selection-to-narrow-down-in-the-next-step.'
			)}
			title={Liferay.Language.get('setup')}
		>
			<ClayLayout.Sheet>
				{Liferay.Language.get('details')}
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('what-would-you-like-to-export')}
			</ClayLayout.Sheet>
		</BasePage>

		<Footer
			backURL={backURL}
			nextStep={ESteps.DataSelection}
			onChangeStep={onChangeStep}
		/>
	</>
);

export default Step;
