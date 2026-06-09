/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

export default function ProcessAuthorRenderer({
	itemData,
	value,
}: {
	itemData?: {creator?: {image?: string}};
	value?: string;
}) {
	return (
		<span className="align-items-center d-flex">
			<ClaySticker
				className="c-mr-2"
				displayType="secondary"
				shape="circle"
				size="sm"
			>
				<ClaySticker.Image
					alt={value ?? ''}
					src={itemData?.creator?.image || '/image/user_portrait'}
				/>
			</ClaySticker>

			{value}
		</span>
	);
}
