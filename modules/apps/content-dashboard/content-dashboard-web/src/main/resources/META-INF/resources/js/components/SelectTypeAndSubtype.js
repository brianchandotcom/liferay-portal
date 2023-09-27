/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import TreeFilter from './TreeFilter/TreeFilter';
import {nodeTreeArrayMapper} from './TreeFilter/treeUtils';

const SelectTypeAndSubtype = ({
	contentDashboardItemTypes,
	itemSelectorSaveEvent,
	portletNamespace,
}) => {
	return (
		<TreeFilter
			childrenPropertyKey="itemSubtypes"
			itemSelectorSaveEvent={itemSelectorSaveEvent}
			mandatoryFieldsForFiltering={[
				'className',
				'classPK',
				'entryClassName',
			]}
			namePropertyKey="label"
			nodes={nodeTreeArrayMapper({
				childrenPropertyKey: 'itemSubtypes',
				namePropertyKey: 'label',
				nodeArray: contentDashboardItemTypes,
			})}
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
