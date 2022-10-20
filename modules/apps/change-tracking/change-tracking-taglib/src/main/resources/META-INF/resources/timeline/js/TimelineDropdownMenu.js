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
