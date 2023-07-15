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

export const eventCollateralsValidation = {
	eventCollaterals: array()
		.of(
			mixed()
				.test(
					'fileSize',
					validateDocument.fileSize.message,
					(eventCollateralsFile) => {
						if (
							eventCollateralsFile &&
							!eventCollateralsFile.documentId
						) {
							return (
								Math.ceil(eventCollateralsFile.size / 1000) <=
								validateDocument.fileSize.maxSize
							);
						}

						return true;
					}
				)
				.test(
					'fileType',
					validateDocument.document.message,
					(eventCollateralsFile) => {
						if (
							eventCollateralsFile &&
							!eventCollateralsFile.documentId
						) {
							return validateDocument.document.types.includes(
								eventCollateralsFile.type
							);
						}

						return true;
					}
				)
		)
		.min(1)
		.required('Required'),
};
