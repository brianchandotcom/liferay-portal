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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {Treeview} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useRef, useState} from 'react';

import {SelectTree} from './SelectTree.es';

function visit(nodes, callback) {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

function getFilter(filterQuery) {
	if (!filterQuery) {
		return null;
	}

	const filterQueryLowerCase = filterQuery.toLowerCase();

	return (node) =>
		!node.vocabulary &&
		node.name.toLowerCase().indexOf(filterQueryLowerCase) !== -1;
}

function OldSelectTree({
	filterQuery,
	itemSelectorSaveEvent,
	items,
	multiSelection,
}) {
	const selectedNodesRef = useRef(null);

	const handleSelectionChange = (selectedNodes) => {
		const data = {};

		// Mark newly selected nodes as selected.

		visit(items, (node) => {
			if (selectedNodes.has(node.id)) {
				data[node.id] = {
					categoryId: node.vocabulary ? 0 : node.id,
					nodePath: node.nodePath,
					value: node.name,
					vocabularyId: node.vocabulary ? node.id : 0,
				};
			}
		});

		// Mark unselected nodes as unchecked.

		if (selectedNodesRef.current) {
			Object.entries(selectedNodesRef.current).forEach(([id, node]) => {
				if (!selectedNodes.has(id)) {
					data[id] = {
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

	const initialSelectedNodeIds = useMemo(() => {
		const selectedNodes = [];

		visit(items, (node) => {
			if (node.selected) {
				selectedNodes.push(node.id);
			}
		});

		return selectedNodes;
	}, [items]);

	return (
		<Treeview
			NodeComponent={Treeview.Card}
			filter={getFilter(filterQuery)}
			initialSelectedNodeIds={initialSelectedNodeIds}
			multiSelection={multiSelection}
			nodes={items}
			onSelectedNodesChange={handleSelectionChange}
		/>
	);
}

const Tree = Liferay.__FF__.enableClayTreeView ? SelectTree : OldSelectTree;

function SelectCategory({
	addCategoryURL,
	itemSelectorSaveEvent,
	moveCategory,
	multiSelection,
	namespace,
	nodes,
	selectedCategoryIds,
}) {
	const [items, setItems] = useState(() => {
		if (nodes.length === 1 && nodes[0].vocabulary && nodes[0].id !== '0') {
			return nodes[0].children;
		}

		return nodes;
	});

	const [filterQuery, setFilterQuery] = useState('');

	return (
		<div className="select-category">
			{moveCategory && (
				<ClayAlert displayType="info" variant="embedded">
					{Liferay.Language.get(
						'categories-can-only-be-moved-to-a-vocabulary-or-a-category-with-the-same-visibility'
					)}
				</ClayAlert>
			)}

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

					{addCategoryURL && (
						<ClayButton
							className="btn-monospaced ml-3 nav-btn nav-btn-monospaced"
							displayType="primary"
							onClick={() => {
								navigate(addCategoryURL);
							}}
						>
							<ClayIcon symbol="plus" />
						</ClayButton>
					)}
				</ClayLayout.ContainerFluid>
			</form>

			<form name={`${namespace}selectCategoryFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="category-tree"
						id={`${namespace}categoryContainer`}
					>
						{items.length > 0 ? (
							<Tree
								filterQuery={filterQuery}
								itemSelectorSaveEvent={itemSelectorSaveEvent}
								items={items}
								multiSelection={multiSelection}
								onItems={setItems}
								selectedCategoryIds={selectedCategoryIds}
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

SelectCategory.propTypes = {
	addCategoryURL: PropTypes.string,
	moveCategory: PropTypes.bool,
};

export default SelectCategory;
