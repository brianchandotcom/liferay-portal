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

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayProgressBar from '@clayui/progress-bar';
import axios from 'axios';
import {
	PagesVisitor,
	convertToFormData,
	useConfig,
	useFormState,
} from 'data-engine-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const CardItem = ({fileEntryTitle, fileEntryURL}) => {
	return (
		<ClayCard horizontal>
			<ClayCard.Body>
				<div className="card-col-content card-col-gutters">
					<h4 className="text-truncate" title={fileEntryTitle}>
						{fileEntryTitle}
					</h4>
				</div>

				<div className="card-col-field">
					<a download={fileEntryTitle} href={fileEntryURL}>
						<ClayIcon symbol="download" />
					</a>
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

const getValue = (value) => {
	if (!value) {
		return '';
	}

	if (typeof value === 'string') {
		return value;
	}

	return JSON.stringify(value);
};

function transformFileEntryProperties({fileEntryTitle, fileEntryURL, value}) {
	if (value && typeof value === 'string') {
		try {
			const fileEntry = JSON.parse(value);

			fileEntryTitle = fileEntry.title;

			if (fileEntry.url) {
				fileEntryURL = fileEntry.url;
			}
		}
		catch (e) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return value ? [fileEntryTitle, fileEntryURL] : [];
}

const DocumentLibrary = ({
	editingLanguageId,
	fileEntryTitle = '',
	fileEntryURL = '',
	id,
	message,
	name,
	onClearButtonClicked,
	onSelectButtonClicked,
	placeholder,
	readOnly,
	value,
}) => {
	const [transformedFileEntryTitle, transformedFileEntryURL] = useMemo(
		() =>
			transformFileEntryProperties({
				fileEntryTitle,
				fileEntryURL,
				value,
			}),
		[fileEntryTitle, fileEntryURL, value]
	);

	return (
		<div className="liferay-ddm-form-field-document-library">
			{transformedFileEntryURL && readOnly ? (
				<CardItem
					fileEntryTitle={transformedFileEntryTitle}
					fileEntryURL={transformedFileEntryURL}
				/>
			) : (
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('file')}
							className="bg-light field"
							dir={Liferay.Language.direction[editingLanguageId]}
							disabled={readOnly}
							id={`${name}inputFile`}
							lang={editingLanguageId}
							onClick={onSelectButtonClicked}
							value={transformedFileEntryTitle || ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							className="select-button"
							disabled={readOnly}
							displayType="secondary"
							onClick={onSelectButtonClicked}
						>
							<span className="lfr-btn-label">
								{Liferay.Language.get('select')}
							</span>
						</ClayButton>
					</ClayInput.GroupItem>

					{transformedFileEntryTitle && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								aria-label={Liferay.Language.get(
									'unselect-file'
								)}
								displayType="secondary"
								onClick={onClearButtonClicked}
								type="button"
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			)}

			<ClayInput
				id={id}
				name={name}
				placeholder={placeholder}
				type="hidden"
				value={getValue(value)}
			/>

			{message && <div className="form-feedback-item">{message}</div>}
		</div>
	);
};

const GuestUploadFile = ({
	fileEntryTitle = '',
	fileEntryURL = '',
	id,
	message,
	name,
	onClearButtonClicked,
	onUploadSelectButtonClicked,
	placeholder,
	progress,
	readOnly,
	value,
}) => {
	const [transformedFileEntryTitle] = useMemo(
		() =>
			transformFileEntryProperties({
				fileEntryTitle,
				fileEntryURL,
				value,
			}),
		[fileEntryTitle, fileEntryURL, value]
	);

	return (
		<div className="liferay-ddm-form-field-document-library">
			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						className="bg-light"
						disabled={readOnly}
						onClick={onUploadSelectButtonClicked}
						type="text"
						value={transformedFileEntryTitle || ''}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append shrink>
					<label
						className={
							'btn btn-secondary select-button' +
							(transformedFileEntryTitle
								? ' clear-button-upload-on'
								: '') +
							(readOnly ? ' disabled' : '')
						}
						htmlFor={`${name}inputFileGuestUpload`}
					>
						{Liferay.Language.get('select')}
					</label>
					<input
						className="input-file"
						disabled={readOnly}
						id={`${name}inputFileGuestUpload`}
						onChange={onUploadSelectButtonClicked}
						type="file"
					/>
				</ClayInput.GroupItem>
				{transformedFileEntryTitle && (
					<ClayInput.GroupItem shrink>
						<ClayButton
							aria-label={Liferay.Language.get('unselect-file')}
							displayType="secondary"
							onClick={onClearButtonClicked}
							type="button"
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>

			<ClayInput
				id={id}
				name={name}
				placeholder={placeholder}
				type="hidden"
				value={getValue(value)}
			/>

			{progress !== 0 && <ClayProgressBar value={progress} />}

			{message && <div className="form-feedback-item">{message}</div>}
		</div>
	);
};

const Main = ({
	_onBlur,
	_onFocus,
	allowGuestUsers,
	displayErrors: initialDisplayErrors,
	editingLanguageId,
	errorMessage: initialErrorMessage,
	fieldName,
	fileEntryTitle,
	fileEntryURL,
	guestUploadURL,
	id,
	itemSelectorURL,
	maximumRepetitions,
	maximumSubmissionLimitReached,
	message,
	name,
	onChange,
	placeholder,
	readOnly,
	valid: initialValid,
	value = '{}',
	...otherProps
}) => {
	const {portletNamespace} = useConfig();
	const {pages} = useFormState();

	const [currentValue, setCurrentValue] = useState(value);
	const [errorMessage, setErrorMessage] = useState(initialErrorMessage);
	const [displayErrors, setDisplayErrors] = useState(initialDisplayErrors);
	const [valid, setValid] = useState(initialValid);
	const [progress, setProgress] = useState(0);

	const getErrorMessages = (errorMessage, isSignedIn) => {
		const errorMessages = [errorMessage];

		if (!isSignedIn && !allowGuestUsers) {
			errorMessages.push(
				Liferay.Language.get(
					'you-need-to-be-signed-in-to-edit-this-field'
				)
			);
		}
		else if (maximumSubmissionLimitReached) {
			errorMessages.push(
				Liferay.Language.get(
					'the-maximum-number-of-submissions-allowed-for-this-form-has-been-reached'
				)
			);
		}

		return errorMessages.join(' ');
	};

	const isSignedIn = Liferay.ThemeDisplay.isSignedIn();

	useEffect(() => {
		setCurrentValue(value);
		setDisplayErrors(initialDisplayErrors);
		setErrorMessage(getErrorMessages(initialErrorMessage, isSignedIn));
		setValid(initialValid);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [initialDisplayErrors, initialErrorMessage, initialValid, value]);

	const checkMaximumRepetitions = () => {
		const visitor = new PagesVisitor(pages);

		let repetitionsCounter = 0;

		visitor.mapFields(
			(field) => {
				if (fieldName === field.fieldName) {
					repetitionsCounter++;
				}
			},
			true,
			true
		);

		return repetitionsCounter === maximumRepetitions;
	};

	const handleFieldChanged = (selectedItem) => {
		if (selectedItem?.value) {
			setCurrentValue(selectedItem.value);

			onChange(selectedItem, selectedItem.value);
		}
	};

	const handleSelectButtonClicked = ({portletNamespace}) => {
		Liferay.Util.openSelectionModal({
			onSelect: handleFieldChanged,
			selectEventName: `${portletNamespace}selectDocumentLibrary`,
			url: itemSelectorURL,
		});
	};

	const configureErrorMessage = (message) => {
		setErrorMessage(message);

		const enable = message ? true : false;

		setDisplayErrors(enable);
		setValid(!enable);
	};

	const disableSubmitButton = (disable = true) => {
		document.getElementById('ddm-form-submit').disabled = disable;
	};

	const handleGuestUploadFileChanged = (errorMessage, event, value) => {
		configureErrorMessage(errorMessage);

		setCurrentValue(value);

		onChange(event, value ? value : '{}');
	};

	const isExceededUploadRequestSizeLimit = (fileSize) => {
		const uploadRequestSizeLimit =
			Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE;

		if (fileSize <= uploadRequestSizeLimit) {
			return false;
		}

		const errorMessage = Liferay.Util.sub(
			Liferay.Language.get(
				'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
			),
			[Liferay.Util.formatStorage(uploadRequestSizeLimit)]
		);

		handleGuestUploadFileChanged(errorMessage, {}, null);

		return true;
	};

	const handleUploadSelectButtonClicked = (event) => {
		const file = event.target.files[0];

		if (isExceededUploadRequestSizeLimit(file.size)) {
			return;
		}

		const data = {
			[`${portletNamespace}file`]: file,
		};

		axios
			.post(guestUploadURL, convertToFormData(data), {
				onUploadProgress: (event) => {
					const progress = Math.round(
						(event.loaded * 100) / event.total
					);

					setCurrentValue(null);

					setProgress(progress);

					disableSubmitButton();
				},
			})
			.then((response) => {
				const {error, file} = response.data;

				disableSubmitButton(false);

				if (error) {
					handleGuestUploadFileChanged(error.message, event, null);
				}
				else {
					handleGuestUploadFileChanged(
						'',
						event,
						JSON.stringify(file)
					);
				}

				setProgress(0);
			})
			.catch(() => {
				disableSubmitButton(false);

				setProgress(0);
			});
	};

	const hasCustomError =
		(!isSignedIn && !allowGuestUsers) || maximumSubmissionLimitReached;

	return (
		<FieldBase
			{...otherProps}
			displayErrors={hasCustomError ? true : displayErrors}
			errorMessage={errorMessage}
			id={id}
			name={name}
			overMaximumRepetitionsLimit={
				maximumRepetitions > 0 ? checkMaximumRepetitions() : false
			}
			readOnly={hasCustomError ? true : readOnly}
			valid={hasCustomError ? false : valid}
		>
			{allowGuestUsers && !isSignedIn ? (
				<GuestUploadFile
					fileEntryTitle={fileEntryTitle}
					fileEntryURL={fileEntryURL}
					id={id}
					message={message}
					name={name}
					onClearButtonClicked={(event) => {
						setCurrentValue(null);

						onChange(event, '{}');

						const guestUploadInput = document.getElementById(
							`${name}inputFileGuestUpload`
						);

						if (guestUploadInput) {
							guestUploadInput.value = '';
						}
					}}
					onUploadSelectButtonClicked={(event) =>
						handleUploadSelectButtonClicked(event)
					}
					placeholder={placeholder}
					progress={progress}
					readOnly={hasCustomError ? true : readOnly}
					value={currentValue || ''}
				/>
			) : (
				<DocumentLibrary
					editingLanguageId={editingLanguageId}
					fileEntryTitle={fileEntryTitle}
					fileEntryURL={fileEntryURL}
					id={id}
					message={message}
					name={name}
					onClearButtonClicked={(event) => {
						setCurrentValue(null);

						onChange(event, '{}');
					}}
					onSelectButtonClicked={() =>
						handleSelectButtonClicked({
							itemSelectorURL,
							portletNamespace,
						})
					}
					placeholder={placeholder}
					readOnly={hasCustomError ? true : readOnly}
					value={currentValue || ''}
				/>
			)}
		</FieldBase>
	);
};

Main.displayName = 'DocumentLibrary';

export default Main;
