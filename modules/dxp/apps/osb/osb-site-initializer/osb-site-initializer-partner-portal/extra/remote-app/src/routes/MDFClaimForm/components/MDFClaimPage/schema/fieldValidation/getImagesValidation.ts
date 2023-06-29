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

import {array, object} from 'yup';

import {ValidateDocument} from '../constants/validateDocument';

const getImagesValidation = () => {
	const basicImagesValidation = {
		images: array().of(
			object()
				.test(
					'fileSize',
					ValidateDocument.fileSize.message,
					(imageFile) => {
						if (imageFile && !imageFile.id) {
							return imageFile
								? Math.ceil(imageFile.size / 1000) <=
										ValidateDocument.fileSize.maxSize
								: false;
						}

						return true;
					}
				)
				.test(
					'fileType',
					ValidateDocument.imageDocument.message,
					(imageFile) => {
						if (imageFile && !imageFile.id) {
							return imageFile
								? ValidateDocument.document.types.includes(
										imageFile.type
								  )
								: false;
						}

						return true;
					}
				)
		),
	};

	return basicImagesValidation;
};

export default getImagesValidation;
