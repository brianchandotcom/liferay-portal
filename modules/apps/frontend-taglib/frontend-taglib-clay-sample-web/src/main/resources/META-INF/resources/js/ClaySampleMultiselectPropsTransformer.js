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
import ClaySticker from '@clayui/sticker';
import React from 'react';

const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`;

const MenuCustom = ({
	inputValue,
	locator,
	onItemClick = () => {},
	sourceItems,
}) => {
	return (
		<ClayDropDown.ItemList>
			{sourceItems
				.filter(
					(item) =>
						inputValue && item[locator.label].match(inputValue)
				)
				.map((item) => (
					<ClayDropDown.Item
						key={item[locator.value]}
						onClick={() => onItemClick(item)}
					>
						<div className="autofit-row autofit-row-center">
							<div className="autofit-col mr-3">
								<ClaySticker
									className="sticker-user-icon"
									size="lg"
								>
									<ClayIcon
										spritemap={spritemap}
										symbol="user"
									/>
								</ClaySticker>
							</div>
							<div className="autofit-col">
								<strong>{item[locator.label]}</strong>
								<span>{item.email}</span>
							</div>
						</div>
					</ClayDropDown.Item>
				))}
		</ClayDropDown.ItemList>
	);
};

export default function propsTransformer(props) {
	return {
		...props,
		menuRenderer: MenuCustom,
	};
}
