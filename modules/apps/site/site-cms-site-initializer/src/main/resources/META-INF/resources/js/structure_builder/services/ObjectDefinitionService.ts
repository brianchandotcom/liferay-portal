/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../common/services/ApiHelper';
import {ObjectDefinition, ObjectDefinitions} from '../types/ObjectDefinition';

async function getObjectDefinitions(): Promise<ObjectDefinitions> {
	const filter =
		"(objectFolderExternalReferenceCode eq 'L_CMS_CONTENT_STRUCTURES') or (objectFolderExternalReferenceCode eq 'L_CMS_FILE_TYPES')";

	const {data, error} = await ApiHelper.get<{items: ObjectDefinition[]}>(
		`/o/object-admin/v1.0/object-definitions?filter=${filter}`
	);

	if (data) {
		const objectDefinitions = new Map();

		for (const objectDefinition of data.items) {
			if (objectDefinition.status?.code !== 0) {
				continue;
			}

			objectDefinitions.set(
				objectDefinition.externalReferenceCode,
				objectDefinition
			);
		}

		return objectDefinitions;
	}

	throw new Error(error);
}

export default {
	getObjectDefinitions,
};
