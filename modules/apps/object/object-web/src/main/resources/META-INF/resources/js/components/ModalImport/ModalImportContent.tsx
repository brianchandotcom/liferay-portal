/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Input} from '@liferay/object-js-components-web';
import {ErrorDetails} from '@liferay/object-js-components-web/src/main/resources/META-INF/resources/utils/api';
import React, {FormEvent, useRef} from 'react';

import {ModalImportProperties} from '../ViewObjectDefinitions/ViewObjectDefinitions';
import {TFile} from './ModalImport';
import {
	modalImportContentFeedbackMessage,
	modalImportContentTitle,
} from './modalImportLanguageUtil';

interface ModalImportContentProps extends ModalImportProperties {
	error?: ErrorDetails;
	externalReferenceCode: string;
	fileName: string;
	handleOnClose: () => void;
	handleSubmit: (value: FormEvent<HTMLFormElement>) => void;
	inputFile: File;
	modalImportKey: string;
	name: string;
	nameMaxLength: string;
	portletNamespace: string;
	setError: (value?: ErrorDetails) => void;
	setExternalReferenceCode: (value: string) => void;
	setFile: (value: TFile) => void;
	setName: (value: string) => void;
}

export function ModalImportContent({
	JSONInputId,
	error,
	externalReferenceCode,
	fileName,
	handleOnClose,
	handleSubmit,
	inputFile,
	modalImportKey,
	name,
	nameMaxLength,
	portletNamespace,
	setError,
	setExternalReferenceCode,
	setFile,
	setName,
}: ModalImportContentProps) {
	const importFormId = `${portletNamespace}importForm`;
	const inputFileRef = useRef() as React.MutableRefObject<HTMLInputElement>;
	const nameInputId = `${portletNamespace}name`;

	const getImportButtonDisableState = () => {
		if (!inputFile || !name) {
			return true;
		}

		if (error && error?.message !== '') {
			if (
				error?.type?.includes('ObjectDefinitionNameException') ||
				error?.type?.includes('ObjectFolderNameException')
			) {
				return false;
			}

			if (modalImportKey === 'objectFolder') {
				return true;
			}
		}

		return false;
	};

	return (
		<>
			<ClayModal.Header>
				{modalImportContentTitle[modalImportKey]}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm id={importFormId} onSubmit={handleSubmit}>
					{error?.message && (
						<ClayAlert displayType="danger">
							{error.message}
						</ClayAlert>
					)}

					<ClayAlert
						displayType="info"
						title={`${Liferay.Language.get('info')}:`}
					>
						{Liferay.Language.get(
							'the-import-process-will-run-in-the-background-and-may-take-a-few-minutes'
						)}
					</ClayAlert>

					<ClayForm.Group>
						<label htmlFor={nameInputId}>
							{Liferay.Language.get('name')}
						</label>

						<ClayInput
							id={nameInputId}
							maxLength={Number(nameMaxLength)}
							name={nameInputId}
							onChange={(event) => setName(event.target.value)}
							type="text"
							value={name}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={`${portletNamespace}${JSONInputId}`}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									disabled
									id={`${portletNamespace}${JSONInputId}`}
									type="text"
									value={fileName}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<ClayButton
									displayType="secondary"
									onClick={() => inputFileRef.current.click()}
								>
									{Liferay.Language.get('select')}
								</ClayButton>
							</ClayInput.GroupItem>

							{inputFile && (
								<ClayInput.GroupItem shrink>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setExternalReferenceCode('');
											setFile({
												fileName: '',
												inputFile: null,
											});
											inputFileRef.current.value = '';
										}}
									>
										{Liferay.Language.get('clear')}
									</ClayButton>
								</ClayInput.GroupItem>
							)}
						</ClayInput.Group>
					</ClayForm.Group>

					{externalReferenceCode && (
						<Input
							disabled
							feedbackMessage={
								modalImportContentFeedbackMessage[
									modalImportKey
								]
							}
							id="externalReferenceCode"
							label={Liferay.Language.get(
								'external-reference-code'
							)}
							name="externalReferenceCode"
							value={externalReferenceCode}
						/>
					)}

					<input
						className="d-none"
						name={`${portletNamespace}${JSONInputId}`}
						onChange={({target}) => {
							const inputFile = target.files?.item(0);

							if (inputFile) {
								setFile({
									fileName: inputFile?.name,
									inputFile,
								});

								const fileReader = new FileReader();

								fileReader.readAsText(inputFile);

								fileReader.onload = () => {
									try {
										const JSONFile = JSON.parse(
											fileReader.result as string
										) as {externalReferenceCode: string};
										setError(undefined);
										setExternalReferenceCode(
											JSONFile.externalReferenceCode
										);
									}
									catch (error) {
										setError({
											message: Liferay.Language.get(
												'the-structure-failed-to-import'
											),
											name: '',
										});
										setExternalReferenceCode('');
										setFile({
											fileName: '',
											inputFile: null,
										});
										inputFileRef.current.value = '';
									}
								};
							}
						}}
						ref={inputFileRef}
						type="file"
					/>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={handleOnClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={getImportButtonDisableState()}
							form={importFormId}
							type="submit"
						>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
