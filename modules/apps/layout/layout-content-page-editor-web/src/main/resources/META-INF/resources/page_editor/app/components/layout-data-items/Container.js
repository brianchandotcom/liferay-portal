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

const Container = React.forwardRef(({children, className, item}, ref) => {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingBottom,
		paddingHorizontal,
		paddingTop,
		type
	} = item.config;

	return (
		<div
			className={classNames(
				className,
				`pb-${paddingBottom} pt-${paddingTop}`,
				{
					[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
					container: type === 'fixed',
					'container-fluid': type === 'fluid',
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
			{children}
		</div>
	);
});

export default Container;
