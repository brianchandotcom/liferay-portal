/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import React from 'react';

export default function LocaleSelector({
	locales,
	onChange,
	portletNamespace,
	selectedLanguageId
}) {
	return (
		<ClayForm.Group>
			<ClaySelect
				name={`_${portletNamespace}_languageId`}
				onChange={onChange}
				value={selectedLanguageId}
			>
				{locales.map(locale => (
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
