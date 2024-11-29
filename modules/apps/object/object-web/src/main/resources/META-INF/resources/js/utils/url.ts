/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';

export async function getEditObjectRelationshipURL(
	baseResourceURL: string,
	objectDefinitionId1: number
) {
	const resourceURL = createResourceURL(baseResourceURL, {
		objectDefinitionId: objectDefinitionId1,
		p_p_resource_id:
			'/object_definitions/get_view_object_relationships_url',
	}).href;

	const {url} = await API.fetchJSON<{
		url: string;
	}>(resourceURL);

	return url;
}
