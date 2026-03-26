/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import classNames from 'classnames';
import {openToast, useId} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ImportOptionsModal, {OverwriteStrategy} from './ImportOptionsModal';
import ImportResults, {Results, getResultsText} from './ImportResults';
import importZipFile from './importZipFile';

const FILE_TEXTS = {
	initial: Liferay.Language.get('no-file-selected'),
	loaded: Liferay.Language.get(
		'the-file-was-loaded.-click-the-import-button-to-import-it'
	),
};

const ZIP_EXTENSION = '.zip';

interface ImportProps {
	backURL: string;
	helpLink?: {href: string; message: string};
	importURL: string;
	portletNamespace: string;
}

export default function Import({
	backURL,
	helpLink,
	importURL,
	portletNamespace,
}: ImportProps) {
	const [error, setError] = useState<string | null>(null);
	const [file, setFile] = useState<File | null>(null);
	const [screenReaderText, setScreenReaderText] = useState<string>(
		FILE_TEXTS.initial
	);
	const [importResults, setImportResults] = useState<Results | null>(null);
	const [importOptionsModalVisible, setImportOptionsModalVisible] =
		useState<boolean>(false);

	const handleImport = useCallback(
		async (overwriteStrategy?: OverwriteStrategy) => {
			try {
				await importZipFile({
					file,
					handleResponse: ({hasConflicts, importResults}, file) => {
						if (hasConflicts) {
							setImportOptionsModalVisible(true);

							return;
						}

						if (!Object.keys(importResults).length) {
							navigate(backURL);
							openToast({
								message: sub(
									Liferay.Language.get(
										'no-new-items-were-imported'
									),
									file.name
								),
								type: 'info',
							});
						}

						setImportResults(importResults);
						setScreenReaderText(getResultsText(importResults));
						setFile(null);
					},
					importURL,
					overwriteStrategy,
					portletNamespace,
				});
			}
			catch (error) {
				if (process.env.NODE_ENV === 'development') {
					console.error('Import failed:', error);
				}
			}
		},
		[backURL, file, importURL, portletNamespace]
	);

	useEffect(() => {
		if (file) {
			const fileName = file.name;
			const fileExtension = fileName
				.substring(fileName.lastIndexOf('.'))
				.toLowerCase();

			if (fileExtension === ZIP_EXTENSION) {
				setError(null);
				setScreenReaderText(FILE_TEXTS.loaded);
			}
			else {
				setError(Liferay.Language.get('only-zip-files-are-allowed'));
				setScreenReaderText(FILE_TEXTS.initial);
			}
		}
		else {
			setError(null);
			setScreenReaderText(FILE_TEXTS.initial);
		}
	}, [file]);

	return (
		<>
			<span aria-live="assertive" className="sr-only">
				{screenReaderText}
			</span>

			<Toolbar
				error={error}
				file={file}
				goBack={() => navigate(backURL)}
				importFile={handleImport}
				importResults={importResults}
				onImportOtherFile={() => {
					setImportResults(null);
					setFile(null);
					setScreenReaderText(FILE_TEXTS.initial);
				}}
			/>

			{!importResults && (
				<FileUpload
					error={error}
					file={file}
					helpLink={helpLink}
					setFile={setFile}
				/>
			)}

			{importOptionsModalVisible && (
				<ImportOptionsModal
					onCloseModal={() => setImportOptionsModalVisible(false)}
					onImport={handleImport}
				/>
			)}

			{importResults && (
				<ClayLayout.ContainerFluid view>
					<ImportResults importResults={importResults} />
				</ClayLayout.ContainerFluid>
			)}
		</>
	);
}

interface ToolbarProps {
	error: string | null;
	file: File | null;
	goBack: () => void;
	importFile: (overwriteStrategy?: OverwriteStrategy) => void;
	importResults: Results | null;
	onImportOtherFile: () => void;
}

function Toolbar({
	error,
	file,
	goBack,
	importFile,
	importResults,
	onImportOtherFile,
}: ToolbarProps) {
	return (
		<ClayToolbar light>
			<ClayLayout.ContainerFluid size={false}>
				<ClayToolbar.Nav className="justify-content-sm-end">
					{importResults ? (
						<>
							<ClayToolbar.Item>
								<ClayButton
									displayType="secondary"
									onClick={onImportOtherFile}
									size="sm"
								>
									{Liferay.Language.get(
										'upload-another-file'
									)}
								</ClayButton>
							</ClayToolbar.Item>
							<ClayToolbar.Item>
								<ClayButton onClick={goBack} size="sm">
									{Liferay.Language.get('done')}
								</ClayButton>
							</ClayToolbar.Item>
						</>
					) : (
						<>
							<ClayToolbar.Item>
								<ClayButton
									displayType="secondary"
									onClick={goBack}
									size="sm"
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayToolbar.Item>
							<ClayToolbar.Item>
								<ClayButton
									disabled={!!error || !file}
									onClick={() => importFile()}
									size="sm"
								>
									{Liferay.Language.get('import')}
								</ClayButton>
							</ClayToolbar.Item>
						</>
					)}
				</ClayToolbar.Nav>
			</ClayLayout.ContainerFluid>
		</ClayToolbar>
	);
}

interface FileUploadProps {
	error: string | null;
	file: File | null;
	helpLink?: {href: string; message: string};
	setFile: React.Dispatch<React.SetStateAction<File | null>>;
}

function FileUpload({error, file, helpLink, setFile}: FileUploadProps) {
	const fileInputRef = useRef<HTMLInputElement>(null);
	const fileButtonDescriptionId = useId();
	const fileInputId = useId();

	const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (!event.target.files || event.target.files?.length === 0) {
			return;
		}

		setFile(event.target.files[0]);
	};

	return (
		<ClayLayout.ContainerFluid view>
			<ClayLayout.Sheet className="c-gap-4 d-flex flex-column" size="lg">
				<h2 className="c-mb-0 text-6">
					{Liferay.Language.get('import-file')}
				</h2>

				<p
					className="c-mb-0 text-secondary"
					id={fileButtonDescriptionId}
				>
					{Liferay.Language.get(
						'select-a-zip-file-containing-one-or-multiple-entries'
					)}

					{helpLink ? (
						<span className="ml-1">
							<ClayLink href={helpLink.href} target="_blank">
								{helpLink.message}
							</ClayLink>
						</span>
					) : null}
				</p>

				<ClayForm.Group
					className={classNames('c-mb-0', {
						'has-error': error,
					})}
				>
					<label htmlFor={fileInputId}>
						{Liferay.Language.get('file-upload')}
					</label>

					<input
						accept={ZIP_EXTENSION}
						hidden
						id={fileInputId}
						onChange={handleFileChange}
						ref={fileInputRef}
						type="file"
					/>

					<ClayButton
						aria-describedby={fileButtonDescriptionId}
						className="d-block"
						displayType="secondary"
						onClick={() => fileInputRef.current?.click()}
						size="sm"
					>
						{file
							? Liferay.Language.get('replace-file')
							: Liferay.Language.get('select-file')}
					</ClayButton>

					{error && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{error}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

				{file ? (
					<p className="c-mb-0 font-weight-semi-bold small">
						{file.name}
					</p>
				) : null}
			</ClayLayout.Sheet>
		</ClayLayout.ContainerFluid>
	);
}
