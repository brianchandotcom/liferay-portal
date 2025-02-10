/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CollapseProvider from './CollapseProvider';
import DropdownProvider from './DropdownProvider';
import TabsProvider from './TabsProvider';

export function main() {
	new CollapseProvider();
	new DropdownProvider();

	if (Liferay.FeatureFlags['LPD-47713']) {
		new TabsProvider();
	}
}
