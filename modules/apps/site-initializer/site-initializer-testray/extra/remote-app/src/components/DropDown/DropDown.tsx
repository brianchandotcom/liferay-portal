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
import React, {useState} from 'react';
import {KeyedMutator} from 'swr';

import DropDownAction from './DropDownAction';

const {ItemList} = ClayDropDown;

type DropDownProps = {
	actions: any[];
	item: any;
	mutate: KeyedMutator<any>;
};

const DropDown: React.FC<DropDownProps> = ({actions, item, mutate}) => {
	const [active, setActive] = useState(false);

	if (!actions.length) {
		return null;
	}

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={(newVal: boolean) => setActive(newVal)}
			trigger={
				<ClayButtonWithIcon
					className="page-link"
					displayType="unstyled"
					symbol="ellipsis-v"
				/>
			}
		>
			<ItemList>
				{actions.map((action, index) => (
					<DropDownAction
						action={action}
						item={item}
						key={index}
						mutate={mutate}
						setActive={setActive}
					/>
				))}
			</ItemList>
		</ClayDropDown>
	);
};

export default DropDown;
