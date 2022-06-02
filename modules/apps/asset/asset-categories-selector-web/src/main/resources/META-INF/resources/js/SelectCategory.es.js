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
import classNames from 'classnames';
import {navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {SelectTree} from './SelectTree.es';
function SelectCategory({
	addCategoryURL,
	inheritSelection,
	itemSelectorSaveEvent,
	moveCategory,
	multiSelection,
	namespace,
	nodes,
	selectedCategoryIds,
	showSelectedCounter,
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
			{moveCategory && (
				<ClayAlert displayType="info" variant="embedded">
					{Liferay.Language.get(
						'categories-can-only-be-moved-to-a-vocabulary-or-a-category-with-the-same-visibility'
					)}
				</ClayAlert>
			)}

			<form
				className={classNames('select-category-filter', {
					'select-category-filter--with-count-feedback': showSelectedCounter,
				})}
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

			{showSelectedCounter && !!selectedItemsCount && (
				<ClayLayout.Container
					className="mb-3 px-4 select-category-count-feedback"
					containerElement="section"
					fluid
				>
					<div className="align-items-center container-fluid d-flex justify-content-between p-0">
						<p className="m-0 text-2">
							{selectedItemsCount + ' '}

							{selectedItemsCount > 1
								? Liferay.Language.get('items-selected')
								: Liferay.Language.get('item-selected')}
						</p>
					</div>
				</ClayLayout.Container>
			)}

			<form name={`${namespace}selectCategoryFm`}>
				<ClayLayout.ContainerFluid containerElement="fieldset">
					<div
						className="category-tree"
						id={`${namespace}categoryContainer`}
					>
						{items.length > 0 ? (
							<SelectTree
								filterQuery={filterQuery}
								inheritSelection={inheritSelection}
								itemSelectorSaveEvent={itemSelectorSaveEvent}
								items={items}
								multiSelection={multiSelection}
								onItems={setItems}
								onSelectionChange={(selectedNodes) => {
									setSelectedItemsCount(selectedNodes.size);
								}}
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

SelectCategory.defaultProps = {
	showSelectedCounter: false,
};

SelectCategory.propTypes = {
	addCategoryURL: PropTypes.string,
	moveCategory: PropTypes.bool,
	showSelectedCounter: PropTypes.bool,
};

export default SelectCategory;
