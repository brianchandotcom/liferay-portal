/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const KB_TO_MB = 1024;
const MAX_MB = KB_TO_MB * 3;

export const validateDocument = {
	document: {
		message:
			'Unsupported File Format, upload a valid format *jpg *jpeg *png *tif *tiff *pdf *doc *docx',
		types: [
			'image/jpg',
			'image/jpeg',
			'image/png',
			'image/tif',
			'image/tiff',
			'application/pdf',
			'application/msword',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
		],
	},
	fileSize: {
		maxSize: MAX_MB,
		message: 'File Size is too large',
	},
	imageDocument: {
		message:
			'Unsupported File Format, upload a valid format *jpg *jpeg *png *tif *tiff *pdf',
		types: [
			'image/jpg',
			'image/jpeg',
			'image/png',
			'image/tif',
			'image/tiff',
			'application/pdf',
		],
	},
	listOfLeadsDocuments: {
		message:
			'Unsupported File Format, upload a valid format *csv *xlsx *xls',
		types: [
			'text/csv',
			'application/vnd.ms-excel',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
		],
	},
};
