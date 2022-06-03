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

import ClayDropDown from '@clayui/drop-down';

const {Divider, Item} = ClayDropDown;

type DropDownActionProps<T = any> = {
	action: {
		action?: (item: T) => void;
		disabled?: boolean;
		name: ((item: T) => string) | string;
	};
	item: T;
	setActive: (active: boolean) => void;
};

const DropDownAction: React.FC<DropDownActionProps> = ({
	action: {action, disabled, name},
	item,
	setActive,
}) => {
	if (name === 'divider') {
		return <Divider />;
	}

	return (
		<Item
			disabled={disabled}
			onClick={(event) => {
				event.preventDefault();

				setActive(false);

				if (action) {
					action(item);
				}
			}}
		>
			{typeof name === 'function' ? name(item) : name}
		</Item>
	);
};

export default DropDownAction;
