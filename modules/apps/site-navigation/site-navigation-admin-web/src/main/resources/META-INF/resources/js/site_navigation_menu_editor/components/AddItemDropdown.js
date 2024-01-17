/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
