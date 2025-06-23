/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ItemSelector} from 'frontend-js-item-selector-web';
import React from 'react';

export default function ItemSelectorSamples() {
	return (
		<ClayLayout.Row className="p-3">
			<ClayLayout.Col>
				<h3>Single Select</h3>

				<ItemSelector />
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}
