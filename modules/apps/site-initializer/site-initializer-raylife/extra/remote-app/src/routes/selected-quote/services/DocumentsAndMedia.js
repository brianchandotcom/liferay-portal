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
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {Liferay} from '../../../common/utils/liferay';

const DeliveryAPI = 'o/headless-delivery';

const documentFolderData = {
	description: '>...',
	parentDocumentFolderId: 0,
	viewableBy: 'Anyone',
};

export async function createDocumentInFolder(folderId, fileEntry, callback) {
	const formData = new FormData();

	formData.append('file', fileEntry);

	return axios.post(
		`${DeliveryAPI}/v1.0/document-folders/${folderId}/documents`,
		formData,
		{
			onUploadProgress: (event) => {
				const progress = Math.round((event.loaded * 100) / event.total);

				callback(progress);
			},
		}
	);
}

export function removeDocumentById(documentId) {
	return axios.delete(`${DeliveryAPI}/v1.0/documents/${documentId}/`);
}

const getDocumentFolders = (siteId, filter = '', parentDocumentFolder) => {
	return axios.get(
		parentDocumentFolder
			? `${DeliveryAPI}/v1.0/document-folders/${siteId}/document-folders/${filter}`
			: `${DeliveryAPI}/v1.0/sites/${siteId}/document-folders/${filter}`
	);
};

export function createDocumentFolder(
	siteId,
	data,
	parentDocumentFolder = false
) {
	return axios.post(
		parentDocumentFolder
			? `${DeliveryAPI}/v1.0/document-folders/${siteId}/document-folders`
			: `${DeliveryAPI}/v1.0/sites/${siteId}/document-folders/`,
		data
	);
}

export async function createFolderIfNotExist(siteId, folderName, parentFolder) {
	let folder;

	const folderExist = await getDocumentFolders(
		siteId,
		`?filter=contains(name, '${folderName}')`,
		parentFolder
	);

	if (folderExist.data.totalCount) {
		folder = folderExist.data.items[0];
	}
	else {
		const response = await createDocumentFolder(
			siteId,
			{
				...documentFolderData,
				name: folderName,
			},
			parentFolder
		);

		folder = response.data;
	}

	return folder;
}

export async function createRootFolders(applicationsFolderName) {
	const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

	const rootFolder = await createFolderIfNotExist(
		Liferay.ThemeDisplay.getScopeGroupId(),
		applicationsFolderName
	);
	const quoteFolder = await createFolderIfNotExist(
		rootFolder.id,
		applicationId,
		true
	);

	return quoteFolder;
}
