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

import React, {useContext} from 'react';

import {ControlsIdConverterContext} from '../ControlsIdConverterContext';
import TopperEmpty from '../TopperEmpty';

const CollectionItem = React.forwardRef(({children, item, layoutData}, ref) => {
	const {collectionItem} = useContext(ControlsIdConverterContext);

	if (React.Children.count(children) === 0) {
		return (
			<TopperEmpty item={item} layoutData={layoutData}>
				{() => (
					<div className="page-editor__collection-item" ref={ref}>
						<p className="page-editor__collection-item__title">
							{collectionItem.title}
						</p>
					</div>
				)}
			</TopperEmpty>
		);
	}

	return children;
});

export default CollectionItem;
