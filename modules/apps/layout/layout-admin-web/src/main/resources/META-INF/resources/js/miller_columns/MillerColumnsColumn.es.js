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

import React from 'react';

import MillerColumnsColumnItem from './MillerColumnsColumnItem.es';

const MillerColumnsColumn = ({index, items}) => (
	<ul className="col-11 col-lg-4 col-md-6 miller-columns-col show-quick-actions-on-hover">
		{items.map(item => (
			<MillerColumnsColumnItem
				actions={item.actions}
				active={item.active}
				checked={item.checked}
				description={item.description}
				draggable={index !== 0}
				hasChild={item.hasChild}
				itemId={item.id}
				key={item.url}
				selectable={index !== 0}
				states={item.states}
				title={item.title}
				url={item.url}
			/>
		))}
	</ul>
);

export default MillerColumnsColumn;
