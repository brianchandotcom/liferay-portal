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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useState} from 'react';

export function GlobalCETOptionsDropDown({
	dropdownItems,
	dropdownTriggerId,
}: IProps) {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDownWithItems
			active={active}
			items={dropdownItems}
			menuElementAttrs={{
				'aria-labelledby': dropdownTriggerId,
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('show-options')}
					displayType="unstyled"
					id={dropdownTriggerId}
					small
					symbol="ellipsis-v"
				/>
			}
		/>
	);
}

interface IProps {
	dropdownItems: Array<{
		label: string;
		onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
		symbolLeft?: string;
		value?: string;
	}>;
	dropdownTriggerId: string;
}
