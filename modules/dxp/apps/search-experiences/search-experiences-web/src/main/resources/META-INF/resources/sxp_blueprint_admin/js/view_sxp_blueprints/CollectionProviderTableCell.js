/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const CollectionProviderTableCell = ({itemData}) => {
	return (
		<span>
			{!itemData.configuration.generalConfiguration.collectionProvider
				? Liferay.Language.get('no')
				: Liferay.Language.get('yes')}
		</span>
	);
};
export default CollectionProviderTableCell;
