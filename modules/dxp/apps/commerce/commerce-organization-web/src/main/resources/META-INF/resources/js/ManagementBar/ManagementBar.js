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
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useContext} from 'react';

import ChartContext from '../ChartContext';

function ManagementBar() {
	const {chartInstanceRef} = useContext(ChartContext);

	return (
		<ClayManagementToolbar>
			<ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Item>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							chartInstanceRef.current.collapseAllNodes()
						}
					>
						{Liferay.Language.get('collapse-all')}
					</ClayButton>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
}

export default ManagementBar;
