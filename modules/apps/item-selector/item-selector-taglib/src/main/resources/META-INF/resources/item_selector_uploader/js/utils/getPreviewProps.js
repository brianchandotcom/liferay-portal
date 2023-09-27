/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatStorage, getOpener} from 'frontend-js-web';

function getUploadFileMetadata(file) {
	return {
		groups: [
			{
				data: [
					{
						key: Liferay.Language.get('format'),
						value: file.type,
					},
					{
						key: Liferay.Language.get('size'),
						value: formatStorage(file.size),
					},
					{
						key: Liferay.Language.get('name'),
						value: file.name,
					},
				],
				title: Liferay.Language.get('file-info'),
			},
		],
	};
}

function getPreviewProps({
	closeCaption,
	file,
	itemData,
	itemSelectedEventName,
	uploadItemReturnType,
}) {
	const itemFile = itemData.file;
	const itemFileUrl = itemFile.url;
	let itemFileValue = itemFile.resolvedValue;

	if (!itemFileValue) {
		const imageValue = {
			fileEntryId: itemFile.fileEntryId,
			groupId: itemFile.groupId,
			title: itemFile.title,
			type: itemFile.type,
			url: itemFileUrl,
			uuid: itemFile.uuid,
		};

		itemFileValue = JSON.stringify(imageValue);
	}

	return {
		currentIndex: 0,
		handleSelectedItem: ({returntype, value}) => {
			getOpener().Liferay.fire(itemSelectedEventName, {
				data: {
					returnType: returntype,
					value,
				},
			});
		},
		headerTitle: closeCaption,
		itemReturnType: uploadItemReturnType,
		items: [
			{
				fileEntryId: itemFile.fileEntryId,
				metadata: JSON.stringify(getUploadFileMetadata(file)),
				mimeType: itemFile.mimeType,
				returntype: uploadItemReturnType,
				title: itemFile.title,
				url: itemFileUrl,
				value: itemFileValue,
			},
		],
	};
}

export default getPreviewProps;
