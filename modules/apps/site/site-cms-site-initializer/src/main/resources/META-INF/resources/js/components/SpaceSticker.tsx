/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

interface SpaceStickerProps {
	color?: string;
	name: string;
}

export default function SpaceSticker({color, name}: SpaceStickerProps) {
	return (
		<>
			<ClaySticker displayType="outline-2">{name.charAt(0)}</ClaySticker>

			<span className="ml-2">
				{name} {color}
			</span>
		</>
	);
}
