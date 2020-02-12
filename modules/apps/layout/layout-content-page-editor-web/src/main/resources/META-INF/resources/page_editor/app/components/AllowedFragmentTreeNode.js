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

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

function hasSomeChildrenSelected(node) {
	return node.children.some(
		children => children.selected || hasSomeChildrenSelected(children)
	);
}

export default function AllowedFragmentTreeNode({node}) {
	return (
		<div className="lfr-treeview-label">
			<ClayCheckbox
				aria-label={node.name}
				checked={node.selected}
				indeterminate={!node.selected && hasSomeChildrenSelected(node)}
				label={node.name}
				onChange={() => {}}
				onDoubleClick={event => {
					event.stopPropagation();
				}}
			/>
		</div>
	);
}
