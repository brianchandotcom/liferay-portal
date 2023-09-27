/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import {AddNewTabButton} from './AddNewTabButton';
import ObjectLayoutTabs from './ObjectLayoutTabs';

import './LayoutScreen.scss';

export default function LayoutScreen() {
	return (
		<div className="lfr-objects__side-panel-content-container">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get(
					'only-the-first-tab-will-be-used-when-creating-a-new-entry'
				)}
			</ClayAlert>

			<AddNewTabButton />

			<ObjectLayoutTabs />
		</div>
	);
}
