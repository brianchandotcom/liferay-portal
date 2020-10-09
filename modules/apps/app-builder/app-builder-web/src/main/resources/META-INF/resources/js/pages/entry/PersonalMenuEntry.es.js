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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import React from 'react';

export default function PersonalMenuEntry({
	size = 'lg',
	appUserPortraitURL,
	items = [],
}) {
	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'dropdown-menu-personal-menu'}}
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('personal-menu')}
					displayType="unstyled"
				>
					<span className={`sticker sticker-${size}`}>
						<ClaySticker
							displayType="light"
							shape="circle"
							size={size}
						>
							{appUserPortraitURL ? (
								<img
									className="sticker-img"
									src={appUserPortraitURL}
								/>
							) : (
								<ClayIcon symbol="user" />
							)}
						</ClaySticker>
					</span>
				</ClayButton>
			}
		/>
	);
}
