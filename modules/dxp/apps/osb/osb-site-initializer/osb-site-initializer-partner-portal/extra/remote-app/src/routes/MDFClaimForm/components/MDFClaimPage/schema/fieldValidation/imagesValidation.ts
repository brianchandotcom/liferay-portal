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

import {array, mixed} from 'yup';

import {validateDocument} from '../constants/validateDocument';

export const imagesValidation = {
	images: array().of(
		mixed()
			.test(
				'fileSize',
				validateDocument.fileSize.message,
				(imageFile) => {
					if (imageFile && !imageFile.documentId) {
						return (
							Math.ceil(imageFile.size / 1000) <=
							validateDocument.fileSize.maxSize
						);
					}

					return true;
				}
			)
			.test(
				'fileType',
				validateDocument.imageDocument.message,
				(imageFile) => {
					if (imageFile && !imageFile.documentId) {
						return validateDocument.document.types.includes(
							imageFile.type
						);
					}

					return true;
				}
			)
	),
};
