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
import ClayDropDown from '@clayui/drop-down';
import React, {MouseEventHandler, useState} from 'react';

import {useLayoutContext} from '../objectLayoutContext';

interface HeaderDropdownProps {
	addCategorization?: MouseEventHandler;
	deleteElement: MouseEventHandler;
	disabled?: boolean;
}

export function HeaderDropdown({
	addCategorization,
	deleteElement,
	disabled,
}: HeaderDropdownProps) {
	const [active, setActive] = useState<boolean>(false);
	const [
		{
			enabledCategorization,
			isViewOnly,
			objectLayout: {objectLayoutTabs},
		},
	] = useLayoutContext();

	const handleOnClick = (handler: Function) => {
		handler();
		setActive(false);
	};
	const isThereFramework = (framework: string): boolean => {
		for (const tab of objectLayoutTabs) {
			if (tab.objectLayoutBoxes.some((box) => box.type === framework)) {
				return true;
			}
		}

		return false;
	};

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					disabled={disabled}
					displayType="unstyled"
					symbol="ellipsis-v"
				/>
			}
		>
			<ClayDropDown.ItemList>
				{addCategorization && (
					<ClayDropDown.Item
						disabled={
							isThereFramework('categorization') ||
							(Liferay.FeatureFlags['LPS-158672'] &&
								!enabledCategorization)
						}
						onClick={() => handleOnClick(addCategorization)}
					>
						{Liferay.Language.get('add-categorization')}
					</ClayDropDown.Item>
				)}

				<ClayDropDown.Item
					disabled={isViewOnly}
					onClick={() => handleOnClick(deleteElement)}
				>
					{Liferay.Language.get('delete')}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
