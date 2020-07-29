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
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

import {actions} from '../utils/showSidebar';

const HoverItems = ({items, namespace}) => {
	return (
		<div className="quick-action-menu">
			{items.map(({data, href, icon, quickAction}) => (
				<>
					{data?.action && quickAction ? (
						<ClayButtonWithIcon
							className="component-action quick-action-item"
							displayType="unstyled"
							onClick={() => {
								actions[data.action](data.fetchURL, namespace);
							}}
							small={true}
							symbol={icon}
						/>
					) : (
						href &&
						icon &&
						quickAction && (
							<ClayLink
								className="component-action quick-action-item"
								href={href}
							>
								<ClayIcon symbol={icon} />
							</ClayLink>
						)
					)}
				</>
			))}
		</div>
	);
};

export default HoverItems;
