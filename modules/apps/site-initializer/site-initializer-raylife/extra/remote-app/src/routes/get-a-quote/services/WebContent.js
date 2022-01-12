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

import {axios} from '../../../common/services/liferay/api';

const headlessAPI = 'o/headless-delivery/v1.0';

/**
 * @description Get all Web Content Templates in this Site
 * @param {Number} siteGroupId
 */
export function getContentTemplates(siteGroupId, filter = '') {
	return axios.get(
		`${headlessAPI}/sites/${siteGroupId}/content-templates${filter}`
	);
}

export function getRenderedContent(structuredContentId, contentTemplateId) {
	return axios.get(
		`${headlessAPI}/structured-contents/${structuredContentId}/rendered-content/${contentTemplateId}`
	);
}

/**
 * @description Get all Web Contents Structure from Raylife folder
 * @param {Number} folderId
 */

export function getStructuredContents(folderId, filter = '') {
	return axios.get(
		`${headlessAPI}/structured-content-folders/${folderId}/structured-contents${filter}`
	);
}

/**
 * @description  Get all Folders from Web Content and get his ID
 * @param {Number} siteGroupId
 */
export function getStructuredContentFolders(siteGroupId, filter = '') {
	return axios.get(
		`${headlessAPI}/sites/${siteGroupId}/structured-content-folders${filter}`
	);
}
