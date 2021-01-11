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
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';

const CreationMenu = ({primaryItems, secondaryItems}) => {
	const [active, setActive] = useState(false);

	return (
		<>
			{primaryItems.length > 1 || secondaryItems ? (
				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={
						<ClayButtonWithIcon
							className="nav-btn nav-btn-monospaced"
							symbol="plus"
						/>
					}
				>
					<ClayDropDown.ItemList>
						{primaryItems?.map((item, index) => (
							<ClayDropDown.Item href={item.href} key={index}>
								{item.label}
							</ClayDropDown.Item>
						))}

						{secondaryItems?.map((secondaryItemsGroup, index) => (
							<ClayDropDown.Group
								header={secondaryItemsGroup.label}
								key={index}
							>
								{secondaryItemsGroup.items.map(
									(item, index) => (
										<ClayDropDown.Item
											href={item.href}
											key={index}
										>
											{item.label}
										</ClayDropDown.Item>
									)
								)}
							</ClayDropDown.Group>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			) : (
				<ClayLink
					button={true}
					className="nav-btn nav-btn-monospaced"
					displayType="primary"
					href={primaryItems[0].href}
				>
					<ClayIcon symbol="plus" />
				</ClayLink>
			)}
		</>
	);
};

export default CreationMenu;
