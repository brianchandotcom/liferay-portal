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

const getEventPhotosValidation = () => {
	const basicEventPhotosValidation = {
		eventPhotos: array()
			.of(
				object()
					.test(
						'fileSize',
						ValidateDocument.fileSize.message,
						(eventPhotoFile) => {
							if (eventPhotoFile && !eventPhotoFile.id) {
								return eventPhotoFile
									? Math.ceil(eventPhotoFile.size / 1000) <=
											ValidateDocument.fileSize.maxSize
									: false;
							}

							return true;
						}
					)
					.test(
						'fileType',
						ValidateDocument.document.message,
						(eventPhotoFile) => {
							if (eventPhotoFile && !eventPhotoFile.id) {
								return eventPhotoFile
									? ValidateDocument.document.types.includes(
											eventPhotoFile.type
									  )
									: false;
							}

							return true;
						}
					)
			)
			.min(1)
			.required('Required'),
	};

	return basicEventPhotosValidation;
};

export default getEventPhotosValidation;
