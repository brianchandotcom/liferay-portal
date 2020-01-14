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

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

export const ContainerT = React.forwardRef(
	(
		{
			backgroundColorCssClass,
			backgroundImage,
			children,
			className,
			paddingBottom,
			paddingHorizontal,
			paddingTop,
			type,
			...props
		},
		ref
	) => {
		return (
			<div
				className={classNames(
					className,
					`pb-${paddingBottom} pt-${paddingTop} px-${paddingHorizontal}`,
					{
						[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
						container: type === 'fixed',
						'container-fluid': type === 'fluid'
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
				{...props}
			>
				{children}
			</div>
		);
	}
);

export const RowT = React.forwardRef(
	({children, className, item, layoutData, ...props}, ref) => {
		const parent = layoutData.items[item.parentId];

		const rowContent = (
			<div
				className={classNames(className, 'row', {
					empty: !item.children.some(
						childId => layoutData.items[childId].children.length
					),
					'no-gutters': !item.config.gutters
				})}
				ref={ref}
				{...props}
			>
				{children}
			</div>
		);

		return (
			<>
				{!parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
					<div className="container-fluid p-0">{rowContent}</div>
				) : (
					rowContent
				)}
			</>
		);
	}
);

export const ColumnT = React.forwardRef(
	({children, className, item, ...props}, ref) => {
		const {size} = item.config;

		return (
			<div
				className={classNames(className, 'col', {
					[`col-${size}`]: size
				})}
				ref={ref}
				{...props}
			>
				{children}
			</div>
		);
	}
);
