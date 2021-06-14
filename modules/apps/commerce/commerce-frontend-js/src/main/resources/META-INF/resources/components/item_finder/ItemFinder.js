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

import {ClayIconSpriteContext} from '@clayui/icon';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {DATASET_ACTION_PERFORMED} from '../../utilities/eventsDefinitions';
import {fetchParams} from '../../utilities/index';
import {
	showErrorNotification,
	showNotification,
} from '../../utilities/notifications';
import AddOrCreate from './AddOrCreate';

function ItemFinder(props) {
	const [items, updateItems] = useState([]);
	const [pageSize, updatePageSize] = useState(props.pageSize);
	const [currentPage, updateCurrentPage] = useState(props.currentPage);
	const [textFilter, updateTextFilter] = useState('');
	const [itemsCount, updateItemsCount] = useState(props.itemsCount || 0);
	const [selectedItems, updateSelectedItems] = useState([]);

	useEffect(() => {
		if (!textFilter) {
			updateItems(null);
			updateItemsCount(0);

			return;
		}

		fetch(
			`${props.apiUrl}${
				props.apiUrl.includes('?') ? '&' : '?'
			}pageSize=${pageSize}&page=${currentPage}&search=${encodeURIComponent(
				textFilter
			)}`,
			{
				...fetchParams,
				method: 'GET',
			}
		)
			.then((data) => data.json())
			.then((jsonResponse) => {
				updateItems(jsonResponse.items);
				updateItemsCount(jsonResponse.totalCount);
			})
			.catch(showErrorNotification);
	}, [
		pageSize,
		currentPage,
		textFilter,
		updateItems,
		updateItemsCount,
		props.apiUrl,
	]);

	useEffect(() => {
		props
			.getSelectedItems()
			.then((selectedItems = []) => updateSelectedItems(selectedItems));

		function handleDatasetActions(event) {
			if (props.linkedDatasetsId.includes(event.id)) {
				props
					.getSelectedItems()
					.then((selectedItems = []) =>
						updateSelectedItems(selectedItems)
					);
			}
		}

		Liferay.on(DATASET_ACTION_PERFORMED, handleDatasetActions);

		return () =>
			Liferay.detach(DATASET_ACTION_PERFORMED, handleDatasetActions);
	}, [props, props.getSelectedItems]);

	function selectItem(itemId) {
		const selectedItem = items.find(
			(item) => item[props.itemsKey] === itemId
		);
		props
			.onItemSelected(selectedItem)
			.then(() => {
				if (props.multiSelectableEntries) {
					showNotification(props.itemSelectedMessage);
				}
				else {
					updateSelectedItems((i) => [...i, itemId]);
				}
			})
			.catch(showErrorNotification);
	}

	function createItem() {
		props
			.onItemCreated(textFilter)
			.then((id) => {
				updateTextFilter('');

				if (id) {
					updateSelectedItems((i) => [...i, id]);
				}
			})
			.catch(showErrorNotification);
	}

	return (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<AddOrCreate
				createNewItemLabel={props.createNewItemLabel}
				currentPage={currentPage}
				inputPlaceholder={props.inputPlaceholder}
				inputSearchValue={textFilter}
				itemCreation={props.itemCreation}
				items={items}
				itemsCount={itemsCount}
				itemsKey={props.itemsKey}
				onInputSearchChange={updateTextFilter}
				onItemCreated={createItem}
				onItemSelected={selectItem}
				pageSize={pageSize}
				panelHeaderLabel={props.panelHeaderLabel}
				schema={props.schema}
				searchInputValue={textFilter}
				selectedItems={selectedItems}
				spritemap={props.spritemap}
				titleLabel={props.titleLabel}
				updateCurrentPage={updateCurrentPage}
				updatePageSize={updatePageSize}
			/>
		</ClayIconSpriteContext.Provider>
	);
}

ItemFinder.propTypes = {
	apiUrl: PropTypes.string.isRequired,
	createNewItemLabel: PropTypes.string,
	getSelectedItems: PropTypes.func.isRequired,
	inputPlaceholder: PropTypes.string,
	itemCreation: PropTypes.bool,
	itemSelectedMessage: PropTypes.string,
	itemsKey: PropTypes.string.isRequired,
	linkedDatasetsId: PropTypes.arrayOf(PropTypes.string),
	multiSelectableEntries: PropTypes.bool,
	onItemCreated: PropTypes.func.isRequired,
	onItemSelected: PropTypes.func.isRequired,
	pageSize: PropTypes.number,
	panelHeaderLabel: PropTypes.string,
	schema: PropTypes.array.isRequired,
	titleLabel: PropTypes.string,
};

ItemFinder.defaultProps = {
	currentPage: 1,
	itemCreation: true,
	itemSelectedMessage: Liferay.Language.get('item-selected'),
	multiSelectableEntries: false,
	pageSize: 5,
	selectedItems: [],
};

export default ItemFinder;
