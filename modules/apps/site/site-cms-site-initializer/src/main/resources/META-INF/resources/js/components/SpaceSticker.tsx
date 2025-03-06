/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

type ValidDisplayType =
	| 'outline-0'
	| 'outline-1'
	| 'outline-2'
	| 'outline-3'
	| 'outline-4'
	| 'outline-5'
	| 'outline-6'
	| 'outline-7'
	| 'outline-8'
	| 'outline-9';

function getRandomDisplayType(): ValidDisplayType {
	const validDisplayTypes: ValidDisplayType[] = [
		'outline-0',
		'outline-1',
		'outline-2',
		'outline-3',
		'outline-4',
		'outline-5',
		'outline-6',
		'outline-7',
		'outline-8',
		'outline-9',
	];

	const randomIndex = Math.floor(Math.random() * validDisplayTypes.length);

	return validDisplayTypes[randomIndex];
}
interface SpaceStickerProps {
	name: string;
}

export default function SpaceSticker({name}: SpaceStickerProps) {
	const displayType = getRandomDisplayType();

	return (
		<>
			<ClaySticker displayType={displayType}>
				{name.charAt(0).toUpperCase()}
			</ClaySticker>

			<span className="ml-2">{name}</span>
		</>
	);
}
