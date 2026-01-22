/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {IGenericStepProps} from '../../../components/Wizard';

const Step: React.FC<
	{children?: React.ReactNode | undefined} & IGenericStepProps
> = () => (
	<>
		<ClayLayout.Sheet>{Liferay.Language.get('details')}</ClayLayout.Sheet>

		<ClayLayout.Sheet>
			{Liferay.Language.get('what-would-you-like-to-export')}
		</ClayLayout.Sheet>
	</>
);

export default Step;
