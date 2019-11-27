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

import React from 'react';

import Filter from '../../../shared/components/filter/Filter.es';
import {useFilterName} from '../../../shared/components/filter/hooks/useFilterName.es';
import {useFilterResource} from '../../../shared/components/filter/hooks/useFilterResource.es';

const RoleFilter = ({
	completed = false,
	dispatch,
	filterKey = 'roleIds',
	options: {
		hideControl = false,
		multiple = true,
		position = 'left',
		withSelectionTitle = false
	} = {},
	processId
}) => {
	const {items, selectedItems} = useFilterResource(
		dispatch,
		filterKey,
		`/processes/${processId}/roles?completed=${completed}`
	);

	const filterName = useFilterName(
		multiple,
		selectedItems,
		Liferay.Language.get('role'),
		withSelectionTitle
	);

	return (
		<Filter
			filterKey={filterKey}
			hideControl={hideControl}
			items={items}
			multiple={multiple}
			name={filterName}
			position={position}
		/>
	);
};

export default RoleFilter;
