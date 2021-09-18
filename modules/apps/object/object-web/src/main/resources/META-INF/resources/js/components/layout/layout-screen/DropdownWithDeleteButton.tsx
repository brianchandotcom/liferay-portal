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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

const DropdownWithDeleteButton: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	onClick,
}) => {
	const [active, setActive] = useState<boolean>(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					displayType="unstyled"
					symbol="ellipsis-v"
				/>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item onClick={onClick}>
					{Liferay.Language.get('delete')}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default DropdownWithDeleteButton;
