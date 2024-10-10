/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {getSpritemap} from '@liferay/frontend-icons-web';
import React from 'react';

const spritemap = getSpritemap();

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
