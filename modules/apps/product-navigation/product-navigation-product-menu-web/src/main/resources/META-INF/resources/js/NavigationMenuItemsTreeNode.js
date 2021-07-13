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

export default function NavigationMenuItemsTreeNode({node}) {
	const Parent = ({children}) =>
		node.url ? (
			<a
				className="d-block h-100 w-100"
				href={node.url}
				onMouseDownCapture={() => {
					Liferay.Util.navigate(node.url);
				}}
			>
				{children}
			</a>
		) : (
			<p className="m-0">{children}</p>
		);

	return (
		<div
			className={classNames('navigation-menu-items-tree-node', {
				selected: node.selected,
			})}
		>
			<Parent>
				<ClayIcon
					className="mr-2"
					symbol={node.url ? 'page' : 'folder'}
				/>
				{node.name}
			</Parent>
		</div>
	);
}
