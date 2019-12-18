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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {DropDownItem} from './DropDownItem.es';
import {IconItem} from './IconItem';

const QuickActionKebab = ({dropDownItems = [], iconItems = [], items = []}) => {
	if (items.length > 0) {
		dropDownItems = items;
		iconItems = items;
	}

	return (
		<>
			<div className="quick-action-menu">
				{iconItems.map((iconItem, index) => (
					<IconItem
						action={iconItem.action}
						icon={iconItem.icon}
						key={index}
					/>
				))}
			</div>

			{dropDownItems.length > 0 && (
				<KebabDropDown>
					{dropDownItems.map((dropDownItem, index) => (
						<DropDownItem
							action={dropDownItem.action}
							key={index}
							title={dropDownItem.title}
						/>
					))}
				</KebabDropDown>
			)}
		</>
	);
};

const KebabDropDown = ({children}) => {
	const [active, setActive] = useState(false);
	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="component-action"
					displayType="unstyled"
					monospaced
				>
					<ClayIcon spritemap={spritemap} symbol="ellipsis-v" />
				</ClayButton>
			}
		>
			{children}
		</ClayDropDown>
	);
};

export {KebabDropDown};
export default QuickActionKebab;
