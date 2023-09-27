/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
