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

const useFilterName = (multiple, selectedItems, title, withSelectionTitle) => {
	const filterName = useMemo(() => {
		if (
			!multiple &&
			withSelectionTitle &&
			selectedItems &&
			selectedItems.length
		) {
			const {name, resultName} = selectedItems[0];

			return resultName || name;
		}

		return title;
	}, [multiple, selectedItems, title, withSelectionTitle]);

	return filterName;
};

export {useFilterName};
