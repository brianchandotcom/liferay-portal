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

import ClayList from '@clayui/list';
import {openConfirmModal, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {saveVariationsListPriorityService} from '../../api/index';
import SortableListItem from './SortableListItem';
import {buildItemsPriorityURL} from './utils/index';

function openConfirm({message, onConfirm}) {
	if (Liferay.FeatureFlags['LPS-148659']) {
		openConfirmModal({message, onConfirm});
	}
	else if (confirm(message)) {
		onConfirm(true);
	}
}

const savePriority = async ({url}) => {
	try {
		const {ok, status} = await saveVariationsListPriorityService({url});

		if (!ok || status !== 200) {
			throw new Error();
		}

		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'success',
		});
	}
	catch (error) {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			type: 'danger',
		});
	}
};

const SortableList = ({items, namespace, savePriorityURL}) => {
	const [listItems, setListItems] = useState(items);

	const handleItemMove = ({
		direction = 0,
		hoverIndex = null,
		index,
		saveAfterMove = false,
	}) => {
		const start = hoverIndex ?? index + direction;
		const tempList = [...listItems];

		tempList.splice(index, 1);

		tempList.splice(start, 0, listItems[index]);

		setListItems(tempList);

		if (saveAfterMove) {
			handleSavePriority(tempList);
		}
	};

	const handleSavePriority = (items = listItems) => {
		savePriority({
			url: buildItemsPriorityURL({
				items,
				namespace,
				url: savePriorityURL,
			}),
		});
	};

	const handleItemDelete = ({deleteURL}) => {
		if (!deleteURL) {
			return;
		}

		openConfirm({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, deleteURL);
				}
			},
		});
	};

	return (
		<DndProvider backend={HTML5Backend}>
			<nav role="navigation">
				<ClayList className="mt-4" role="list">
					{listItems.map((item, index) => (
						<SortableListItem
							handleItemDelete={handleItemDelete}
							handleItemMove={handleItemMove}
							handleSavePriority={handleSavePriority}
							id={`sortableListItem-id-${item.assetListEntrySegmentsEntryRelId}`}
							index={index}
							key={item.editAssetListEntryURL}
							role="listitem"
							sortableListItem={item}
							totalItems={listItems.length}
						/>
					))}
				</ClayList>
			</nav>
		</DndProvider>
	);
};

SortableList.propTypes = {
	items: PropTypes.array.isRequired,
	namespace: PropTypes.string.isRequired,
	savePriorityURL: PropTypes.string.isRequired,
};

export default SortableList;
