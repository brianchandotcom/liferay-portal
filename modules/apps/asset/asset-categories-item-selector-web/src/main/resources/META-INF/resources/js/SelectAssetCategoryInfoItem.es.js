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
import React, {useState} from 'react';

import {AssetCategoryTree} from './AssetCategoryTree.es';

function SelectAssetCategory({
	itemSelectedEventName,
	multiSelection,
	namespace,
	nodes,
}) {
	const [items, setItems] = useState(() => {
		if (nodes.length === 1 && nodes[0].vocabulary && nodes[0].id !== '0') {
			return nodes[0].children;
		}

		return nodes;
	});

	const [filterQuery, setFilterQuery] = useState('');
	const [selectedItemsCount, setSelectedItemsCount] = useState(0);

	return (
		<div className="select-category">
			<form
				className="mb-0 select-category-filter"
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

			{selectedItemsCount ? (
				<ClayLayout.Container
					className="category-tree-count-feedback"
					containerElement="section"
					fluid
				>
					<div className="container p-0">
						<p className="m-0">
							{selectedItemsCount > 1
								? `${selectedItemsCount} ${Liferay.Language.get(
										'items-selected'
								  )}`
								: `${selectedItemsCount} ${Liferay.Language.get(
										'item-selected'
								  )}`}
						</p>
					</div>
				</ClayLayout.Container>
			) : null}

			<form name={`${namespace}selectCategoryFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="category-tree mt-3"
						id={`${namespace}categoryContainer`}
					>
						{items.length ? (
							<AssetCategoryTree
								filterQuery={filterQuery}
								itemSelectedEventName={itemSelectedEventName}
								items={items}
								multiSelection={multiSelection}
								onItems={setItems}
								onSelectedItemsCount={setSelectedItemsCount}
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
