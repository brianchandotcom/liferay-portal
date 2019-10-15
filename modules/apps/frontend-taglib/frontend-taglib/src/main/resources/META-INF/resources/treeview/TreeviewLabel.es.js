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
import classNames from 'classnames';

export default function TreeviewLabel({
	node,
	selectedNodesIds,
	onNodeSelected
}) {
	const isSelected = selectedNodesIds.includes(node.id);

	const labelClassName = classNames(
		isSelected ? 'font-weight-bold' : 'font-weight-normal'
	);

	const inputId = `${node.id}-input`;

	return (
		<div className="lfr-treeview-label">
			<input
				checked={isSelected}
				className="sr-only"
				id={inputId}
				onChange={() => onNodeSelected(node.id)}
				type="checkbox"
			/>

			<label className={labelClassName} htmlFor={inputId}>
				{node.name}
			</label>
		</div>
	);
}
