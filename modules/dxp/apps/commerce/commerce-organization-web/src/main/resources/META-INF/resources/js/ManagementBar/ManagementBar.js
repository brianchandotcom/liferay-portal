/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useContext} from 'react';

import ChartContext from '../ChartContext';

function ManagementBar() {
	const {chartInstanceRef} = useContext(ChartContext);

	return (
		<ManagementToolbar.Container>
			<ManagementToolbar.ItemList>
				<ManagementToolbar.Item>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							chartInstanceRef.current.collapseAllNodes()
						}
					>
						{Liferay.Language.get('collapse-all')}
					</ClayButton>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}

export default ManagementBar;
