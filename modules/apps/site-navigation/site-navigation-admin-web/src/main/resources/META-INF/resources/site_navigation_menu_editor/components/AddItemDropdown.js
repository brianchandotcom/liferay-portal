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

export const AddItemDropDown = ({trigger}) => {
	const [active, setActive] = useState(false);
	const {addSiteNavigationMenuItemOptions, portletNamespace} = useConstants();

	return (
		<>
			<ClayDropDown
				active={active}
				className="mr-3"
				onActiveChange={setActive}
				trigger={trigger}
			>
				<ClayDropDown.ItemList>
					{addSiteNavigationMenuItemOptions.map(({data, label}) => (
						<ClayDropDown.Item
							key={label}
							onClick={() => {
								Liferay.Util.openWindow({
									dialog: {
										destroyOnHide: true,
									},
									id: `${portletNamespace}addMenuItem`,
									title: label,
									uri: data.href,
								});
							}}
						>
							{label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
};

AddItemDropDown.propTypes = {
	trigger: PropTypes.element,
};
