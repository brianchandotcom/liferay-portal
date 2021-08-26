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

import PropTypes from 'prop-types';
import React from 'react';

import TreeFilter from './components/TreeFilter';
import {nodeTreeArrayMapper} from './utils/tree-utils';

const SelectTypeAndSubtype = ({
	contentDashboardItemTypes,
	itemSelectorSaveEvent,
	portletNamespace,
}) => {
	const nodes = nodeTreeArrayMapper({
		childrenPropertyKey: 'itemSubtypes',
		namePropertyKey: 'label',
		nodeArray: contentDashboardItemTypes,
	});

	return (
		<TreeFilter
			childrenPropertyKey="itemSubtypes"
			itemSelectorSaveEvent={itemSelectorSaveEvent}
			mandatoryFieldsForFiltering={['className', 'classPK']}
			namePropertyKey="label"
			nodes={nodes}
			portletNamespace={portletNamespace}
		/>
	);
};

SelectTypeAndSubtype.propTypes = {
	contentDashboardItemTypes: PropTypes.array.isRequired,
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default SelectTypeAndSubtype;
