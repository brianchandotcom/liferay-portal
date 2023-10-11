/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FORM_MAPPING_SOURCES} from '../config/constants/formMappingSources';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';

import type {
	LayoutData,
	LayoutDataItem,
} from '../../types/layout_data/LayoutData';

export default function selectFormConfiguration(
	item: LayoutDataItem,
	layoutData: LayoutData
) {
	if (!item) {
		return {};
	}

	const findFormConfiguration: (childItem: LayoutDataItem) => void = (
		childItem
	) => {
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
