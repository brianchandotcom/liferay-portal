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
import classNames from 'classnames';
import {Treeview} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useCallback, useMemo, useRef, useState} from 'react';

import {
	filterNodes,
	restoreChildrenForEmptiedParent,
	visit,
} from '../utils/tree-utils';

/**
 * Filter parent nodes with no children inside currentSelection
 * @param {object} object
 * @param {Map} object.currentSelection The data selected returned by the Tree component
 * @param {array} object.parentNodes Array of parent items included in currentSelection
 * @return {array} Parent selected nodes with no children
 */
const getParentsWithNoChildren = ({
	currentSelection,
	mandatoryFieldsForFiltering,
	nodeIdProp,
	parentNodes = [],
}) => {
	if (!parentNodes.length) {
		return [];
	}

	const currentIdentifiers =
		mandatoryFieldsForFiltering.length === 1
			? currentSelection
			: currentSelection.map((node) => node[nodeIdProp]);

	return parentNodes.filter((node) =>
		node.children.every(
			(child) => !currentIdentifiers.includes(child[nodeIdProp])
		)
	);
};

const TreeFilter = ({
	childrenPropertyKey,
	itemSelectorSaveEvent,
	mandatoryFieldsForFiltering,
	namePropertyKey,
	nodeIdProp,
	nodes,
	portletNamespace,
}) => {
	const [filterQuery, setFilterQuery] = useState('');
	const [selectedMessage, setSelectedMessage] = useState({
		count: 0,
		show: false,
	});

	const selectedNodesRef = useRef(null);
	const refItemsCount = selectedNodesRef.current?.length || 0;

	const initialSelectedNodeIds = useMemo(() => {
		const selectedNodes = [];

		visit(nodes, (node) => {
			if (
				node.selected ||
				node.children?.every((childNode) => childNode.selected)
			) {
				selectedNodes.push(node.id);
			}
		});

		return selectedNodes;
	}, [nodes]);

	const computedNodes = () => {
		if (!filterQuery) {
			return nodes;
		}

		const filterQueryLowerCase = filterQuery.toLowerCase();
		const clonedNodes = JSON.parse(JSON.stringify(nodes));

		const filteredNodes = filterNodes({
			childrenPropertyKey,
			namePropertyKey,
			nodes: clonedNodes,
			query: filterQueryLowerCase,
		});

		return restoreChildrenForEmptiedParent({
			childrenPropertyKey,
			filteredNodes,
			namePropertyKey,
		});
	};

	const handleSelectionChange = useCallback(
		(selectedNodes) => {
			const data = [];
			const parentsCandidates = [];

			// Mark newly selected nodes as selected.

			visit(nodes, (node) => {
				const isChildNode = !node.children;
				const isParentNode =
					Array.isArray(node.children) && node.children.length > 0;

				if (selectedNodes.has(node.id) && isChildNode) {
					let newSelectedNode = {};

					if (mandatoryFieldsForFiltering.length === 1) {
						newSelectedNode = node[mandatoryFieldsForFiltering[0]];
					}
					else {
						mandatoryFieldsForFiltering.forEach((key) => {
							newSelectedNode[key] = node[key];
						});
					}
					data.push(newSelectedNode);
				}
				else if (selectedNodes.has(node.id) && isParentNode) {
					parentsCandidates.push(node);
				}
			});

			const parentsSelectedWithNoChildren =
				getParentsWithNoChildren({
					currentSelection: data,
					mandatoryFieldsForFiltering,
					nodeIdProp,
					parentNodes: parentsCandidates,
				}) || [];

			if (parentsSelectedWithNoChildren.length) {
				parentsSelectedWithNoChildren.forEach((node) => {
					node.children.forEach((child) => {
						let newSelectedNode = {};

						if (mandatoryFieldsForFiltering.length === 1) {
							newSelectedNode =
								child[mandatoryFieldsForFiltering[0]];
						}
						else {
							mandatoryFieldsForFiltering.forEach((key) => {
								newSelectedNode[key] = child[key];
							});
						}
						data.push(newSelectedNode);
					});
				});
			}

			// Mark unselected nodes as unchecked.

			if (selectedNodesRef.current) {
				Object.entries(selectedNodesRef.current).forEach(
					([id, node]) => {
						const nodeIndex = data.findIndex(
							(node) => node.id === id
						);

						if (!selectedNodes.has(id) && nodeIndex > -1) {
							data[nodeIndex] = {
								...node,
								unchecked: true,
							};
						}
					}
				);
			}

			selectedNodesRef.current = data;

			const openerWindow = Liferay.Util.getOpener();

			openerWindow.Liferay.fire(itemSelectorSaveEvent, {data});

			setSelectedMessage({
				count: data.length,
				show: true,
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[refItemsCount]
	);

	const showSelectionFeedback =
		selectedMessage.show && !!selectedMessage.count;

	const handleInputClear = () => {
		setFilterQuery('');
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
								value={filterQuery}
							/>

							<div className="input-group-inset-item input-group-inset-item-after pr-3">
								<ClayIcon
									className={classNames({
										'select-type-and-subtype-clear': filterQuery,
									})}
									onClick={
										filterQuery ? handleInputClear : null
									}
									symbol={filterQuery ? 'times' : 'search'}
								/>
							</div>
						</div>
					</div>
				</ClayLayout.ContainerFluid>
			</form>

			{showSelectionFeedback && (
				<ClayLayout.Container
					className="tree-filter-count-feedback"
					containerElement="section"
					fluid
				>
					<div className="container p-0">
						<p className="m-0">
							{selectedMessage.count} Subtypes Selected
						</p>
					</div>
				</ClayLayout.Container>
			)}

			<form name={`${portletNamespace}selectFilterFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="tree-filter-type-tree"
						id={`${portletNamespace}typeContainer`}
					>
						{computedNodes().length > 0 ? (
							<Treeview
								NodeComponent={Treeview.Card}
								inheritSelection
								initialSelectedNodeIds={initialSelectedNodeIds}
								multiSelection
								nodes={computedNodes()}
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
	childrenPropertyKey: PropTypes.string.isRequired,
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	mandatoryFieldsForFiltering: PropTypes.array.isRequired,
	namePropertyKey: PropTypes.string.isRequired,
	nodeIdProp: PropTypes.string.isRequired,
	nodes: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default TreeFilter;
