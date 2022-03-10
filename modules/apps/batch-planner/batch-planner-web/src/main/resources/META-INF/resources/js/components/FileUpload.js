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

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import parseFile from '../FileParsers';
import {
	FILE_EXTENSION_INPUT_PARTIAL_NAME,
	FILE_SCHEMA_EVENT,
	IMPORT_FILE_FORMATS,
} from '../constants';

function updateExtensionInputValue(namespace, value) {
	const externalTypeInput = document.getElementById(
		`${namespace}${FILE_EXTENSION_INPUT_PARTIAL_NAME}`
	);

	if (externalTypeInput) {
		externalTypeInput.value = value.toUpperCase();
	}
}

const acceptedExtensions = IMPORT_FILE_FORMATS.map(
	(format) => `.${format}`
).join(', ');

function FileUpload({portletNamespace}) {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [fileToBeUploaded, setFileToBeUploaded] = useState(null);

	const inputContainsHeadersId = `${portletNamespace}containsHeaders`;
	const inputCsvSeparatorId = `${portletNamespace}csvSeparator`;
	const inputFileId = `${portletNamespace}importFile`;

	const [parserOptions, setParserOptions] = useState({
		csvContainsHeaders: true,
		csvSeparator: ',',
	});

	const fileExtension = fileToBeUploaded
		? fileToBeUploaded.name
				.substring(fileToBeUploaded.name.lastIndexOf('.') + 1)
				.toLowerCase()
		: null;

	useEffect(() => {
		if (!fileToBeUploaded) {
			updateExtensionInputValue(portletNamespace, '');

			Liferay.fire(FILE_SCHEMA_EVENT, {
				firstItemDetails: null,
				schema: null,
			});

			return;
		}

		const onComplete = ({extension, firstItemDetails, schema}) => {
			updateExtensionInputValue(portletNamespace, extension);

			Liferay.fire(FILE_SCHEMA_EVENT, {
				firstItemDetails,
				schema,
			});
		};

		const onError = () => {
			if (isMounted()) {
				setErrorMessage(Liferay.Language.get('unexpected-error'));
			}
		};

		parseFile({
			extension: fileExtension,
			file: fileToBeUploaded,
			onComplete,
			onError,
			options: parserOptions,
		});
	}, [
		fileExtension,
		fileToBeUploaded,
		isMounted,
		parserOptions,
		portletNamespace,
	]);

	return (
		<>
			<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
				<label htmlFor={inputFileId}>{`${Liferay.Language.get(
					'file'
				)} (${acceptedExtensions})`}</label>

				<ClayInput
					accept={acceptedExtensions}
					className="h-auto"
					id={inputFileId}
					name={inputFileId}
					onChange={({target}) => {
						setFileToBeUploaded(
							target.files?.length ? target.files[0] : null
						);
					}}
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

			{fileExtension === 'csv' && (
				<>
					<ClayForm.Group>
						<ClayCheckbox
							checked={parserOptions.csvContainsHeaders}
							label={Liferay.Language.get(
								'this-file-contains-headers'
							)}
							name={inputContainsHeadersId}
							onChange={({target}) =>
								setParserOptions({
									...parserOptions,
									csvContainsHeaders: target.checked,
								})
							}
							value="true"
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={inputCsvSeparatorId}>
							{Liferay.Language.get('csv-separator')}
						</label>

						<ClayInput
							id={inputCsvSeparatorId}
							name={inputCsvSeparatorId}
							onChange={({target}) =>
								setParserOptions({
									...parserOptions,
									csvSeparator: target.value,
								})
							}
							value={parserOptions.csvSeparator}
						/>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}

export default FileUpload;
