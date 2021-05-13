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
import ClayLayout from '@clayui/layout';
import {Treeview} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

function getFilter(filterQuery) {
	if (!filterQuery) {
		return null;
	}

	const filterQueryLowerCase = filterQuery.toLowerCase();

	return (node) =>
		!node.vocabulary &&
		node.name.toLowerCase().indexOf(filterQueryLowerCase) !== -1;
}

function visit(nodes, callback) {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

function SelectAssetCategory({itemSelectedEventName, namespace, nodes}) {
	const flattenedNodes = useMemo(() => {
		if (nodes.length === 1 && nodes[0].vocabulary && nodes[0].id !== '0') {
			return nodes[0].children;
		}

		return nodes;
	}, [nodes]);

	const [filterQuery, setFilterQuery] = useState('');

	const handleSelectionChange = (selectedNodeIds) => {
		if (!selectedNodeIds.size) {
			return;
		}

		let data;

		visit(nodes, (node) => {
			if (selectedNodeIds.has(node.id)) {
				data = {
					className: node.className,
					classNameId: node.classNameId,
					classPK: node.id,
					title: node.name,
				};
			}
		});

		Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
			data,
		});
	};

	return (
		<div className="select-category">
			<form
				className="select-category-filter"
				onSubmit={(event) => event.preventDefault()}
				role="search"
			>
				<ClayLayout.ContainerFluid className="d-flex">
					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control h-100 input-group-inset input-group-inset-after"
								onChange={(event) =>
									setFilterQuery(event.target.value)
								}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<div className="input-group-inset-item input-group-inset-item-after pr-3">
								<ClayIcon symbol="search" />
							</div>
						</div>
					</div>
				</ClayLayout.ContainerFluid>
			</form>

			<form name={`${namespace}selectCategoryFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="category-tree"
						id={`${namespace}categoryContainer`}
					>
						{flattenedNodes.length > 0 ? (
							<Treeview
								NodeComponent={Treeview.Card}
								filter={getFilter(filterQuery)}
								multiSelection={false}
								nodes={flattenedNodes}
								onSelectedNodesChange={handleSelectionChange}
							/>
						) : (
							<div className="border-0 pt-0 sheet taglib-empty-result-message">
								<div className="taglib-empty-result-message-header"></div>
								<div className="sheet-text text-center">
									{Liferay.Language.get(
										'no-categories-were-found'
									)}
								</div>
							</div>
						)}
					</div>
				</ClayLayout.ContainerFluid>
			</form>
		</div>
	);
}

export default SelectAssetCategory;
