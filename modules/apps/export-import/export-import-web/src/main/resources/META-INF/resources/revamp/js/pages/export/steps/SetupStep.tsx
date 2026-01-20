/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import RequiredMark from '../../../components/forms/RequiredMark';

export default function Step() {
	return (
		<>
			<ClayLayout.Sheet>
				<div className="mb-4 sheet-header">
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
				</div>

				<ClayForm.Group>
					<label htmlFor="nameInputText">
						{Liferay.Language.get('name')}

						<RequiredMark />
					</label>

					<ClayInput id="nameInputText" required type="text" />
				</ClayForm.Group>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('what-would-you-like-to-export')}
			</ClayLayout.Sheet>
		</>
	);
}
