/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STATUS_CODE, formatStorage, sub} from 'frontend-js-web';

export default function getUploadErrorMessage(error, maxFileSize) {
	let message = Liferay.Language.get(
		'an-unexpected-error-occurred-while-uploading-your-file'
	);

	if (error && error.errorType) {
		const errorType = error.errorType;

		switch (errorType) {
			case STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION:
				if (error.message) {
					message = error.message;
				}

				break;
			case STATUS_CODE.SC_FILE_EXTENSION_EXCEPTION:
				if (error.message) {
					message = sub(
						Liferay.Language.get(
							'please-enter-a-file-with-a-valid-extension-x'
						),
						[error.message]
					);
				}
				else {
					message = Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-type'
					);
				}

				break;
			case STATUS_CODE.SC_FILE_NAME_EXCEPTION:
				message = Liferay.Language.get(
					'please-enter-a-file-with-a-valid-file-name'
				);

				break;
			case STATUS_CODE.SC_FILE_SIZE_EXCEPTION:
			case STATUS_CODE.SC_UPLOAD_REQUEST_CONTENT_LENGTH_EXCEPTION:
				message = sub(
					Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
					),
					[formatStorage(maxFileSize)]
				);

				break;
			case STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION: {
				const maxUploadRequestSize =
					Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

				message = sub(
					Liferay.Language.get(
						'request-is-larger-than-x-and-could-not-be-processed'
					),
					[formatStorage(maxUploadRequestSize)]
				);

				break;
			}
			default: {
				message = Liferay.Language.get(
					'an-unexpected-error-occurred-while-uploading-your-file'
				);
			}
		}
	}

	return message;
}
