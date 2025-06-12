/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import React from 'react';

import SpacesDisplay from '../../components/SpacesDisplay';

export default function MultipleSpacesRenderer({itemData}: {itemData: any}) {
	const assetLibraryIds = itemData.assetLibraries.map(
		(assetLibrary: any) => assetLibrary.id
	);

	if (assetLibraryIds.includes(-1)) {
		return (
			<Badge
				className="badge-pill"
				displayType="secondary"
				label={Liferay.Language.get('all-spaces')}
			/>
		);
	}

	const spaces = itemData.assetLibraries.map(
		(assetLibrary: {name: string; settings?: {logoColor: string}}) => ({
			logoColor: assetLibrary.settings?.logoColor,
			name: assetLibrary.name,
		})
	);

	return <SpacesDisplay spaces={spaces} />;
}
