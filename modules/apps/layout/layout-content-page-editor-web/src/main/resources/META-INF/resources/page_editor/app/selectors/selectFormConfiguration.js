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

import {FORM_MAPPING_SOURCES} from '../config/constants/formMappingSources';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';

export default function selectFormConfiguration(item, layoutData) {
	if (!item) {
		return {};
	}

	const findFormConfiguration = (childItem) => {
		const parentItem = layoutData.items[childItem?.parentId];

		if (!parentItem) {
			return {};
		}

		if (parentItem.type === LAYOUT_DATA_ITEM_TYPES.form) {
			const classNameId = parentItem.config?.classNameId;
			const mappingSource = parentItem.config?.formConfig;

			if (classNameId && classNameId !== '0') {
				return {...parentItem.config, formId: parentItem.itemId};
			}
			else if (
				config.layoutType === LAYOUT_TYPES.display &&
				(!mappingSource ||
					mappingSource === FORM_MAPPING_SOURCES.displayPage)
			) {
				const {selectedMappingTypes} = config;

				return {
					classNameId: selectedMappingTypes?.type.id,
					classTypeId: selectedMappingTypes?.subtype.id,
					formId: parentItem.itemId,
				};
			}
			else {
				return {};
			}
		}

		return findFormConfiguration(parentItem);
	};

	return findFormConfiguration(layoutData.items[item.itemId]);
}
