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

export default function TreeviewLabel({node}) {
	const inputId = `${node.id}-treeview-label-input`;

	return (
		<div className="lfr-treeview-label">
			<input
				checked={node.selected}
				className="sr-only"
				id={inputId}
				onChange={() => {

					// Let NodeListItem handle checked state.

				}}
				type="checkbox"
			/>

			<label
				className={
					node.selected ? 'font-weight-bold' : 'font-weight-normal'
				}
				htmlFor={inputId}
				onClick={(event) => event.preventDefault()}
			>
				{node.name}
			</label>
		</div>
	);
}
