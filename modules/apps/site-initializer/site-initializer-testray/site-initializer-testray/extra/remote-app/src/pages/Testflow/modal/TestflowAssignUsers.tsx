/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {UserListView} from '../../Manage/User';

type TestflowAssignUsersProps = {
	modalState: any;
	setState: any;
};

const TestflowAssignUsers: React.FC<TestflowAssignUsersProps> = ({
	modalState,
	setState,
}) => (
	<UserListView
		listViewProps={{
			initialContext: {
				selectedRows: modalState,
			},
			managementToolbarProps: {title: ''},
			onContextChange: ({selectedRows}) => setState(selectedRows),
		}}
		tableProps={{rowSelectable: true}}
	/>
);

export default TestflowAssignUsers;
