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

import ClayForm, {ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useCallback, useState} from 'react';

import parseFile from '../FileParsers';
import {FILE_SCHEMA_EVENT, IMPORT_FILE_FORMATS} from '../constants';

function FileUpload({portletNamespace}) {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();

	const onFileChange = useCallback(
		(event) => {
			const {files} = event.target;
			if (files?.length === 0) {
				return Liferay.fire(FILE_SCHEMA_EVENT, {
					schema: null,
				});
			}

			const onComplete = (schema) => {
				Liferay.fire(FILE_SCHEMA_EVENT, {
					schema,
				});
			};

			const onError = () => {
				if (isMounted()) {
					setErrorMessage(Liferay.Language.get('unexpected-error'));
				}
			};

			return parseFile({
				file: files[0],
				onComplete,
				onError,
			});
		},
		[isMounted]
	);

	const inputNameId = `${portletNamespace}importFile`;

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
			<label htmlFor={inputNameId}>{Liferay.Language.get('file')}</label>

			<ClayInput
				accept={IMPORT_FILE_FORMATS.map((format) => `.${format}`).join(
					', '
				)}
				id={inputNameId}
				name={inputNameId}
				onChange={onFileChange}
				type="file"
			/>

			{errorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errorMessage}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}

export default FileUpload;
