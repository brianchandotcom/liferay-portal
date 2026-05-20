/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import {FormikFieldText} from '../../../components/forms/formik';

export default function Setup() {
	return (
		<>
			<header className="mb-1 sheet-header">
				<div className="mb-1 sheet-title">
					{Liferay.Language.get('setup')}
				</div>

				<p className="sheet-text text-secondary">
					{Liferay.Language.get(
						'name-your-export-and-configure-your-export-settings'
					)}
				</p>
			</header>

			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-0">
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

				<FormikFieldText
					label={Liferay.Language.get('file-name')}
					name="fileName"
					placeholder={Liferay.Language.get('add-an-export-name')}
					required
				/>
			</ClayLayout.Sheet>
		</>
	);
}
