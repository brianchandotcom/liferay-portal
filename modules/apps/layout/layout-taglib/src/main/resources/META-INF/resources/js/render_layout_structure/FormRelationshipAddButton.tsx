/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

export default function FormRelationshipAddButton({
	label,
}: {
	label: Record<Liferay.Language.Locale, string>;
}) {
	const value =
		label?.[Liferay.ThemeDisplay.getLanguageId()] ??
		label?.[Liferay.ThemeDisplay.getDefaultLanguageId()] ??
		Liferay.Language.get('add-new');

	return (
		<ClayButton
			aria-label={value ? '' : Liferay.Language.get('add-new')}
			borderless
			displayType="primary"
			size="sm"
		>
			<ClayIcon
				className={classNames('text-primary', {
					'mr-2': value,
				})}
				style={{transform: 'rotate(45deg)'}}
				symbol="times-circle-full"
			/>

			{value}
		</ClayButton>
	);
}
