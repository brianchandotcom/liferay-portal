/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import React from 'react';

export default function LocaleSelector({
	locales,
	onChange,
	portletNamespace,
	selectedLanguageId,
}) {
	return (
		<ClayForm.Group>
			<ClaySelect
				name={`${portletNamespace}languageId`}
				onChange={onChange}
				value={selectedLanguageId}
			>
				{locales.map((locale) => (
					<ClaySelect.Option
						key={locale.languageId}
						label={locale.displayName}
						value={locale.languageId}
					/>
				))}
			</ClaySelect>
		</ClayForm.Group>
	);
}
