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
import ClayIcon from '@clayui/icon';
import {KeyedMutator} from 'swr';

import {Action} from '../../types';

type DropDownActionProps<T = any> = {
	action: Omit<Action, 'permission'>;
	item: T;
	mutate?: KeyedMutator<T>;
	onBeforeClickAction?: () => any;
	setActive: (active: boolean) => void;
};

const DropDownAction: React.FC<DropDownActionProps> = ({
	action: {action, disabled, icon, name},
	item,
	mutate = () => {},
	onBeforeClickAction,
	setActive,
}) => {
	if (name === 'divider') {
		return <ClayDropDown.Divider />;
	}

	return (
		<ClayDropDown.Item
			disabled={disabled}
			onClick={(event) => {
				event.preventDefault();

				setActive(false);

				if (action) {
					if (onBeforeClickAction) {
						onBeforeClickAction();
					}

					action(item, mutate as KeyedMutator<any>);
				}
			}}
		>
			{icon && <ClayIcon className="mr-2" symbol={icon} />}

			{typeof name === 'function' ? name(item) : name}
		</ClayDropDown.Item>
	);
};

export default DropDownAction;
