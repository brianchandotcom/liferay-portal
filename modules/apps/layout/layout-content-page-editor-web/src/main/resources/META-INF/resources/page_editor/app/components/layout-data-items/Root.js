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

import classNames from 'classnames';
import React from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import TopperEmpty from '../TopperEmpty';

const Root = React.forwardRef(({children, item, layoutData}, ref) => {
	return (
		<TopperEmpty item={item} layoutData={layoutData}>
			<div className={classNames('page-editor__root')} ref={ref}>
				{React.Children.count(children) ? (
					children
				) : (
					<div
						className={classNames(
							'page-editor__no-fragments-message'
						)}
					>
						<div className="page-editor__no-fragments-message__title">
							{Liferay.Language.get('place-fragments-here')}
						</div>
					</div>
				)}
			</div>
		</TopperEmpty>
	);
});

Root.displayName = 'Root';

Root.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default Root;
