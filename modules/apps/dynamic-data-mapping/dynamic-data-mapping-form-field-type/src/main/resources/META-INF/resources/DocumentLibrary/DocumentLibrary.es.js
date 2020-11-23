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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayProgressBar from '@clayui/progress-bar';
import axios from 'axios';
import {usePage} from 'dynamic-data-mapping-form-renderer';
import {convertToFormData} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';
import {
	ItemSelectorDialog,
	createActionURL,
	createPortletURL,
} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

function getDocumentLibrarySelectorURL({
	folderId,
	groupId,
	itemSelectorAuthToken,
	portletNamespace,
}) {
	const criterionJSON = {
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const uploadCriterionJSON = {
		URL: getUploadURL(),
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const documentLibrarySelectorParameters = {
		'0_json': JSON.stringify(criterionJSON),
		'1_json': JSON.stringify(criterionJSON),
		'2_json': JSON.stringify(uploadCriterionJSON),
		criteria:
			'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
		doAsGroupId: groupId,
		folderId,
		itemSelectedEventName: `${portletNamespace}selectDocumentLibrary`,
		p_p_auth: itemSelectorAuthToken,
		p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
		p_p_mode: 'view',
		p_p_state: 'pop_up',
		refererGroupId: groupId,
	};

	const documentLibrarySelectorURL = createPortletURL(
		themeDisplay.getLayoutRelativeControlPanelURL(),
		documentLibrarySelectorParameters
	);

	return documentLibrarySelectorURL.toString();
}

function getUploadURL() {
	const uploadParameters = {
		cmd: 'add_temp',
		'javax.portlet.action': '/document_library/upload_file_entry',
		p_auth: Liferay.authToken,
		p_p_id: Liferay.PortletKeys.DOCUMENT_LIBRARY,
	};

	const uploadURL = createActionURL(
		themeDisplay.getLayoutRelativeURL(),
		uploadParameters
	);

	return uploadURL.toString();
}

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

	return [fileEntryTitle, fileEntryURL];
}

const DocumentLibrary = ({
	fileEntryTitle = '',
	fileEntryURL = '',
	id,
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
							className="field"
							disabled
							id={`${name}inputFile`}
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
						<ClayInput.GroupItem append shrink>
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'unselect-file'
								)}
								className="clear-button"
								displayType="secondary"
								onClick={onClearButtonClicked}
								symbol="times"
							/>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			)}

			<ClayInput
				id={id}
				name={name}
				placeholder={placeholder}
				type="hidden"
				value={value || ''}
			/>
		</div>
	);
};

const GuestUploadFile = ({
	fileEntryTitle = '',
	fileEntryURL = '',
	id,
	name,
	onClearButtonClicked,
	onUploadSelectButtonClicked,
	placeholder,
	progress,
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
								: '')
						}
						htmlFor={`${name}inputFileGuestUpload`}
					>
						{Liferay.Language.get('select')}
					</label>
					<input
						className="input-file"
						id={`${name}inputFileGuestUpload`}
						onChange={onUploadSelectButtonClicked}
						type="file"
					/>
				</ClayInput.GroupItem>
				{transformedFileEntryTitle && (
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('unselect-file')}
						className="clear-button-upload"
						displayType="secondary"
						onClick={onClearButtonClicked}
						symbol="times"
					/>
				)}
			</ClayInput.Group>

			<ClayInput
				id={id}
				name={name}
				placeholder={placeholder}
				type="hidden"
				value={value || ''}
			/>

			{progress !== 0 && <ClayProgressBar value={progress} />}
		</div>
	);
};

const Main = ({
	allowGuestUsers,
	displayErrors: initialDisplayErrors,
	errorMessage: initialErrorMessage,
	fileEntryTitle,
	fileEntryURL,
	folderId,
	groupId,
	id,
	itemSelectorAuthToken,
	maximumRepetitions,
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	readOnly,
	uploadURL,
	valid: initialValid,
	value = '{}',
	...otherProps
}) => {
	const {portletNamespace} = usePage();
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

		return errorMessages.join(' ');
	};

	const handleVisibleChange = (event) => {
		if (event.selectedItem) {
			onFocus({}, event);
		}
		else {
			onBlur({}, event);
		}
	};

	const handleFieldChanged = (event) => {
		const selectedItem = event.selectedItem;

		if (selectedItem) {
			const {value} = selectedItem;

			setCurrentValue(value);

			onChange(event, value);
		}
	};

	const handleSelectButtonClicked = ({
		itemSelectorAuthToken,
		portletNamespace,
	}) => {
		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			singleSelect: true,
			url: getDocumentLibrarySelectorURL({
				folderId,
				groupId,
				itemSelectorAuthToken,
				portletNamespace,
			}),
		});

		itemSelectorDialog.on('selectedItemChange', handleFieldChanged);
		itemSelectorDialog.on('visibleChange', handleVisibleChange);

		itemSelectorDialog.open();
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

	const handleUploadSelectButtonClicked = (event) => {
		const data = {
			[`${portletNamespace}file`]: event.target.files[0],
		};

		axios
			.post(uploadURL, convertToFormData(data), {
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
					configureErrorMessage(error.message);

					setCurrentValue(null);

					onChange(event, '{}');
				}
				else {
					configureErrorMessage('');

					setCurrentValue(JSON.stringify(file));

					onChange(event, JSON.stringify(file));
				}

				setProgress(0);
			});
	};

	const isSignedIn = Liferay.ThemeDisplay.isSignedIn();

	return (
		<FieldBase
			{...otherProps}
			displayErrors={isSignedIn ? displayErrors : true}
			errorMessage={getErrorMessages(errorMessage, isSignedIn)}
			id={id}
			name={name}
			readOnly={isSignedIn ? readOnly : true}
			valid={isSignedIn ? valid : false}
		>
			{allowGuestUsers && !isSignedIn ? (
				<GuestUploadFile
					fileEntryTitle={fileEntryTitle}
					fileEntryURL={fileEntryURL}
					id={id}
					name={name}
					onClearButtonClicked={(event) => {
						setCurrentValue(null);

						onChange(event, '{}');
					}}
					onUploadSelectButtonClicked={(event) =>
						handleUploadSelectButtonClicked(event)
					}
					placeholder={placeholder}
					progress={progress}
					value={currentValue || ''}
				/>
			) : (
				<DocumentLibrary
					fileEntryTitle={fileEntryTitle}
					fileEntryURL={fileEntryURL}
					id={id}
					name={name}
					onClearButtonClicked={(event) => {
						setCurrentValue(null);

						onChange(event, '{}');
					}}
					onSelectButtonClicked={() =>
						handleSelectButtonClicked({
							itemSelectorAuthToken,
							portletNamespace,
						})
					}
					placeholder={placeholder}
					readOnly={isSignedIn ? readOnly : true}
					value={currentValue || ''}
				/>
			)}
		</FieldBase>
	);
};

Main.displayName = 'DocumentLibrary';

export default Main;
