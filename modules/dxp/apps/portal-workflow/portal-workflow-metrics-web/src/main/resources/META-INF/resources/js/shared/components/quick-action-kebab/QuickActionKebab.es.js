/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

const IconItem = ({icon, onClick}) => {
	return (
		<ClayButton
			className="component-action quick-action-item"
			displayType="unstyled"
			onClick={onClick}
			role="button"
		>
			<ClayIcon symbol={icon} />
		</ClayButton>
	);
};

const QuickActionKebab = ({
	dropDownItems = [],
	disabled = false,
	iconItems = [],
	items = [],
}) => {
	if (items.length) {
		dropDownItems = items;
		iconItems = items.filter(({icon}) => icon);
	}

	dropDownItems = dropDownItems.map((item) => ({
		...item,
	}));

	return (
		<>
			{!disabled && !!iconItems.length && (
				<div className="quick-action-menu">
					{iconItems.map(({icon, onClick}, index) => (
						<IconItem icon={icon} key={index} onClick={onClick} />
					))}
				</div>
			)}

			{!!dropDownItems.length && (
				<KebabDropDown disabled={disabled} items={dropDownItems} />
			)}
		</>
	);
};

const KebabDropDown = ({disabled, items}) => {
	return (
		<ClayDropDownWithItems
			items={items}
			trigger={
				<ClayButton
					className="component-action"
					disabled={disabled}
					displayType="unstyled"
					monospaced
				>
					<ClayIcon symbol="ellipsis-v" />
				</ClayButton>
			}
		/>
	);
};

export {KebabDropDown};
export default QuickActionKebab;
