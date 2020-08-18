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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayPopover from '@clayui/popover';
import React, {useEffect, useState} from 'react';

const {Item, ItemList} = ClayDropDown;

export default ({actions, disabled}) => {
	const [active, setActive] = useState(false);
	const [showPopover, setShowPopover] = useState(false);

	const DropdownButton = (
		<ClayButtonWithIcon
			className="page-link"
			disabled={disabled}
			displayType="unstyled"
			symbol="ellipsis-v"
		/>
	);

	if (actions.length === 0) {
		return DropdownButton;
	}

	const onSelectItem = (event, action) => {
		event.preventDefault();

		if (typeof action.action === 'function') {
			action.action();
		}

		setActive(false);
	};

	useEffect(() => {
		if (showPopover && !active) {
			setShowPopover(false);
		}
	}, [active, showPopover]);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={(item) => setActive(item)}
			trigger={DropdownButton}
		>
			<ItemList>
				{actions.map((action, index) => {
					const ItemWrapper = () => (
						<Item
							className={action.className}
							key={index}
							onClick={(event) => onSelectItem(event, action)}
						>
							{action.name}
						</Item>
					);

					if (action.popover) {
						const {alignPosition, body, header} = action.popover;

						return (
							<ClayPopover
								alignPosition={alignPosition}
								disableScroll
								header={header}
								show={showPopover}
								style={{zIndex: 1420}}
								trigger={
									<div
										onMouseOut={() => setShowPopover(false)}
										onMouseOver={() => setShowPopover(true)}
									>
										<ItemWrapper />
									</div>
								}
							>
								{body}
							</ClayPopover>
						);
					}

					return <ItemWrapper key={index} />;
				})}
			</ItemList>
		</ClayDropDown>
	);
};
