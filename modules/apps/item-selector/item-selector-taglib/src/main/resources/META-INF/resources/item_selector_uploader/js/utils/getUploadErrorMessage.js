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

export default function getUploadErrorMessage(error, maxFileSize) {
	let message = Liferay.Language.get(
		'an-unexpected-error-occurred-while-uploading-your-file'
	);

	if (error && error.errorType) {
		const errorType = error.errorType;

		switch (errorType) {
			case Liferay.STATUS_CODE.SC_FILE_ANTIVIRUS_EXCEPTION:
				if (error.message) {
					message = error.message;
				}

				break;
			case Liferay.STATUS_CODE.SC_FILE_EXTENSION_EXCEPTION:
				if (error.message) {
					message = Liferay.Util.sub(
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
			case Liferay.STATUS_CODE.SC_FILE_NAME_EXCEPTION:
				message = Liferay.Language.get(
					'please-enter-a-file-with-a-valid-file-name'
				);

				break;
			case Liferay.STATUS_CODE.SC_FILE_SIZE_EXCEPTION:
			case Liferay.STATUS_CODE.SC_UPLOAD_REQUEST_CONTENT_LENGTH_EXCEPTION:
				message = Liferay.Util.sub(
					Liferay.Language.get(
						'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
					),
					[Liferay.Util.formatStorage(maxFileSize)]
				);

				break;
			case Liferay.STATUS_CODE.SC_UPLOAD_REQUEST_SIZE_EXCEPTION: {
				const maxUploadRequestSize =
					Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

				message = Liferay.Util.sub(
					Liferay.Language.get(
						'request-is-larger-than-x-and-could-not-be-processed'
					),
					[Liferay.Util.formatStorage(maxUploadRequestSize)]
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
