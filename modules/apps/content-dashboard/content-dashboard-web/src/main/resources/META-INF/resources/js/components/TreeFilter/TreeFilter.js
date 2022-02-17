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
import {cancelDebounce, debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {
	filterNodes,
	getSelectedNodeObject,
	selectedDataOutputTransfomer,
	visit,
} from './treeUtils';

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
	const [filterQuery, setFilterQuery] = useState('');
	const [selectedItemsCount, setSelectedItemsCount] = useState(0);

	const selectedNodesRef = useRef(null);
	const refItemsCount = selectedNodesRef.current?.length || 0;

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

	const computedNodes = () => {
		if (!filterQuery || filterQuery.length < SEARCH_QUERY_MIN_LENGHT) {
			return nodes;
		}

		const filterQueryLowerCase = filterQuery.toLowerCase();
		const clonedNodes = JSON.parse(JSON.stringify(nodes));

		return filterNodes({
			childrenPropertyKey,
			namePropertyKey,
			nodes: clonedNodes,
			query: filterQueryLowerCase,
		});
	};

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
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[refItemsCount]
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

	useEffect(() => {
		return () => {
			cancelDebounce(debouncedSetFilterQuery);
		};
	}, [debouncedSetFilterQuery]);

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
						<Treeview
							NodeComponent={Treeview.Card}
							inheritSelection
							initialSelectedNodeIds={initialSelectedNodeIds}
							multiSelection
							nodes={computedNodes()}
							onSelectedNodesChange={handleSelectionChange}
						/>

						{!computedNodes().length && (
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
