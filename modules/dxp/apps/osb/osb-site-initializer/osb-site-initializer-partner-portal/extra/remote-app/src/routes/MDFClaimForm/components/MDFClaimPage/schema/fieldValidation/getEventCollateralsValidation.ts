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

const getEventCollateralsValidation = () => {
	const basicEventCollateralsValidation = {
		eventCollaterals: array()
			.of(
				object()
					.test(
						'fileSize',
						ValidateDocument.fileSize.message,
						(eventCollateralsFile) => {
							if (
								eventCollateralsFile &&
								!eventCollateralsFile.id
							) {
								return eventCollateralsFile
									? Math.ceil(
											eventCollateralsFile.size / 1000
									  ) <= ValidateDocument.fileSize.maxSize
									: false;
							}

							return true;
						}
					)
					.test(
						'fileType',
						ValidateDocument.document.message,
						(eventCollateralsFile) => {
							if (
								eventCollateralsFile &&
								!eventCollateralsFile.id
							) {
								return eventCollateralsFile
									? ValidateDocument.document.types.includes(
											eventCollateralsFile.type
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

	return basicEventCollateralsValidation;
};

export default getEventCollateralsValidation;
