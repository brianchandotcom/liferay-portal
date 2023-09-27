/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import parseFile from '../FileParsers';
import {
	CSV_ENCLOSING_CHARACTERS,
	CSV_FORMAT,
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

function updateNameInput(namespace, fileName) {
	const nameInput = document.getElementById(`${namespace}name`);

	if (nameInput && !nameInput.value) {
		nameInput.value = fileName.substring(0, fileName.lastIndexOf('.'));
	}
}

function getAcceptedExtensions() {
	let acceptedExtensions = '';
	for (const i in IMPORT_FILE_FORMATS) {
		if (
			IMPORT_FILE_FORMATS[i] === CSV_FORMAT &&
			!Liferay.FeatureFlags['LPS-173135']
		) {
			continue;
		}

		acceptedExtensions += ', .' + IMPORT_FILE_FORMATS[i];
	}

	return acceptedExtensions.substring(1);
}

function FileUpload({portletNamespace}) {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [fileToBeUploaded, setFileToBeUploaded] = useState(null);

	const inputContainsHeadersId = `${portletNamespace}containsHeaders`;
	const inputDelimiterId = `${portletNamespace}delimiter`;
	const inputEnclosingCharacterId = `${portletNamespace}enclosingCharacter`;
	const inputFileId = `${portletNamespace}importFile`;

	const [parserOptions, setParserOptions] = useState({
		CSVContainsHeaders: true,
		CSVEnclosingCharacter: '"',
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

	useEffect(() => {
		if (fileToBeUploaded?.name) {
			updateNameInput(portletNamespace, fileToBeUploaded.name);
		}
	}, [portletNamespace, fileToBeUploaded]);

	return (
		<>
			<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
				<label htmlFor={inputFileId}>{`${Liferay.Language.get(
					'file'
				)} (${getAcceptedExtensions()})`}</label>

				<ClayInput
					accept={getAcceptedExtensions()}
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
		</>
	);
}

export default FileUpload;
