/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ENTITY_PATH = '/specifications';

const VERSION = 'v1.0';

function resolvePath(basePath = '', id = '') {
	return `${basePath}${VERSION}${ENTITY_PATH}/${id}`;
}
export default function Specification(basePath) {
	return {
		createSpecificationListTypeDefinition: (
			specificationId,
			listTypeDefinitionId
		) =>
			AJAX.POST(
				`${resolvePath(basePath, specificationId)}/list-type-definitions/${listTypeDefinitionId}`
			),
		createSpecificationListTypeDefinitionById: (specificationId, json) =>
			AJAX.POST(
				`${resolvePath(basePath, specificationId)}/list-type-definitions`,
				json
			),
		deleteSpecificationListTypeDefinition: (
			specificationId,
			listTypeDefinitionId
		) =>
			AJAX.DELETE(
				`${resolvePath(basePath, specificationId)}/list-type-definitions/${listTypeDefinitionId}`
			),
		getSpecificationListTypeDefinitionsById: (specificationId, json) =>
			AJAX.GET(
				`${resolvePath(basePath, specificationId)}/list-type-definitions`,
				json
			),
		getSpecifications: (search = '') =>
			AJAX.GET(`${resolvePath(basePath)}?search=${search}`),
		updateSpecificationById: (specificationId, json) =>
			AJAX.PATCH(`${resolvePath(basePath, specificationId)}`, json),
	};
}
