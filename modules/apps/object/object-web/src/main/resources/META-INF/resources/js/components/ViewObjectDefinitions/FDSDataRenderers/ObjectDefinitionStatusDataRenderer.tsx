/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

export default function objectDefinitionStatusDataRenderer({
	itemData,
}: {
	itemData: ObjectDefinition;
}) {
	return (
		<strong
			className={classNames(
				itemData.status.label === 'approved'
					? 'label-success'
					: 'label-info',
				'label'
			)}
		>
			{itemData.status.label_i18n}
		</strong>
	);
}
