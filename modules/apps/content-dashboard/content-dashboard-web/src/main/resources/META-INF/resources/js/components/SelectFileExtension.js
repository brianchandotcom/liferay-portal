/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import TreeFilter from './TreeFilter/TreeFilter';
import {nodeTreeArrayMapper} from './TreeFilter/treeUtils';

const SelectFileExtension = ({
	fileExtensionGroups,
	itemSelectorSaveEvent,
	portletNamespace,
}) => {
	return (
		<TreeFilter
			childrenPropertyKey="fileExtensions"
			itemSelectorSaveEvent={itemSelectorSaveEvent}
			mandatoryFieldsForFiltering={['id']}
			namePropertyKey="fileExtension"
			nodes={nodeTreeArrayMapper({
				childrenPropertyKey: 'fileExtensions',
				namePropertyKey: 'fileExtension',
				nodeArray: fileExtensionGroups,
			})}
			portletNamespace={portletNamespace}
		/>
	);
};

SelectFileExtension.propTypes = {
	fileExtensionGroups: PropTypes.array.isRequired,
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default SelectFileExtension;
