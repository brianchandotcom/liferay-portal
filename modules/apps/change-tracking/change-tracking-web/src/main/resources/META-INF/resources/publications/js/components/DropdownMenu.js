/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

export default function DropdownMenu({dropdownItems, spritemap}) {
	return (
		<>
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomLeft}
				items={dropdownItems}
				spritemap={spritemap}
				trigger={
					<ClayButtonWithIcon
						displayType="unstyled"
						small
						spritemap={spritemap}
						symbol="ellipsis-v"
					/>
				}
			/>
		</>
	);
}
