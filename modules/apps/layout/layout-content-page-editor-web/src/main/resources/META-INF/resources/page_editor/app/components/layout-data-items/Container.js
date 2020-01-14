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

import FloatingToolbar from '../../components/FloatingToolbar';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import Topper from '../Topper';

const Container = React.forwardRef(({children, item, layoutData}, ref) => {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingBottom,
		paddingHorizontal,
		paddingTop,
		type
	} = item.config;

	return (
		<Topper
			acceptDrop={[
				LAYOUT_DATA_ITEM_TYPES.fragment,
				LAYOUT_DATA_ITEM_TYPES.row
			]}
			active
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('container')}
		>
			{() => (
				<div
					className={classNames(
						`page-editor__container pb-${paddingBottom} pt-${paddingTop}`,
						{
							[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
							container: type === 'fixed',
							'container-fluid': type === 'fluid',
							empty: !item.children.length,
							[`px-${paddingHorizontal}`]: paddingHorizontal !== 3
						}
					)}
					ref={ref}
					style={
						backgroundImage
							? {
									backgroundImage: `url(${backgroundImage})`,
									backgroundPosition: '50% 50%',
									backgroundRepeat: 'no-repeat',
									backgroundSize: 'cover'
							  }
							: {}
					}
				>
					<FloatingToolbar
						buttons={[
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
						]}
						item={item}
						itemRef={ref}
					/>

					<div className="page-editor__container-outline">
						{children}
					</div>
				</div>
			)}
		</Topper>
	);
});

export default Container;
