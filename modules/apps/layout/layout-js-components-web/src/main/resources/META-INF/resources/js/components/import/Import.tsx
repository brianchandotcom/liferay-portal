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
import {useId} from 'frontend-js-components-web';
import {fetch, navigate, openToast, sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import isNullOrUndefined from '../../utils/isNullOrUndefined';
import ImportOptionsModal, {OverwriteStrategy} from './ImportOptionsModal';
import ImportResults, {Results, getResultsText} from './ImportResults';

interface Props {
	backURL: string;
	helpLink?: {
		href: string;
		message: string;
	};
	importURL: string;
	portletNamespace: string;
}

const FILE_TEXTS = {
	initial: Liferay.Language.get('no-file-selected'),
	loaded: Liferay.Language.get(
		'the-file-was-loaded.-click-the-import-button-to-import-it'
	),
};

const ZIP_EXTENSION = '.zip';

function Import({backURL, helpLink, importURL, portletNamespace}: Props) {
	const [error, setError] = useState<string | null>(null);
	const [file, setFile] = useState<File | null>(null);
	const [fileName, setFileName] = useState<string | null>(null);
	const [fileText, setFileText] = useState<string>(FILE_TEXTS.initial);
	const [importResults, setImportResults] = useState<Results | null>(null);
	const [importOptionsModalVisible, setImportOptionsModalVisible] = useState<
		boolean
	>(false);

	const fileInputRef = useRef<HTMLInputElement>(null);

	const fileInputId = useId();
	const fileButtonDescriptionId = useId();

	const validateFile = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (!event.target.files || event.target.files?.length === 0) {
			return;
		}

		setFile(event.target.files[0]);

		const fileName: string = event.target.files[0]?.name || '';

		setFileName(fileName);

		const fileExtension = fileName
			.substring(fileName.lastIndexOf('.'))
			.toLowerCase();

		if (fileExtension === ZIP_EXTENSION) {
			setError(null);
			setFileText(FILE_TEXTS.loaded);
		}
		else {
			setError(Liferay.Language.get('only-zip-files-are-allowed'));
			setFileText(FILE_TEXTS.initial);
		}
	};

	const goBack = () => {
		navigate(backURL);
	};

	const importOtherFile = () => {
		setImportResults(null);
		setFileName(null);
		setFileText(FILE_TEXTS.initial);
	};

	const importFile = (overwriteStrategy?: OverwriteStrategy) => {
		const formData = new FormData();

		if (!file) {
			return;
		}

		formData.append(`${portletNamespace}file`, file);

		if (overwriteStrategy) {
			formData.append(`${portletNamespace}importType`, overwriteStrategy);
		}

		fetch(importURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({importResults, valid}) => {
				if (!isNullOrUndefined(valid) && !valid) {
					setImportOptionsModalVisible(true);

					return;
				}

				if (!Object.keys(importResults).length) {
					navigate(backURL);
					openToast({
						message: sub(
							Liferay.Language.get('no-new-items-were-imported'),
							fileName || ''
						),
						type: 'info',
					});
				}

				setImportResults(importResults);
				setFileText(getResultsText(importResults));

				setFile(null);
			})
			.catch(() => {
				openToast({
					message: sub(
						Liferay.Language.get(
							'something-went-wrong-and-the-x-could-not-be-imported'
						),
						fileName || ''
					),
					type: 'danger',
				});
			});
	};

	return (
		<>
			<ClayToolbar light>
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav className="justify-content-sm-end">
						{importResults ? (
							<>
								<ClayToolbar.Item>
									<ClayButton
										displayType="secondary"
										onClick={importOtherFile}
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

			<ClayLayout.ContainerFluid view>
				<span aria-live="assertive" className="sr-only">
					{fileText}
				</span>

				{importResults ? (
					<ImportResults
						fileName={fileName}
						importResults={importResults}
					/>
				) : (
					<ClayLayout.Sheet
						className="c-gap-4 d-flex flex-column"
						size="lg"
					>
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

							{helpLink && (
								<span className="ml-1">
									<ClayLink
										href={helpLink.href}
										target="_blank"
									>
										{helpLink.message}
									</ClayLink>
								</span>
							)}
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
								onChange={validateFile}
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

						{fileName && (
							<p className="c-mb-0 font-weight-semi-bold small">
								{fileName}
							</p>
						)}
					</ClayLayout.Sheet>
				)}
			</ClayLayout.ContainerFluid>

			{importOptionsModalVisible && (
				<ImportOptionsModal
					onCloseModal={() => setImportOptionsModalVisible(false)}
					onImport={importFile}
				/>
			)}
		</>
	);
}

export default Import;
