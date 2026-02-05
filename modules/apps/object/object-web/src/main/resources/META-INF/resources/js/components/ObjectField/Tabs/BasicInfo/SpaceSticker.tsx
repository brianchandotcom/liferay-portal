/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

interface SpaceStickerProps
	extends Pick<
		React.ComponentProps<typeof ClaySticker>,
		'className' | 'displayType' | 'id' | 'size'
	> {
	name: string;
}

export default function SpaceSticker({
	displayType = 'outline-0',
	name,
	size,
	...otherProps
}: SpaceStickerProps) {
	return (
		<ClaySticker displayType={displayType} size={size} {...otherProps}>
			{name.charAt(0).toUpperCase()}
		</ClaySticker>
	);
}
