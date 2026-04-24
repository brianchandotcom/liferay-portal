/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React from 'react';

export default function SharedIcon({
	className,
	spaceName,
}: {
	className?: string;
	spaceName: string;
}) {
	return (
		<ClayTooltipProvider>
			<ClaySticker
				className={classNames('flex-shrink-0', className)}
				data-tooltip-align="top"
				displayType="unstyled"
				title={sub(
					Liferay.Language.get('shared-from-x'),
					`"${spaceName}"`
				)}
			>
				<ClayIcon className="text-secondary" symbol="users" />
			</ClaySticker>
		</ClayTooltipProvider>
	);
}
