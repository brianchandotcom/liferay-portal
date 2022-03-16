/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
