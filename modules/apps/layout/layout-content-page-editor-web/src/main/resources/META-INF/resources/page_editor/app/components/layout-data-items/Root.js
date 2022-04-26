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

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {useSelectorCallback} from '../../contexts/StoreContext';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../topper/TopperEmpty';

const Root = React.forwardRef(({children, item}, ref) => {
	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	return (
		<TopperEmpty item={item}>
			<div className="page-editor__root" ref={ref}>
				{isEmpty ? (
					<div className="page-editor__no-fragments-message">
						<div className="page-editor__no-fragments-message__title">
							{Liferay.Language.get('place-fragments-here')}
						</div>
					</div>
				) : (
					children
				)}
			</div>
		</TopperEmpty>
	);
});

Root.displayName = 'Root';

Root.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default Root;
