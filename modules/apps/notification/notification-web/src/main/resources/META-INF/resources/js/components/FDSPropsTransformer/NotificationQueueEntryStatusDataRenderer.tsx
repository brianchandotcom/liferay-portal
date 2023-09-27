/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

const statusMap = new Map([
	[0, {className: 'label-danger', label: Liferay.Language.get('failed')}],
	[1, {className: 'label-success', label: Liferay.Language.get('sent')}],
	[2, {className: 'label-warning', label: Liferay.Language.get('unsent')}],
]);

interface NotificationQueueEntryStatusDataRendererProps {
	value: boolean | number | string;
}

export function NotificationQueueEntryStatusDataRenderer({
	value,
}: NotificationQueueEntryStatusDataRendererProps) {
	const statusInfo = typeof value === 'number' ? statusMap.get(value) : null;

	return statusInfo ? (
		<strong className={`label ${statusInfo.className}`}>
			{statusInfo.label}
		</strong>
	) : (
		<strong
			className={classNames('label', {
				'label-danger': !value,
				'label-success': value,
			})}
		>
			{value
				? Liferay.Language.get('sent')
				: Liferay.Language.get('unsent')}
		</strong>
	);
}
