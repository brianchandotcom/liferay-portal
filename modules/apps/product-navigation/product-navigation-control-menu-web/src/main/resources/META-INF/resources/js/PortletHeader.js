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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

const PortletHeader = ({apps, category, title}) => {
	return (
		<>
			<ClayDropDownWithItems
				items={apps}
				trigger={
					<div>
						<span className="small">{category}</span>
						<div>
							<span
								className="control-menu-level-1-heading inline-item inline-item-before text-truncate"
								data-qa-id="headerTitle"
							>
								{title}
							</span>

							<ClayIcon symbol="caret-double-l" />
						</div>
					</div>
				}
			/>
		</>
	);
};

export default PortletHeader;
