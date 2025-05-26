/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';
import {SidePanel} from './clay_side_panel';

export function InfoPanel({component: InfoPanelContent, ...props}: any) {
	const {selectedItems} = useContext(FrontendDataSetContext);

	return (
		<SidePanel {...props}>
			<InfoPanelContent items={selectedItems} />
		</SidePanel>
	);
}
