/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import SpacesDisplay, {SpaceDisplayProps} from '../../components/SpacesDisplay';

interface MultipleSpacesRendererProps {
	itemData: {
		assetLibraries: SpaceDisplayProps['spaces'];
	};
}

export default function MultipleSpacesRenderer({
	itemData,
}: MultipleSpacesRendererProps) {
	return <SpacesDisplay spaces={itemData.assetLibraries} />;
}
