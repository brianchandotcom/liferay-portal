/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../common/utils/constants';

const AssetTypeRenderer = ({
	itemData,
	value,
}: {
	itemData: {entryClassName: string};
	value: string;
}) => {
	return (
		<>
			{itemData.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME &&
				Liferay.Language.get('folder')}{' '}
			{value}
		</>
	);
};

export default AssetTypeRenderer;
