/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

export default function TimelineDropdownMenu({
	deleteURL,
	editURL,
	revertURL,
	reviewURL,
	spritemap,
}) {
	const dropdownItems = [];

	if (editURL) {
		dropdownItems.push({
			href: editURL,
			label: Liferay.Language.get('edit'),
			symbolLeft: 'pencil',
		});
	}

	if (revertURL) {
		dropdownItems.push({
			href: revertURL,
			label: Liferay.Language.get('revert'),
			symbolLeft: 'list-ul',
		});
	}

	dropdownItems.push({
		href: reviewURL,
		label: Liferay.Language.get('review-changes'),
		symbolLeft: 'list-ul',
	});

	if (deleteURL) {
		dropdownItems.push(
			{type: 'divider'},
			{
				href: deleteURL,
				label: Liferay.Language.get('delete'),
				symbolLeft: 'times-circle',
			}
		);
	}

	return (
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
	);
}
