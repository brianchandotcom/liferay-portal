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
import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import {addParams, createPortletURL} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const ExportFileFormats = ({
	availableExportFileFormats,
	exportMimeType,
	portletNamespace,
	setExportMimeType,
}) => {
	if (availableExportFileFormats.length == 1) {
		return (
			<ClayInput
				readOnly
				value={availableExportFileFormats[0].displayName}
			/>
		);
	}
	else {
		return (
			<ClaySelect
				id={`${portletNamespace}exportMimeType`}
				name={`${portletNamespace}exportMimeType`}
				onChange={(event) => {
					setExportMimeType(event.currentTarget.value);
				}}
				value={exportMimeType}
			>
				{availableExportFileFormats.map((exportFileFormat) => (
					<ClaySelect.Option
						key={exportFileFormat.mimeType}
						label={exportFileFormat.displayName}
						value={exportFileFormat.mimeType}
					/>
				))}
			</ClaySelect>
		);
	}
};

const SourceLocales = ({
	availableSourceLocales,
	portletNamespace,
	setSourceLanguageId,
	sourceLanguageId,
}) => {
	if (availableSourceLocales.length == 1) {
		return (
			<ClayInput readOnly value={availableSourceLocales[0].displayName} />
		);
	}
	else {
		return (
			<ClaySelect
				id={`${portletNamespace}sourceLanguageId`}
				name={`${portletNamespace}sourceLanguageId`}
				onChange={(event) => {
					setSourceLanguageId(event.currentTarget.value);
				}}
				value={sourceLanguageId}
			>
				{availableSourceLocales.map((locale) => (
					<ClaySelect.Option
						key={locale.languageId}
						label={locale.displayName}
						value={locale.languageId}
					/>
				))}
			</ClaySelect>
		);
	}
};

const TargetLocale = ({
	locale,
	onChangeTargetLanguage,
	selectedTargetLanguageIds,
	sourceLanguageId,
}) => {
	const languageId = locale.languageId;
	const checked = selectedTargetLanguageIds.indexOf(languageId) != -1;

	return (
		<ClayLayout.Col className="py-2" md={4}>
			<ClayCheckbox
				checked={checked}
				disabled={languageId === sourceLanguageId}
				label={locale.displayName}
				onChange={() => {
					onChangeTargetLanguage(!checked, languageId);
				}}
			/>
		</ClayLayout.Col>
	);
};

const ExportTranslation = ({
	availableExportFileFormats,
	availableSourceLocales,
	availableTargetLocales,
	defaultSourceLanguageId,
	exportTranslationURL: initialExportTranslationURL,
	portletNamespace,
	redirectURL,
}) => {
	const [exportMimeType, setExportMimeType] = useState(
		availableExportFileFormats[0].mimeType
	);

	const [sourceLanguageId, setSourceLanguageId] = useState(
		defaultSourceLanguageId
	);

	const [selectedTargetLanguageIds, setSelectedTargetLanguageIds] = useState(
		[]
	);

	const exportTranslationURL = addParams(
		'download=true',
		initialExportTranslationURL
	);

	const onChangeTargetLanguage = (checked, selectedLanguageId) => {
		setSelectedTargetLanguageIds((languageIds) =>
			checked
				? languageIds.concat(selectedLanguageId)
				: languageIds.filter(
						(languageId) => languageId != selectedLanguageId
				  )
		);
	};

	return (
		<ClayForm
			className="export-modal-content"
			onSubmit={(event) => {
				event.preventDefault();

				const params = {
					exportMimeType,
					sourceLanguageId,
					targetLanguageIds: selectedTargetLanguageIds.join(','),
				};

				location.href = createPortletURL(exportTranslationURL, params);
			}}
		>
			<ClayForm.Group className="w-50">
				<label
					htmlFor={
						availableExportFileFormats.length > 1
							? `${portletNamespace}exportMimeType`
							: undefined
					}
				>
					{Liferay.Language.get('export-file-format')}
				</label>
				<ExportFileFormats
					availableExportFileFormats={availableExportFileFormats}
					exportMimeType={exportMimeType}
					portletNamespace={portletNamespace}
					setExportMimeType={setExportMimeType}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}sourceLanguageId`}>
					{Liferay.Language.get('original-language')}
				</label>
				<SourceLocales
					availableSourceLocales={availableSourceLocales}
					portletNamespace={portletNamespace}
					setSourceLanguageId={setSourceLanguageId}
					sourceLanguageId={sourceLanguageId}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label className="mb-2">
					{Liferay.Language.get('languages-to-translate-to')}
				</label>
				<ClayLayout.Row>
					{availableTargetLocales.map((locale) => (
						<TargetLocale
							key={locale.languageId}
							locale={locale}
							onChangeTargetLanguage={onChangeTargetLanguage}
							portletNamespace={portletNamespace}
							selectedTargetLanguageIds={
								selectedTargetLanguageIds
							}
							sourceLanguageId={sourceLanguageId}
						/>
					))}
				</ClayLayout.Row>
			</ClayForm.Group>

			<ClayButton.Group spaced>
				<ClayButton
					disabled={selectedTargetLanguageIds.length === 0}
					displayType="primary"
					type="submit"
				>
					{Liferay.Language.get('export')}
				</ClayButton>

				<ClayLink button displayType="secondary" href={redirectURL}>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</ClayButton.Group>
		</ClayForm>
	);
};

ExportTranslation.propTypes = {
	availableExportFileFormats: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			mimeType: PropTypes.string,
		})
	).isRequired,
	availableSourceLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
	availableTargetLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
	defaultSourceLanguageId: PropTypes.string.isRequired,
	keys: PropTypes.array,
};

export default ExportTranslation;
