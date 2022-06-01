/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import {UserListView} from '../../Manage/User';

type SelectCaseParametersProps = {
	setState: any;
};

const SelectCaseParameters: React.FC<SelectCaseParametersProps> = ({
	setState,
}) => (
	<UserListView
		listViewProps={{
			onContextChange: ({selectedRows}) => setState(selectedRows),
		}}
		tableProps={{rowSelectable: true}}
	/>
);
export default SelectCaseParameters;
