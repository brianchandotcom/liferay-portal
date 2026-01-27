/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import FieldText from '../../../components/forms/FieldText';

export default function SetupStep() {
	return (
		<>
			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader>
					<div className="mb-2 sheet-title">
						{sub(
							Liferay.Language.get('x-details'),
							Liferay.Language.get('export')
						)}
					</div>

					<div className="sheet-text text-3">
						{Liferay.Language.get(
							'provide-a-descriptive-name-for-your-export'
						)}
					</div>
				</ClayLayout.SheetHeader>

				<FieldText
					label={Liferay.Language.get('file-name')}
					name="filename"
					required
				/>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('what-would-you-like-to-export')}
			</ClayLayout.Sheet>
		</>
	);
}
