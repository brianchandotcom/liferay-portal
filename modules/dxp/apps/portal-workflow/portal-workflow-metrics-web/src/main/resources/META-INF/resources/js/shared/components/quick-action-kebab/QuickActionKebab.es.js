/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {IconItem} from './IconItem';

const QuickActionKebab = ({
	dropDownItems = [],
	disabled = false,
	iconItems = [],
	items = [],
}) => {
	if (items.length > 0) {
		dropDownItems = items;
		iconItems = items.filter(({icon}) => icon);
	}

	return (
		<>
			<div className={`quick-action-menu ${disabled ? 'disabled' : ''}`}>
				{iconItems.map(({icon, onClick}, index) => (
					<IconItem icon={icon} key={index} onClick={onClick} />
				))}
			</div>

			{dropDownItems.length > 0 && (
				<KebabDropDown disabled={disabled} items={items} />
			)}
		</>
	);
};

const KebabDropDown = ({disabled, items}) => {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDownWithItems
			active={active}
			items={items}
			onActiveChange={setActive}
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
