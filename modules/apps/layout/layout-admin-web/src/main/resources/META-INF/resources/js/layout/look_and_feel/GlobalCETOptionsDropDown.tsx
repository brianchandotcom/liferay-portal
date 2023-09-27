/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
