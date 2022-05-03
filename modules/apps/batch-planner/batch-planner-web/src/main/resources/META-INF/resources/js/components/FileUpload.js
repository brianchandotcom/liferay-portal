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

import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import parseFile from '../FileParsers';
import {
	CSV_ENCLOSING_CHARACTERS,
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
	const inputDelimiterId = `${portletNamespace}delimiter`;
	const inputEnclosingCharacterId = `${portletNamespace}enclosingCharacter`;
	const inputFileId = `${portletNamespace}importFile`;
	const inputNameId = `${portletNamespace}name`;

	const [parserOptions, setParserOptions] = useState({
		CSVContainsHeaders: true,
		CSVEnclosingCharacter: '',
		CSVSeparator: ',',
	});

	const fileExtension = fileToBeUploaded
		? fileToBeUploaded.name
				.substring(fileToBeUploaded.name.lastIndexOf('.') + 1)
				.toLowerCase()
		: null;

	useEffect(() => {
		if (!fileToBeUploaded || !parserOptions.CSVSeparator) {
			updateExtensionInputValue(portletNamespace, '');

			Liferay.fire(FILE_SCHEMA_EVENT, {
				fileContent: null,
				schema: null,
			});

			return;
		}

		const onComplete = ({extension, fileContent, schema}) => {
			updateExtensionInputValue(portletNamespace, extension);

			Liferay.fire(FILE_SCHEMA_EVENT, {
				fileContent,
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
							checked={parserOptions.CSVContainsHeaders}
							label={Liferay.Language.get(
								'this-file-contains-headers'
							)}
							name={inputContainsHeadersId}
							onChange={({target}) =>
								setParserOptions({
									...parserOptions,
									CSVContainsHeaders: target.checked,
								})
							}
							value="true"
						/>
					</ClayForm.Group>

					<div className="row">
						<div className="col-md-6">
							<ClayForm.Group>
								<label htmlFor={inputDelimiterId}>
									{Liferay.Language.get('csv-separator')}
								</label>

								<ClayInput
									id={inputDelimiterId}
									maxLength={1}
									name={inputDelimiterId}
									onChange={({target}) => {
										setParserOptions({
											...parserOptions,
											CSVSeparator: target.value,
										});
									}}
									value={parserOptions.CSVSeparator}
								/>
							</ClayForm.Group>
						</div>

						<div className="col-md-6">
							<ClayForm.Group>
								<label htmlFor={inputEnclosingCharacterId}>
									{Liferay.Language.get('csv-enclosure')}
								</label>

								<ClaySelect
									id={inputEnclosingCharacterId}
									name={inputEnclosingCharacterId}
									onChange={({target}) =>
										setParserOptions({
											...parserOptions,
											CSVEnclosingCharacter: target.value,
										})
									}
								>
									{CSV_ENCLOSING_CHARACTERS.map(
										(delimiter) => (
											<ClaySelect.Option
												key={delimiter}
												label={delimiter}
												value={delimiter}
											/>
										)
									)}
								</ClaySelect>
							</ClayForm.Group>
						</div>
					</div>
				</>
			)}

			{fileToBeUploaded && (
				<ClayForm.Group>
					<label htmlFor={inputNameId}>
						{Liferay.Language.get('name')}
					</label>

					<ClayInput
						defaultValue={fileToBeUploaded.name.substring(
							0,
							fileToBeUploaded.name.lastIndexOf('.')
						)}
						id={inputNameId}
						name={inputNameId}
					/>
				</ClayForm.Group>
			)}
		</>
	);
}

export default FileUpload;
