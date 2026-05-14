/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import SectionHeader from '../../../components/SectionHeader';
import {FormikFieldText} from '../../../components/forms/formik';

export default function Setup() {
	return (
		<>
			<SectionHeader
				subtitle={Liferay.Language.get(
					'provide-a-descriptive-name-for-your-file'
				)}
				title={sub(
					Liferay.Language.get('x-details'),
					Liferay.Language.get('export')
				)}
			/>

			<ClayLayout.Sheet>
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
