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

import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useState} from 'react';

type ManagementToolbarLeftProps = {
	disabled: boolean;
	onSelectAllRows: () => void;
	rowSelectable?: boolean;
};

const ManagementToolbarLeft: React.FC<ManagementToolbarLeftProps> = ({
	disabled,
	onSelectAllRows,
	rowSelectable,
}) => {
	const [checked, setChecked] = useState(false);

	if (!rowSelectable) {
		return null;
	}

	return (
		<ClayManagementToolbar.ItemList>
			<ClayManagementToolbar.Item>
				<ClayCheckbox
					checked={checked}
					disabled={disabled}
					onChange={() => {
						onSelectAllRows();
						setChecked(!checked);
					}}
				/>
			</ClayManagementToolbar.Item>
		</ClayManagementToolbar.ItemList>
	);
};

export default ManagementToolbarLeft;
