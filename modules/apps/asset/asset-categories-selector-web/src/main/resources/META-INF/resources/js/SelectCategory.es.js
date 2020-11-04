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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

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

function SelectCategory({
	addCategoryURL,
	itemSelectorSaveEvent,
	moveCategory,
	multiSelection,
	namespace,
	nodes,
}) {
	const flattenedNodes = useMemo(() => {
		if (nodes.length === 1 && nodes[0].vocabulary && nodes[0].id !== '0') {
			return nodes[0].children;
		}

		return nodes;
	}, [nodes]);

	const [filterQuery, setFilterQuery] = useState('');

	const selectedNodesRef = useRef(null);

	const handleAddCategoryClick = useCallback(() => {
		const dialog = Liferay.Util.getWindow(itemSelectorSaveEvent);
		const footer = dialog.getToolbar('footer');

		footer.get('boundingBox').one('#addButton').hide();

		footer.get('boundingBox').one('#cancelButton').hide();

		Liferay.Util.navigate(addCategoryURL);
	}, [addCategoryURL, itemSelectorSaveEvent]);

	useEffect(() => {
		const dialog = Liferay.Util.getWindow(itemSelectorSaveEvent);
		const footer = dialog.getToolbar('footer');

		if (!dialog.get('initialTitle')) {
			dialog.set(
				'initialTitle',
				dialog.headerNode.one('.modal-title').text()
			);
		}

		footer.get('boundingBox').all('.add-category-toolbar-button').hide();

		footer.get('boundingBox').one('#addButton').show();

		footer.get('boundingBox').one('#cancelButton').show();

		if (
			dialog.get('initialTitle') !==
			dialog.headerNode.one('.modal-title').text()
		) {
			dialog.headerNode
				.one('.modal-title')
				.text(dialog.get('initialTitle'));
		}
	}, [itemSelectorSaveEvent]);

	const handleSelectionChange = (selectedNodes) => {
		const data = {};

		// Mark newly selected nodes as selected.

		visit(flattenedNodes, (node) => {
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

		Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {data});
	};

	const initialSelectedNodeIds = useMemo(() => {
		const selectedNodes = [];

		visit(flattenedNodes, (node) => {
			if (node.selected) {
				selectedNodes.push(node.id);
			}
		});

		return selectedNodes;
	}, [flattenedNodes]);

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
							onClick={handleAddCategoryClick}
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
						{flattenedNodes.length > 0 ? (
							<Treeview
								NodeComponent={Treeview.Card}
								filter={getFilter(filterQuery)}
								initialSelectedNodeIds={initialSelectedNodeIds}
								multiSelection={multiSelection}
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

SelectCategory.propTypes = {
	addCategoryURL: PropTypes.string.isRequired,
	moveCategory: PropTypes.bool,
};

export default SelectCategory;
