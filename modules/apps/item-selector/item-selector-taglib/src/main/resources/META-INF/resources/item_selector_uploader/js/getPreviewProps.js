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
						value: Liferay.Util.formatStorage(file.size),
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
			Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
				data: {
					returnType: returntype,
					value,
				},
			});
		},
		headerTitle: closeCaption,
		items: [
			{
				metadata: JSON.stringify(getUploadFileMetadata(file)),
				returntype: uploadItemReturnType,
				title: itemFile.title,
				url: itemFileUrl,
				value: itemFileValue,
			},
		],
	};
}

export default getPreviewProps;
