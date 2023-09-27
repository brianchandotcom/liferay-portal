/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Node} from './TreeviewContext';

export default function TreeviewLabel({node}: IProps) {
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

interface IProps {
	node: Node;
}
