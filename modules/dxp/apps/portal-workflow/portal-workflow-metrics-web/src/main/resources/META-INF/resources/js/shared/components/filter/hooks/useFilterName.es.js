/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useMemo} from 'react';

const useFilterName = (
	multiple,
	selectedItems = [],
	title,
	withSelectionTitle
) =>
	useFilterNameWithLabel({
		multiple,
		selectedItems,
		title,
		withSelectionTitle,
	});

const useFilterNameWithLabel = ({
	labelPropertyName = 'name',
	multiple,
	selectedItems = [],
	title,
	withSelectionTitle,
}) => {
	return useMemo(() => {
		if (!multiple && withSelectionTitle && selectedItems.length) {
			const [{resultName, [labelPropertyName]: label}] = selectedItems;

			return resultName || label;
		}

		return title;
	}, [labelPropertyName, multiple, selectedItems, title, withSelectionTitle]);
};

export {useFilterName, useFilterNameWithLabel};
