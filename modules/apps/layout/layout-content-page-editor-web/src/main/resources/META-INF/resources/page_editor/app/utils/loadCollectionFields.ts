/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addMappingFields} from '../actions';
import CollectionService from '../services/CollectionService';

export default function loadCollectionFields(
	dispatch: (action: ReturnType<typeof addMappingFields>) => void,
	fieldName: string,
	itemType: string,
	itemSubtype: string,
	mappingFieldsKey: string
) {
	CollectionService.getCollectionMappingFields({
		fieldName: fieldName || '',
		itemSubtype: itemSubtype || '',
		itemType,
	})
		.then((response) => {
			dispatch(
				addMappingFields({
					fields: response.mappingFields,
					key: mappingFieldsKey,
				})
			);
		})
		.catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
}
