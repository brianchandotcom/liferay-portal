/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {findAction} from '@liferay/frontend-data-set-web';
import React from 'react';

import {ActionItem} from '../../types';
import {BaseLinkRenderer, BaseLinkRendererProps} from './BaseLinkRenderer';

export default function LinkRenderer({
	actions,
	options: {actionId},
	stickerClassName,
	symbol,
	...props
}: BaseLinkRendererProps & {
	actions: ActionItem[];
	options: {actionId: string};
	stickerClassName: string;
	symbol: string;
}) {
	const action = findAction(actions, actionId);

	return (
		<BaseLinkRenderer
			{...props}
			action={action}
			stickerClassName={stickerClassName}
			symbol={symbol}
		/>
	);
}
