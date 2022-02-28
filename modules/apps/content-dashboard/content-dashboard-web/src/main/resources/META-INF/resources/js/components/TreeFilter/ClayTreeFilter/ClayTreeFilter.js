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

import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {cancelDebounce, debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {
	filterNodes,
	getSelectedNodeObject,
	handleNodeName,
	selectedDataOutputTransfomer,
	visit,
} from './clayTreeUtils';

const SEARCH_QUERY_MIN_LENGHT = 2;
const SEARCH_INPUT_DEBOUNCE = 300;

const TreeFilter = ({
	childrenPropertyKey,
	itemSelectorSaveEvent,
	mandatoryFieldsForFiltering,
	namePropertyKey,
	nodes,
	portletNamespace,
}) => {
	const [treeItems, setTreeItems] = useState(nodes);
	const initialItemsRef = useRef(treeItems);

	const [filterQuery, setFilterQuery] = useState('');
	const [selectedItemsCount, setSelectedItemsCount] = useState(0);

	const expanded = treeItems.length ? [treeItems[0].id] : [];

	const [expandedKeys, setExpandedKeys] = useState(new Set(expanded));

	const selectedNodesRef = useRef(null);
	const selectedNodesRefCount = selectedNodesRef.current?.length || 0;

	const searchInputElementRef = useRef(null);

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

	const [selectedKeys, setSelectedKeys] = useState(
		new Set(initialSelectedNodeIds)
	);

	const handleSelectionChange = useCallback(
		(selectedNodes) => {
			const data = [];

			// Mark newly selected nodes as selected.

			visit(nodes, (node) => {
				const isChildNode = !node.children;

				if (selectedNodes.has(node.id) && isChildNode) {
					data.push(
						getSelectedNodeObject({
							mandatoryFieldsForFiltering,
							node,
						})
					);
				}
			});

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

			openerWindow.Liferay.fire(itemSelectorSaveEvent, {
				data: selectedDataOutputTransfomer({
					data,
					mandatoryFieldsForFiltering,
				}),
			});

			setSelectedItemsCount(data.length);
			setSelectedKeys(selectedNodes);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedNodesRefCount]
	);

	const debouncedSetFilterQuery = debounce((event) => {
		setFilterQuery(event.target.value);
	}, SEARCH_INPUT_DEBOUNCE);

	const inputSearchHandler = (event) => {
		event.persist();

		debouncedSetFilterQuery(event);
	};

	const handleInputClear = () => {
		setFilterQuery('');
		searchInputElementRef.current.value = '';
	};

	const handleTreeItemsChange = useCallback(() => {
		if (!filterQuery || filterQuery.length < SEARCH_QUERY_MIN_LENGHT) {
			setTreeItems(initialItemsRef.current);

			return;
		}

		setTreeItems(
			filterNodes({
				childrenPropertyKey,
				namePropertyKey,
				nodes: JSON.parse(JSON.stringify(nodes)),
				query: filterQuery.toLowerCase(),
			})
		);
	}, [childrenPropertyKey, filterQuery, namePropertyKey, nodes]);

	useEffect(() => {
		handleSelectionChange(selectedKeys);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		return () => {
			cancelDebounce(debouncedSetFilterQuery);
		};
	}, [debouncedSetFilterQuery]);

	useEffect(() => {
		handleTreeItemsChange();
	}, [filterQuery, handleTreeItemsChange]);

	useEffect(() => {
		setExpandedKeys(new Set([treeItems[0]?.id]));
	}, [treeItems]);

	return (
		<div className="tree-filter">
			<form
				className="pb-3 pt-3 tree-filter-search"
				onSubmit={(event) => event.preventDefault()}
				role="search"
			>
				<ClayLayout.ContainerFluid className="d-flex">
					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control h-100 input-group-inset input-group-inset-after"
								onChange={inputSearchHandler}
								placeholder={Liferay.Language.get('search')}
								ref={searchInputElementRef}
								type="text"
							/>

							<div className="input-group-inset-item input-group-inset-item-after pr-3">
								<ClayIcon
									className={classNames({
										'tree-filter-clear': filterQuery,
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

			{!!selectedItemsCount && (
				<ClayLayout.Container
					className="tree-filter-count-feedback"
					containerElement="section"
					fluid
				>
					<div className="container p-0">
						<p className="m-0 text-2">
							{selectedItemsCount + ' '}

							{selectedItemsCount > 1
								? Liferay.Language.get('items-selected')
								: Liferay.Language.get('item-selected')}
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
						{treeItems.length ? (
							<ClayTreeView
								expandedKeys={expandedKeys}
								items={treeItems}
								onExpandedChange={setExpandedKeys}
								onItemsChange={handleTreeItemsChange}
								onSelectionChange={handleSelectionChange}
								selectedKeys={selectedKeys}
								selectionMode="multiple-recursive"
								showExpanderOnHover={false}
							>
								{(item) => (
									<ClayTreeView.Item>
										<ClayTreeView.ItemStack>
											<ClayCheckbox />

											<ClayIcon symbol={item.icon} />

											{handleNodeName({
												childrenPropertyKey,
												node: item,
											})}
										</ClayTreeView.ItemStack>

										<ClayTreeView.Group
											items={item.children}
										>
											{(item) => (
												<ClayTreeView.Item>
													<ClayCheckbox />

													{item.name}
												</ClayTreeView.Item>
											)}
										</ClayTreeView.Group>
									</ClayTreeView.Item>
								)}
							</ClayTreeView>
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
	nodes: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default TreeFilter;
