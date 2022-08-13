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

import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import DropDownAction from '../DropDown/DropDownAction';

export type ContextMenuState = {
	actions: any[];
	item: any;
	position: {
		x: number;
		y: number;
	};
	rowIndex: number;
	visible: boolean;
};

type ContextMenuProps = {
	contextMenuState: ContextMenuState;
	mutate: any;
	setContextMenuState: React.Dispatch<ContextMenuState>;
};

const ContextMenu: React.FC<ContextMenuProps> = ({
	contextMenuState,
	mutate,
	setContextMenuState,
}) => {
	const [active, setActive] = useState(true);

	if (!contextMenuState.visible) {
		return null;
	}

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={setActive}
			style={{
				left: contextMenuState.position.x - 40,
				position: 'fixed',
				top: contextMenuState.position.y,
			}}
			trigger={
				<div onContextMenu={(event) => event.preventDefault()}></div>
			}
		>
			<ClayDropDown.ItemList>
				{contextMenuState.actions.map((action, index) => (
					<DropDownAction
						action={action}
						item={contextMenuState.item}
						key={index}
						mutate={mutate}
						onBeforeClickAction={() =>
							setContextMenuState({
								...contextMenuState,
								visible: false,
							})
						}
						setActive={(active) => {
							setActive(active);

							if (!active) {
								setContextMenuState({
									...contextMenuState,
									visible: false,
								});
							}
						}}
					/>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default ContextMenu;
