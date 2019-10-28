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

import 'metal';

import 'metal-component';
import ClayIcon from '@clayui/icon';
import Treeview from 'frontend-taglib/treeview/Treeview.es';
import React, {useState, useEffect, useCallback} from 'react';

const filterNodes = (nodes, filterValue) => {
	const filteredNodes = [];

	nodes.forEach(node => {
		if (node.name.toLowerCase().indexOf(filterValue) !== -1) {
			filteredNodes.push({...node, children: []});
		}

		if (node.children) {
			filteredNodes.push(...filterNodes(node.children, filterValue));
		}
	});

	return filteredNodes;
};

function SelectCategory({multiSelection, namespace, nodes}) {
	const [filteredNodes, setFilteredNodes] = useState(nodes);

	useEffect(() => {
		setFilteredNodes(nodes);
	}, [nodes]);

	const handleOnChange = useCallback(
		event => {
			const searchValue = event.target.value.toLowerCase();

			if (searchValue) {
				setFilteredNodes(filterNodes(nodes, searchValue));
			} else {
				setFilteredNodes(nodes);
			}
		},
		[nodes]
	);

	return (
		<div className="select-category">
			<form className="select-category-filter" role="search">
				<div className="container-fluid-1280">
					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control input-group-inset input-group-inset-after"
								onChange={handleOnChange}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<div className="input-group-inset-item input-group-inset-item-after pr-3">
								<ClayIcon symbol="search" />
							</div>
						</div>
					</div>
				</div>
			</form>

			<form name={`${namespace}selectCategoryFm`}>
				<fieldset className="container-fluid-1280">
					<div
						className="category-tree"
						id={`${namespace}categoryContainer`}
					>
						<Treeview
							NodeComponent={Treeview.Card}
							multiSelection={multiSelection}
							nodes={filteredNodes}
						/>
					</div>
				</fieldset>
			</form>
		</div>
	);
}

export default function(props) {
	return <SelectCategory {...props} />;
}
