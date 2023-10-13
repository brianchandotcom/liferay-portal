/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface ItemData {
	system: boolean;
}

export function NotificationTemplateSourceDataRenderer({
	itemData,
}: {
	itemData: ItemData;
}) {
	return (
		<strong
			className={classNames(
				itemData.system ? 'label-info' : 'label-warning',
				'label'
			)}
		>
			{itemData.system
				? Liferay.Language.get('system')
				: Liferay.Language.get('custom')}
		</strong>
	);
}
