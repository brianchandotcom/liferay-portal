/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Sticker from '@clayui/sticker';
import React from 'react';

const SpaceRenderer = ({value}: {value: string}) => {
	const isStructure = false;

	return isStructure ? (
		<span className="badge badge-pill badge-secondary">
			<span className="badge-item badge-item-expand">
				{Liferay.Language.get('all-spaces')}
			</span>
		</span>
	) : (
		<span className="align-items-center d-flex">
			<Sticker
				className="c-mr-2 sticker-small-square"
				displayType="outline-1"
				size="sm"
			>
				{value?.charAt(0).toUpperCase()}
			</Sticker>

			{value}
		</span>
	);
};

export default SpaceRenderer;
