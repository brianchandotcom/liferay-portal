/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';

export function InfoPanel({component: InfoPanelContent, ...props}: any) {
	const {selectedItems} = useContext(FrontendDataSetContext);

	return (
		<SidePanel {...props}>
			<InfoPanelContent items={selectedItems} />
		</SidePanel>
	);
}
