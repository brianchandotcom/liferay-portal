/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {API, Input} from '@liferay/object-js-components-web';
import {fetch, sub} from 'frontend-js-web';
import React, {FormEvent, useEffect, useRef, useState} from 'react';

import {FormDataJSONFormat, jsonToFormData} from '../utils/formData';
import {ModalImportWarning} from './ModalImportWarning';
import {ModalImportObjectDefinitionInfo} from './ViewObjectDefinitions/ViewObjectDefinitions';
interface ModalImportObjectDefinitionProps {
	importObjectDefinitionURL: string;
	modalImportObjectDefinitionInfo?: ModalImportObjectDefinitionInfo;
	nameMaxLength: string;
	objectFolderExternalReferenceCode?: string;
	portletNamespace: string;
	setModalImportObjectDefinitionInfo?: (
		value: React.SetStateAction<ModalImportObjectDefinitionInfo>
	) => void;
}

type TFile = {
	fileName?: string;
	inputFile?: File | null;
};

export default function ModalImportObjectDefinition({
	importObjectDefinitionURL,
	modalImportObjectDefinitionInfo,
	nameMaxLength,
	objectFolderExternalReferenceCode,
	portletNamespace,
	setModalImportObjectDefinitionInfo,
}: ModalImportObjectDefinitionProps) {
	const [error, setError] = useState<string>('');
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		''
	);
	const [importFormData, setImportFormData] = useState<FormData>();
	const [visible, setVisible] = useState(
		modalImportObjectDefinitionInfo?.visible ?? false
	);
	const [warningModalVisible, setWarningModalVisible] = useState(false);
	const inputFileRef = useRef() as React.MutableRefObject<HTMLInputElement>;
	const [name, setName] = useState('');
	const importObjectDefinitionModalComponentId = `${portletNamespace}importObjectDefinitionModal`;
	const importObjectDefinitionFormId = `${portletNamespace}importObjectDefinitionForm`;
	const nameInputId = `${portletNamespace}name`;
	const objectDefinitionJSONInputId = `${portletNamespace}objectDefinitionJSON`;
	const [{fileName, inputFile}, setFile] = useState<TFile>({});

	const warningModalBody: string[] = [
		sub(
			Liferay.Language.get(
				'another-x-has-the-same-external-reference-code'
			),
			Liferay.Language.get('object-definition').toLowerCase()
		),
		sub(
			Liferay.Language.get(
				'before-importing-the-new-x-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
			),
			Liferay.Language.get('object-definition').toLowerCase()
		),
		Liferay.Language.get('do-you-want-to-proceed-with-the-import-process'),
	];

	const {observer, onClose} = useModal({
		onClose: () => {
			if (setModalImportObjectDefinitionInfo) {
				setModalImportObjectDefinitionInfo(
					(previousState: ModalImportObjectDefinitionInfo) => ({
						...previousState,
						visible: false,
					})
				);
			}
			setVisible(false);
			setError('');
			setExternalReferenceCode('');
			setFile({
				fileName: '',
				inputFile: null,
			});
			setName('');
			setImportFormData(undefined);
		},
	});

	const handleImport = async (formData: FormData) => {
		try {
			await API.save({
				item: formData,
				method: 'POST',
				url: importObjectDefinitionURL,
			});

			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const formData = new FormData(event.currentTarget);
		const formDataObject: FormDataJSONFormat = {};
		formData.forEach((value, key) => {
			if (key.includes('objectDefinitionJSON')) {
				formDataObject[key] = inputFile as File;

				return;
			}

			formDataObject[key] = value;

			return;
		});

		if (
			Liferay.FeatureFlags['LPS-148856'] &&
			objectFolderExternalReferenceCode
		) {
			formDataObject[
				`${portletNamespace}objectFolderExternalReferenceCode`
			] = objectFolderExternalReferenceCode;
		}

		const newFormData = jsonToFormData(formDataObject);

		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}`
		);

		if (response.status === 204) {
			handleImport(newFormData);
		}
		else {
			setImportFormData(newFormData);
			setVisible(false);
			setWarningModalVisible(true);
		}
	};

	useEffect(() => {
		Liferay.component(
			importObjectDefinitionModalComponentId,
			{
				open: () => {
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () =>
			Liferay.destroyComponent(importObjectDefinitionModalComponentId);
	}, [importObjectDefinitionModalComponentId, setVisible]);

	return visible ? (
		<ClayModal center observer={observer}>
			<ClayModal.Header>
				{Liferay.FeatureFlags['LPS-148856']
					? modalImportObjectDefinitionInfo?.title
					: sub(
							Liferay.Language.get('import-x'),
							Liferay.Language.get('object-definition')
					  )}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm
					id={importObjectDefinitionFormId}
					onSubmit={handleSubmit}
				>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
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
						<label htmlFor={objectDefinitionJSONInputId}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									disabled
									id={objectDefinitionJSONInputId}
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
							feedbackMessage={Liferay.Language.get(
								'unique-key-for-referencing-the-object-definition'
							)}
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
						name={objectDefinitionJSONInputId}
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
										const objectDefinitionJSON = JSON.parse(
											fileReader.result as string
										) as {externalReferenceCode: string};
										setError('');
										setExternalReferenceCode(
											objectDefinitionJSON.externalReferenceCode
										);
									}
									catch (error) {
										setError(
											Liferay.Language.get(
												'the-structure-failed-to-import'
											)
										);
										setExternalReferenceCode('');
										setFile({
											fileName: '',
											inputFile: null,
										});
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
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!inputFile || !name}
							form={importObjectDefinitionFormId}
							type="submit"
						>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : warningModalVisible ? (
		<ModalImportWarning
			handleImport={() => handleImport(importFormData as FormData)}
			header={Liferay.Language.get('update-existing-object-definition')}
			onClose={(value: boolean) => {
				if (setModalImportObjectDefinitionInfo) {
					setModalImportObjectDefinitionInfo(
						(previousState: ModalImportObjectDefinitionInfo) => ({
							...previousState,
							visible: value,
						})
					);
				}
				setWarningModalVisible(false);
				setImportFormData(undefined);
			}}
			paragraphs={warningModalBody}
		/>
	) : null;
}
