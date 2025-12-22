/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import {displayErrorToast} from '../../common/utils/toastUtil';

type FileFormat = {
	displayName: string;
	mimeType: string;
};

type Locale = {
	displayName: string;
	languageId: string;
};

const ExportFileFormats = ({
	availableExportFileFormats,
	exportMimeType,
	setExportMimeType,
}: {
	availableExportFileFormats: FileFormat[];
	exportMimeType: string;
	setExportMimeType: (mimeType: string) => void;
}) => {
	if (availableExportFileFormats.length === 1) {
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
				id="exportMimeType"
				name="exportMimeType"
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
	setSourceLanguageId,
	sourceLanguageId,
}: {
	availableSourceLocales: Locale[];
	setSourceLanguageId: (languageId: string) => void;
	sourceLanguageId: string;
}) => {
	if (availableSourceLocales.length === 1) {
		return (
			<ClayInput
				id="sourceLanguageId"
				readOnly
				value={availableSourceLocales[0].displayName}
			/>
		);
	}
	else {
		return (
			<ClaySelect
				id="sourceLanguageId"
				name="sourceLanguageId"
				onChange={(event) => {
					setSourceLanguageId(event.currentTarget.value);
				}}
				value={sourceLanguageId}
			>
				{availableSourceLocales.map((locale: Locale) => (
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
}: {
	locale: Locale;
	onChangeTargetLanguage: (checked: boolean, languageId: string) => void;
	selectedTargetLanguageIds: string[];
	sourceLanguageId: string;
}) => {
	const languageId = locale.languageId;
	const checked = selectedTargetLanguageIds.indexOf(languageId) !== -1;

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

export default function ExportTranslationModalContent({
	availableExportFileFormats = [],
	availableSourceLocales = [],
	availableTargetLocales = [],
	closeModal,
	defaultSourceLanguageId,
	itemId,
}: {
	availableExportFileFormats: FileFormat[];
	availableSourceLocales: Locale[];
	availableTargetLocales: Locale[];
	closeModal: () => void;
	defaultSourceLanguageId: string;
	itemId: number;
}) {
	const [exportMimeType, setExportMimeType] = useState(
		availableExportFileFormats[0].mimeType
	);
	const [sourceLanguageId, setSourceLanguageId] = useState(
		defaultSourceLanguageId
	);
	const [selectedTargetLanguageIds, setSelectedTargetLanguageIds] = useState<
		string[]
	>([]);

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const version = availableExportFileFormats
			.find((format) => format.mimeType === exportMimeType)
			?.displayName.split(' ')[1] as string;

		const params = new URLSearchParams({
			sourceLanguageId,
			targetLanguageIds: selectedTargetLanguageIds.join(','),
			version,
		});

		return fetch(
			`/o/cms/basic-web-contents/${itemId}/translations?${params}`,
			{
				headers: {
					'Accept': 'application/zip',
					'Accept-Language':
						Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				},
			}
		).then(async (response) => {
			if (!response.ok) {
				displayErrorToast();
			}
			else {
				openToast({
					message: Liferay.Language.get(
						'the-download-will-begin-shortly'
					),
					title: Liferay.Language.get('success'),
					type: 'success',
				});

				const blob = response.blob();
				const blobURL = URL.createObjectURL(await blob);

				const link = document.createElement('a');
				link.href = blobURL;

				link.click();

				URL.revokeObjectURL(blobURL);

				closeModal();
			}
		});
	};

	const onChangeTargetLanguage = (
		checked: boolean,
		selectedLanguageId: string
	) => {
		setSelectedTargetLanguageIds((languageIds) =>
			checked
				? languageIds.concat(selectedLanguageId)
				: languageIds.filter(
						(languageId) => languageId !== selectedLanguageId
					)
		);
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('export-for-translation')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group className="w-50">
					<label
						htmlFor={
							availableExportFileFormats.length > 1
								? 'exportMimeType'
								: undefined
						}
					>
						{Liferay.Language.get('export-file-format')}
					</label>

					<ExportFileFormats
						availableExportFileFormats={availableExportFileFormats}
						exportMimeType={exportMimeType}
						setExportMimeType={setExportMimeType}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="sourceLanguageId">
						{Liferay.Language.get('source-language')}
					</label>

					<SourceLocales
						availableSourceLocales={availableSourceLocales}
						setSourceLanguageId={setSourceLanguageId}
						sourceLanguageId={sourceLanguageId}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label className="mb-2">
						{Liferay.Language.get('translation-languages')}
					</label>

					<ClayLayout.Row>
						{availableTargetLocales.map((locale) => (
							<TargetLocale
								key={locale.languageId}
								locale={locale}
								onChangeTargetLanguage={onChangeTargetLanguage}
								selectedTargetLanguageIds={
									selectedTargetLanguageIds
								}
								sourceLanguageId={sourceLanguageId}
							/>
						))}
					</ClayLayout.Row>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedTargetLanguageIds.length}
							displayType="primary"
							type="submit"
						>
							{Liferay.Language.get('export')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</form>
	);
}
