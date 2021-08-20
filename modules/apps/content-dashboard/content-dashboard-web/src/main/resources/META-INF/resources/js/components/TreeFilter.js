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
import PropTypes from 'prop-types';
import React, {useMemo, useRef, useState} from 'react';

const visit = (nodes, callback) => {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
};

const getFilter = (filterQuery) => {
	if (!filterQuery) {
		return null;
	}

	const filterQueryLowerCase = filterQuery.toLowerCase();

	return (node) =>
		node.name.toLowerCase().indexOf(filterQueryLowerCase) !== -1;
};

const TreeFilter = ({
	itemSelectorSaveEvent,
	necessaryKeys,
	nodes,
	portletNamespace,
}) => {
	const [filterQuery, setFilterQuery] = useState('');

	const selectedNodesRef = useRef(null);

	const initialSelectedNodeIds = useMemo(() => {
		const selectedNodes = [];

		visit(nodes, (node) => {
			if (node.selected) {
				selectedNodes.push(node.id);
			}
		});

		return selectedNodes;
	}, [nodes]);

	const handleSelectionChange = (selectedNodes) => {
		const data = [];

		// Mark newly selected nodes as selected.

		visit(nodes, (node) => {
			const isChildNode = !node.children;

			if (selectedNodes.has(node.id) && isChildNode) {
				let newSelectedNode = {};

				if (necessaryKeys.length === 1) {
					newSelectedNode = node[necessaryKeys[0]];
				}
				else {
					necessaryKeys.forEach((key) => {
						newSelectedNode[key] = node[key];
					});
				}
				data.push(newSelectedNode);
			}
		});

		// Mark unselected nodes as unchecked.

		if (selectedNodesRef.current) {
			Object.entries(selectedNodesRef.current).forEach(([id, node]) => {
				const nodeIndex = data.findIndex((node) => node.id === id);

				if (!selectedNodes.has(id) && nodeIndex > -1) {
					data[nodeIndex] = {
						...node,
						unchecked: true,
					};
				}
			});
		}

		selectedNodesRef.current = data;

		const openerWindow = Liferay.Util.getOpener();

		openerWindow.Liferay.fire(itemSelectorSaveEvent, {data});
	};

	return (
		<div className="tree-filter">
			<form
				className="mb-4 pb-3 pt-3 tree-filter-search"
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

			<form name={`${portletNamespace}selectFilterFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="type-tree"
						id={`${portletNamespace}typeContainer`}
					>
						{nodes.length > 0 ? (
							<Treeview
								NodeComponent={Treeview.Card}
								filter={getFilter(filterQuery)}
								inheritSelection={true}
								initialSelectedNodeIds={initialSelectedNodeIds}
								multiSelection={true}
								nodes={nodes}
								onSelectedNodesChange={handleSelectionChange}
							/>
						) : (
							<div className="border-0 pt-0 sheet taglib-empty-result-message">
								<div className="taglib-empty-result-message-header"></div>
								<div className="sheet-text text-center">
									{Liferay.Language.get(
										'no-results-were-found'
									)}
								</div>
							</div>
						)}
					</div>
				</ClayLayout.ContainerFluid>
			</form>
		</div>
	);
};

TreeFilter.propTypes = {
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	necessaryKeys: PropTypes.array.isRequired,
	nodes: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default TreeFilter;
