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

/**
 * Map the node array prop in an understandable format for the Treeview component
 * @param {array} nodeArray - Array of nodes.
 * @return {array} A new array of nodes.
 */
const nodeTreeArrayMapper = (nodeArray) => {
	return nodeArray.map((node, index) => {
		const hasChildren = !!node.itemSubtypes?.length;

		const _getNodeId = ({index, node}) =>
			hasChildren ? `_${index}` : `${node.className}_${node.classPK}`;

		return {
			...node,
			children: hasChildren
				? nodeTreeArrayMapper(node.itemSubtypes)
				: null,
			expanded: !!(!index && hasChildren) || false,
			id: _getNodeId({index, node}),
			name: hasChildren ? handleNodeName({node}) : node.label,
		};
	});
};

const visit = (nodes, callback) => {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
};

/**
 * Filters the array of nodes using a string
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.nodes - Array of nodes.
 * @param {string} object.query - string used in search input.
 * @return {array} A new array of nodes.
 */
const filterNodes = ({nodes, query}) => {
	const nodeHasChildren = (node) => Array.isArray(node) && node.length > 0;

	return nodes.filter((node) => {
		if (nodeHasChildren(node.children)) {
			node.children = [
				...filterNodes({
					nodes: node.children,
					query,
				}),
			];
			node.expanded = true;
			node.name = handleNodeName({node});
		}

		const nodeMatchesInQuery = node.name.toLowerCase().includes(query);

		return (
			nodeMatchesInQuery ||
			(!nodeMatchesInQuery && nodeHasChildren(node.children))
		);
	});
};

/**
 * Filter parent nodes with no children inside currentData
 * @param {object} object
 * @param {Map} object.currentData The data selected returned by the Tree component
 * @param {array} object.parentNodes Array of parent items included in currentData
 * @return {array} Parent selected nodes with no children
 */
const getParentsWithNoChildren = ({currentData, parentNodes = []}) => {
	if (!parentNodes.length) {
		return [];
	}

	const currentPkClasses = currentData.map((node) => node.classPK);

	return parentNodes.filter((node) =>
		node.children.every(
			(child) => !currentPkClasses.includes(child.classPK)
		)
	);
};

/**
 * Adds to the node name the total count of children, if any
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.node Tree node object.
 * @return {string} The node name with the total count.
 */
const handleNodeName = ({node}) => {
	const children = node.children || node.itemSubtypes;
	let count = children.length;

	if (!count) {
		count = node.itemSubtypes.length;
	}

	const langKey =
		count > 1
			? Liferay.Language.get('subtypes')
			: Liferay.Language.get('subtype');

	return `${node.label} (${count} ${langKey})`;
};

const SelectTypeAndSubtype = ({
	contentDashboardItemTypes,
	itemSelectorSaveEvent,
	portletNamespace,
}) => {
	const nodes = nodeTreeArrayMapper(contentDashboardItemTypes);

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

	const computedNodes = useMemo(() => {
		if (!filterQuery) {
			return nodes;
		}

		const filterQueryLowerCase = filterQuery.toLowerCase();
		const clonedNodes = JSON.parse(JSON.stringify(nodes));

		return filterNodes({nodes: clonedNodes, query: filterQueryLowerCase});
	}, [filterQuery, nodes]);

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
					data.push({
						className: node.className,
						classPK: node.classPK,
					});
				}

				// case => the user is filtering looking for a type, removing all the children

				else if (selectedNodes.has(node.id) && isParentNode) {
					parentsCandidates.push(node);
				}
			});

			const parentsSelectedWithNoChildren =
				getParentsWithNoChildren({
					currentData: data,
					parentNodes: parentsCandidates,
				}) || [];

			if (parentsSelectedWithNoChildren.length) {
				parentsSelectedWithNoChildren.forEach((node) => {
					node.children.forEach((child) => {
						data.push({
							className: child.className,
							classPK: child.classPK,
						});
					});
				});
			}

			// end case

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
		<div className="select-type-and-subtype">
			<form
				className="pb-3 pt-3 select-type-and-subtype-filter"
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
					className="select-type-and-subtype-count-feedback"
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

			<form name={`${portletNamespace}selectSelectTypeAndSubtypeFm`}>
				<ClayLayout.ContainerFluid
					className="select-type-and-subtype-tree"
					containerElement="fieldset"
				>
					<div
						className="type-tree"
						id={`${portletNamespace}typeContainer`}
					>
						{nodes.length > 0 ? (
							<Treeview
								NodeComponent={Treeview.Card}
								inheritSelection={true}
								initialSelectedNodeIds={initialSelectedNodeIds}
								multiSelection={true}
								nodes={computedNodes}
								onSelectedNodesChange={handleSelectionChange}
							/>
						) : (
							<div className="border-0 pt-0 sheet taglib-empty-result-message">
								<div className="taglib-empty-result-message-header"></div>
								<div className="sheet-text text-center">
									{Liferay.Language.get(
										'no-types-were-found'
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

SelectTypeAndSubtype.propTypes = {
	contentDashboardItemTypes: PropTypes.array.isRequired,
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default SelectTypeAndSubtype;
