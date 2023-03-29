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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';

export function AddItemDropDown({
	className,
	trigger,
	order = -1,
	parentSiteNavigationMenuItemId = 0,
}) {
	const [active, setActive] = useState(false);
	const {addSiteNavigationMenuItemOptions} = useConstants();

	return (
		<>
			<ClayDropDown
				active={active}
				className={className}
				menuElementAttrs={{
					containerProps: {
						className: 'menu-item-dropdown',
					},
				}}
				onActiveChange={setActive}
				trigger={trigger}
			>
				<ClayDropDown.ItemList>
					{addSiteNavigationMenuItemOptions.map(
						({label, onClick}) => (
							<ClayDropDown.Item
								key={label}
								onClick={() =>
									onClick({
										order,
										parentSiteNavigationMenuItemId,
									})
								}
							>
								{label}
							</ClayDropDown.Item>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

AddItemDropDown.propTypes = {
	trigger: PropTypes.element,
};
