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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

const ListItem = React.forwardRef(({disabled, item}, ref) => {
	return (
		<li
			className={classNames(
				'page-editor__fragments-widgets__tab-list-item',
				{
					disabled,
					'page-editor__fragments-widgets__tab-portlet-item':
						item.data.portletItemId,
				}
			)}
			ref={ref}
		>
			<div className="page-editor__fragments-widgets__tab-list-item-body">
				<ClayIcon className="mr-3" symbol={item.icon} />
				<div className="text-truncate title">{item.label}</div>
			</div>
		</li>
	);
});

export default ListItem;
